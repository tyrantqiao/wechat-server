package com.tyrantqiao.request;

/**
 * @author tyrantqiao [tyrantqiao@gmail.com]
 * @see <a href="https://github.com/tyrantqiao">qiao's github</a>
 */
public class StateMapping {
	/**
	 * 外部系统代码。
	 */
	private String collection;

	/**
	 * 外部代码。
	 */
	private String code;

	/**
	 * 代码描述。
	 */
	private String desc;

	/**
	 * 本地状态码对象。
	 */
	private StateCode stateCode;

	public StateCode getStateCode() {
		return this.stateCode;
	}

	public void setStateCode(StateCode stateCode) {
		this.stateCode = stateCode;
	}

	public String getCollection() {
		return this.collection;
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return this.desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	private int localcode;

	public int getLocalcode() {
		return this.localcode;
	}

	public void setLocalcode(int localcode) {
		this.localcode = localcode;
	}
}
