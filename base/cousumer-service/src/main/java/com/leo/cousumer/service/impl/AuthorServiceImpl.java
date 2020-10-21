package com.leo.cousumer.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leo.cousumer.entity.Author;
import com.leo.cousumer.mapper.AuthorMapper;
import com.leo.cousumer.service.AuthorService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 作者服务层实现
 *
 * @author LIULU_LEO
 * Date 2020/6/9
 */
@Service
public class AuthorServiceImpl extends ServiceImpl<AuthorMapper, Author>
        implements AuthorService {

    @Autowired
    private ApplicationContext applicationContext;

    @Override
    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    //@Transactional(rollbackFor = Exception.class)
    @Transactional
    public Boolean saveAuthor(){
        System.out.println("AuthorServiceImpl.saveAuthor事务名称："
                + TransactionSynchronizationManager.getCurrentTransactionName());
        Author user = new Author();
        user.setNickName("saveAuthor");
        user.setRealName("saveAuthor");
        boolean save = this.save(user);
        //applicationContext.getBean(AuthorServiceImpl.class).saveAuthor1();

        /*TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
            @SneakyThrows
            @Override
            public void afterCommit() {
                Thread.sleep(10000);
                saveAuthor1();
            }

        });*/
        applicationContext.getBean(AuthorServiceImpl.class).testThread();
        System.out.println("=-====================");
        return save;

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public Boolean saveAuthor1() {
        Author user = new Author();
        user.setNickName("saveAuthor1");
        user.setRealName("saveAuthor1");
        boolean save = this.save(user);
        System.out.println("AuthorServiceImpl.saveAuthor1事务名称："
                + TransactionSynchronizationManager.getCurrentTransactionName());
        //throw new Exception();
        return save;

    }

    @Transactional
    public void testThread(){
        System.out.println("AuthorServiceImpl.testThread前面事务名称："
                + TransactionSynchronizationManager.getCurrentTransactionName());
        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("class"+this.getClass());
            System.out.println("AuthorServiceImpl.testThread事务名称："
                    + TransactionSynchronizationManager.getCurrentTransactionName());
            applicationContext.getBean(AuthorServiceImpl.class).saveAuthor1();
        }, 10, 10, TimeUnit.SECONDS);

        System.out.println("AuthorServiceImpl.testThread后面事务名称："
                + TransactionSynchronizationManager.getCurrentTransactionName());
    }
}
