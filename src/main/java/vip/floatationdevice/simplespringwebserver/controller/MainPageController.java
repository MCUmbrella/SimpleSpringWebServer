package vip.floatationdevice.simplespringwebserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainPageController
{
    @GetMapping({"/", "/index.html"})
    public String action(Model model)
    {
        return "index";
    }
}
