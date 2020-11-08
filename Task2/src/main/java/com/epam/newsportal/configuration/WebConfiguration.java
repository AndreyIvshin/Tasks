package com.epam.newsportal.configuration;

import com.epam.newsportal.util.DateFormatter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import java.util.Locale;

@Configuration
@EnableWebMvc
@EnableAspectJAutoProxy
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan("com.epam.newsportal")
@PropertySource({"classpath:application.properties", "classpath:messages.properties"})
public class WebConfiguration implements WebMvcConfigurer, ApplicationContextAware {

    @Value("${template.mode}") private String mode;
    @Value("${template.cache}") private String cache;
    @Value("${template.prefix}") private String prefix;
    @Value("${template.suffix}") private String suffix;
    @Value("${template.encoding}") private String encoding;
    @Value("${template.messages}") private String source;
    @Value("${locale.default}") private String locale;
    @Value("${locale.parameter}") private String parameter;
    @Value("${template.static.location}") private String location;
    @Value("${template.static.pattern}") private String pattern;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(viewResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(pattern).addResourceLocations(location);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(dateFormatter());
    }

    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(applicationContext);
        templateResolver.setTemplateMode(mode);
        templateResolver.setCacheable(Boolean.parseBoolean(cache));
        templateResolver.setPrefix(prefix);
        templateResolver.setSuffix(suffix);
        return templateResolver;
    }

    @Bean
    public SpringTemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        templateEngine.setTemplateEngineMessageSource(reloadableResourceBundleMessageSource());
        templateEngine.addDialect(springSecurityDialect());
        return templateEngine;
    }

    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setCharacterEncoding(encoding);
        return viewResolver;
    }

    @Bean
    public ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource() {
        ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
        reloadableResourceBundleMessageSource.setBasename(source);
        return reloadableResourceBundleMessageSource;
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

    @Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale(locale));
        localeResolver.setLocaleAttributeName(parameter);
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName(parameter);
        return localeChangeInterceptor;
    }

    @Bean
    public DateFormatter dateFormatter() {
        return new DateFormatter(reloadableResourceBundleMessageSource(), "date.format");
    }
}

