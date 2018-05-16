package com.sixi.oaplatform.common.utils;

import java.io.Serializable;

public class RD implements Serializable {
	private static final long serialVersionUID = -3463142389287416459L;

	public RD() {
	}

	private int state;
	private String msg;
	private Object data;

	/**
	 * 服务端操作成功
	 */
	public static final int SUCCESS =200;
	/**
	 * 服务端操作失败
	 */
	public static final int ERROR =202;
	/**
	 * 失败路径
	 */
	public static final int ERROR_PATH =404;
	/**
	 * 服务端报错
	 */
	public static final int EXCEPTION =500;
	/**
	 * 无访问权限
	 */
	public static final int PERMISSIONS =403;
	/**
	 * 未登录
	 */
	public static final int NOTLOGIN =401;

	/**
	 * 错误页面
	 * @return
	 */
	public static RD errorPath(){return new RD(ERROR_PATH,"你打开的接口不存在",null);}

	/**
	 * 未登录
	 * @return
	 */
	public static RD notlogin(){return new RD(NOTLOGIN,"未登录","");}

	/**
	 * 报错情况
	 * @param msg
	 * @return
	 */
	public static RD exception(String msg){return new RD(EXCEPTION,msg,"");}

	/**
	 * 无访问权限
	 * @return
	 */
	public static RD permissions(){
		return new RD(PERMISSIONS,"无访问权限","");
	}

	/**
	 * 获取返回状态的实例
	 *
	 * @param state
	 * @param msg
	 * @param data
	 * @return
	 */
	public static RD getInstance(int state, String msg, Object data) {
		return new RD(state, msg, data);
	}

	/**
	 * 返回错误处理
	 *
	 * @param msg
	 * @param data
	 * @return
	 */
	public static RD error(String msg, Object data) {
		return new RD(ERROR, msg, data);
	}

	/**
	 * 返回错误处理(data 默认为 null)
	 *
	 * @param msg
	 * @param data
	 * @return
	 */
	public static RD error(String msg) {
		return new RD(ERROR, msg, null);
	}

	/**
	 * 成功处理
	 *
	 * @param msg
	 * @param data
	 * @return
	 */
	public static RD success(String msg, Object data) {
		return new RD(SUCCESS, msg, data);
	}

	/**
	 * 成功处理(msg 默认为 "")
	 *
	 * @param msg
	 * @param data
	 * @return
	 */
	public static RD success(Object data) {
		return new RD(SUCCESS, "", data);
	}

	/**
	 * 成功处理(msg 默认为 ""，data默认为null)
	 *
	 * @param msg
	 * @param data
	 * @return
	 */
	public static RD success() {
		return new RD(SUCCESS, "", null);
	}

	/**
	 * 快速或敏捷返回数据
	 *
	 * @param b
	 * @return
	 */
	public static RD quick(boolean b) {
		if (b) {
			return new RD(SUCCESS, "", null);
		} else {
			return new RD(ERROR, "失败", null);
		}
	}

	/**
	 * 快速或敏捷返回数据
	 *
	 * @param b
	 * @param errorMsg 若异常时，返回的消息
	 * @return
	 */
	public static RD quick(boolean b, String errorMsg) {
		if (b) {
			return new RD(SUCCESS, "", null);
		} else {
			return new RD(ERROR, errorMsg, null);
		}
	}

	/**
	 * @param b
	 * @param successData 若成功时，返回的数据
	 * @return
	 */
	public static RD quick(boolean b, Object successData) {
		if (b) {
			return new RD(SUCCESS, "", successData);
		} else {
			return new RD(ERROR, "失败", null);
		}
	}

	/**
	 * @param b
	 * @param errorMsg    若异常时，返回的消息
	 * @param successData 若成功时，返回的数据
	 * @return
	 */
	public static RD quick(boolean b, String errorMsg, Object successData) {
		if (b) {
			return new RD(SUCCESS, "", successData);
		} else {
			return new RD(ERROR, errorMsg, null);
		}
	}

	/**
	 * 状态 0：失败或错误 1：成功
	 *
	 * @return
	 */
	public int getState() {
		return state;
	}

	/**
	 * 消息内容
	 *
	 * @return
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * 返回数据
	 *
	 * @return
	 */
	public Object getData() {
		return data;
	}

	/**
	 * 创建实例
	 *
	 * @param state
	 * @param msg
	 * @param data
	 */
	public RD(int state, String msg, Object data) {
		this.state = state;
		this.msg = msg;
		this.data = data;
	}

}
