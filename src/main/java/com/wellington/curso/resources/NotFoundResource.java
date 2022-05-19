package com.wellington.curso.resources;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;

@Controller
public class NotFoundResource {

	@ApiIgnore
    @RequestMapping("/404.html")
    public String render404() {
        return "404";
    }
	
	
}
