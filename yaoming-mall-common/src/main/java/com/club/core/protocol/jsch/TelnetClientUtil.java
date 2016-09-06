package com.club.core.protocol.jsch;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.apache.commons.net.telnet.TelnetClient;

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
import com.club.framework.util.Utils;

/**
 * <Description>Telnet客户端 <br>
 * 
 * @author hu.bo<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2015年8月31日 <br>
 * @since V1.0<br>
 */
public class TelnetClientUtil {
    private static final ClubLogManager logger = ClubLogManager
            .getLogger(TelnetClientUtil.class);
    private TelnetClient telnetClient;
    private InputStream in;  
    private PrintStream out;  
    private char promptChar = '$'; 
    private String prompt = ">"; //结束标识字符串,Windows中是>,Linux中是# 
    public TelnetClient getTelnetClient(String ip, int port, String user, String password){
        telnetClient=new TelnetClient("VT100");  // VT100 VT52 VT220 VTNT ANSI
        boolean valid = true;
        try {
            telnetClient.setDefaultTimeout(5000);
            telnetClient.connect(ip, port);
            in = telnetClient.getInputStream();  
            out = new PrintStream(telnetClient.getOutputStream()); 
            valid = login(user, password);
        }  catch (Exception e) {
            valid = false;
            logger.error(e);
        }
        if(!valid){
            disconnect();
            return null;
        }
        return telnetClient;
    }
    
    /** 
     * 关闭连接 
     */  
    public void disconnect(){  
        try {  
            if(telnetClient!=null&&telnetClient.isConnected())  
                telnetClient.disconnect();  
        } catch (IOException e) {  
            logger.error(e); 
        }  
    } 
    
    /** * 登录 * * @param user * @param password */  
    public boolean login(String user, String password) {  
        readUntil("login:");  
        write(user);  
        readUntil("password:");  
        write(password);  
        String rs = readUntil(null);  
        if(Utils.isEmpty(rs) || (rs.contains("failed") || rs.contains("incorrect"))){  
            return false;  
        } else{
            return true;  
        }
    } 
    
    /** * 读取分析结果 * * @param pattern * @return */  
    public String readUntil(String pattern) {  
        StringBuffer sb = new StringBuffer();  
        try {  
            char lastChar = (char)-1;  
            boolean flag = pattern!=null&&pattern.length()>0;  
            if(flag)  
                lastChar = pattern.charAt(pattern.length() - 1);  
            char ch;  
            Integer code = in.read();  
            while (code != null && code != -1) {  
                ch = (char)code.intValue();  
                sb.append(ch);  
                //匹配到结束标识时返回结果  
                if (flag) {  
                    if (ch == lastChar && sb.toString().toLowerCase().endsWith(pattern)) {  
                        return sb.toString();  
                    }  
                }else{  
                    //如果没指定结束标识,匹配到默认结束标识字符时返回结果  
                    if(ch == '$' || ch == '#' || ch == '>')  
                        return sb.toString();  
                }
                code = in.read();
                //登录失败时返回结果  
                String result = sb.toString().toLowerCase();
                if(result.contains("failed") || result.contains("incorrect")){  
                    return sb.toString();  
                }  
            }  
        } catch (Exception e) {  
            logger.error(e);
        }  
        return null; 
    }  
  
    /** * 写操作 * * @param value */  
    public void write(String value) {  
        try {  
            out.println(value);  
            out.flush();  
        } catch (Exception e) {  
            logger.error(e);
        }  
    }  
  
}
