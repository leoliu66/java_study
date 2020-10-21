package com.leo.cousumer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leo.cousumer.entity.Author;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


/**
 * mapper
 *
 * @author LIULU_LEO
 * Date 2020/6/9
 */
@Repository
public interface AuthorMapper extends BaseMapper<Author> {
}
