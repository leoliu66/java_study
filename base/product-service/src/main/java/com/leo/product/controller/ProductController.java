package com.leo.product.controller;

import com.leo.product.entity.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 生产者
 *
 * @author LIULU_LEO
 * Date 2020/5/28
 */
@RestController
@RequestMapping(value = "/leo/v1/leo-peoduct/products")
public class ProductController {

    @GetMapping
    public String getProduct() {
        Product product = new Product();
        return product.toString();
    }
}
