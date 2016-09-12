package com.compses.dao.system;


import com.compses.model.Dict;

import java.util.List;

/**
 * Created by nini on 2016/3/16.
 */
public interface IDictDao {

    public List<Dict> selectDictList(String type, int pid);

    public List<Dict> selectAll();
}
