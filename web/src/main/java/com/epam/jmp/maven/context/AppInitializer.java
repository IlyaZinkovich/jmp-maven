package com.epam.jmp.maven.context;

import com.epam.jmp.maven.WebAppConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextCleanupListener;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.annotation.PreDestroy;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class AppInitializer implements WebApplicationInitializer {

    private AnnotationConfigWebApplicationContext rootContext = null;

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        rootContext = new AnnotationConfigWebApplicationContext();
        rootContext.register(WebAppConfig.class);

        servletContext.addListener(new ContextLoaderListener(rootContext));
        servletContext.addListener(new ContextCleanupListener());

        ServletRegistration.Dynamic springWebMvc = servletContext.addServlet("DispatcherServlet",
                new DispatcherServlet(rootContext));
        springWebMvc.setLoadOnStartup(1);
        springWebMvc.addMapping("/");
        springWebMvc.setAsyncSupported(true);

    }

    @PreDestroy
    protected final void cleanup() {
        if (rootContext != null) {
            rootContext.close();
        }
    }
}
