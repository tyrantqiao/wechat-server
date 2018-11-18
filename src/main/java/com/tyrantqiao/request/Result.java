package com.tyrantqiao.request;

import com.tyrantqiao.util.EmptyUtil;
import org.apache.shiro.SecurityUtils;

/**
 * @author tyrantqiao [tyrantqiao@gmail.com]
 * @see <a href="https://github.com/tyrantqiao">qiao's github</a>
 */
public class Result implements java.io.Serializable, Cloneable {
    public static final String KEY = "jsonResponse";
    // 客户端请求id
    private long id = 0;
    private String sessionId = null;
    private State state;
    private Object data;

    {
        try {
            sessionId = SecurityUtils.getSubject().getSession().getId().toString();
        } catch (Exception e) {
        }
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    /**
     * 分页信息。
     */
    Pagination page;

    public Result() {
        super();
        this.state = new State();
        this.data = null;
    }

    /**
     * 实例一个成功的响应，状态码为：2000000（具体以字典为准），状态描述使用字典中的定义。
     *
     * @return 响应实例
     */
    public static Result newOk() {
        Result response = new Result();
        response.setState(State.newOk());
        return response;
    }

    /**
     * 实例一个包括数据的成功响应，状态码为：2000000（具体以字典为准），状态描述使用字典中的定义。
     *
     * @param data 数据
     * @return 响应实例
     */
    public static Result newOk(Object data) {
        Result response = new Result();
        response.setData(data);
        response.setState(State.newOk());
        return response;
    }

    public static Result newOkMessage(String msg) {
        Result result = new Result();
        State state = new State(State.SUCCESS);
        state.setMsg(msg);
        result.setState(state);
        return result;
    }

    /**
     * 实例化一个自定义状态信息的响应，状态描述使用字典中的定义。
     *
     * @param data      数据
     * @param stateCode 自定义的状态信息
     * @return 响应实例
     */
    public static Result newInstance(StateCode stateCode, Object data) {
        Result response = new Result();
        response.setData(data);
        response.setState(new State(stateCode));
        return response;
    }

    /**
     * 实例化一个自定义状态信息的响应。
     *
     * @param data      数据
     * @param msg       自定义的提示信息
     * @param stateCode 自定义的状态信息
     * @return 响应实例
     */
    public static Result newInstance(StateCode stateCode, String msg, Object data) {
        Result response = new Result();
        response.setData(data);
        State state = new State(stateCode);
        state.setMsg(msg);
        response.setState(state);
        return response;
    }

    /**
     * 使用自定义的状态信息，实例化一个错误的响应，响应信息对应字典中的信息。
     *
     * @param stateCode 状态信息
     * @return 响应实例
     * @see StateCode
     */
    public static Result newError(StateCode stateCode) {
        Result response = new Result();
        response.setState(State.newState(stateCode));
        return response;
    }

    /**
     * 使用自定义的状态信息，实例化一个错误的响应。
     *
     * @param stateCode 状态信息
     * @param msg       自定义的错误信息，如果 msg 为空，那么调用字典中对应的信息
     * @return 响应实例
     * @see StateCode
     */
    public static Result newError(StateCode stateCode, String msg) {
        Result response = new Result();
        State state = State.newState(stateCode);
        if (msg == null || "".equals(msg)) {
            state.setMsg(state.getMsg());
        } else {
            state.setMsg(msg);
        }
        response.setState(state);
        return response;
    }

    /**
     * 实例化一个系统错误的响应，状态码为：5000（具体以字典为准）。
     *
     * @param msg 自定义的错误信息，如果 msg 为空，那么使用字典中的定义
     * @return 响应实例
     */
    public static Result newError(String msg) {
        Result response = new Result();
        State state = State.newState(State.ERROR);
        state.setMsg(msg);
        response.setState(state);
        return response;
    }

    /**
     * 使用自定义的状态信息，实例化一个错误的响应。
     *
     * @param stateCode 状态信息
     * @param msg       自定义的错误信息，如果 msg 为空，那么调用字典中对应的信息
     * @return 响应实例
     * @see StateCode
     */
    public static Result newInfo(StateCode stateCode, String msg) {
        Result response = new Result();
        State state = State.newState(stateCode);
        if (EmptyUtil.isEmpty(msg)) {
            state.setMsg(state.getMsg());
        } else {
            state.setMsg(msg);
        }
        response.setState(state);
        return response;
    }


    @Override
    public Result clone() {
        Result response = null;
        try {
            response = (Result) super.clone();
            if (this.state != null) {
                response.setState(this.state.clone());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return response;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Pagination createPage() {
        this.page = new Pagination();
        return this.page;
    }

    public Pagination getPage() {
        return this.page;
    }

    public void setPage(Pagination page) {
        this.page = page;
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
