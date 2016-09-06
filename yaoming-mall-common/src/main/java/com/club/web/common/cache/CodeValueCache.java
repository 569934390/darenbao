package com.club.web.common.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.club.framework.util.Utils;
import com.club.web.common.Constants;
import com.club.web.common.db.arg.CodeValueArg;
import com.club.web.common.db.arg.CodeValueArg.CodeValueCriteria;
import com.club.web.common.db.dao.CodeValueDao;
import com.club.web.common.db.po.CodeValuePO;

public class CodeValueCache{
    @Autowired
    private CodeValueDao codeValueDao;
    private static Map<String,List<CodeValuePO>> codeValues = new HashMap<String, List<CodeValuePO>>();
    private static Map<String,String> codeValueNames = new HashMap<String, String>();
    /**
     *  获取codeValue对应的中文名
     * @author  hu.bo<br>
     * @param codeType
     * @param codeValue
     * @return <br>
     */
    public static String findCodeName(String codeType,String codeValue) {
        StringBuffer sb = new StringBuffer();
        sb.append(codeType).append("_").append(codeValue);
        return codeValueNames.get(sb.toString());
    }

    /**
     * 获取codeType下的所有CodeValue数据
     * @author  hu.bo<br>
     * @param codeType
     * @return <br>
     */
    public static List<CodeValuePO> findCodeValues(String codeType) {
        return codeValues.get(codeType);
    }
    
    public void init() {
        iniCodeValue(null);
    }
    
    public void refresh(String codeType) {
        if(Utils.isEmpty(codeType)){
            codeValues.clear();
        }else{
            codeValues.get(codeType).clear();
        }
        iniCodeValue(codeType);
    }
    
    public void iniCodeValue(String codeType){
        CodeValueArg arg = new CodeValueArg();
        CodeValueCriteria criteria = arg.createCriteria();
        if(Utils.notEmpty(codeType)){
            criteria.andCodeTypeEqualTo(codeType);
        }
        criteria.andStateEqualTo(Constants.EFF_STATE);
        List<CodeValuePO> datas = codeValueDao.selectByArg(arg);
        if(Utils.notEmpty(datas)){
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < datas.size(); i++) {
                sb.setLength(0);
                CodeValuePO po = datas.get(i);
                sb.append(po.getCodeType()).append("_").append(po.getCodeValue());
                List<CodeValuePO> list = codeValues.get(po.getCodeType());
                if(list == null){
                    list = new ArrayList<CodeValuePO>();
                    codeValues.put(po.getCodeType(), list);
                }
                list.add(po);
                codeValueNames.put(sb.toString(), po.getCodeName());
            }
        }
    }
}
