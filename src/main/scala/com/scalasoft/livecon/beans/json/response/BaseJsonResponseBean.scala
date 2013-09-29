/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.beans.json.response

import scala.beans.BeanProperty

/**
 * Represents a base JSON response bean.
 */
class BaseJsonResponseBean {
  @BeanProperty var hadErrors: Boolean = _
  @BeanProperty var errors: java.util.List[String] = new java.util.ArrayList[String]
}
