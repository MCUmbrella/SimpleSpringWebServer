package vip.floatationdevice.simplespringwebserver.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer
{
    @Autowired
    UserHomePageInterceptor userHomePageInterceptor;
    @Autowired
    LoginInterceptor loginInterceptor;
    @Autowired
    LogoutInterceptor logoutInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(userHomePageInterceptor);
        registry.addInterceptor(loginInterceptor);
        registry.addInterceptor(logoutInterceptor);
    }
}