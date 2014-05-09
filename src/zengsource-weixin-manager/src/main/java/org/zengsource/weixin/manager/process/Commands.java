/**
 * 
 */
package org.zengsource.weixin.manager.process;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
public interface Commands {

	public static final int CMD_UNKNOWN = 0;
	// 命令
	public static final int CMD_SET_USERNAME = 100;
	public static final int CMD_SET_PASSWORD = 1014986;
	public static final int CMD_SET_EMAIL = 1023579;
	public static final int CMD_SET_BASE = 101;
	public static final int CMD_SET_EDU = 102;

	public static final int CMD_TEXT_SENT = 201; // 用户发送了文本
	public static final int CMD_IMAGE_SENT = 202; // 用户发送了文本
	public static final int CMD_CANCEL_OPERATION = 0; // 取消操作
	public static final int CMD_CONFIRM_QUESTION = 1; // 确认提问
	public static final int CMD_CONFIRM_SERVICES = 2; // 转发客服消息
	public static final int CMD_QUESTION_CREATED = 203; // 问题已创建
	public static final int CMD_QUESTION_VIEWING = 204; // 正在查看问题
	public static final int CMD_QUESTION_ANSWERING = 205; // 正在回答问题
	public static final int CMD_QUESTION_TO_ANSWER = 206; // 设置要回答的问题

	public static final int CMD_APPLY_FREE_TICKETS = 513; // 申请免费次数

	public static final int CMD_HELP_DESK = 999; // 客服帮助

	// 自定义菜单

	public static final String EVENT_KEY_REFESH = "REFRESH";
	public static final String EVENT_KEY_ASK = "ASK_QUESTION";
	public static final String EVENT_KEY_ANSWER = "ANSWER_QUESTION";
	public static final String EVENT_KEY_USER_INFO = "GET_USER_INFO";
	public static final String EVENT_KEY_MY_QUESTIONS = "GET_MY_QUESTIONS";
	public static final String EVENT_KEY_MY_ANSWERS = "GET_MY_ANSWERS";
	public static final String EVENT_KEY_CHECK_BALANCE = "CHECK_BALANCE";
	public static final String EVENT_KEY_PAY_WEIXIN = "PAY_BY_WEIXIN";
	public static final String EVENT_KEY_PAY_TAOBAO = "PAY_BY_TAOBAO";
	public static final String EVENT_KEY_SAY_HELLO = "SAY_HELLO";

}
