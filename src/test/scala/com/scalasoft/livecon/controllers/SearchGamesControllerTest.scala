/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

package com.scalasoft.livecon.controllers

import org.junit.{Before, Test}
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import org.junit.runner.RunWith
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.context.WebApplicationContext
import org.springframework.test.web.servlet.setup.MockMvcBuilders._

/**
 * Test for the hello controller.
 */
@RunWith(classOf[SpringJUnit4ClassRunner])
@WebAppConfiguration
@ContextConfiguration(Array("/mvc-servlet.xml"))
class SearchGamesControllerTest {
  protected var mockMvc: MockMvc = _

  @Autowired
  protected var wac: WebApplicationContext = null

  /**
   * Sets up the context.
   */
  @Before
  def setup() {
    this.mockMvc = webAppContextSetup(this.wac).build
  }

  /**
   * Tests hello get for root.
   */
  @Test
  def indexPageTest() {
    mockMvc.perform(get("/")).andExpect(status.isOk).andExpect(view.name("latest_games"))
  }
}

