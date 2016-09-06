package com.club.web.stock.vo;

import java.util.Date;
import java.util.List;

import com.club.web.stock.constant.CargoClassifyStatus;

/**
 * 货品分类Vo
 * @author zhuzd
 *
 */
public class CargoClassifyVo {
	
	private String id;
	
    private String name;
    
    private String parentId;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;
    
    private Integer status;

    private Integer orderIndex;

    private CargoClassifyVo parent;
    
    private List<CargoClassifyVo> children;
    
    private String imgUrl;

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
    public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}    
	
	public String getStatusText() {
        return CargoClassifyStatus.getTextByDbData(this.status);
    }
	
	public CargoClassifyVo getParent() {
		return parent;
	}

	public void setParent(CargoClassifyVo parent) {
		this.parent = parent;
	}	

	public List<CargoClassifyVo> getChildren() {
		return children;
	}

	public void setChildren(List<CargoClassifyVo> children) {
		this.children = children;
	}

	public String getParentName(){
		String parentName = "";
		if(this.getParent() != null){
			parentName = this.getParent().getName();
		}
        return parentName;
	}
	
	public String getFullName() {
		String fullName = this.getName();
		if(this.getParent() != null){
			fullName = this.getParent().getFullName()+"--"+fullName;
		}
        return fullName;
    }
}
