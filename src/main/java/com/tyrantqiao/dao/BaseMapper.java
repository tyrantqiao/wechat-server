package com.tyrantqiao.dao;

import com.tyrantqiao.entity.BaseEntity;

import java.util.List;
import java.util.Map;

/**
 * @author tyrantqiao [tyrantqiao@gmail.com]
 * @see <a href="https://github.com/tyrantqiao">qiao's github</a>
 */
public interface BaseMapper<R extends BaseEntity> {
    /**
     * 通过id找到数据
     *
     * @param id primaryKey
     * @return Result
     */
    R selectByPrimaryKey(Integer id);

    /**
     * 通过魔数查找
     *
     * @param magicId 魔数
     * @return Result
     */
    R selectByMagicId(String magicId);

    /**
     * 通过混合条件的map查询
     *
     * @param params 混合条件组成的map
     * @return Result
     */
    R selectByMap(Map<String, Object> params);

    /**
     * 通过混合条件的Map查询，获得list<R>
     *
     * @param params Map
     * @return List<Result>
     */
    List<R> findByMap(Map<String, Object> params);

    /**
     * 通过primaryKey删除
     *
     * @param id primaryKey
     * @return 删除结果【200-success】
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 通过Map删除
     *
     * @param params map
     * @return 删除结果
     */
    int deleteByMap(Map<String, Object> params);

    /**
     * 插入
     *
     * @param record 记录
     * @return 返回插入结果
     * @throws Exception 抛出异常避免发生情况
     */
    int insert(R record) throws Exception;

    /**
     * 批量插入
     *
     * @param records records
     * @throws Exception 抛出异常避免发生情况
     */
    void batchInsert(List<R> records) throws Exception;

    /**
     * 通过首要id更新
     *
     * @param record 更新的内容
     * @return 更新结果
     */
    int updateByPrimaryKeySelective(R record);

    /**
     * 更新通过primaryKey
     *
     * @param record 更新的内容
     * @return 更新结果
     */
    int updateByPrimaryKey(R record);

    /**
     * 获得count()数值
     *
     * @return size
     */
    int countAll();

    /**
     * 获得map条件下的数值大小
     *
     * @param params 条件
     * @return size
     */
    int countByMap(Map<String, Object> params);

    /**
     * 通过R查询列表
     *
     * @param record 记录
     * @return 涵盖这个的列表
     */
    List<R> find(R record);

    /**
     * 通过Map获取列表
     *
     * @param params Map
     * @return List<R>
     */
    List<R> fuzzyFind(Map<String, Object> params);
}
