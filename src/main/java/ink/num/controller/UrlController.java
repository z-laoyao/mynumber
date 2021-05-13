package ink.num.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Description
 * @Author
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/3/24 15:38:44
 */
@Controller
public class UrlController {
    @GetMapping({"index", "index.html"})
    public String index() {
        return "index";
    }

    @GetMapping({"login", "login.html", "/"})
    public String login() {
        return "login";
    }

    @GetMapping({"/dic/index", "/dic/", "/dic/index.html"})
    public String dicIndex() {
        return "dic/index";
    }

    @GetMapping({"main"})
    public String main() {
        return "main";
    }

    @GetMapping({"table"})
    public String table() {
        return "table";
    }

    @GetMapping({"register"})
    public String register() {
        return "register";
    }

    @GetMapping({"user"})
    public String user() {
        return "user";
    }

}
