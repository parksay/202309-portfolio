package simple.myboard.myprac.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/test")
public class TestController {

    @GetMapping(value="/hello")
    public String test() {
        System.out.println("############test here ###############");
        return "hello2";
    }
}
