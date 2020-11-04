package com.leo.cousumer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leo.cousumer.entity.Author;
import com.leo.cousumer.entity.BfsSensitiveDataMigrate;
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
public interface CheckOrderMapper extends BaseMapper<BfsSensitiveDataMigrate>{
    List<BfsSensitiveDataMigrate> queryMigrateTableList(@Param("tableName") String tableName);

    Long queryTableSum(@Param("tableName") String tableName);

    Long queryTableSumByBvn(@Param("tableName") String tableName, @Param("bvn") String bvn);
}
