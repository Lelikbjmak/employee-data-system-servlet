package com.innowise.employeesystem.listener;

import com.innowise.employeesystem.config.HikariDatasource;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        HikariDatasource.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        HikariDatasource.getInstance().close();
    }
}
