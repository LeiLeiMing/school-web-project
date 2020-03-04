package com.secondhand.getcros;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * 解决跨域
 * Created by LeiMing on 2020/2/6 13:33
 */
@Configuration
public class GetWayCros {
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        /*写上*就说明所有方法可以跨域访问，但不携带cookie*/
        corsConfiguration.addAllowedOrigin("*");
        /*允许开启跨域*/
        corsConfiguration.setAllowCredentials(true);
        /*允许所有方法进行跨域*/
        corsConfiguration.addAllowedMethod("*");
        /*默认就写“*”*/
        corsConfiguration.addAllowedHeader("*");

        /*初始化cors对象*/
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        /*将改写后的配置写入到请求中带到服务器*/
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
}
