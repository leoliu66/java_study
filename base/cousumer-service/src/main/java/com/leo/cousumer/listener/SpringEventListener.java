package com.leo.cousumer.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

/**
 * spring事件监听
 *
 * @author LIULU_LEO
 * Date 2020/5/29
 */
@Component
public class SpringEventListener
        implements ApplicationListener<ApplicationEvent> {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(event instanceof ContextClosedEvent){
            System.out.println(event.getClass().getSimpleName()+" 事件已发生！");
        }else if(event instanceof ContextRefreshedEvent){
            System.out.println(event.getClass().getSimpleName()+" 事件已发生！");
        }else if(event instanceof ContextStartedEvent){
            System.out.println(event.getClass().getSimpleName()+" 事件已发生！");
        }else if(event instanceof ContextStoppedEvent){
            System.out.println(event.getClass().getSimpleName()+" 事件已发生！");
        }else{
            System.out.println("有其它事件发生:"+event.getClass().getName());
        }
    }
}
