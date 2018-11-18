package com.tyrantqiao.service;

import com.tyrantqiao.dao.BaseMapper;
import com.tyrantqiao.entity.BaseEntity;
import com.tyrantqiao.request.Page;
import com.tyrantqiao.util.ConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author tyrantqiao [tyrantqiao@gmail.com]
 * @see <a href="https://github.com/tyrantqiao">qiao's github</a>
 */
public abstract class AbstractQueryService<R extends BaseEntity> {

	private static Logger logger = LoggerFactory.getLogger(AbstractQueryService.class);

	/**
	 * 获取mapper引用
	 * @return
	 */
	protected abstract BaseMapper<R> getMapper();


}
