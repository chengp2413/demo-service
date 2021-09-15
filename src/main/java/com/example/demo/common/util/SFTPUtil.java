package com.example.demo.common.util;

import com.jcraft.jsch.*;
import org.apache.commons.lang.StringUtils;

import java.util.Properties;

/**
 * sftp工具类
 *
 * @author 时良宝 k48299
 * @date 2021/07/19
 */
public class SFTPUtil {


    /**
     * 构造方法
     */
    private SFTPUtil() {
        //do something
    }

    /**
     * 上传文件
     *
     * @param host       上传服务器IP
     * @param port       上传服务器端口
     * @param userName   上传服务器用户名
     * @param password   上传服务器密码
     * @param localpath  本地文件全路径
     * @param remotrpath 远程服务器目录
     * @throws JSchException JSchException
     * @throws SftpException SftpException
     */
    public static void uploadFile(String host, int port, String userName, String password, String localpath,
                                  String remotrpath) throws JSchException, SftpException {
        Session sftpSession = getSftpSession(host, port, userName, password, null);
        uploadFile(remotrpath, localpath, sftpSession);
    }

    /**
     * 上传文件-调用方法
     *
     * @param remotepath 远程服务器目录
     * @param localpath  本地文件全路径
     * @param session    session对象
     * @throws JSchException JSchException
     * @throws SftpException SftpException
     */
    private static void uploadFile(String remotepath, String localpath, Session session)
            throws JSchException, SftpException {
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp channelSftp = (ChannelSftp) channel;
        try {
            channelSftp.put(localpath, remotepath);
        } finally {
            channelSftp.quit();
            channel.disconnect();
            session.disconnect();
        }
    }


    /**
     * 下载文件
     *
     * @param host       下载服务器IP
     * @param port       下载服务器端口
     * @param userName   下载服务器用户名
     * @param password   下载服务器密码
     * @param remotepath 下载文件全路径
     * @param localpath  本地保存目录
     * @throws JSchException JSchException
     * @throws SftpException SftpException
     */
    public static void downloadFile(String host, int port, String userName, String password, String remotepath,
                                    String localpath) throws JSchException, SftpException {
        Session sftpSession = getSftpSession(host, port, userName, password, null);
        downloadFile(remotepath, localpath, sftpSession);
    }

    /**
     * 下载文件-调用方法
     *
     * @param remotepath 下载文件全路径
     * @param localpath  本地保存目录
     * @param session    session
     * @throws JSchException JSchException
     * @throws SftpException SftpException
     */
    private static void downloadFile(String remotepath, String localpath, Session session)
            throws JSchException, SftpException {
        Channel channel = session.openChannel("sftp");
        channel.connect();
        ChannelSftp channelSftp = (ChannelSftp) channel;
        try {
            if (remotepath.startsWith("/")) {
                channelSftp.cd("/");
            }
            channelSftp.get(remotepath, localpath);
        } finally {
            channelSftp.quit();
            channel.disconnect();
            session.disconnect();
        }
    }

    /**
     * 获取session
     *
     * @param host       连接服务器IP
     * @param port       连接服务器端口
     * @param userName   连接服务器用户名
     * @param password   连接服务器密码
     * @param privateKey 连接服务器密钥
     * @return session
     * @throws JSchException JSchException
     */
    private static Session getSftpSession(String host, int port, String userName, String password,
                                          String privateKey) throws JSchException {
        JSch jSch = new JSch();
        if (StringUtils.isNotBlank(privateKey)) {
            jSch.addIdentity(privateKey);
        }
        Session session = jSch.getSession(userName, host, port);
        session.setTimeout(60000);
        if (StringUtils.isNotBlank(password)) {
            session.setPassword(password);
        }
        Properties properties = new Properties();
        properties.put("StrictHostKeyChecking", "no");
        session.setConfig(properties);
        session.connect();
        return session;
    }

}
