/*
 * Copyright (c) 2016.
 * Igor Avdeev
 */

package com.tsystems.javaschool.logiweb.test;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.net.URL;
import java.net.URLClassLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextHierarchy({
    @ContextConfiguration(locations = { "classpath*:/META-INF/spring/applicationContext.xml" }),
    @ContextConfiguration("/testApplicationContext.xml")
})
@Sql
//@ContextConfiguration("/META-INF/spring/applicationContext.xml")
//@ContextConfiguration("/testApplicationContext.xml")
public class DriverServiceTest {
    private static final Log logger = LogFactory.getLog(DriverServiceTest.class);

   @Test
   public void testHello() {
       System.out.println("Hi");
       logger.debug("Hello from log");
       ClassLoader cl = ClassLoader.getSystemClassLoader();

        URL[] urls = ((URLClassLoader)cl).getURLs();

        for(URL url: urls){
        	System.out.println(url.getFile());
        }
   }
}
