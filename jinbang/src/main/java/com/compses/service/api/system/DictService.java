package com.compses.service.api.system;


import com.compses.dao.system.IDictDao;
import com.compses.model.Dict;
import com.compses.redis.util.RedisKeyConfig;
import com.compses.redis.util.RedisStringUtil;
import com.compses.redis.util.RedisSystemParameterUtil;
import com.compses.service.common.BaseCommonService;
import com.compses.util.JsonUtils;
import com.compses.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nini on 2016/3/11.
 */

@Service
@Transactional
public class DictService extends BaseCommonService {

    @Autowired
    private BaseCommonService baseCommonService;

    @Autowired
    private IDictDao dictDao;

    public List<Dict> selectDictList(String type,int pid){
        List<String> dictJsons = RedisSystemParameterUtil.mget(RedisKeyConfig.DICT_PREFIX+type);
        List<Dict> dictList = new ArrayList<Dict>();
        if (dictJsons.size()!=0){
            for (String dictJson :dictJsons){
                Dict dict = JsonUtils.toBean(dictJson,Dict.class);
                dictList.add(dict);
            }
        }else {
            dictList = dictDao.selectDictList(type,pid);
        }
        return dictList;
    }

    public List<Dict> selectAll(){
        List<Dict> dictList = dictDao.selectAll();
        return dictList;
    }

    public Dict selectOne(Dict dict){
        List<String> dictJson = RedisSystemParameterUtil.hmget(RedisKeyConfig.DICT_PREFIX+dict.getTypes(),dict.getValueField());
        if(dictJson.size()!=0){
            dict = JsonUtils.toBean(dictJson.get(0),Dict.class);
        }else {
            dict = baseCommonService.loadData(dict).get(0);
        }
        return dict;
    }
}

