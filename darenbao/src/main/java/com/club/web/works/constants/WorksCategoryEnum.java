package com.club.web.works.constants;


import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lifei on 2016/9/4.
 */
public enum  WorksCategoryEnum {
    /**
     * 作品分类类型
     *
     */
    热门(1),电影(2),体育(3),游戏(4),音乐(5);


    private Integer name;


    private WorksCategoryEnum(Integer name) {
        this.name = name;
    }

    public Integer getName() {
        return name;
    }


    public void setName(Integer name) {
        this.name = name;
    }

    public static WorksCategoryEnum getByName(Integer name){
        if(name!=null){
            for (WorksCategoryEnum c : WorksCategoryEnum.values()) {
                if (c.getName().equals(name)) {
                    return c;
                }
            }
        }

        return null;
    }

    public static String toJsonString(){
        WorksCategoryEnum[] categoryEnum=WorksCategoryEnum.values();
        Map<Integer,String> map=new HashMap<>();
        for (WorksCategoryEnum worksCategoryEnum : categoryEnum) {
            map.put(worksCategoryEnum.name,worksCategoryEnum.toString());
        }
        return JSON.toJSONString(map);
    }



}
