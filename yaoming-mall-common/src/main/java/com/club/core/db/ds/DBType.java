/**
 * 
 */
package com.club.core.db.ds;

/**
 * <Description>数据库类型 <br>
 * 
 * @author pan.xiaobo<br>
 * @version 1.0<br>
 * @taskId <br>
 * @CreateDate 2015年8月5日 <br>
 * @since V1.0<br>
 * @see com.club.core.db.ds <br>
 */

public enum DBType {
    MYSQL("mysql"), SQLSERVER2000("sqlserver2000"), SQLSERVER2005(
            "sqlserver2005"), ORACLE("oracle"), INFORMIX("informix"), TIMESTEN(
            "timesten"), MDB("mdb"), DB2("db2");
    private String typeName;

    private DBType(String typeName) {
        this.typeName = typeName;
    }

    public String toString() {
        return this.typeName;
    }

    /**
     * 根据类型名称，返回数据库类型实例
     * 
     * @param typeName
     * @return
     */
    public static DBType getInstance(String typeName) {
        for (DBType dbType : values()) {
            if (dbType.typeName.equals(typeName))
                return dbType;
        }
        return null;
    }
}
