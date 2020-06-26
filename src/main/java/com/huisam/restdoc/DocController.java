package com.huisam.restdoc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DocController {

    @GetMapping("/docs")
    public String docs() {
        return "docs/api-docs.html";
    }
}
