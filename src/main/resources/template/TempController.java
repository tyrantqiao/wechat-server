package com.tyrantqiao.${model}.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tyrantqiao.service.AbstractCrudService;
import com.tyrantqiao.web.BaseController;
import com.tyrantqiao.service.${className}Service;
import com.tyrantqiao.repository.dao.${model}.entity.${className};

@RestController
@RequestMapping("/service/${model}/$!{lowerName}")
public class ${className}Controller extends BaseController<${className}>{
	private static Logger logger = LoggerFactory.getLogger(${className}Controller.class);
	@Autowired
	private ${className}Service ${lowerName}Service;
	
	@Override
	public AbstractCrudService<${className}> getBaseService() {
		return ${lowerName}Service;
	}
}
