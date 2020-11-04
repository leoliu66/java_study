package com.leo.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leo.product.entity.BfsSensitiveDataMigrate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * mapper
 *
 * @author LIULU_LEO
 * Date 2020/6/9
 */
@Repository
public interface CheckOrderMapper extends BaseMapper<BfsSensitiveDataMigrate> {

    Long queryTableSum(@Param("tableName") String tableName);
}
