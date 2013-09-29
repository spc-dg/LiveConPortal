/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.datasources.repositories.jpa.entity

import com.scalasoft.livecon.datasources.entities.ServerE
import org.springframework.data.repository.PagingAndSortingRepository

/**
 * JPA Server repository.
 */
trait ServerRepository extends PagingAndSortingRepository[ServerE, java.lang.Long]