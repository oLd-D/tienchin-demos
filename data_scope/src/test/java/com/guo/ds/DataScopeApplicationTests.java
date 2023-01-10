package com.guo.ds;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

@SpringBootTest
class DataScopeApplicationTests {

    @Test
    void contextLoads() {
        FastAutoGenerator.create("jdbc:mysql:///test06?serverTimezone=Asia/Shanghai&useSSL=false", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("guo") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D:\\Desktop\\data_scope\\src\\main\\java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.guo.ds") // 设置父包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D:\\Desktop\\data_scope\\src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("sys_dept","sys_role","sys_user") // 设置需要生成的表名
                            .addTablePrefix("sys_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

    }

}
