package vip.floatationdevice.simplespringwebserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class E500Controller
{
    @GetMapping("/500")
    public String action()
    {
        throw new RuntimeException("人哪有不疯的？硬撑罢了！忍不了，一拳地把球打爆！妈的，忍不了，一拳把地球打爆！妈的，忍不了，一拳把地球打爆！妈的，忍不了，一拳把地球打爆！妈的忍不了，一拳把地球打爆！妈的，忍不了，一拳把地球打爆！妈的，忍不了，一拳把地球打爆！妈的，忍不了，一拳把地球打爆！");
    }
}
