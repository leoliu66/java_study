package com.leo.cousumer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * 作者
 *
 * @author LIULU_LEO
 * Date 2020/6/9
 */
@Data
public class AuthorDTO{

    private Long id;

    private String realName;

    private String nickName;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date date;
}
