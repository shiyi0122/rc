package com.hna.hka.archive.management.system.config;

import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * @program: rc
 * @description:
 * @author: zhaoxianglong
 * @create: 2021-09-17 17:53
 **/
@Configuration
public class TemplateEngineConfig {

//    @Bean
    public TemplateEngine templateEngine(){
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        // 设置模版前缀，相当于需要在资源文件夹中创建一个html2pdfTemplate文件夹，所有的模版都放在这个文件夹中
        resolver.setPrefix("/templates/pdf/");
        // 设置模版后缀
        resolver.setSuffix(".html");
        resolver.setCharacterEncoding("UTF-8");
        // 设置模版模型为HTML
        resolver.setTemplateMode("HTML");
        TemplateEngine engine = new TemplateEngine();
        engine.setTemplateResolver(resolver);
        return engine;
    }
}
