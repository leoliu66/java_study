package com.leo.cousumer.util;

import com.leo.cousumer.entity.Product;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

/**
 * bean配置
 *
 * @author LIULU_LEO
 * Date 2020/5/29
 */
@Configurable
public class BeanConfig {

    @Bean(name="product")
    public Product generateDemo() {
        Product demo = new Product();
        return demo;
    }
}
