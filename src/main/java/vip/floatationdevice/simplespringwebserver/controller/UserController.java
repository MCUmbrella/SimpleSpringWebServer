package vip.floatationdevice.simplespringwebserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.addAttribute("TITLE", "SimpleSpringWebServer");
        response.setStatus(400);
        if(SessionManager.hasSession(sessionId) && UserManager.hasUser(userId))
        { // valid session
            if(!displayName.isBlank()) // valid display name
            {
                UserManager.setDisplayName(userId, displayName);
                l.info("User display name changed: user=" + userId + ", name=" + displayName);
                response.setStatus(200);
                model.addAttribute("HEADING", "SUCCESS");
                model.addAttribute("DETAILS", "Your display name has been changed to: " + displayName);
            }
            else
            {
                model.addAttribute("HEADING", "FAILED");
                model.addAttribute("DETAILS", "New display name is empty");
            }
        }
        else
        {
            model.addAttribute("HEADING", "FAILED");
            model.addAttribute("DETAILS", "Invalid session");
        }
        return "msg";
    }

    @RequestMapping("/chpw") // change password
    public String chpwAction(HttpServletRequest request, HttpServletResponse response, @RequestParam(name = "oldpass") String oldPass, @RequestParam(name = "newpass") String newPass, Model model)
    {
        String sessionId = SessionManager.getSessionId(request.getCookies());
        String userId = SessionManager.getUserId(sessionId);
        model.addAttribute("TITLE", "SimpleSpringWebServer");
        response.setStatus(400);
        if(SessionManager.hasSession(sessionId) && UserManager.hasUser(userId))
        { // valid session
            if(!newPass.isBlank()) // valid new password
            {
                if(UserManager.verify(userId, oldPass)) // valid old password
                {
                    UserManager.putUser(userId, newPass, UserManager.getDisplayName(userId));
                    l.info("User changed password: user=" + userId + ", password=" + newPass);
                    response.setStatus(200);
                    model.addAttribute("HEADING", "SUCCESS");
                    model.addAttribute("DETAILS", "Your password has been changed");
                }
                else
                {
                    model.addAttribute("HEADING", "FAILED");
                    model.addAttribute("DETAILS", "Incorrect old password");
                }
            }
            else
            {
                model.addAttribute("HEADING", "FAILED");
                model.addAttribute("DETAILS", "New password is empty");
            }
        }
        else
        {
            model.addAttribute("HEADING", "FAILED");
            model.addAttribute("DETAILS", "Invalid session");
        }
        return "msg";
    }
}
