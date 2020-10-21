package com.leo.cousumer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leo.cousumer.entity.Author;

/**
 * 作者服务层接口
 *
 * @author LIULU_LEO
 * Date 2020/6/9
 */
public interface AuthorService extends IService<Author> {

    Boolean saveAuthor();
    Boolean saveAuthor1() throws Exception;
}
