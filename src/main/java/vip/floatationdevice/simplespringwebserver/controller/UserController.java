package vip.floatationdevice.simplespringwebserver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vip.floatationdevice.simplespringwebserver.UserManager;

@Controller
public class UserController
{
    private final static Logger l = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/chdn") // change display name
    public String chdnAction(@RequestParam(name = "username") String userId, @RequestParam(name = "password") String password, @RequestParam(name = "displayname") String displayName, Model model)
    {
        if(UserManager.hasUser(userId))
        {
            if(UserManager.verify(userId, password))
            {
                if(!displayName.isEmpty())
                {
                    UserManager.putUser(userId, password, displayName);
                    l.info("User display name changed: user=" + userId + ", name=" + displayName);
                    return "chdnSuccess";
                }
                else
                {
                    model.addAttribute("REASON", "New display name is empty");
                    return "chdnFail";
                }
            }
            else
            {
                model.addAttribute("REASON", "Incorrect password");
                return "chdnFail";
            }
        }
        else
        {
            model.addAttribute("REASON", "User not exists");
            return "chdnFail";
        }
    }

    @GetMapping("/chpw") // change password
    public String chpwAction(@RequestParam(name = "username") String userId, @RequestParam(name = "oldpass") String oldPass, @RequestParam(name = "newpass") String newPass, Model model)
    {
        if(UserManager.hasUser(userId))
        {
            if(UserManager.verify(userId, oldPass))
            {
                UserManager.putUser(userId, newPass, UserManager.getDisplayName(userId));
                l.info("User changed password: user=" + userId + ", password=" + newPass);
                return "chpwSuccess";
            }
            else
            {
                model.addAttribute("REASON", "Incorrect old password");
                return "chpwFail";
            }
        }
        else
        {
            model.addAttribute("REASON", "User not exists");
            return "chpwFail";
        }
    }
}
