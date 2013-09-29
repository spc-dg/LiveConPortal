/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.beans.json.request.searchgames

import com.scalasoft.livecon.beans.json.request.BaseJsonRequestBean
import scala.beans.BeanProperty

/**
 * Represents the search info when searching for players.
 */
class SearchGamesRequestBean extends BaseJsonRequestBean {
  @BeanProperty var page: Int = _
  @BeanProperty var numPerPage: Int = _
  @BeanProperty var servers: Array[String] = _
  @BeanProperty var players: Array[PlayerReq] = _
}
