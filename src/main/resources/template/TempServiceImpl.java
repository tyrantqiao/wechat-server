package com.tyrantqiao.${model}.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyrantqiao.dao.BaseMapper;
import com.tyrantqiao.service.AbstractCrudService;
import com.tyrantqiao.repository.dao.${model}.${className}Mapper;
import com.tyrantqiao.repository.dao.${model}.entity.${className};
@Service
public class ${className}Service extends AbstractCrudService<${className}>{
  private static Logger logger = LoggerFactory.getLogger(${className}Service.class);
	@Autowired
	private ${className}Mapper ${lowerName}Mapper;
	
	/*必须保留*/
	@Override
	protected BaseMapper<${className}> getMapper() {
		return ${lowerName}Mapper;
	}
}
