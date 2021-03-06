/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.club.web.store.vo;

import java.util.Date;

/**
 *@Title:
 *@Description:
 *@Author:Czj
 *@Since:2016年3月25日
 *@Version:1.1.0
 */
public class TradeGoodVo {
	
	    private Long id;

	    private Long headstoreId;

	    private Long cargoId;

	    private String name;

	    private String describe;

	    private Long sort;

	    private Integer type;

	    private Long category;

	    private String post;

	    private Double postFee;

	    private Long creator;

	    private Date createTime;
	    
	    private Integer status;
	    
	    private Integer saleNum;
	    
	    private Long imgSquare;

	    private Long imgRectangle;
	    
	    private Date beginTime;

	    private Date endTime;
	    
	    private Long postid;
	    
	    public Long getPostid() {
			return postid;
		}

		public void setPostid(Long postid) {
			this.postid = postid;
		}

		public Date getBeginTime() {
			return beginTime;
		}

		public void setBeginTime(Date beginTime) {
			this.beginTime = beginTime;
		}

		public Date getEndTime() {
			return endTime;
		}

		public void setEndTime(Date endTime) {
			this.endTime = endTime;
		}

		public Long getImgSquare() {
			return imgSquare;
		}

		public void setImgSquare(Long imgSquare) {
			this.imgSquare = imgSquare;
		}

		public Long getImgRectangle() {
			return imgRectangle;
		}

		public void setImgRectangle(Long imgRectangle) {
			this.imgRectangle = imgRectangle;
		}

		public Long getId() {
	        return id;
	    }

	    public void setId(Long id) {
	        this.id = id;
	    }

	    public Long getHeadstoreId() {
	        return headstoreId;
	    }

	    public void setHeadstoreId(Long headstoreId) {
	        this.headstoreId = headstoreId;
	    }

	    public Long getCargoId() {
	        return cargoId;
	    }

	    public void setCargoId(Long cargoId) {
	        this.cargoId = cargoId;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name == null ? null : name.trim();
	    }

	    public String getDescribe() {
	        return describe;
	    }

	    public void setDescribe(String describe) {
	        this.describe = describe == null ? null : describe.trim();
	    }

	    public Long getSort() {
	        return sort;
	    }

	    public void setSort(Long sort) {
	        this.sort = sort;
	    }

	    public Integer getType() {
	        return type;
	    }

	    public void setType(Integer type) {
	        this.type = type;
	    }

	    public Long getCategory() {
	        return category;
	    }

	    public void setCategory(Long category) {
	        this.category = category;
	    }

	    public String getPost() {
	        return post;
	    }

	    public void setPost(String post) {
	        this.post = post == null ? null : post.trim();
	    }

	    public Double getPostFee() {
	        return postFee;
	    }

	    public void setPostFee(Double postFee) {
	        this.postFee = postFee;
	    }

	    public Long getCreator() {
	        return creator;
	    }

	    public void setCreator(Long creator) {
	        this.creator = creator;
	    }

	    public Date getCreateTime() {
	        return createTime;
	    }

	    public void setCreateTime(Date createTime) {
	        this.createTime = createTime;
	    }
	    
	    public Integer getStatus() {
	        return status;
	    }

	    public void setStatus(Integer status) {
	        this.status = status;
	    }

		public Integer getSaleNum() {
			return saleNum;
		}

		public void setSaleNum(Integer saleNum) {
			this.saleNum = saleNum;
		}
	
}
