/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.beans.json.response.searchgames

import scala.beans.BeanProperty
import java.util.Date

/**
 * Represents game resp data.
 */
class GameResp {
  @BeanProperty var startDate: Date = _
  @BeanProperty var endDate: Date = _
  @BeanProperty var dcrec: String = _
  @BeanProperty var serverData: ServerResp = _
  @BeanProperty var playerData: java.util.List[PlayerResp] = _
}
