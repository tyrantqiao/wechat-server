package com.tyrantqiao.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tyrantqiao [tyrantqiao@gmail.com]
 * @see <a href="https://github.com/tyrantqiao">qiao's github</a>
 */
@Data
public class BaseEntity implements Serializable, Comparable<BaseEntity> {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String userId;
    private String createUser;
    private String updatedUser;
    /**
     * 用作compareTo比较大小
     */
    private int index;
    private java.util.Date createdDate;
    private java.util.Date updatedDate;

    @Override
    public int compareTo(BaseEntity entity) {
        if (entity == null) {
            return -1;
        }
        return this.index - entity.index;
    }
}
