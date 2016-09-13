package com.compses.dao.impl.order;

import com.compses.dao.impl.BaseCommonDAO;
import com.compses.dao.order.ICommentInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/8/14 0014.
 */
@Repository
public class CommentInfoDao extends BaseCommonDAO implements ICommentInfoDao{

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
}
