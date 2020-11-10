package com.leo.cousumer.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * spring工具类
 *
 * @author LIULU_LEO
 * Date 2020/5/29
 */
@Component
public class SpringUtil implements ApplicationContextAware, InitializingBean {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        if(SpringUtil.applicationContext == null) {
            SpringUtil.applicationContext = applicationContext;
        }

        /*System.out.println("--- me.shijunjie.util.SpringUtil" + "---");

        System.out.println("========ApplicationContext配置成功," +
                "在普通类可以通过调用SpringUtils.getAppContext()" +
                "获取applicationContext对象,applicationContext="
                +SpringUtil.applicationContext+"========");

        String[] names = applicationContext.getBeanDefinitionNames();
        for(String s : names){
            System.out.println("bean:" + s);
        }*/
    }

    //获取applicationContext
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    //通过name获取 Bean.
    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    //通过class获取Bean.
    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    //通过name,以及Clazz返回指定的Bean
    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean.afterPropertiesSet");
    }
}