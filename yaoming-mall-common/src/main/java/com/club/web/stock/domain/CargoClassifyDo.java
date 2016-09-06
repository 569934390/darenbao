package com.club.web.stock.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.club.web.stock.constant.ClassifyConstant;
import com.club.web.stock.domain.repository.CargoClassifyRepository;

/**
 * 货品分类Do
 * @author zhuzd
 *
 */
@Configurable
public class CargoClassifyDo {
	
	private Long id;

    private String name;

    private Date createTime;

    private Long createBy;

    private Date updateTime;

    private Long updateBy;
    
    private Long parentId;
    
    private Integer status;

    private Integer orderIndex;
    
    private String imgUrl;
    
    @Autowired 
    private CargoClassifyRepository repository;
    
    /**
     * 获取父节点
     * @return
     */
	public CargoClassifyDo getParent() {
		return this.parentId != null ? this.repository.findDoById(this.parentId):null;
	}
	
	/**
     * 获取子节点
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<CargoClassifyDo> getChildrens() {
		return this.id != null ? repository.findDoByParentId(this.id):Collections.EMPTY_LIST;
	}
	
	public List<Long> getAllIds(Integer status){
		List<Long> ids = new ArrayList<Long>();
		if(status == null || status.equals(this.getStatus())){
			if(ClassifyConstant.CARGO_CLASSIFY != this.getId()){
				ids.add(this.getId());
			}
			List<CargoClassifyDo> childrens = this.getChildrens();
			if(childrens != null && childrens.size() != 0){
				for (CargoClassifyDo cargoClassifyDo : childrens) {
					ids.addAll(cargoClassifyDo.getAllIds(status));
				}
			}
		}
		return ids;
	}
	
	public void save() {
		repository.add(this);
	}

	public void update() {
		repository.modify(this);
	}

	public void updateStop(Date updateTime,long userId,int status) {
		for (CargoClassifyDo children : this.getChildrens()) {
			children.updateStop(updateTime,userId,status);
		}
		this.setStatus(status);
		this.setUpdateBy(userId);
		this.setUpdateTime(updateTime);
		repository.updateStatus(this);
	}

	public void updateStart(Date updateTime, long userId, int status) {
		CargoClassifyDo parent = this.getParent();
		if(parent != null){
			parent.updateStart(updateTime, userId, status);
		}
		this.setStatus(status);
		this.setUpdateBy(userId);
		this.setUpdateTime(updateTime);
		repository.updateStatus(this);
	}
	
    
    public int delete() {
    	int i = 1;
		if(this.id != null){
			for(CargoClassifyDo childrenDo : this.getChildrens()){
				childrenDo.delete();
			}
			i = repository.delete(this);
		}
		return i;
	}
	
	public CargoClassifyDo() {}

    
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

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
    
    public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
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

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

}
