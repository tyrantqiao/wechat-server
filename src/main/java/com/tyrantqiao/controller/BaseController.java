package com.tyrantqiao.controller;

import com.tyrantqiao.entity.BaseEntity;
import com.tyrantqiao.service.AbstractCrudService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author tyrantqiao [tyrantqiao@gmail.com]
 * @see <a href="https://github.com/tyrantqiao">qiao's github</a>
 */
public abstract class BaseController<R extends BaseEntity> {
	private static Logger logger = LoggerFactory.getLogger(BaseController.class);

	private AbstractCrudService<R> baseService;

	public AbstractCrudService<R> getBaseService() {
		return this.baseService;
	}


}
