/**
 * @description 定义常量
 * 
 * @author pan.xiaobo
 */
Ext.define('common.WEBConstants', {
    alternateClassName : 'WEBConstants',
    statics : {
        DEFAULT_OFFSET : 0,// Ext分页查询默认的开始记录
        DEFAULT_PAGE_SIZE : 20,// Ext分页查询默认的每页记录数
        BASE_URL : webRoot+'base/getList.do',// 基础查询
        FIELD_DEFAULT_WIDTH:230,//通用表单字段宽度
        FIELD_REQUIRED_WIDTH:7,
        REQUIRED : '<td   valign="middle" width="7"><div  style="color:red;padding-left:1px;font-size:14px;">*</div></td>',
        BLANK:'<td  valign="middle" width="7"><div >&nbsp;&nbsp;</div></td>',
        DEFAULT_DATE_FORMAT : 'Y-m-d', // 默认日期格式化
        DEFAULT_DATETIME_FORMAT : 'Y-m-d H:i:s', // 默认日期时间格式化
        PAGE_ROOT : "resultList", // 分页返回的记录列表属性，对应Page.java
        PAGE_TOTAL : "totalRecords", // 分页数据总记录数返回属性 ，对应Page.java

        // 页面当前的操作类型，用于Form与Grid交互时传递动作
        ACTIONTYPE : {
            VIEW : "VIEW",// 显示
            NEW : "NEW",// 新增
            EDIT : "EDIT",// 编辑
            DELETE : "DELETE",// 删除,
            SELECT : "SELECT", //选择
            NOBUTTON : "NOBUTTON" //不需要按钮
        },

        // 前后台结合的，查询条件操作符，传回后台生成Arg
        OPERATION : {
            IsNull : "IsNull", // XXXX is null
            IsNotNull : "IsNotNull", // XXXX is not null
            EqualTo : "EqualTo", // 等于 =
            NotEqualTo : "NotEqualTo", // 不等于 <>
            GreaterThan : "GreaterThan", // 大于 >
            GreaterThanOrEqualTo : "GreaterThanOrEqualTo", // 大于等于 >=
            LessThan : "LessThan", // 小于 <
            LessThanOrEqualTo : "LessThanOrEqualTo", // 小于等于 <=
            Like : "Like", // 模糊查询 XXXX like %value%
            NotLike : "NotLike", // XXXX not like %value%
            LeftLike : "LeftLike", // XXXX like %value
            NotLeftLike : "NotLeftLike", // XXXX not like %value
            RightLike : "RightLike", // XXXX like value%
            NotRightLike : "NotRightLike", // XXXX not like value%
            In : "In", // in ()
            NotIn : "NotIn", // not in ()
            Between : "Between", // 范围 between
            NotBetween : "NotBetween"// not between
        },
        
        DATA_SHOW_TYPE : {
        	PieChart : "pie",
        	BarGraph : "bar",
        	CurveGraph : "line",
        	List : "list",
        	Report : "list",
        	Web : "list"
        },
        
        CONNECT_TYPE : {
        	telnet : 1,
        	ftp : 2,
        	ssh : 3,
        	oracle : 4,
        	informix : 5,
        	mysql : 6
        },
        STATUS_DATAS : [['有效', '00A'], ['无效', '00X']],
        DATAS : {'状态':"有效@00A,无效@00X",'性别':"男@1,女@2"},
        EFF_VALUE : '00A',
        fileType:"doc,docx,xls,xlsx,ppt,pptx,pdf,txt,rar,zip,tar,gz,7z"
    }
});
