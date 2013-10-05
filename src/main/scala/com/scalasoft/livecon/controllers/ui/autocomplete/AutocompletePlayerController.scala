/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.controllers.ui.autocomplete

import org.springframework.web.bind.annotation.{RequestParam, ResponseBody, RequestMethod, RequestMapping}
import org.springframework.ui.ModelMap
import org.springframework.stereotype.Controller
import com.scalasoft.livecon.controllers.base.BaseController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort.Direction
import scala.collection.JavaConversions
import scala.collection.mutable.ListBuffer
import com.scalasoft.livecon.datasources.repositories.jpa.standard.IngameNameRepository

/**
 * Controller for autocomplete player name.
 */
@Controller
@RequestMapping(Array("autocomplete_player"))
class AutocompletePlayerController extends BaseController {

  @Autowired
  var ingameNameRepository: IngameNameRepository = _

  /**
   * Default get action.
   *
   * @param modelMap the model
   * @param term the search term
   * @return ingame names
   */
  @RequestMapping(method = Array(RequestMethod.GET))
  @ResponseBody
  def get(modelMap: ModelMap, @RequestParam term: String): java.util.List[String] = {

    // Get first 5 ingame names by the filter
    val pageable = new PageRequest(0, 20, Direction.ASC, "name")
    val result = ingameNameRepository.findByNameLike(term, pageable)

    val contentList = result.getContent
    val nameList = new ListBuffer[String]
    val contListIterator = contentList.iterator()
    while (contListIterator.hasNext) {
      nameList += contListIterator.next()
    }

    JavaConversions.bufferAsJavaList(nameList)
  }
}
