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
import vip.floatationdevice.simplespringwebserver.UserManager;

@Component
public class LoginLogoutInterceptor implements HandlerInterceptor
{
    private final static Logger l = LoggerFactory.getLogger(LoginLogoutInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        String sessionId = SessionManager.getSessionId(request.getCookies());
        String userId = request.getParameter("username");
        String password = request.getParameter("password");
        // login
        if("/l".equals(request.getRequestURI()))
        {
            if(!UserManager.verify(userId, password))
            {
                l.warn("Login fail: user=" + userId + ", password=" + password);
                response.sendError(403);
                return false;
            }
            l.info("Login success: user=" + userId + ", password=" + password);
            // already logged in?
            if(SessionManager.hasSession(sessionId) && UserManager.hasUser(SessionManager.getUserId(sessionId)))
            {
                response.sendRedirect("/home");
                return false;
            }
            // fresh login?
            response.addCookie(new Cookie("ssws-session", SessionManager.generateSession(userId)));
            response.sendRedirect("/home");
            return false;
        }
        // logout
        if("/logout".equals(request.getRequestURI()))
        {
            if(SessionManager.hasSession(sessionId))
            {
                l.info(SessionManager.getUserId(sessionId) + " logout");
                SessionManager.destroySession(sessionId);
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
