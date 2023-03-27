package vip.floatationdevice.simplespringwebserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vip.floatationdevice.simplespringwebserver.SessionManager;
import vip.floatationdevice.simplespringwebserver.UserManager;

@Controller
public class UserController
{
    private final static Logger l = LoggerFactory.getLogger(UserController.class);

    @RequestMapping("/chdn") // change display name
    public String chdnAction(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "displayname") String displayName, Model model)
    {
        String sessionId = SessionManager.getSessionId(request.getCookies());
        String userId = SessionManager.getUserId(sessionId);
        if(SessionManager.hasSession(sessionId) && UserManager.hasUser(userId))
        {
            if(!displayName.isEmpty())
            {
                UserManager.setDisplayName(userId, displayName);
                l.info("User display name changed: user=" + userId + ", name=" + displayName);
                model.addAttribute("NEW_NAME", displayName);
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
            model.addAttribute("REASON", "Invalid session");
            return "chdnFail";
        }
    }

    @GetMapping("/chpw") // change password
    public String chpwAction(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "oldpass") String oldPass, @RequestParam(name = "newpass") String newPass, Model model)
    {
        String sessionId = SessionManager.getSessionId(request.getCookies());
        String userId = SessionManager.getUserId(sessionId);
        if(SessionManager.hasSession(sessionId) && UserManager.hasUser(userId))
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
            model.addAttribute("REASON", "Invalid session");
            return "chpwFail";
        }
    }
}
