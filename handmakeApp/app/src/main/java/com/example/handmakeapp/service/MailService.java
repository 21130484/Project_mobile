package com.example.handmakeapp.service;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Random;

public class MailService extends AsyncTask<Void,Void,Void> {

    private Context mContext;
    private Session mSession;
    private String mEmail;
    private String mSubject;
    private String mMessage;
    private ProgressDialog mProgressDialog;

    //Constructor
    public MailService(Context mContext, String mEmail, String mSubject, String mMessage) {
        this.mContext = mContext;
        this.mEmail = mEmail;
        this.mSubject = mSubject;
        this.mMessage = mMessage;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //Show progress dialog while sending email
        mProgressDialog = ProgressDialog.show(mContext,"Đang gửi mail", "Vui lòng đợi...",false,false);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        //Dismiss progress dialog when message successfully send
        if (mProgressDialog != null && mProgressDialog.isShowing() && !((Activity)mContext).isFinishing()) {
            mProgressDialog.dismiss();
        }

        //Show success toast
        Toast.makeText(mContext,"Đã gửi mail thành công.",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        //Creating properties
        try {
            // Get properties object
            Properties props = new Properties();
            props.put("mail.smtp.auth", MailProperties.auth);
            props.put("mail.smtp.starttls.enable", MailProperties.ssl);
            props.put("mail.smtp.host", MailProperties.host);
            props.put("mail.smtp.port", MailProperties.port);

            // get Session
            Session session = Session.getDefaultInstance(props,
                    new Authenticator() {
                        @Override
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(MailProperties.username, MailProperties.password);
                        }
                    });

            // compose message
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(MailProperties.username, "Hand Made Store"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mEmail));
            message.setSubject(mSubject);
//            Vì bold bằng html nên thay vì setText thì setsetContent bên dưới
//            message.setText(messageContent);
            // Thiết lập nội dung là HTML
            message.setContent(mMessage, "text/html; charset=utf-8");
            // send message
            Transport.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

}