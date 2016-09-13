package com.compses.action.api.system;

import com.compses.framework.ResultData;
import com.compses.model.Dict;
import com.compses.service.api.system.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by nini on 2016/3/11.
 */

@Controller
@RequestMapping("dict")
public class DictController {


    @Autowired
    private DictService dictService;


    @RequestMapping(value = "listDict.do")
    @ResponseBody
    public List<Dict> listDict(String type ,Integer pid)  {
        Dict dict =new Dict();
        dict.setTypes(type);
        dict.setPid(pid);
        return    dictService.loadData(dict);
    }

    @RequestMapping(value = "listAllDict.do")
    @ResponseBody
    public ResultData selectAll(){
        ResultData resultData = new ResultData();
        List<Dict> dictList = dictService.selectAll();
        resultData.putEntities(Dict.class,dictList);
        resultData.setReturnMsg(true,"获取成功！");
        return resultData;
    }
}
