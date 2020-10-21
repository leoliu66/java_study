package com.leo.cousumer.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 作者
 *
 * @author LIULU_LEO
 * Date 2020/6/9
 */
@Data
@TableName("t_author")
public class Author {

    private Long id;

    @TableField("real_name")
    private String realName;

    @TableField("nick_name")
    private String nickName;
}
