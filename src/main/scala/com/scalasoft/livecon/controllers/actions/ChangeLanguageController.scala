/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.controllers.actions

import org.springframework.web.bind.annotation.{PathVariable, RequestMethod, RequestMapping}
import org.springframework.ui.ModelMap
import org.springframework.stereotype.Controller
import com.scalasoft.livecon.controllers.base.BaseController
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}
import org.springframework.web.servlet.support.RequestContextUtils
import org.springframework.beans.propertyeditors.LocaleEditor
import java.util.Locale

/**
 * Controller for changing languages.
 */
@Controller
class ChangeLanguageController extends BaseController {

  /**
   * Changes languages.
   *
   * @param modelMap the model
   * @param lang the language to change to
   * @param request http request
   * @param response http response
   * @return latest_games.jsp
   */
  @RequestMapping(value = Array("/change_language/{lang}"), method = Array(RequestMethod.GET))
  def changeLanguage(@PathVariable lang: String, modelMap: ModelMap, request: HttpServletRequest, response: HttpServletResponse): String = {

    // Get the locale and set the new language
    val localeResolver = RequestContextUtils.getLocaleResolver(request)
    val localeEditor = new LocaleEditor()
    localeEditor.setAsText(lang)
    localeResolver.setLocale(request, response, localeEditor.getValue.asInstanceOf[Locale])

    // Get the referrer and redirect to it
    val referrer = request.getHeader("Referer")
    "redirect:" + referrer
  }
}