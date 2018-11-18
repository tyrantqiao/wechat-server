package com.tyrantqiao.repository.dao.${model};

import org.apache.ibatis.annotations.Mapper;

import com.tyrantqiao.base.dao.BaseMapper;
import com.tyrantqiao.repository.dao.${model}.entity.${className};
@Mapper
public interface ${className}Mapper extends BaseMapper<${className}> {
    
}
