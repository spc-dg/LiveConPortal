/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.datasources.repositories.jpa.standard


import org.springframework.data.domain.{Page, Pageable}
import org.springframework.data.jpa.repository.Query
import com.scalasoft.livecon.datasources.entities.IngameNameE
import org.springframework.data.repository.PagingAndSortingRepository

/**
 * JPA Ingame name repository.
 */
trait IngameNameRepository extends PagingAndSortingRepository[IngameNameE, java.lang.Long] {

  /**
   * Finds by ingame name's name attribute.
   * @param name the name
   * @param pageable the pagination data
   * @return an IngameNameE instance
   */
  @Query("select distinct i.name from IngameNameE i where i.name like %?1%")
  def findByNameLike(name: String, pageable: Pageable): Page[String]
}