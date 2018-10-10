package com.tyrantqiao.request;


import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tyrantqiao.util.ConvertUtil;
import com.tyrantqiao.util.EmptyUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

/**
 * @author tyrantqiao [tyrantqiao@gmail.com]
 * @see <a href="https://github.com/tyrantqiao">qiao's github</a>
 */
public class StateMapper {
	private static Map<String, List<StateMapping>> stateMappings = new HashMap<String, List<StateMapping>>();
	private static Map<Integer, StateCode> localStateCode = new HashMap<Integer, StateCode>();
	private static Map<String, String> collections = new HashMap<String, String>();
	private static Logger logger = LoggerFactory.getLogger(StateMapper.class);
	private static final String MSG_OF_UNKNOWN_CODE = "未知状态";

	static {
		StateMapper.init();
	}

	/**
	 * 取得外部代码对象。
	 *
	 * @param externalName 外部系统代号
	 * @param externalCode 外部代码
	 * @return 如果找不到的话，那么返回 null
	 */
	public static StateMapping getMapping(String externalName, String externalCode) {
		List<StateMapping> stateList = stateMappings.get(externalName);
		if (stateList == null) {
			return null;
		}
		for (StateMapping entity : stateList) {
			if (entity.getCode().equalsIgnoreCase(externalCode)) {
				return entity;
			}
		}
		return null;
	}

	/**
	 * 在程序启动的时候初始化。
	 */
	public static void init() {

		// 注意：必须优先初始化本地状态码
		logger.info("初始化状态码...");
		StateMapper.loadLocal("classpath:local-state-code.xml");
		logger.info("本地状态码数量：{}", localStateCode.size());

		logger.info("加载外部系统状态码.");
		for (String external : collections.keySet()) {
			String file = StateMapper.getApplicationPath() + File.separator + collections.get(external);
			List<StateMapping> stateList = StateMapper.loadCollection(external, file);
			stateMappings.put(external, stateList);
		}
		logger.info("共有 [{}] 组外部系统代码：", stateMappings.size());
		for (String key : stateMappings.keySet()) {
			logger.info("{}({})", key, stateMappings.get(key).size());
		}
	}

	private static void loadLocal(String file) {
		try {
			logger.info("加载本地状态码：{}", file);
			SAXReader reader = new SAXReader();
			InputStream input = ResourceUtils.getURL(file).openStream();
			Document document = reader.read(input);
			List<?> states = document.selectNodes("/root/code");
			for (Object state : states) {
				Element el = (Element) state;
				final String value = el.attributeValue("value");
				final String desc = el.attributeValue("desc");
				final String msg = el.attributeValue("msg");

				StateCode es;
				final Integer code;
				try {
					es = new StateCode();
					code = Integer.parseInt(value);
					es.setCode(code);
					es.setMsg(msg);
				} catch (NumberFormatException e) {
					logger.warn("无载解析本地代码：{}，必须保证状态码为数字",value);
				}
			}
		} catch (Exception e) {
			logger.warn("加载本地状态码出错：", e);
		}
	}

	private static String getApplicationPath() {
		return "/var/webroot/config";
	}

	private static List<StateMapping> loadCollection(String collection, String file) {
		List<StateMapping> stateList = new ArrayList<StateMapping>();
		try {
			logger.info("加载状态映射文件({}):{}", collection, file);
			SAXReader reader = new SAXReader();
			Document document = reader.read(file);
			List<?> states = document.selectNodes("/root/code");
			StateMapping es = null;
			for (Object state : states) {
				Element el = (Element) state;
				final String value = el.attributeValue("value");
				final String desc = el.attributeValue("desc");
				final String localcode = el.attributeValue("localcode");

				es = new StateMapping();
				es.setCode(value);
				es.setCollection(collection);
				final int localcode1 = Integer.parseInt(localcode);
				es.setLocalcode(localcode1);
				es.setDesc(desc);
				es.setStateCode(StateMapper.get(localcode1));
				stateList.add(es);
			}

			stateMappings.put(collection, stateList);
		} catch (Exception e) {
			logger.warn("加载状态映射文件（{}）出错：",file, e);
		}

		return stateList;
	}

	/**
	 * 取得本地状态码信息.
	 *
	 * @param code 状态码
	 * @return 如果找不到的话，那么根据此状态码实例化一个对象，提示信息为：“未知状态”
	 */
	public static StateCode get(Integer code) {
		StateCode stateCode = localStateCode.get(code);
		if (stateCode == null) {
			logger.error("状态码不存在：" + code + "，请检查配置文件");
			stateCode = new StateCode();
			stateCode.setCode(State.ERROR);
			stateCode.setMsg(MSG_OF_UNKNOWN_CODE);
		}
		return stateCode;
	}

	/**
	 * 取得本地状态码信息。
	 *
	 * @param code 状态码
	 * @param defaultMsg 自定义提示信息
	 * @return 如果状态找不到的话，那么根据此状态码实例化一个对象，提示信息为：“未知状态”
	 */
	public static StateCode get(Integer code, String defaultMsg) {
		StateCode stateCode = StateMapper.get(code);
		if (MSG_OF_UNKNOWN_CODE.equals(stateCode.getMsg())) {
			return stateCode;
		}
		StateCode newState = ConvertUtil.convertType(stateCode, StateCode.class);
		newState.setMsg(defaultMsg);
		return newState;
	}

	/**
	 * 取得本地状态码信息,拼接自定义信息
	 * @param code 状态码
	 * @return StateCode
	 */
	public static StateCode getWithParams(Integer code, Object... customMsg) {
		StateCode stateCode = StateMapper.get(code);
		if (MSG_OF_UNKNOWN_CODE.equals(stateCode.getMsg())) {
			return stateCode;
		}
		StateCode newState = ConvertUtil.convertType(stateCode, StateCode.class);
		if (EmptyUtil.isNotEmpty(customMsg)) {
			newState.setMsg(String.format(stateCode.getMsg(), customMsg));
		} else {
			newState.setMsg(String.format(stateCode.getMsg(), ""));
		}
		return newState;
	}

	/**
	 * 取得本地的成功状态码对象。
	 *
	 * @return 状态码为 2000 的对象
	 */
	public static StateCode getOK() {
		return StateMapper.get(State.SUCCESS);
	}

	/**
	 * 取得本地的错误状态码对象。
	 *
	 * @return 状态码为 5000 的对象
	 */
	public static StateCode getError() {
		return StateMapper.get(State.ERROR);
	}

	public void setCollections(Map<String, String> collections) {
		StateMapper.collections = collections;
	}
}
