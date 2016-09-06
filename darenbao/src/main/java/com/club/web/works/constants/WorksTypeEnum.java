package com.club.web.works.constants;

/**
 * Created by lifei on 2016/9/4.
 */
public enum  WorksTypeEnum {
    /**
     * 作品分类类型
     *
     */
    视频("video"),文章("article");


    private String name;


    private WorksTypeEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    public static WorksTypeEnum getByName(String name){
        if(name!=null&&!name.isEmpty()){
            for (WorksTypeEnum c : WorksTypeEnum.values()) {
                if (c.getName().equals(name)) {
                    return c;
                }
            }
        }

        return null;
    }
}
