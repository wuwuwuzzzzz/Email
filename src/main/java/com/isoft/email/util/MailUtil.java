package com.isoft.email.util;

import com.isoft.email.bean.MailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

@Component
public class MailUtil {
    @Autowired
    JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    String from;

    /**
     * 发送简单邮件
     */
    public void sendSimpleMail(MailMessage message) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(from);
        msg.setTo(message.getTo());
        msg.setSubject(message.getSubject());
        msg.setText(message.getText());
        javaMailSender.send(msg);
    }

    /**
     * 发送HTML邮件
     */

    public void sendMailHtml(MailMessage message) {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(from);
            helper.setTo(message.getTo());
            helper.setSubject(message.getSubject());
            helper.setText(message.getText(), true);
        };
        javaMailSender.send(mimeMessagePreparator);
    }

    /**
     * 发送带有附件的邮件
     */

    public void sendMailAttachment(MailMessage message, boolean isHtml) {
        MimeMessagePreparator mimeMessagePreparator = mimeMessage -> {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(message.getTo());
            helper.setSubject(message.getSubject());
            helper.setText(message.getText(), isHtml);
            helper.addAttachment(message.getAttachmentFileName(), message.getAttachmentFile());
        };
        javaMailSender.send(mimeMessagePreparator);
    }
}
