package com.club.core.protocol.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.club.core.protocol.pool.JSchSessionFactory;
import com.club.core.protocol.pool.JSchSessionKeyedObjectPool;
import com.club.core.protocol.pool.ServerConfig;
import com.club.framework.exception.BaseAppException;
import com.club.framework.exception.ExceptionHandler;
import com.club.framework.log.ClubLogManager;

/**
 * <Description>基于JSch的SSH客户端 <br>
 * 
 * @author pan.xiaobo<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2015年8月31日 <br>
 * @since V1.0<br>
 * @see com.club.core.protocol.jsch <br>
 */
public class JSchClient {
    private static final ClubLogManager logger = ClubLogManager
            .getLogger(JSchClient.class);

    /**
     * 根据配置获取JSch Session
     * 
     * @param serverConfig
     * @return
     * @throws BaseAppException
     */
    public static Session creatSession(ServerConfig serverConfig)
            throws BaseAppException {
        Session session = null;
        try {
            session = JSchSessionKeyedObjectPool.getInstance().getPool()
                    .borrowObject(serverConfig);
        }
        catch (Exception e) {
            logger.error(
                    "ERROR: Unrecoverable error when trying to get session to serverConfig :  "
                            + serverConfig, e);
            throw ExceptionHandler.publish("APP-00-0081",
                    "ERROR: Unrecoverable error when trying to get session to serverConfig :  "
                            + serverConfig, e);
        }
        return session;
    }

    /**
     * 根据配置获取JSch Session
     * 
     * @param host 主机
     * @param user 主机用户
     * @param password 主机用户密码
     * @param port 端口
     * @return
     * @throws BaseAppException
     */
    public static Session creatSession(String host, String user,
            String password, int port) throws BaseAppException {
        Session session = null;
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setHost(host);
        serverConfig.setUser(user);
        serverConfig.setPassword(password);
        serverConfig.setPort(port);

        try {
            session = JSchSessionKeyedObjectPool.getInstance().getPool()
                    .borrowObject(serverConfig);
        }
        catch (Exception e) {
            logger.error(
                    "ERROR: Unrecoverable error when trying to get session to serverConfig :  "
                            + serverConfig, e);
            throw ExceptionHandler.publish("APP-00-0081",
                    "ERROR: Unrecoverable error when trying to get session to serverConfig :  "
                            + serverConfig, e);
        }
        return session;
    }

    /**
     * 根据配置获取JSch Session，使用默认端口22
     * 
     * @param host
     * @param user
     * @param password
     * @return
     * @throws BaseAppException
     */
    public static Session creatSession(String host, String user, String password)
            throws BaseAppException {
        return creatSession(host, user, password, 22);
    }

    /**
     * 从JSch Session，获取ChannelExec
     * 
     * @param session
     * @return
     * @throws BaseAppException
     */
    public static Channel getExecChannel(Session session)
            throws BaseAppException {
        Channel channel;
        try {
            channel = session.openChannel("exec");
            return channel;
        }
        catch (JSchException e) {
            logger.error("ERROR: Cann't open ChannelExec from this session : ",
                    e);
            throw ExceptionHandler.publish("APP-00-0082",
                    "ERROR: Cann't open ChannelExec from this session : ", e);
        }

    }

    /**
     * 从JSch Session，获取ChannelShell
     * 
     * @param session
     * @return
     * @throws BaseAppException
     */
    public static Channel getShellChannel(Session session)
            throws BaseAppException {
        Channel channel;
        try {
            channel = session.openChannel("shell");
            return channel;
        }
        catch (JSchException e) {
            logger.error(
                    "ERROR: Cann't open ChannelShell from this session : ", e);
            throw ExceptionHandler.publish("APP-00-0082",
                    "ERROR: Cann't open ChannelShell from this session : ", e);
        }

    }

    /**
     * 从JSch Session，获取ChannelSftp
     * 
     * @param session
     * @return
     * @throws BaseAppException
     */
    public static Channel getSftpChannel(Session session)
            throws BaseAppException {
        Channel channel;
        try {
            channel = session.openChannel("sftp");
            return channel;
        }
        catch (JSchException e) {
            logger.error("ERROR: Cann't open ChannelSftp from this session : ",
                    e);
            throw ExceptionHandler.publish("APP-00-0082",
                    "ERROR: Cann't open ChannelSftp from this session : ", e);
        }

    }

    /**
     * 关闭Channel
     * 
     * @param channel
     */
    public static void closeChannel(Channel channel) {
        if (channel != null) {
            channel.disconnect();
        }
    }

    /**
     * 把JSch Session返回给连接池
     * 
     * @param host
     * @param user
     * @param password
     * @param port
     * @param session
     * @throws BaseAppException
     */
    public static void returnSession(String host, String user, String password,
            int port, Session session) throws BaseAppException {
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setHost(host);
        serverConfig.setUser(user);
        serverConfig.setPassword(password);
        serverConfig.setPort(port);

        if (session != null) {
            try {
                JSchSessionKeyedObjectPool.getInstance().getPool()
                        .returnObject(serverConfig, session);
            }
            catch (Exception e) {
                logger.error("ERROR: Return session to pool cast exception : ",
                        e);
                throw ExceptionHandler.publish("APP-00-0083",
                        "ERROR: Return session to pool cast exception : ", e);
            }
        }
    }

    /**
     * 把JSch Session返回给连接池
     * 
     * @param serverConfig
     * @param session
     * @throws BaseAppException
     */
    public static void returnSession(ServerConfig serverConfig, Session session)
            throws BaseAppException {
        if (session != null) {
            try {
                JSchSessionKeyedObjectPool.getInstance().getPool()
                        .returnObject(serverConfig, session);
            }
            catch (Exception e) {
                logger.error("ERROR: Return session to pool cast exception : ",
                        e);
                throw ExceptionHandler.publish("APP-00-0083",
                        "ERROR: Return session to pool cast exception : ", e);
            }
        }
    }

    /**
     * 通过ChannelExec执行命令
     * 
     * @param channel
     * @param command
     * @return 返回结果
     * @throws IOException
     * @throws JSchException
     */
    public static String execNormalCommand(ChannelExec channel, String command)
            throws IOException, JSchException {
        return execNormalCommand(channel, command, 30000);
    }

    /**
     * 通过ChannelExec执行命令
     * 
     * @param channel
     * @param command
     * @param connectTimeout
     * @return
     * @throws IOException
     * @throws JSchException
     */
    public static String execNormalCommand(ChannelExec channel, String command,
            int connectTimeout) throws IOException, JSchException {
        channel.setCommand(command);
        InputStream in = channel.getInputStream();
        channel.connect(connectTimeout);
        byte[] tmp = new byte[1024];
        StringBuffer buffer = new StringBuffer();
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0)
                    break;
                buffer.append(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                if (in.available() > 0)
                    continue;
                break;
            }
        }
        return buffer.toString();
    }

    /**
     * 通过ChannelShell执行命令
     * 
     * @param channel
     * @param command
     * @return
     * @throws IOException
     * @throws JSchException
     */
    public static String execShellCommand(ChannelShell channel, String command)
            throws IOException, JSchException {

        return execShellCommand(channel, command, 30000);
    }

    /**
     * 通过ChannelShell执行命令
     * 
     * @param channel
     * @param command
     * @param connectTimeout
     * @return
     * @throws IOException
     * @throws JSchException
     */
    public static String execShellCommand(ChannelShell channel, String command,
            int connectTimeout) throws IOException, JSchException {
        channel.connect(connectTimeout);

        // 获取输入流和输出流
        InputStream in = channel.getInputStream();
        OutputStream out = channel.getOutputStream();

        // 发送需要执行的SHELL命令，需要用\n结尾，表示回车
        command += " \n";
        out.write(command.getBytes());
        out.flush();

        byte[] tmp = new byte[1024];
        StringBuffer buffer = new StringBuffer();
        while (true) {
            while (in.available() > 0) {
                int i = in.read(tmp, 0, 1024);
                if (i < 0)
                    break;
                buffer.append(new String(tmp, 0, i));
            }
            if (channel.isClosed()) {
                if (in.available() > 0)
                    continue;
                break;
            }
        }
        out.close();
        in.close();
        return buffer.toString();
    }
}
