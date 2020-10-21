package com.leo.product.service.remote;

import com.leo.cousumer.dto.AuthorDTO;
import com.leo.product.controller.ProductController;
import com.leo.product.dto.ProductDTO;
import com.leo.product.remoteapi.ProductRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 生产者远程调用实现
 *
 * @author LIULU_LEO
 * Date 2020/5/28
 */
@RestController
@RequestMapping("/remote/v1/leo-product/products")
public class ProductRemoteServiceImpl implements ProductRemoteService {

    @Autowired
    private ProductController productController;

    @Override
    @PostMapping("/get")
    public String getProduct(@RequestBody ProductDTO productDTO) {
        System.out.println("远程调用成功===>"+productDTO);

        return productController.getProduct();
    }
}
