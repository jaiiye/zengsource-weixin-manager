/**
 * 
 */
package org.zengsource.weixin.manager.web;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.zengsource.weixin.manager.AppConfig;
import org.zengsource.weixin.manager.ServletConfig;

/**
 * @author Shaoning Zeng
 * @since 7.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { AppConfig.class, ServletConfig.class })
@WebAppConfiguration
public class WeixinControllerTest {

	// + STATIC ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + FIELDS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext webApplicationContext;

	// + CSTORS ++++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + METHODS +++++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	@Before
	public void setUp() throws Exception {
		assertNotNull(webApplicationContext);
		mockMvc = webAppContextSetup(webApplicationContext).build();
	}

	private Date now() {
		return new Date();
	}

	@Test
	public void test_doPost_BindingAdmin() throws Exception {
		String url = "/weixin"; // 请求URL
		String xml = "";
		xml = "<xml><ToUserName><![CDATA[gh_4c4332624af2]]></ToUserName>" //
				+ "<FromUserName><![CDATA[oQYOLt1yQq4aWHwwNGETvH79tcRw]]></FromUserName>" //
				+ "<CreateTime>" + now().getTime() + "</CreateTime>" //
				+ "<MsgType><![CDATA[text]]></MsgType>" //
				+ "<Content><![CDATA[13923626967]]></Content>" //
				+ "<MsgId>" + now().getTime() + "</MsgId>" //
				+ "</xml>"; // 提交文字
		mockMvc.perform(post(url).contentType(MediaType.APPLICATION_XML).content(xml))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_XML));
		xml = "<xml><ToUserName><![CDATA[gh_4c4332624af2]]></ToUserName>" //
				+ "<FromUserName><![CDATA[oQYOLt1yQq4aWHwwNGETvH79tcRw]]></FromUserName>" //
				+ "<CreateTime>" + now().getTime() + "</CreateTime>" //
				+ "<MsgType><![CDATA[text]]></MsgType>" //
				+ "<Content><![CDATA[123456]]></Content>" //
				+ "<MsgId>" + now().getTime() + "</MsgId>" //
				+ "</xml>"; // 提交文字
		mockMvc.perform(post(url).contentType(MediaType.APPLICATION_XML).content(xml))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_XML));
		xml = "<xml><ToUserName><![CDATA[gh_4c4332624af2]]></ToUserName>" //
				+ "<FromUserName><![CDATA[oQYOLt1yQq4aWHwwNGETvH79tcRw]]></FromUserName>" //
				+ "<CreateTime>" + now().getTime() + "</CreateTime>" //
				+ "<MsgType><![CDATA[text]]></MsgType>" //
				+ "<Content><![CDATA[1]]></Content>" //
				+ "<MsgId>" + now().getTime() + "</MsgId>" //
				+ "</xml>"; // 提交文字
		mockMvc.perform(post(url).contentType(MediaType.APPLICATION_XML).content(xml))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_XML));
	}

	@Test
	public void test_doPost_ReceivingMessage() throws Exception {
		String url = "/weixin"; // 请求URL
		String xml = "";
		xml = "<xml><ToUserName><![CDATA[gh_4c4332624af2]]></ToUserName>" //
				+ "<FromUserName><![CDATA[oQYOLt4jCR8k3yw7m23gxJI5umq0]]></FromUserName>" //
				+ "<CreateTime>" + now().getTime() + "</CreateTime>" //
				+ "<MsgType><![CDATA[text]]></MsgType>" //
				+ "<Content><![CDATA[发一条消息过来]]></Content>" //
				+ "<MsgId>" + now().getTime() + "</MsgId>" //
				+ "</xml>"; // 提交文字
		mockMvc.perform(post(url).contentType(MediaType.APPLICATION_XML).content(xml))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_XML));
	}

	@Test
	public void test_doPost_ReceivingMessage_Admin() throws Exception {
		String url = "/weixin"; // 请求URL
		String xml = "";
		xml = "<xml><ToUserName><![CDATA[gh_4c4332624af2]]></ToUserName>" //
				+ "<FromUserName><![CDATA[oQYOLt1yQq4aWHwwNGETvH79tcRw]]></FromUserName>" //
				+ "<CreateTime>" + now().getTime() + "</CreateTime>" //
				+ "<MsgType><![CDATA[text]]></MsgType>" //
				+ "<Content><![CDATA[收]]></Content>" //
				+ "<MsgId>" + now().getTime() + "</MsgId>" //
				+ "</xml>"; // 提交文字
		mockMvc.perform(post(url).contentType(MediaType.APPLICATION_XML).content(xml))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_XML));
	}

	@Test
	public void test_doPost_SendingMessage() throws Exception {
		String url = "/weixin"; // 请求URL
		String xml = "";
		xml = "<xml><ToUserName><![CDATA[gh_4c4332624af2]]></ToUserName>" //
				+ "<FromUserName><![CDATA[oQYOLt1yQq4aWHwwNGETvH79tcRw]]></FromUserName>" //
				+ "<CreateTime>" + now().getTime() + "</CreateTime>" //
				+ "<MsgType><![CDATA[text]]></MsgType>" //
				+ "<Content><![CDATA[@2:回复您的消息]]></Content>" //
				+ "<MsgId>" + now().getTime() + "</MsgId>" //
				+ "</xml>"; // 提交文字
		mockMvc.perform(post(url).contentType(MediaType.APPLICATION_XML).content(xml))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_XML));
	}

	@Test
	public void test_doPost_Subscribe() throws Exception {
		String url = "/weixin"; // 请求URL
		String xml = "";
		xml = "<xml><ToUserName><![CDATA[gh_4c4332624af2]]></ToUserName>" //
				+ "<FromUserName><![CDATA[oQYOLt4jCR8k3yw7m23gxJI5umq0]]></FromUserName>" //
				+ "<CreateTime>" + now().getTime() + "</CreateTime>" //
				+ "<MsgType><![CDATA[event]]></MsgType>" //
				+ "<Event><![CDATA[subscribe]]></Event>" //
				+ "</xml>";
		mockMvc.perform(post(url).contentType(MediaType.APPLICATION_XML).content(xml))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_XML));
	}

	@Test
	public void test_doPost_Unsubscribe() throws Exception {
		String url = "/weixin"; // 请求URL
		String xml = "";
		xml = "<xml><ToUserName><![CDATA[gh_4c4332624af2]]></ToUserName>" //
				+ "<FromUserName><![CDATA[oQYOLt4jCR8k3yw7m23gxJI5umq0]]></FromUserName>" //
				+ "<CreateTime>" + now().getTime() + "</CreateTime>" //
				+ "<MsgType><![CDATA[event]]></MsgType>" //
				+ "<Event><![CDATA[unsubscribe]]></Event>" //
				+ "</xml>";
		mockMvc.perform(post(url).contentType(MediaType.APPLICATION_XML).content(xml))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_XML));
	}

	// + G^SETTERS +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

	// + MAIN^TEST +++++++++++++++++++++++++++++++++++++++++++++++++++++ //

}
