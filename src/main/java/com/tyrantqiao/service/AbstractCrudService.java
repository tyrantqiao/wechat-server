package com.tyrantqiao.service;

import com.tyrantqiao.entity.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author tyrantqiao [tyrantqiao@gmail.com]
 * @see <a href="https://github.com/tyrantqiao">qiao's github</a>
 */
public abstract class AbstractCrudService<R extends BaseEntity> extends AbstractQueryService<R> {
    private static Logger logger = LoggerFactory.getLogger(AbstractCrudService.class);


}
