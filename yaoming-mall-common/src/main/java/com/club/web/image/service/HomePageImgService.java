package com.club.web.image.service;

import java.util.Map;

import com.club.core.common.Page;

public interface HomePageImgService {
Page getimgList(Page page);
int saveOrUpdateImg(Long CreatorId,Long groupId,String imgs);
int deletHomePageImg(String imgIds);
Page selectMoreColumnImg(Page page);
}
