package com.leo.product.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName ProductDTO
 * @Description
 * @Author liulu_leo
 * @Date 2020/9/24
 * @Version 1.0
 */
@Data
public class ProductDTO{

    private Long id;

    private String realName;

    private String nickName;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;
}
