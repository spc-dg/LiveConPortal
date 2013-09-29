/*
 * Copyright (c) 2013.
 * LICENSE: GNU General Public License v3 (GPL-3).
 */

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.web.servlet.config.annotation.{WebMvcConfigurerAdapter, EnableWebMvc}

/**
 * Web config class.
 */
@Configuration
@EnableWebMvc
@EnableJpaRepositories
class WebConfiguration extends WebMvcConfigurerAdapter {

}
 