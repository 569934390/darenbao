/**
 * 
 */
package com.club.core.protocol.pool;

import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;
import com.club.framework.exception.BaseAppException;
import com.club.framework.exception.ExceptionHandler;
import com.club.framework.log.ClubLogManager;

/**
 * <Description>JSchSession的工厂类 <br>
 * 
 * @author pan.xiaobo<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2015年8月19日 <br>
 * @since V1.0<br>
 * @see com.club.core.pool <br>
 */

public class JSchSessionFactory extends
        BaseKeyedPoolableObjectFactory<ServerConfig, Session> {
    private static final ClubLogManager logger = ClubLogManager
            .getLogger(JSchSessionFactory.class);

    /**
     * This creates a Session if not already present in the pool.
     */
    @Override
    public Session makeObject(ServerConfig serverConfig)
            throws BaseAppException {
        Session session = null;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(serverConfig.getUser(),
                    serverConfig.getHost(), serverConfig.getPort());
            session.setConfig("StrictHostKeyChecking",
                    serverConfig.getStrictHostKeyChecking()); //
            UserInfo userInfo = new JSchUserInfo(serverConfig.getUser(),
                    serverConfig.getPassword());
            session.setUserInfo(userInfo);
            session.setTimeout(serverConfig.getTimeout());
            session.setPassword(serverConfig.getPassword());
            session.connect();
        }
        catch (Exception e) {
            logger.error(
                    "ERROR: Unrecoverable error when trying to connect to serverConfig :  "
                            + serverConfig, e);
            throw ExceptionHandler.publish("APP-00-0080",
                    "ERROR: Unrecoverable error when trying to connect to serverConfig :  "
                            + serverConfig, e);

        }
        return session;
    }

    /**
     * This is called when closing the pool object
     */
    @Override
    public void destroyObject(ServerConfig serverConfig, Session session) {
        session.disconnect();
    }

    /*
     * (non-Javadoc)
     * @see org.apache.commons.pool.BaseKeyedPoolableObjectFactory#validateObject(java.lang.Object, java.lang.Object)
     */
    @Override
    public boolean validateObject(ServerConfig key, Session session) {
        return session.isConnected();
    }

    /*
     * (non-Javadoc)
     * @see org.apache.commons.pool.BaseKeyedPoolableObjectFactory#activateObject(java.lang.Object, java.lang.Object)
     */
    @Override
    public void activateObject(ServerConfig key, Session obj) throws Exception {
        super.activateObject(key, obj);
    }

    /*
     * (non-Javadoc)
     * @see org.apache.commons.pool.BaseKeyedPoolableObjectFactory#passivateObject(java.lang.Object, java.lang.Object)
     */
    @Override
    public void passivateObject(ServerConfig key, Session obj) throws Exception {
        super.passivateObject(key, obj);
    }

}
