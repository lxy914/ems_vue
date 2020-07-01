package zone.lxy.config;

import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import zone.lxy.realm.CustomRealm;

@Configuration
public class ShiroConfig {
    // 创建web安全管理器
    @Bean
    public DefaultWebSecurityManager getDefaultWebSecurityManager(CustomRealm realm) {
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        // 设置自定义Realm
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;
    }
    // 配置url过滤器
    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        // 静态资源直接访问
        chainDefinition.addPathDefinition("/*.html","anon");
        chainDefinition.addPathDefinition("/css/*","anon");
        chainDefinition.addPathDefinition("/js/*","anon");
        chainDefinition.addPathDefinition("/img/*","anon");
//
//        // 登录注册页直接访问
        chainDefinition.addPathDefinition("/user/*", "anon");
//
//        // 只有登录或者勾选记住密码的用户才能访问
        chainDefinition.addPathDefinition("/**", "authc");

        return chainDefinition;
    }


    // 前后端分离，解决跨域请求过滤器，
    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        // #允许向该服务器提交请求的URI，*表示全部允许，在SpringMVC中，如果设成*，会自动转成当前请求头中的Origin
        config.addAllowedOrigin("*");
        // #允许访问的头信息,*表示全部
        config.addAllowedHeader("*");
        // 允许提交请求的方法，*表示全部允许
        config.addAllowedMethod("*");
        // 预检请求的缓存时间（秒），即在这个时间段里，对于相同的跨域请求不会再预检了
        config.setMaxAge(18000L);
        // 允许cookies跨域
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);

        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        // 设置监听器的优先级，设置最高
        bean.setOrder(0);

        return bean;
    }
}
