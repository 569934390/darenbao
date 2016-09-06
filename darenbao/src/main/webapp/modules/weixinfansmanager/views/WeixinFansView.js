define([
    'text!modules/weixinfansmanager/templates/WeixinFansView.html',
    'Portal'
], function (WeixinFansViewTpl,Portal) {
	var GRID_DOM = "#weixin-fans-content-div";
	return club.View.extend({
        template: club.compile(WeixinFansViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {
            // '.memberinfo-content-div': new memberinfoImageListViewTpl()
        },
        events:{
        	"click .weixin-fans-main-btngroup .btn": "tbarHandler"
        },
        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
            this._initListView();
            $('.weixin-fans-main-btngroup input[field=datetime]').datetimepicker({
                format: 'yyyy-mm-dd'
            });
            var startTime = new Date(),endTime = new Date();
            startTime.setDate(startTime.getDate()-90);
            endTime.setDate(endTime.getDate()+1);
            $('.weixin-fans-main-btngroup input[name=istartTime]').datetimepicker('value',club.dateutil.format(startTime,'yyyy-mm-dd'));
            $('.weixin-fans-main-btngroup input[name=iendTime]').datetimepicker('value',club.dateutil.format(endTime,'yyyy-mm-dd'));
        },
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action"),selectRecord;
            switch(action){
                case 'search' : this.search();break;
                case 'refresh': this.refresh();break;
            }
        },
        selectRecord:function(){
            return $(GRID_DOM).grid('getSelection');
        },
        selectRecords:function(){
            return $(GRID_DOM).grid('getCheckRows');
        },
        search:function(){
        	 this.pageData(1);
        },
        refresh:function(){
        	 console.log("刷新Grid列表");
             $('input[name=name]').val('');
             $('input[name=address]').val('');
             $('input[name=istartTime]').val('');
             $('input[name=iendTime]').val('');
             this.pageData(1);
        },
        pageData :function (page, rowNum, sortname, sortorder) {
            var name = $('input[name=name]').val();
            var address = $('input[name=address]').val();
            var startTime = $('input[name=istartTime]').val();
            var endTime = $('input[name=iendTime]').val();
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/client/weixinFans/weixinFansPage.do',{
                conditionStr:JSON.stringify({
                    'name':'%'+name+'%','address':'%'+address+'%','startTime':startTime,'endTime':endTime}),
                start:(page-1)*rowNum,limit:rowNum},function(result){
                result = Portal.convertPage(result);
                result.page = page;
                $(GRID_DOM).grid("reloadData", result);
            });
            return false;
        },_initListView:function(){ 
        	
        	//参数配置
            var opt = {
                height:$(window).height()-125,
                width:this.$el.width(),
                rownumbers:true,
                colModel: [{
                    name: 'id',
                    sorttype: "string",
                    hidden:true,
                    key:true
                }, {
                    name: 'nickname',
                    width:90,
                    label: '昵称',
                    align: "left"
                },{
                    name: 'userName',
                    width:90,
                    label: '用户名称',
                    align: "left"
                },{
                	 name: 'headimgurl',
                     width:90,
                     label: '头像',
                     align: "center",
                     formatter: function(cellval, opts, rwdat, _act) {
                    	 if(cellval)
                          return "<img src='"+cellval+"' style='width:60px;heigth:60px'/>";
                    	 else
                          return "";
                     }
                },{
                    name: 'sex',
                    width:40,
                    align: "center",
                    label: '性别',
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval=='1') {
                        	return '男'
                        }else if (cellval=='2'){
                        	return '女'
                        }else{
                        	return '未知';
                        }
                    }
                },{
                    name: 'tel',
                    width:90,
                    label: '手机号',
                    align: "left"
                },{
                    name: 'weixinNum',
                    width:90,
                    label: '微信号',
                    align: "left"
                },{
                    name: 'birthday',
                    width:90,
                    label: '生日',
                    align: "left"
                },{
                    name: 'address',
                    width:90,
                    label: '所在地区',
                    align: "left", 
                    formatter: function(cellval, opts, rwdat, _act) {
                    	var address="";
                    	if(rwdat.country!=null&&rwdat.country!=''){
                    		address+=rwdat.country;
                    	}
                    	if(rwdat.province!=null&&rwdat.province!=''){
                    		address+=(" "+rwdat.province);
                    	}
                    	if(rwdat.city!=null&&rwdat.city!=''){
                    		address+=(" "+rwdat.city);
                    	}
                    	if(address==""){
                    		address="未知";
                    	}
                        return address;
                    }
                },{
                    name: 'personSign',
                    width:90,
                    label: '个性签名',
                    align: "left"
                },{
                    name: 'createTime',
                    width:110,
                    label: '注册时间',
                    align: "left"
                }],pager: true
                ,rowNum: 20
                ,rowList: [10,20,50,100,200]
                ,datatype: "json"
                ,recordtext:"{0} 到 {1} 总共: {2} "
                ,pgtext : " {0} / {1}"
                ,rowtext:"{0}"
                ,gotext:'跳转至第{0}页'
                ,multiselect:true
                ,pageData:this.pageData
            };
            this.pageData(1);
            //加载grid
            $grid = $(GRID_DOM).grid(opt);
            $('.gotext').find('span').html('跳转至第<input class="ui-pagination-input">页');
        }
 	});
});