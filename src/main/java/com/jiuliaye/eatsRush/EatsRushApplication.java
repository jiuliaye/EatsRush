package com.jiuliaye.eatsRush;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j //lombok中提供的注解, 通过slf4j记录日志。
@SpringBootApplication
@ServletComponentScan   //Servlet组件扫描，用于扫描过滤器配置的@WebFilter注解，使过滤器在Application运行时就生效了。同样适用于自动注册@WebServlet , @WebFilter , @WebListener 组件
@EnableTransactionManagement    //开启对事务的支持，主要是为了应对dto的分步保存的数据一致性
@EnableCaching //开启Spring Cache注解方式缓存功能
public class EatsRushApplication {
    public static void main(String[] args) {
        SpringApplication.run(EatsRushApplication.class,args);
        log.info("项目启动成功...");

        //前端资源位于resources/backend和resources/front中，backend为B端资源，front位C端资源。
    }
}
