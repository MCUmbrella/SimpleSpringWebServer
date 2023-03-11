package vip.floatationdevice.simplespringwebserver.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vip.floatationdevice.simplespringwebserver.SysStatusUtil;
import vip.floatationdevice.simplespringwebserver.UserManager;

@Controller
public class UserHomePageController
{
    @GetMapping("/home")
    public String action(@RequestParam(name = "username") String userId, @RequestParam(name = "password") String password, Model model)
    {
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
