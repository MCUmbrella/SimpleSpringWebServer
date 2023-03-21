package vip.floatationdevice.simplespringwebserver.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import vip.floatationdevice.simplespringwebserver.SessionManager;
import vip.floatationdevice.simplespringwebserver.SysStatusUtil;
import vip.floatationdevice.simplespringwebserver.UserManager;

@Controller
public class UserHomePageController
{
    @GetMapping("/home")
    public String action(HttpServletRequest request, HttpServletResponse response, Model model)
    {
        String userId = null;
        for(Cookie c : request.getCookies())
            if(c.getName().equals("ssws-session"))
                userId = SessionManager.getUserId(c.getValue());
        model.addAttribute("USERID", userId)
                .addAttribute("USERNAME", UserManager.getDisplayName(userId))
                .addAttribute("SYSTIME", SysStatusUtil.getTime())
                .addAttribute("SYSMEM", SysStatusUtil.getSysMem())
                .addAttribute("SYSMEM_MAX", SysStatusUtil.getSysMemMax())
                .addAttribute("JVMMEM", SysStatusUtil.getJVMMem())
                .addAttribute("JVMMEM_MAX", SysStatusUtil.getJVMMemMax())
                .addAttribute("SYSCPU", SysStatusUtil.getSysCpu())
                .addAttribute("JVMCPU", SysStatusUtil.getJVMCpu());
        return "home";
    }
}
