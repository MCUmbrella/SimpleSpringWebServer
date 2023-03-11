package vip.floatationdevice.simplespringwebserver.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.PrintWriter;
import java.io.StringWriter;

@Controller
public class ErrorPageController implements ErrorController
{
    @RequestMapping("/error")
    public String action(HttpServletRequest request, Model model)
    {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object msg = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object err = request.getAttribute("org.springframework.boot.web.servlet.error.DefaultErrorAttributes.ERROR");
        StringWriter sw = new StringWriter();
        if(err != null) ((Throwable) err).printStackTrace(new PrintWriter(sw));
        model.addAttribute("ERR_CODE", status);
        model.addAttribute("ERR_MSG", msg);
        model.addAttribute("ERR_TRACE", sw.toString());
        if(status != null)
        {
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode == 403)
                return "403";
            else if(statusCode == 404)
                return "404";
            else if(statusCode == 500)
                return "500";
        }
        return "error";
    }
}
