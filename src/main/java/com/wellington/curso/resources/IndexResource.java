package com.wellington.curso.resources;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import springfox.documentation.annotations.ApiIgnore;

@Controller
public class IndexResource {

	@ApiIgnore
    @RequestMapping("/")
    public String index() {
        return "index";
    }
	
}
