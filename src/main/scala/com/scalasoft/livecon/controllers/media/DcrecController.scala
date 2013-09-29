/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.controllers.media

import org.springframework.web.bind.annotation.{RequestParam, ResponseBody, RequestMethod, RequestMapping}
import org.springframework.ui.ModelMap
import org.springframework.stereotype.Controller
import com.scalasoft.livecon.controllers.base.BaseController
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import java.io.{FileInputStream, File}
import org.apache.commons.io.IOUtils
import org.springframework.http.MediaType

/**
 * Controller for getting dcrecs.
 */
@Controller
class DcrecController extends BaseController {

  /**
   * Gets dcrecs.
   *
   * @param dcrecPath path from the (hidden to the public) base dcrec folder
   * @param modelMap the model map
   * @param request the request
   * @param response the response
   * @return the dcrec
   */
  @RequestMapping(value = Array("/dcrec"), method = Array(RequestMethod.POST), produces = Array(MediaType.APPLICATION_OCTET_STREAM_VALUE))
  @ResponseBody
  def getImage(@RequestParam dcrecPath: String, modelMap: ModelMap, request: HttpServletRequest, response: HttpServletResponse): Array[Byte] = {
    val basePathString = "/dcrec/"
    val session = request.getSession
    val servletContext = session.getServletContext

    // Check if the file exists
    val file = new File(servletContext.getRealPath(basePathString + dcrecPath))
    if (file.exists()) {
      IOUtils.toByteArray(new FileInputStream(file))
    } else {

      null
    }
  }
}