package com.club.web.works.constants;


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

    public static WorksCategoryEnum getByName(String name){
        if(name!=null&&!name.isEmpty()){
            for (WorksCategoryEnum c : WorksCategoryEnum.values()) {
                if (c.getName().equals(name)) {
                    return c;
                }
            }
        }

        return null;
    }



}
