package com.club.web.works.constants;


/**
 * Created by lifei on 2016/9/4.
 */
public enum  WorksCategoryEnum {
    /**
     * 作品分类类型
     *
     */
    音乐("music");


    private String name;


    private WorksCategoryEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
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
