package com.club.web.stock.vo;

public class ClassifyColumnVo {
	
	private String id;

    private String classifyId;
    
    private String name;

    private String imgUrl;

    private String classifyName;    
    
    private Integer orderIndex = 0;
    
    private Integer deleteFlag;

    private Integer effectFlag;
    
    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl == null ? null : imgUrl.trim();
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassifyId() {
		return classifyId;
	}

	public void setClassifyId(String classifyId) {
		this.classifyId = classifyId;
	}
	
	public Integer getOrderIndex() {
        return orderIndex;
    }

    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }

	public String getClassifyName() {
		return classifyName;
	}

	public void setClassifyName(String classifyName) {
		this.classifyName = classifyName;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getStatusText(){
		return this.deleteFlag != null && this.deleteFlag > 0?(this.effectFlag != null && this.effectFlag > 0 ? "已启用" : "已禁用"):"已删除";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getEffectFlag() {
		return effectFlag;
	}

	public void setEffectFlag(Integer effectFlag) {
		this.effectFlag = effectFlag;
	}
	
}
