package com.club.web.works.vo;

import com.club.web.common.vo.BaseVo;
import com.club.web.works.constants.WorksCategoryEnum;

/**
 * Created by lifei on 2016/9/4.
 */
public class PersonalWorksVo extends BaseVo{
    private long worksId;
    private String worksTitle;
    private String worksCover;
    private Integer worksCategory;
    private WorksCategoryEnum worksCategoryName;
    private Integer worksType;
    private String worksLabel;
    private String recommendedProductId;
    private String worksContent;
    private String worksLink;
    private Integer worksLikes;

    public long getWorksId() {
        return worksId;
    }

    public void setWorksId(long worksId) {
        this.worksId = worksId;
    }

    public String getWorksTitle() {
        return worksTitle;
    }

    public void setWorksTitle(String worksTitle) {
        this.worksTitle = worksTitle;
    }

    public String getWorksCover() {
        return worksCover;
    }

    public void setWorksCover(String worksCover) {
        this.worksCover = worksCover;
    }

    public Integer getWorksCategory() {
        return worksCategory;
    }

    public void setWorksCategory(Integer worksCategory) {
        this.worksCategory = worksCategory;
    }

    public WorksCategoryEnum getWorksCategoryName() {
        return WorksCategoryEnum.getByName(this.worksCategory);
    }


    public Integer getWorksType() {
        return worksType;
    }

    public void setWorksType(Integer worksType) {
        this.worksType = worksType;
    }

    public String getWorksLabel() {
        return worksLabel;
    }

    public void setWorksLabel(String worksLabel) {
        this.worksLabel = worksLabel;
    }

    public String getRecommendedProductId() {
        return recommendedProductId;
    }

    public void setRecommendedProductId(String recommendedProductId) {
        this.recommendedProductId = recommendedProductId;
    }

    public String getWorksContent() {
        return worksContent;
    }

    public void setWorksContent(String worksContent) {
        this.worksContent = worksContent;
    }


    public String getWorksLink() {
        return worksLink;
    }

    public void setWorksLink(String worksLink) {
        this.worksLink = worksLink;
    }

    public Integer getWorksLikes() {
        return worksLikes;
    }

    public void setWorksLikes(Integer worksLikes) {
        this.worksLikes = worksLikes;
    }
}
