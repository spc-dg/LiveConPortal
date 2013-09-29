/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.beans.json.response.searchgames

import com.scalasoft.livecon.beans.json.response.BaseJsonResponseBean
import scala.beans.BeanProperty

/**
 * Represents search games response bean.
 */
class SearchGamesResponseBean extends BaseJsonResponseBean {
  @BeanProperty var page: Int = _
  @BeanProperty var maxPages: Int = _
  @BeanProperty var games: java.util.List[GameResp] = _
}