package com.leo.product.remoteapi;

import com.leo.product.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 生产者远程调用
 *
 * @author LIULU_LEO
 * Date 2020/5/28
 */
//name 为product项目中application.yml配置文件中的application.name;
//path 为product项目中application.yml配置文件中的context.path;
@FeignClient(name = "product",path ="/remote/v1/leo-product/products" )
public interface ProductRemoteService {

    @PostMapping("/get")
    String getProduct(@RequestBody ProductDTO productDTO);

    @PostMapping("/queryTableSum")
    Long queryTableSum(@RequestBody String tableName);
}
