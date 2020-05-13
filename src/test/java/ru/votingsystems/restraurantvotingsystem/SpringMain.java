package ru.votingsystems.restraurantvotingsystem;

import org.springframework.context.support.GenericXmlApplicationContext;


public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.load("spring/spring-app.xml", "spring/spring-db.xml");
            appCtx.refresh();
        }
    }
}

