package com.tyrantqiao.controller;

import com.tyrantqiao.entity.BaseEntity;
import com.tyrantqiao.service.AbstractCrudService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tyrantqiao [tyrantqiao@gmail.com]
 * @see <a href="https://github.com/tyrantqiao">qiao's github</a>
 */
@Slf4j
public abstract class BaseController<R extends BaseEntity> {
    private AbstractCrudService<R> baseService;

    public AbstractCrudService<R> getBaseService() {
        return this.baseService;
    }

//    @RequestMapping("/{id}")
//    @ResponseBody
//    public Result detail(@PathVariable("id") Integer id, HttpServletRequest request) {
//        log.info("{}的信息为{}", request.getRequestURI(), id);
//        return Result.newOk(this.getBaseService().selectByPrimary(id));
//    }
}
