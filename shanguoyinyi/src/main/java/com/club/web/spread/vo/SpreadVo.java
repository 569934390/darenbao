package com.club.web.spread.vo;

import java.util.Date;

public class SpreadVo {
	   
	    private String id;

	    private String name;

	    private String classifyId;

	    private Long createBy;

	    private Date updateTime;

	    private Long updateBy;

	    private Long readNum;

	    private String author;

	    private Integer spreadContentType;

	    private Integer contentType;

	    private String content;
	    
	    private String goodId;

	    private Long shareNum;

	    private Long collectNum;
	    
	    //用于前端展示推广类型
	    private String classifyName;
	    //用于前端展示推广内容类型(店铺还是商品)
	    private String spreadContentTypeName;
	    //货品编号
	    private String cargoNo;
	    //商品名称
	    private String goodName;
	    
	    private String headshopId;
	    //图片id
	    private String logo;
	    //图片url
	    private String url;
	    
	    private String updateTime1;
	    
	    public String getUpdateTime1() {
			return updateTime1;
		}

		public void setUpdateTime1(String updateTime1) {
			this.updateTime1 = updateTime1;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getLogo() {
			return logo;
		}

		public void setLogo(String logo) {
			this.logo = logo;
		}
	    
		public String getHeadshopId() {
			return headshopId;
		}

		public void setHeadshopId(String headshopId) {
			this.headshopId = headshopId;
		}

		public String getCargoNo() {
			return cargoNo;
		}

		public void setCargoNo(String cargoNo) {
			this.cargoNo = cargoNo;
		}

		public String getGoodName() {
			return goodName;
		}

		public void setGoodName(String goodName) {
			this.goodName = goodName;
		}

		public String getGoodId() {
			return goodId;
		}

		public void setGoodId(String goodId) {
			this.goodId = goodId;
		}

		public Long getShareNum() {
			return shareNum;
		}

		public void setShareNum(Long shareNum) {
			this.shareNum = shareNum;
		}

		public Long getCollectNum() {
			return collectNum;
		}

		public void setCollectNum(Long collectNum) {
			this.collectNum = collectNum;
		}

		public String getSpreadContentTypeName() {
			return spreadContentTypeName;
		}

		public void setSpreadContentTypeName(String spreadContentTypeName) {
			this.spreadContentTypeName = spreadContentTypeName;
		}

		public String getClassifyName() {
			return classifyName;
		}

		public void setClassifyName(String classifyName) {
			this.classifyName = classifyName;
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

	    public String getClassifyId() {
	        return classifyId;
	    }

	    public void setClassifyId(String classifyId) {
	        this.classifyId = classifyId;
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

	    public Long getReadNum() {
	        return readNum;
	    }

	    public void setReadNum(Long readNum) {
	        this.readNum = readNum;
	    }

	    public String getAuthor() {
	        return author;
	    }

	    public void setAuthor(String author) {
	        this.author = author == null ? null : author.trim();
	    }

	    public Integer getSpreadContentType() {
	        return spreadContentType;
	    }

	    public void setSpreadContentType(Integer spreadContentType) {
	        this.spreadContentType = spreadContentType;
	    }

	    public Integer getContentType() {
	        return contentType;
	    }

	    public void setContentType(Integer contentType) {
	        this.contentType = contentType;
	    }

	    public String getContent() {
	        return content;
	    }

	    public void setContent(String content) {
	        this.content = content == null ? null : content.trim();
	    }
}
