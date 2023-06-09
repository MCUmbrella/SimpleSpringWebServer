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
    LoginLogoutInterceptor loginLogoutInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(userHomePageInterceptor);
        registry.addInterceptor(loginLogoutInterceptor);
    }
}
