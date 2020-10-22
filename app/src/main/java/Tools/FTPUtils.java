package Tools;

import android.util.Log;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class FTPUtils {

    //ftp服务器地址
    private String hostname = "";
    //ftp服务器端口号默认为21
    private Integer port = 21;
    //ftp登录账号
    private String username = "";
    //ftp登录密码
    private String password = "";
    //超时时间
    public int timeOut = 6;
    //被动模式开关 如果不开被动模式 有防火墙 可能会上传失败， 但被动模式需要ftp支持
    public boolean enterLocalPassiveMode = true;

    private FTPClient ftpClient = null;

    private static FTPUtils mFTPUtils = null;


    public FTPUtils() {

    }

    public static FTPUtils getInstance() {
        if (mFTPUtils == null) {
            synchronized (FTPUtils.class) {
                if (mFTPUtils == null) {
                    mFTPUtils = new FTPUtils();
                }
            }
        }

        return mFTPUtils;
    }


    /**
     * 初始化配置  全局只需初始化一次
     *
     * @param hostname ftp服务器地址
     * @param port     ftp服务器端口号默认为21
     * @param username ftp登录账号
     * @param password ftp登录密码
     */
    public void initFtpClient(String hostname, int port, String username, String password) {
        this.hostname = hostname;
        this.port = port;
        this.username = username;
        this.password = password;

        //初始化ftpclient对象
        ftpClient = new FTPClient();
        //设置超时时间以毫秒为单位使用时，从数据连接读。
        ftpClient.setDefaultTimeout(timeOut * 10000);
        ftpClient.setConnectTimeout(timeOut * 10000);
        ftpClient.setDataTimeout(timeOut * 10000);
        ftpClient.setControlEncoding("utf-8");
    }


    /**
     * 连接并登陆ftp
     *
     * @return 是否成功
     */
    public boolean connectFtp() {
        try {
            Log.e("FTP", "连接...FTP服务器...");
            ftpClient.connect(hostname, port); //连接ftp服务器
            //是否开启被动模式
            if (enterLocalPassiveMode) {
                ftpClient.setRemoteVerificationEnabled(false);
                ftpClient.enterLocalPassiveMode();
            }
            ftpClient.login(username, password); //登录ftp服务器
            int replyCode = ftpClient.getReplyCode(); //是否成功登录服务器
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                Log.e("--------->", "连接...FTP服务器...失败: " + this.hostname + ":" + this.port + "");
                return false;
            }
            Log.e("FTP", "连接...FTP服务器...成功:" + this.hostname + ":" + this.port);
            return true;
        } catch (IOException e) {
            Log.e(e.getMessage(), e + "");
        }
        return false;
    }


    /**
     * 上传文件
     *
     * @param ftpSavePath     ftp服务保存地址  (不带文件名)
     * @param ftpSaveFileName 上传到ftp的文件名
     * @param originFile      待上传文件
     * @return  是否上传成功
     */
    public boolean uploadFile(String ftpSavePath, String ftpSaveFileName, File originFile) {
        boolean flag = false;
        try {
            FileInputStream inputStream = new FileInputStream(originFile);
            flag = uploadFile(ftpSavePath, ftpSaveFileName, inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("FTP", e.getMessage() + "  " + e);
        }
        return flag;
    }


    /**
     * 上传文件
     *
     * @param ftpSavePath     ftp服务保存地址  (不带文件名)
     * @param ftpSaveFileName 上传到ftp的文件名
     * @param originFileName  待上传文件的名称（绝对地址） *
     * @return  是否上传成功
     */
    public boolean uploadFile(String ftpSavePath, String ftpSaveFileName, String originFileName) {
        boolean flag = false;

        try {
            FileInputStream inputStream = new FileInputStream(new File(originFileName));
            flag = uploadFile(ftpSavePath, ftpSaveFileName, inputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.e("------------>", e.getMessage() + "  " + e);
        }
        return flag;
    }

    /**
     * 上传文件(直接读取输入流形式)
     *
     * @param ftpSavePath     ftp服务保存地址
     * @param ftpSaveFileName 上传到ftp的文件名
     * @param inputStream     输入文件流
     * @return 是否上传成功
     */
    public boolean uploadFile(String ftpSavePath, String ftpSaveFileName, InputStream inputStream) {
        boolean flag = false;
        try {
            //第一次进来,将上传路径设置成相对路径
            if (ftpSavePath.startsWith("/")) {
                ftpSavePath = ftpSavePath.substring(1);
            }
            Log.e("FTP", "上传文件的路径 :" + ftpSavePath);
            Log.e("FTP", "上传文件名 :" + ftpSaveFileName);
            //初始化FTP服务器
            connectFtp();
            Log.e("FTP","开始上传文件...");
            //设置文件类型,图片为二进制
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            //创建文件路径
            if (!CreateDirecroty(ftpSavePath)) {
                return flag;
            }
            flag = ftpClient.storeFile(new String(ftpSaveFileName.getBytes("GBK"), StandardCharsets.ISO_8859_1), inputStream);
            inputStream.close();
            ftpClient.logout();
        } catch (Exception e) {
            Log.e("FTP", e.getLocalizedMessage());
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    Log.e(e.getMessage(), e + "");
                }
            }
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    Log.e("FTP", e.getLocalizedMessage());
                }
            }
            Log.e("FTP", "上传文件结束...结果 :" + (flag ? "成功" : "失败 "));
        }
        return flag;
    }


    //改变目录路径
    public boolean changeWorkingDirectory(String directory) {
        boolean flag = true;
        try {
            flag = ftpClient.changeWorkingDirectory(directory);
            if (!flag) {
                Log.e("FTP","所在的目录 : " + ftpClient.printWorkingDirectory() + " 进入下一级 " + directory + " 目录失败");
            } else {
                Log.e("FTP", "进入目录成功，当前所在目录 :" + ftpClient.printWorkingDirectory());
            }
        } catch (IOException ioe) {
            Log.e(ioe.getMessage(), ioe + "");
        }
        return flag;
    }

    //创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
    public boolean CreateDirecroty(String remote) throws IOException {
        String directory = remote + "/";
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(directory)) {
            int start, end;
            if (directory.startsWith("/")) start = 1;
            else start = 0;

            //从第一个"/"索引之后开始得到下一个"/"的索引
            end = directory.indexOf("/", start);
            do {
                Log.e("FTP", "所在的目录 :" + ftpClient.printWorkingDirectory());
                String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), StandardCharsets.ISO_8859_1);
                if (!existFile(subDirectory)) {
                    if (makeDirectory(subDirectory))
                        if (!changeWorkingDirectory(subDirectory)) return false;
                        else return false;
                } else {
                    if (!changeWorkingDirectory(subDirectory)) return false;
                }

                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
            } while (end > start);
        }
        return true;
    }

    //判断ftp服务器文件是否存在
    public boolean existFile(String path) throws IOException {
        boolean flag = false;
        FTPFile[] ftpFileArr = ftpClient.listFiles(path);
        if (ftpFileArr.length > 0) {
            flag = true;
        }
        return flag;
    }

    //创建目录
    public boolean makeDirectory(String dir) {
        boolean flag = true;
        try {
            flag = ftpClient.makeDirectory(dir);
            if (!flag) {
                Log.e("FTP", "所在的目录 : " + ftpClient.printWorkingDirectory() + " 创建下一级 " + dir + " 目录失败 ");
            } else {
                Log.e("FTP", "所在的目录 : " + ftpClient.printWorkingDirectory() + " 创建下一级 " + dir + " 目录成功 ");
            }
        } catch (Exception e) {
            Log.e(e.getMessage(), e + "");
        }
        return flag;
    }

    /**
     * 下载文件 *
     *
     * @param pathname  FTP服务器文件目录 *
     * @param filename  文件名称 *
     * @param localpath 下载后的文件路径 *
     * @return  是否下载成功
     */
    public boolean downloadFile(String pathname, String filename, String localpath) {
        boolean flag = false;
        OutputStream os = null;
        try {
            connectFtp();
            //第一次进来,将上传路径设置成相对路径
            if (pathname.startsWith("/")) {
                pathname = pathname.substring(1);
            }
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            //切换FTP目录
            changeWorkingDirectory(pathname);

            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles) {
                if (filename.equalsIgnoreCase(file.getName())) {
                    File localFile = new File(localpath + "/" + file.getName());
                    os = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(file.getName(), os);
                    os.close();
                }
            }
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            Log.e("下载：", e.getLocalizedMessage());
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    Log.e(e.getMessage(), e + "");
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    Log.e("下载：", e.getLocalizedMessage());
                }
            }
        }
        Log.e("FTP", "下载文件结束...结果 :" + (flag ? "成功" : "失败 "));
        return flag;
    }

    /**
     * 删除文件 *(未测试)
     *
     * @param pathname FTP服务器保存目录 *
     * @param filename 要删除的文件名称 *
     * @return  是否删除成功
     */
    public boolean deleteFile(String pathname, String filename) {
        boolean flag = false;
        try {
            //第一次进来,将上传路径设置成相对路径
            if (pathname.startsWith("/")) {
                pathname = pathname.substring(1);
            }
            connectFtp();
            //切换FTP目录
            changeWorkingDirectory(pathname);
            ftpClient.dele(filename);
            ftpClient.logout();
            flag = true;
        } catch (Exception e) {
            Log.e("FTP", "删除文件失败 ");
            Log.e("FTP", e.getMessage());
        } finally {
            if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException e) {
                    Log.e(e.getMessage(), e + "");
                }
            }
        }
        return flag;
    }

    /**
     * 文件转成 byte[]
     *
     * @param inStream  文件输入流
     * @return  输出的byte[]
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    public static byte[] input2byte(InputStream inStream) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[inStream.available()];
        int rc;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        swapStream.close();
        return in2b;
    }

    /**
     * 转化输出的编码
     */
    private static String toFtpFilename(String fileName) throws Exception {
        return new String(fileName.getBytes(StandardCharsets.UTF_8), "ISO8859-1");
    }

    public String setDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }

    public void disconnectFtp(){
        try {
            if (ftpClient.isConnected()) ftpClient.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("FTP", e.getLocalizedMessage());
        }
    }
}