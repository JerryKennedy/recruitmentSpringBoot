package com.recruiting.pros.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfiguration implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {

        registry.addViewController("/").setViewName("index");
        registry.addViewController("/job").setViewName("job");
        registry.addViewController("/job1").setViewName("job1");
        registry.addViewController("/job2").setViewName("job2");
        registry.addViewController("/job3").setViewName("job3");
        registry.addViewController("/buttons").setViewName("buttons");
        registry.addViewController("/create-user").setViewName("create-user");
        registry.addViewController("/userList").setViewName("userList");
        registry.addViewController("/JobDetails").setViewName("JobDetails");
        registry.addViewController("/pages/table1").setViewName("pages/table1");
        registry.addViewController("/pages/tables").setViewName("pages/tables");
        registry.addViewController("/pages/table2").setViewName("pages/table2");
        registry.addViewController("/pages/table3").setViewName("pages/table3");
        registry.addViewController("/pages/tableJob").setViewName("pages/tableJob");
//        registry.addViewController("/pages/dashboard").setViewName("pages/dashboard");
//        registry.addViewController("/employee/registration").setViewName("registration");


    }
}