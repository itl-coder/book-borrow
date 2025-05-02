package com.book.borrow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class BookBorrowApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookBorrowApplication.class, args);

        // 控制台美化输出（固定内容）
        System.out.println();
        System.out.println("==========================================================");
        System.out.println(" 🌐 应用名称: 阜新市图书馆借阅管理系统");
        System.out.println(" 🔗 访问地址: http://localhost:8080");
        System.out.println(" 🌍 外网地址: http://192.168.1.100:8080");
        System.out.println(" 🧱 上下文路径: /");
        System.out.println(" 🛠 运行环境: dev");
        System.out.println(" 📦 版本号: v1.0.0");
        System.out.println("==========================================================");
        System.out.println();
    }
}


