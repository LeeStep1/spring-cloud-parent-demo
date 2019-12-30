package com.bit.module.manager.bean;

import lombok.Data;
import org.apache.commons.mail.EmailAttachment;

import java.util.List;

/**
 * @Description 邮件
 * @Author chenduo
 * @Date 2019/12/17 16:52
 **/
@Data
public class EmailInfo {

	// 收件人
	private List<String> toAddress = null;
	// 抄送人地址
	private List<String> ccAddress = null;
	// 密送人
	private List<String> bccAddress = null;
	// 附件信息
	private List<EmailAttachment> attachments = null;
	// 邮件主题
	private String subject;
	// 邮件的文本内容
	private String content;
}
