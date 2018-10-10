package com.tyrantqiao.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author tyrantqiao [tyrantqiao@gmail.com]
 * @see <a href="https://github.com/tyrantqiao">qiao's github</a>
 */
public class BaseEntity implements Serializable, Comparable<BaseEntity> {
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String magicId;
	private String userMagicId;
	private Integer sortNum;
	private String createUser;
	private String updatedUser;
	private String isDeleted;
	private java.util.Date createdTime;
	private java.util.Date updatedTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMagicId() {
		return magicId;
	}

	public void setMagicId(String magicId) {
		this.magicId = magicId;
	}

	public String getUserMagicId() {
		return userMagicId;
	}

	public void setUserMagicId(String userMagicId) {
		this.userMagicId = userMagicId;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getUpdatedUser() {
		return updatedUser;
	}

	public void setUpdatedUser(String updatedUser) {
		this.updatedUser = updatedUser;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	@Override
	public int compareTo(BaseEntity entity) {
		if (entity == null) {
			return -1;
		}
		return this.getSortNum() - entity.getSortNum();
	}
}
