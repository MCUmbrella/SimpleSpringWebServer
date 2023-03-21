package vip.floatationdevice.simplespringwebserver.interceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import vip.floatationdevice.simplespringwebserver.SessionManager;

@Component
public class LogoutInterceptor implements HandlerInterceptor
{
    private final static Logger l = LoggerFactory.getLogger(LogoutInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if("/logout".equals(request.getRequestURI()))
        {
            if(request.getCookies() != null)
                for(Cookie c : request.getCookies())
                    if(c.getName().equals("ssws-session") && SessionManager.hasSession(c.getValue()))
                    {
                        l.info(SessionManager.getUserId(c.getValue()) + " logout");
                        SessionManager.destroySession(c.getValue());
                    }
            response.sendRedirect("/");
            return false;
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}