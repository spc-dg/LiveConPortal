/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.controllers.media

import org.springframework.web.bind.annotation.{ResponseBody, PathVariable, RequestMethod, RequestMapping}
import org.springframework.ui.ModelMap
import org.springframework.stereotype.Controller
import com.scalasoft.livecon.controllers.base.BaseController
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import java.io.{FileInputStream, File}
import org.apache.commons.io.IOUtils
import org.springframework.http.MediaType
import javax.imageio.ImageIO
import com.scalasoft.scalalib.CoreImplicits._
import com.scalasoft.scalalib.CoreExcCodes
import java.awt.image.BufferedImage

/**
 * Controller for getting territory images.
 */
@Controller
class TerritoryController extends BaseController {


  /**
   * Gets territory images. This path is added as a cache in the mvc-servlet config but just in case it's not cached the
   * controller searches for a an already served image to return if it can.
   *
   * @param imgCode the image code - this will be used for caching
   * @param modelMap the model map
   * @param request the request
   * @param response the response
   * @return the image
   */
  @RequestMapping(value = Array("/territory/{imgCode}"), method = Array(RequestMethod.GET), produces = Array(MediaType.IMAGE_PNG_VALUE))
  @ResponseBody
  def getImage(@PathVariable imgCode: String, modelMap: ModelMap, request: HttpServletRequest, response: HttpServletResponse): Array[Byte] = {
    val baseDirPathString = "terr/base/"
    val serveDirPathString = "terr/serve/"
    val session = request.getSession
    val servletContext = session.getServletContext

    // Check if the file is in the cache already
    val cached = new File(servletContext.getRealPath(serveDirPathString + imgCode + ".png"))
    if (cached.exists()) {
      IOUtils.toByteArray(new FileInputStream(cached))
    } else {

      /**
       * If not we need to create the image.
       * You read the image code. It is like this:
       *
       * {type}_{territory-color}_{territory-color}_......._{territory-color}
       *
       * You take the base image. Then for each territory and color you find the image and
       * fill it with that color then overlay it on top of the base image. Save it in the serve dir for caching then serve it.
       */
      ((imgCode ? "").trim != "") #! CoreExcCodes.OBJ_NULL

      // decode the request data
      val reqData = imgCode.split("_")
      (reqData.length > 2) #! CoreExcCodes.OBJ_NOT_OF_EXPECTED_TYPE

      // define known data (types, territories, teams and the base image)
      val types = Map("1" -> "default", "2" -> "diplo")
      val territories = Map("1" -> "NA", "2" -> "SA", "3" -> "EU", "4" -> "RU", "5" -> "AS", "6" -> "AF")
      val teams = Map("1" ->(255, 0, 0), "2" ->(0, 128, 0), "3" ->(0, 0, 255), "4" ->(255, 255, 0), "5" ->(255, 165, 0), "6" ->(64, 224, 208), "7" ->(255, 255, 255))
      val baseImgFile = new File(servletContext.getRealPath(baseDirPathString + "ALL" + ".png"))
      baseImgFile.exists() #! CoreExcCodes.OBJ_NULL
      var baseImg = ImageIO.read(baseImgFile)

      // is it diplo?
      val gameMode = reqData(0)
      types.contains(gameMode) #! CoreExcCodes.OBJ_NOT_OF_EXPECTED_TYPE
      val diplo = gameMode == "2"

      // parse the rest of the request
      for (item <- reqData.drop(1)) {
        // split the items into territory and team
        val itemData = item.split("-")
        (itemData.length == 2) #! CoreExcCodes.OBJ_NOT_OF_EXPECTED_TYPE

        // get the territory info
        val territory = itemData(0)
        territories.contains(territory) #! CoreExcCodes.OBJ_NOT_OF_EXPECTED_TYPE

        // get the territory specific image
        val terrImg = ImageIO.read(new File(servletContext.getRealPath(baseDirPathString + territories(territory) + ".png"))) ?! CoreExcCodes.OBJ_NULL

        // get it's color and change it
        val color = teams(itemData(1))
        colorImage(terrImg, color)

        // overlay the image
        baseImg = overlayImage(baseImg, terrImg)
      }

      // save the image file for caching purposes
      val imgFile = new File(servletContext.getRealPath(serveDirPathString + imgCode + ".png"))
      ImageIO.write(baseImg, "PNG", imgFile)

      // return the image
      IOUtils.toByteArray(new FileInputStream(imgFile))
    }
  }


  /**
   * Colors an image while keeping alpha.
   *
   * @param image the input image
   * @param colorTuple a tuple with red green and blue
   */
  private def colorImage(image: BufferedImage, colorTuple: (Int, Int, Int)) {
    // get dimensions
    val width = image.getWidth
    val height = image.getHeight

    // get the raster to be able to change color
    val raster = image.getRaster
    for (x <- 0 to width - 1) {
      for (y <- 0 to height - 1) {
        // get pixel
        val pixels = raster.getPixel(x, y, null.asInstanceOf[Array[Int]])

        // change color
        pixels(0) = colorTuple._1
        pixels(1) = colorTuple._2
        pixels(2) = colorTuple._3

        //set it back
        raster.setPixel(x, y, pixels)
      }
    }
  }

  /**
   * Overlays an image on top of another.
   *
   * @param image the source image
   * @param overlay the overlay
   * @return the combined image
   */
  private def overlayImage(image: BufferedImage, overlay: BufferedImage): BufferedImage = {

    // create the combined image
    val combined = new BufferedImage(image.getWidth, image.getHeight, BufferedImage.TYPE_INT_ARGB)

    // paint the source then the overlay
    val graphics = combined.getGraphics
    graphics.drawImage(image, 0, 0, null)
    graphics.drawImage(overlay, 0, 0, null)

    // return the combined imag
    combined
  }
}