/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.beans.json.response.searchgames

import scala.beans.BeanProperty

/**
 * Represents player resp data.
 */
class ServerResp {
  @BeanProperty var name: String = _
  @BeanProperty var diplo: Boolean = _
  @BeanProperty var archived: Boolean = _
  @BeanProperty var numPlayers: Int = _
}
