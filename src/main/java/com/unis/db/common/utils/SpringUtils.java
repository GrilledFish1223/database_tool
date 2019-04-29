package com.unis.db.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import java.util.Locale;

/**
 * @author xuli
 * @date 2019/3/16
 */
@Component
public class SpringUtils implements ApplicationContextAware, EmbeddedValueResolverAware {

    private static ApplicationContext context ;
    private static StringValueResolver stringValueResolver ;


    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        if (stringValueResolver == null)  {
            SpringUtils.stringValueResolver = resolver;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if (context == null ) {
            SpringUtils.context = applicationContext;
        }
    }

    public static ApplicationContext getContext() {
        return context;
    }

    public static void setContext(ApplicationContext context) {
        SpringUtils.context = context;
    }

    public static Object getBean(String beanName){
        return  getContext().getBean(beanName);
    }

    public static String getMessage(String key){
        return context.getMessage(key, null, Locale.getDefault());
    }

    public static StringValueResolver getStringValueResolver() {
        return stringValueResolver;
    }

    public static void setStringValueResolver(StringValueResolver stringValueResolver) {
        SpringUtils.stringValueResolver = stringValueResolver;
    }

    public static String getProperty(String value) {
        return  stringValueResolver.resolveStringValue(String.format("${%s}",value));
    }
}
