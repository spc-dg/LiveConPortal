/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.beans.json.response.searchgames

import scala.beans.BeanProperty

/**
 * Represents player resp data.
 */
class PlayerResp {
  @BeanProperty var names: java.util.List[String] = _
  @BeanProperty var territory: String = _
  @BeanProperty var score: Int = _
  @BeanProperty var hadScore: Boolean = _
  @BeanProperty var teams: java.util.List[String] = _
}
