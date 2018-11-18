package com.tyrantqiao.dao;

import com.tyrantqiao.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author tyrantqiao
 * date: 2018/11/15
 * blog: tyrantqiao.com
 * contact: tyrantqiao@icloud.com
 */
@Mapper
public class UserMapper extends BaseMapper<User> {

    @Override
    public User selectByPrimaryKey(Integer id) {
        return null;
    }

    @Override
    public User selectByMagicId(String magicId) {
        return null;
    }

    @Override
    public User selectByMap(Map<String, Object> params) {
        return null;
    }

    @Override
    public List<User> findByMap(Map<String, Object> params) {
        return null;
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return 0;
    }

    @Override
    public int deleteByMap(Map<String, Object> params) {
        return 0;
    }

    @Override
    public int insert(User record) throws Exception {
        return 0;
    }

    @Override
    public void batchInsert(List<User> records) throws Exception {

    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return 0;
    }

    @Override
    public int countAll() {
        return 0;
    }

    @Override
    public int countByMap(Map<String, Object> params) {
        return 0;
    }

    @Override
    public List<User> find(User record) {
        return null;
    }

    @Override
    public List<User> fuzzyFind(Map<String, Object> params) {
        return null;
    }
}
