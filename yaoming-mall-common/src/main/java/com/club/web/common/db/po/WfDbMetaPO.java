package com.club.web.common.db.po;

import java.util.*;
import org.apache.commons.lang.StringUtils;
import com.club.framework.dto.AbstractDto;

public class WfDbMetaPO extends AbstractDto{
	private String  dbName;
	private String  type;
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getDbName() {
        return StringUtils.isBlank(dbName) ? dbName : dbName.trim();
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return StringUtils.isBlank(type) ? type : type.trim();
    }
}