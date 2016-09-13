package com.club.web.works.constants;

/**
 * Created by lifei on 2016/9/4.
 */
public enum  WorksTypeEnum {
    /**
     * 作品分类类型
     *
     */
    视频(1),文章(2);


    private Integer name;


    private WorksTypeEnum(Integer name) {
        this.name = name;
    }

    public Integer getName() {
        return name;
    }


    public void setName(Integer name) {
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
