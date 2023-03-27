package vip.floatationdevice.simplespringwebserver.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import vip.floatationdevice.simplespringwebserver.SessionManager;

@Component
public class UserHomePageInterceptor implements HandlerInterceptor
{
    private final static Logger l = LoggerFactory.getLogger(UserHomePageInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        if("/home".equals(request.getRequestURI()))
        {
            if(SessionManager.hasSession(SessionManager.getSessionId(request.getCookies())))
                return true;
            l.warn("Unauthorized access to /home: addr=" + request.getRemoteAddr());
            response.sendRedirect("/login.html");
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
