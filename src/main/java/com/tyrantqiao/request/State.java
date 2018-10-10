package com.tyrantqiao.request;

import java.io.Serializable;

/**
 * @author tyrantqiao [tyrantqiao@gmail.com]
 * @see <a href="https://github.com/tyrantqiao">qiao's github</a>
 */
public class State implements Serializable, Cloneable {
	public static final int SUCCESS = 200;
	public static final int ERROR = 5000;
	public static final Integer PARAMS_NOT_FOUND = 4000;
	public static final Integer PARAMS_INVALID = 50000;

	// 响应code
	int code = StateMapper.getOK().getCode();

	// 响应消息
	String msg = StateMapper.getOK().getMsg();

	// 客户端更新频率,单位：小时
	// long updateFreq = 12L;

	/**
	 * 实例化一个成功的响应状态，提示信息使用默认文本（操作成功）。
	 *
	 * @return 成功的响应状态实例
	 */
	public static State newOk() {
		return new State(StateMapper.getOK());
	}

	/**
	 * 实例化一个错误的响应状态，提示信息使用默认文本。
	 *
	 * @return 错误的响应状态实例
	 */
	public static State newError() {
		return new State(StateMapper.getError());
	}

	/**
	 * 实例化一个成功的响应状态。
	 *
	 * @param msg 成功提示信息
	 * @return 成功的响应状态实例
	 */
	public static State newOk(String msg) {
		State state = new State();
		state.setCode(StateMapper.getOK().getCode());
		state.setMsg(msg);
		return state;
	}

	/**
	 * 通过状态码对象实例化一下状态对象。
	 *
	 * @param stateCode 状态码对象
	 * @return 状态对象
	 */
	public static State newState(StateCode stateCode) {
		State state = new State();
		state.setCode(stateCode.getCode());
		state.setMsg(stateCode.getMsg());
		return state;
	}

	public State() {
	}

	public State(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public State(StateCode stateCode) {
		this.code = stateCode.getCode();
		this.msg = stateCode.getMsg();
	}

	// ------------------------ CANONICAL METHODS ------------------------

	@Override
	public State clone() {
		State state = null;
		try {
			state = (State) super.clone();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return state;
	}

	public int getCode() {
		return this.code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "statCode=" + this.code + "`stateMsg=" + this.msg;
	}
}
