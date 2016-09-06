package com.club.core.db.dao.mapper;

import java.util.List;
import java.util.Map;

public interface IBaseMapper {

    Map<String,Object> selectOne(Map<String, Object> params);
    List<Map<String,Object>> selectList(Map<String, Object> params);
    int insert(Map<String, Object> params);
    int update(Map<String, Object> params);
    int delete(Map<String, Object> params);
}
