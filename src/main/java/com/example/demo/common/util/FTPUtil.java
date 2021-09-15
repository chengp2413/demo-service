package com.example.demo.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * FTP传输工具类
 *
 * @author 程鹏 k6048
 * @date 2021/08/06
 */
@Slf4j
public class FTPUtil {

    /**
     * 远程服务器IP
     */
    private String server;

    /**
     * 远程服务器端口
     */
    private int port;

    /**
     * 远程服务器用户名
     */
    private String username;

    /**
     * 远程服务器密码
     */
    private String password;

    /**
     * 远程服务器基础路径
     */
    private String remotepath;

    /**
     * 本地基础路径
     */
    private String localpath;

    /**
     * 构造方法
     *
     * @param server     远程服务器IP
     * @param port       远程服务器端口
     * @param username   远程服务器用户名
     * @param password   远程服务器密码
     * @param remotepath 远程服务器基础路径
     * @param localpath  本地基础路径
     */
    public FTPUtil(String server, int port, String username, String password, String remotepath, String localpath) {
        this.server = server;
        this.port = port;
        this.username = username;
        this.password = password;
        this.remotepath = remotepath;
        this.localpath = localpath;
    }

    /**
     * FTPClient类
     */
    private FTPClient ftpClient;
    /**
     * 文件编码方式
     */
    private static final String ENCODING = System.getProperty("file.encoding");
    /**
     * 二进制文件类型
     */
    private static final int BINARY_FILE_TYPE = FTP.BINARY_FILE_TYPE;
    /**
     * 传输缓存
     */
    private static final int TRANSFER_CACHE = 1024;


    /**
     * 使用远程服务器信息进行服务器连接
     *
     * @throws SocketException socket异常
     * @throws IOException     io异常
     */
    private void connectServer() throws IOException {
        if (ftpClient == null) {
            ftpClient = new FTPClient();
            ftpClient.connect(server, port);
            log.info("正在尝试连接到服务器:{}", server);
            // 连接成功后的响应码
            if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                log.info("连接服务器成功！！！");
            }
            boolean flag = ftpClient.login(username, password);
            if (flag) {
                log.info("用户【{}】登录成功", username);
            } else {
                log.info("用户【{}】登录失败", username);
            }
            if (remotepath != null && remotepath.length() != 0) {
                boolean moveDir = ftpClient.changeWorkingDirectory(remotepath);
                if (moveDir) {
                    log.info("FTP工作目录切换成功");
                } else {
                    log.info("FTP工作目录切换失败");
                }
            }
            ftpClient.setBufferSize(TRANSFER_CACHE);// 设置上传缓存大小
            ftpClient.setControlEncoding(ENCODING);// 设置编码
            ftpClient.setFileType(BINARY_FILE_TYPE);// 设置文件类型
            ftpClient.enterLocalPassiveMode();
            ftpClient.setRemoteVerificationEnabled(false);
        }
    }

    /**
     * 设置传输文件类型
     *
     * @param fileType 传输文件类型
     * @throws IOException io异常
     */
    public void setFileType(int fileType) throws IOException {
        this.connectServer();
        ftpClient.setFileType(fileType);
    }

    /**
     * 关闭远程服务器连接
     *
     * @throws IOException IO异常
     */
    private void closeServer() throws IOException {
        if (ftpClient != null && ftpClient.isConnected()) {
            ftpClient.logout();
            ftpClient.disconnect();
            ftpClient = null;
        }
        log.info("关闭服务器连接成功！！！");
    }

    /**
     * 切换FTP服务器工作路径
     *
     * @param path 远程服务器路径
     * @return Boolean 切换结果
     * @throws IOException IO异常
     */
    public boolean changeDirectory(String path) throws IOException {
        this.connectServer();
        return ftpClient.changeWorkingDirectory(path);
    }

    /**
     * 在FTP服务器上创建目录
     *
     * @param pathName 待创建目录
     * @return Boolean 创建结果
     * @throws IOException IO异常
     */
    public boolean createDirectory(String pathName) throws IOException {
        this.connectServer();
        return ftpClient.makeDirectory(pathName);
    }

    /**
     * 在FTP服务器上删除目录
     *
     * @param path 待删除目录
     * @return Boolean 删除结果
     * @throws IOException IO异常
     */
    private boolean removeDirectory(String path) throws IOException {
        this.connectServer();
        return ftpClient.removeDirectory(path);
    }

    /**
     * 删除FTP服务器目录及目录下所有文件
     *
     * @param path  待删除目录
     * @param isAll 删除标志位 true-全部删除 flase-仅删除目录
     * @return Boolean 删除结果
     * @throws IOException IO异常
     */
    private boolean removeDirectory(String path, boolean isAll) throws IOException {
        this.connectServer();
        if (!isAll) {
            return removeDirectory(path);
        }
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr == null || ftpFileArr.length == 0) {
            return removeDirectory(path);
        }
        for (FTPFile ftpFile : ftpFileArr) {
            String name = ftpFile.getName();
            if (ftpFile.isDirectory()) {
                log.info("正在删除子目录:{}", path + "/" + name);
                removeDirectory(path + "/" + name, true);
            } else if (ftpFile.isFile()) {
                log.debug("正在删除文件:{}", path + "/" + name);
                deleteFile(path + "/" + name);
            }
        }
        return ftpClient.removeDirectory(path);
    }

    /**
     * 判断文件在FTP服务器是否存在
     *
     * @param path FTP服务器目录
     * @param name 文件名
     * @return Boolean 是否存在
     * @throws IOException IO异常
     */
    public boolean existFile(String path, String name) throws IOException {
        this.connectServer();
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        for (FTPFile ftpFile : ftpFileArr) {
            if (ftpFile.isFile()
                    && ftpFile.getName().equalsIgnoreCase(name)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    /**
     * 获取FTP服务器指定目录下的所有文件名及子目录名
     *
     * @param path FTP服务器目录
     * @return List
     * @throws IOException IO异常
     */
    public List<String> getFileList(String path) throws IOException {
        this.connectServer();
        ftpClient.enterLocalPassiveMode();//ftp设置为主动模式
        FTPFile[] ftpFiles = ftpClient.listFiles(path);
        List<String> retList = new ArrayList<>();
        if (ftpFiles == null || ftpFiles.length == 0) {
            return retList;
        }
        for (FTPFile ftpFile : ftpFiles) {
            if (ftpFile.isFile()) {
                retList.add(ftpFile.getName());
            }
        }
        return retList;
    }

    /**
     * 获取FTP服务器指定目录下的所有文件及子目录
     *
     * @param path FTP服务器目录
     * @return List
     * @throws IOException IO异常
     */
    private List<FTPFile> getFtpFileList(String path) throws IOException {
        this.connectServer();
        ftpClient.enterLocalPassiveMode();
        FTPFile[] ftpFiles = ftpClient.listFiles(path);
        List<FTPFile> retList = new ArrayList<>();
        if (ftpFiles == null || ftpFiles.length == 0) {
            return retList;
        }
        for (FTPFile ftpFile : ftpFiles) {
            if (ftpFile.isFile()) {
                retList.add(ftpFile);
            }
        }
        return retList;
    }

    /**
     * 删除FTP服务器上的文件
     *
     * @param fileName 待删除文件
     * @return Boolean 删除结果
     * @throws IOException IO异常
     */
    public boolean deleteFile(String fileName) throws IOException {
        this.connectServer();
        boolean flag = ftpClient.deleteFile(fileName);
        this.closeServer();
        return flag;
    }

    /**
     * 上传文件至FTP服务器
     *
     * @param localFilePath  本地文件全路径
     * @param remoteFileName 远程文件名
     * @return 上传结果
     * @throws IOException IO异常
     */
    public boolean uploadFile(String localFilePath, String remoteFileName) throws IOException {
        boolean flag = false;
        try (InputStream iStream = new FileInputStream(localFilePath)) {
            this.connectServer();
            flag = ftpClient.storeFile(remoteFileName, iStream);
            if (flag) {
                log.info("上传文件【{}】至FTP服务器成功", localFilePath);
            } else {
                log.info("上传文件【{}】至FTP服务器失败", localFilePath);
            }
        } catch (IOException e) {
            log.error("上传文件【{}】至FTP服务器失败:{} ", localFilePath, e.getMessage());
            return false;
        } finally {
            this.closeServer();
        }
        return flag;
    }

    /**
     * 从FTP服务器上下载文件到本地
     *
     * @param remoteFileName FTP服务器上文件名
     * @param localFileName  本地文件全路径
     * @return Boolean 下载结果
     * @throws IOException IO异常
     */
    public boolean download(String remoteFileName, String localFileName) throws IOException {
        boolean flag = false;
        File localFile = new File(localFileName);
        if (!localFile.getParentFile().exists()) {
            localFile.getParentFile().mkdirs();
        }
        localFile.createNewFile();
        try (OutputStream oStream = new FileOutputStream(localFile);) {
            this.connectServer();
            ftpClient.enterLocalPassiveMode();// 设置ftp访问模式为被动模式
            flag = ftpClient.retrieveFile(remoteFileName, oStream);
            if (flag) {
                log.info("FTP下载文件【{}】至本地成功", remotepath + "/" + remoteFileName);
            } else {
                log.info("FTP下载文件【{}】至本地失败", remotepath + "/" + remoteFileName);
            }
        } catch (IOException e) {
            log.error("FTP下载文件【{}】至本地失败:{}", remotepath + "/" + remoteFileName, e.getMessage());
            return false;
        } finally {

            this.closeServer();
        }
        return flag;
    }

    /**
     * 根据文件名下载文件至基础路径
     *
     * @param remoteFileName 远程文件全路径
     * @throws IOException IO异常
     */
    public void downloadFileByFileName(String remoteFileName) throws IOException {
        this.download(remoteFileName, localpath + "/" + remoteFileName);
    }

    /**
     * 根据文件名前缀匹配下载文件
     *
     * @param prefix 文件名前缀
     * @throws IOException IO异常
     */
    public void downloadByFileNamePrefix(String prefix) throws IOException {
        List<String> fileNameList = this.getFileList(remotepath);
        for (String fileName : fileNameList) {
            if (fileName.startsWith(prefix)) {
                this.downloadFileByFileName(fileName);
            }
        }
    }

    /**
     * 根据文件名后缀匹配下载文件
     *
     * @param suffix 文件名后缀
     * @throws IOException IO异常
     */
    public void downloadByFileNameSuffix(String suffix) throws IOException {
        List<String> fileNameList = this.getFileList(remotepath);
        for (String fileName : fileNameList) {
            if (fileName.endsWith(suffix)) {
                this.downloadFileByFileName(fileName);
            }
        }
    }

    /**
     * 下载FTP服务器最新文件
     *
     * @throws IOException IO异常
     */
    public void downloadLastModifiedFile() throws IOException {
        List<FTPFile> ftpFileList = this.getFtpFileList(remotepath);
        Date lastMod = ftpFileList.get(0).getTimestamp().getTime();
        FTPFile ftpFile = ftpFileList.get(0);
        for (FTPFile file : ftpFileList) {
            if (file.getTimestamp().getTime().after(lastMod)) {
                ftpFile = file;
                lastMod = file.getTimestamp().getTime();
            }
        }
        String fileName = ftpFile.getName();
        this.downloadFileByFileName(fileName);
    }

    /**
     * 下载FTP服务器最旧文件
     *
     * @throws IOException IO异常
     */
    public void downloadFirstModifiedFile() throws IOException {
        List<FTPFile> ftpFileList = this.getFtpFileList(remotepath);
        Date lastMod = ftpFileList.get(0).getTimestamp().getTime();
        FTPFile ftpFile = ftpFileList.get(0);
        for (FTPFile file : ftpFileList) {
            if (file.getTimestamp().getTime().before(lastMod)) {
                ftpFile = file;
                lastMod = file.getTimestamp().getTime();
            }
        }
        String fileName = ftpFile.getName();
        this.downloadFileByFileName(fileName);
    }

    /**
     * 下载FTP服务器最大文件
     *
     * @throws IOException IO异常
     */
    public void downloadMaxSizeFile() throws IOException {
        List<FTPFile> ftpFileList = this.getFtpFileList(remotepath);
        long size = ftpFileList.get(0).getSize();
        FTPFile ftpFile = ftpFileList.get(0);
        for (FTPFile file : ftpFileList) {
            if (file.getSize() > size) {
                ftpFile = file;
                size = file.getSize();
            }
        }
        String fileName = ftpFile.getName();
        this.downloadFileByFileName(fileName);
    }

    /**
     * 下载FTP服务器最小文件
     *
     * @throws IOException IO异常
     */
    public void downloadMinSizeFile() throws IOException {
        List<FTPFile> ftpFileList = this.getFtpFileList(remotepath);
        long size = ftpFileList.get(0).getSize();
        FTPFile ftpFile = ftpFileList.get(0);
        for (FTPFile file : ftpFileList) {
            if (file.getSize() < size) {
                ftpFile = file;
                size = file.getSize();
            }
        }
        String fileName = ftpFile.getName();
        this.downloadFileByFileName(fileName);
    }

    /**
     * 从FTP服务器上下载文件流到本地
     *
     * @param sourceFileName 远程文件全路径
     * @return InputStream 文件流
     * @throws IOException IO异常
     */
    public InputStream downFile(String sourceFileName) throws IOException {
        this.connectServer();
        ftpClient.enterLocalPassiveMode();
        InputStream inputStream = ftpClient.retrieveFileStream(sourceFileName);
        this.closeServer();
        return inputStream;
    }

    /**
     * 上传文件至FTP服务器
     *
     * @param localFileName 本地文件名
     * @return 上传结果
     */
    public boolean uploadWithResult(String localFileName) {
        boolean flag = false;
        try {
            String localFilePath = localpath + '/' + localFileName;
            flag = this.uploadFile(localFilePath, localFileName);
        } catch (IOException e) {
            log.error("关闭FTP服务器连接异常：{}", e.getMessage());
            flag = false;
        }
        return flag;
    }

    /**
     * 下载FTP服务器文件至本地
     *
     * @param remoteFileName 远程文件名
     * @return 下载结果
     */
    public boolean downloadWithResult(String remoteFileName) {
        boolean flag = false;
        try {
            String localFilePath = localpath + '/' + remoteFileName;
            flag = this.download(remoteFileName, localFilePath);
        } catch (IOException e) {
            log.error("关闭FTP服务器连接异常：{}", e.getMessage());
            flag = false;
        }
        return flag;
    }
}

