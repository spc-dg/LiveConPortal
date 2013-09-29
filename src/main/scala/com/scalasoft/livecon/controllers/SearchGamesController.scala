/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.controllers

import org.springframework.web.bind.annotation.{RequestBody, ResponseBody, RequestMethod, RequestMapping}
import org.springframework.ui.ModelMap
import org.springframework.stereotype.Controller
import com.scalasoft.livecon.controllers.base.BaseController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import com.scalasoft.livecon.datasources.repositories.jpa.entity.GameRepository
import com.scalasoft.livecon.datasources.repositories.jpa.entity.ServerRepository
import com.scalasoft.livecon.beans.json.response.searchgames.SearchGamesResponseBean
import org.springframework.validation.BindingResult
import com.scalasoft.livecon.beans.json.request.searchgames.SearchGamesRequestBean
import com.scalasoft.scalalib.CoreImplicits._
import com.scalasoft.scalalib.CoreExcCodes

/**
 * Controller for search games.
 */
@Controller
class SearchGamesController extends BaseController {

  @Autowired
  var serverRepository: ServerRepository = _

  @Autowired
  var gameRepository: GameRepository = _

  /**
   * Default get action.
   *
   * @param modelMap the model
   * @return search_games.jsp
   */
  @RequestMapping(value = Array("/"), method = Array(RequestMethod.GET))
  def indexPage(modelMap: ModelMap): String = {

    // Get all servers
    val servers = serverRepository.findAll(new Sort(Direction.ASC, "numPlayers"))
    modelMap.put("servers", servers)

    // return the view
    "search_games"
  }

  /**
   * Search games.
   *
   * @param modelMap the model map
   * @param request the request data
   * @param bindingResult the binding result
   * @return the response data
   */
  @RequestMapping(value = Array("/search"), method = Array(RequestMethod.POST))
  @ResponseBody
  def search(modelMap: ModelMap, @RequestBody request: SearchGamesRequestBean, bindingResult: BindingResult): SearchGamesResponseBean = {
    var response = new SearchGamesResponseBean

    try {
      !bindingResult.hasErrors #! CoreExcCodes.OBJ_NOT_OF_EXPECTED_TYPE

      // Get the game list from the bd
      response = gameRepository.findBySearchBean(request)

    }
    catch {
      case e: Exception => {
        response.getErrors.add(e.getMessage ? "UNKNOWN ERROR")
        response.setHadErrors(true)
      }
    }

    response
  }
}