/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.controllers.base

import org.springframework.context.MessageSource
import javax.annotation.Resource

/**
 * Base controller.
 */
class BaseController {
  @Resource(name = "messageSource")
  protected var messageSource: MessageSource = _
}
