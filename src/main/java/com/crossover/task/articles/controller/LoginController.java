package com.crossover.task.articles.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.crossover.task.articles.model.User;

/**
 * @since 11/22/2015
 */
@Controller
@RequestMapping("/login")
public class LoginController {
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);
	
    @RequestMapping
    public String getLoginPage() {
        return "login";
    }
    
    @RequestMapping(value = "/user.json", method = RequestMethod.POST)
    public String login(@RequestBody User u) {
    	log.info("login user " + u .getUser() + " " + u.getPass());
    	return "articles/layout_search";
    }
     
    @RequestMapping(value = "/register.json", method = RequestMethod.POST)
    public String register(@RequestBody User u) {
        return "articles/layout";
    }
}
