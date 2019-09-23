package com.streambuf.subtitle.configuration;

import com.streambuf.subtitle.interceptor.HttpServletFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterRegistrationConfige {
    @Bean
    public FilterRegistrationBean httpServletFilter() {
        FilterRegistrationBean myFilter = new FilterRegistrationBean();
        myFilter.addUrlPatterns("/*");
        myFilter.setFilter(new HttpServletFilter());
        return myFilter;
    }
}
