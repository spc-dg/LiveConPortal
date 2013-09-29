/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.datasources.repositories.jpa.entity

import org.springframework.data.repository.Repository

import com.scalasoft.livecon.beans.json.request.searchgames.SearchGamesRequestBean
import com.scalasoft.livecon.datasources.entities.GameE
import com.scalasoft.livecon.beans.json.response.searchgames.SearchGamesResponseBean

/**
 * Game repository definition.
 */
trait GameRepository extends Repository[GameE, java.lang.Long] {
  /**
   * Finds games by the filter data in the search bean.
   *
   * @param searchBean the search bean
   * @return games
   */
  def findBySearchBean(searchBean: SearchGamesRequestBean): SearchGamesResponseBean
}