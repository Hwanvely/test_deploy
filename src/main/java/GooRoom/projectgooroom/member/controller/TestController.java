package GooRoom.projectgooroom.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {

    @GetMapping("/")
    public String naverTest(){
        return "home";
    }
}
