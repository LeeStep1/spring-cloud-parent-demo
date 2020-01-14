package com.bit.utils;

import com.bit.module.manager.bean.EmailInfo;
import com.sun.mail.util.MailSSLSocketFactory;
import org.apache.commons.mail.EmailAttachment;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * @Description
 * @Author chenduo
 * @Date 2019/12/30 14:46
 **/
//@Component
public class MailUtil {

	// 腾讯企业邮箱 SMTP 服务器地址为: smtp.163.com
	private static String TencentEntmailServerHost = "smtp.exmail.qq.com";

	// 发件人
	private static String TencentEntmailSenderAddress  = "GEBJ@xblgroup.com";
	// 发件人邮箱账号

	private static String TencentEntmailSenderUsername  = "GEBJ@xblgroup.com";
	// 发件人邮箱密码
	private static String TencentEntmailSenderPassword  = "GEdtbj123456";
	// 邮箱服务器端口
	private static String TencentEntmailServerHostPort  = "465";
	// 协议
	private static String TencentEntmailprotocol = "smtp";


	public static void send(String sender,EmailInfo emailInfo) {
		try {
			System.setProperty("mail.mime.splitlongparameters","false");
			Session session = initProperties();
			MimeMessage mimeMessage = new MimeMessage(session);
			// 发件人,可以设置发件人的别名
			mimeMessage.setFrom(new InternetAddress(TencentEntmailSenderUsername, sender));
			String receiver = assembleReceiver(emailInfo.getToAddress());
			// 收件人,多人接收
			InternetAddress[] internetAddressTo = InternetAddress.parse(receiver);
			mimeMessage.setRecipients(Message.RecipientType.TO, internetAddressTo);
            if(emailInfo.getCcAddress()!=null&&emailInfo.getCcAddress().size()>0){
				String copyReceiver = assembleReceiver(emailInfo.getCcAddress());
				InternetAddress[] copyAddress = InternetAddress.parse(copyReceiver);
				mimeMessage.setRecipients(Message.RecipientType.CC, copyAddress);
			}

			// 抄收人,多人接收

			// 主题
			mimeMessage.setSubject(emailInfo.getSubject(),"UTF-8");
			// 时间
			mimeMessage.setSentDate(new Date());
			// 容器类 附件
			MimeMultipart mimeMultipart = new MimeMultipart();
			// 可以包装文本,图片,附件
			MimeBodyPart bodyPart = new MimeBodyPart();
			// 设置内容
			bodyPart.setContent(emailInfo.getContent(), "text/html; charset=UTF-8");
			mimeMultipart.addBodyPart(bodyPart);
			// 添加图片&附件
			bodyPart = new MimeBodyPart();
			for (EmailAttachment emailAttachment : emailInfo.getAttachments()) {
				bodyPart.attachFile(emailAttachment.getPath());
				bodyPart.setFileName(MimeUtility.encodeText(emailAttachment.getName(), "UTF-8", "B"));

				mimeMultipart.addBodyPart(bodyPart);
			}
			mimeMessage.setContent(mimeMultipart);
			mimeMessage.saveChanges();
			Transport.send(mimeMessage);
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static void send(EmailInfo emailInfo){
		send( TencentEntmailSenderAddress, emailInfo);
	}

	private static String assembleReceiver(List<String> toAddress){
		StringBuilder receiver = new StringBuilder();
		for (String address : toAddress) {
			receiver.append(address);
			receiver.append(",");
		}
		return receiver.toString().substring(0,receiver.toString().length()-1);
	}

	//初始化参数
	public static Session initProperties() {
		Properties properties = new Properties();
		properties.setProperty("mail.transport.protocol", TencentEntmailprotocol);
		properties.setProperty("mail.smtp.host", TencentEntmailServerHost);
		properties.setProperty("mail.smtp.port", TencentEntmailServerHostPort);
		// 使用smtp身份验证
		properties.put("mail.smtp.auth", "true");
		// 使用SSL,企业邮箱必需 start
		// 开启安全协议
		MailSSLSocketFactory mailSSLSocketFactory = null;
		try {
			mailSSLSocketFactory = new MailSSLSocketFactory();
			mailSSLSocketFactory.setTrustAllHosts(true);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
		properties.put("mail.smtp.enable", "true");
		properties.put("mail.smtp.ssl.socketFactory", mailSSLSocketFactory);
		properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		properties.put("mail.smtp.socketFactory.fallback", "false");
		properties.put("mail.smtp.socketFactory.port", TencentEntmailServerHostPort);
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(TencentEntmailSenderAddress, TencentEntmailSenderPassword);
			}
		});
		// 使用SSL,企业邮箱必需 end
		// TODO 显示debug信息 正式环境注释掉
		session.setDebug(true);
		return session;
	}

	public static void main(String[] args) {
		EmailInfo emailInfo = new EmailInfo();
		//接收人邮箱集合
		List<String> toList = new ArrayList<String>();
		toList.add("star9c2009@163.com");
		toList.add("1248202283@qq.com");
		//抄送人邮箱集合
		List<String> ccList = new ArrayList<String>();
		ccList.add(TencentEntmailSenderUsername);

		//邮件附件集合
		List<EmailAttachment> attachments = new ArrayList<>();
		EmailAttachment emailAttachment = new EmailAttachment();
		emailAttachment.setPath("D:\\upload\\1.xls");
		emailAttachment.setName("报价.xlsx");
		attachments.add(emailAttachment);

		//收件人
		emailInfo.setToAddress(toList);
		//附件
		emailInfo.setAttachments(attachments);
		//标题
		emailInfo.setSubject("主题");
		//内容
		emailInfo.setContent("内容：<h1>test,测试</h1>");
		//抄送人
		emailInfo.setCcAddress(ccList);

//		sendEmail(emailInfo);
		send(TencentEntmailSenderAddress,emailInfo);
	}
}
