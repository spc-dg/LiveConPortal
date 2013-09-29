/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.beans.json.request.searchgames

import scala.beans.BeanProperty

/**
 * Represents req player data.
 */
class PlayerReq {
  @BeanProperty var name: String = _
  @BeanProperty var territory: String = _
}
