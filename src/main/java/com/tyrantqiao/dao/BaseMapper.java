package com.tyrantqiao.dao;

import com.tyrantqiao.entity.BaseEntity;

import java.util.List;
import java.util.Map;

/**
 * @author tyrantqiao [tyrantqiao@gmail.com]
 * @see <a href="https://github.com/tyrantqiao">qiao's github</a>
 */
public interface BaseMapper<R extends BaseEntity> {
	R selectByPrimaryKey(Integer id);

	R selectByMagicId(String magicId);

	R selectOne(Map<String, Object> params);

	List<R> find(Map<String, Object> params);

	int deleteByPrimaryKey(Integer id);

	int deleteByMap(Map<String, Object> params);

	int insert(R record) throws Exception;

	void batchInsert(List<R> records) throws Exception;

	int insertSelective(R record) throws Exception;

	int updateByPrimaryKeySelective(R record);

	int updateByPrimaryKey(R record);

	int countAll();

	int countByMap(Map<String, Object> params);

	List<R> find(R record);

	List<R> fuzzy(Map<String, Object> params);
}
