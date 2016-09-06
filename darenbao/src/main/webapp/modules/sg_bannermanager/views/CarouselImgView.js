define([
    'text!modules/sg_bannermanager/templates/CarouselImgView.html',
    'modules/sg_bannermanager/add/views/AddCarouselImgView',
    'modules/sg_bannermanager/detail/views/DetailCarouselImgView',
    'Portal'
], function (CarouselImgViewTpl,AddCarouselImgView,DetailCarouselImgView,Portal) {
	var GRID_DOM = "#carousel-img-rule-content-div";
	return club.View.extend({
        template: club.compile(CarouselImgViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {
        },
        events:{
        	"click .carousel-img-manager .btn": "tbarHandler"
        },
        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
            this._initListView();
        },
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action"),selectRecord;
            var rowId= $(event.currentTarget).parent().attr("id");
            var record= $(GRID_DOM).grid("getRowData", rowId);
            var id=record.id;
            switch(action){
                case 'search-carousel-img' : 
                	this.search();
                	break;
                case 'carousel-img-del-btn' :
                	this.delAll();
                	break;
                case 'carousel-img-add-btn' :
                	AddCarouselImgView.openAddWin({
                        callback:this
                    },action,'');
                	break;
                case 'carousel-del-btn':
                	this.del(id);
                	break;
                case 'carousel-sort-btn':
                	this.update(id);
                	break;
                case 'carousel-edit-btn':
                  	AddCarouselImgView.openAddWin({
                        callback:this
                    },action,record);
                	break;
                case 'carousel-img-enable-btn':
                	var records=$(GRID_DOM).grid('getCheckRows');
                	var ids='';
                	if(records==null||records.length<1){
                		return club.toast('warn', '请选择要操作的记录!');
                	}else{
                		var flag=false;
                		for(var i=0;i<records.length;i++){
                			if(records[i].status!='0'){
                				flag=true;
                				break;
                			}
                		}
                		if(flag){
                			club.toast('warn', '操作选中记录包含有已启用状态，请确认！');
                		}else{
                    		for(var i=0;i<records.length;i++){
                    			ids+=records[i].id;
                    			if(i!=records.length-1){
                    				ids+=',';
                    			}
                    		}
                    		this.upateStatus(ids,1);
                		}
                	}
                	break;
                case 'carousel-img-disable-btn':
                	var records=$(GRID_DOM).grid('getCheckRows');
                	var ids='';
                	if(records==null||records.length<1){
                		return club.toast('warn', '请选择要操作的记录!');
                	}else{
                		var flag=false;
                		for(var i=0;i<records.length;i++){
                			if(records[i].status!='1'){
                				flag=true;
                				break;
                			}
                		}
                		if(flag){
                			club.toast('warn', '操作选中记录包含有已禁用状态，请确认！');
                		}else{
                    		for(var i=0;i<records.length;i++){
                    			ids+=records[i].id;
                    			if(i!=records.length-1){
                    				ids+=',';
                    			}
                    		}
                    		this.upateStatus(ids,0);
                		}
                	}
                	break;
                case 'carousel-detail-btn':
                	DetailCarouselImgView.openDetWin({
                        callback:this
                    },action,record);
                	break;
                case 'carousel-refresh':
                	this.refresh();
                	break;
            }
        },
        upateStatus:function(ids,status){
        	var me = this;
        	var t= club.confirm('您确定要执行此操作吗？');
        	 t.result.then(function resolve(){
	    		 $.post(Portal.webRoot+'/carousel/updateCarouselImgStatus.do',{
	                 conditionStr:JSON.stringify({
	                     'ids':ids,'status':status
	                     })},function(result){
	                     if(result!=null){
	                       var resultCode=result.code;
	                       if(resultCode=='000000'){
	                    	  club.toast('info',result.msg );
	                     	  me.refresh();
	                       }else{
	                    	   club.toast('warn',result.msg );
	                       }
	                     }else{
	                    	 club.toast('warn','操作失败');
	                     }
	             });
     	 }, function reject(){
     		 return;
     	 });
        },
        update:function(id){
        	var me=this;
        	var t=club.prompt('请填写排序数字:');	
        	$('.ui-draggable .form-control').keyup(function(){
        		this.value=this.value.replace(/[^0-9\.]/,''); 
        		if(!(/^\d+$/.test(this.value))){
        			this.value='';
        		}
        	});
        	t.result.then(function resolve(val){
        		if(val==''||val==null||val==undefined){
        			club.toast('warn', '请填写排序数字!');
        		}else{
        			var vd=parseInt(val);
		    		 $.post(Portal.webRoot+'/carousel/updateCarouselImg.do',{
		                 conditionStr:JSON.stringify({
		                     'id':id,'updateCount':vd
		                     })},function(result){
		                     if(result!=null){
		                       var resultCode=result.code;
		                       if(resultCode=='000000'){
		                     	 club.toast('info',result.msg );
		                     	  me.refresh();
		                       }else{
		                    	   club.toast('warn',result.msg );
		                       }
		                     }else{
		                    	 club.toast('warn','操作失败');
		                     }
		             });
        		}
       	 	}, function reject(){
       	 		return;
       	 	});
        },
        delAll:function(){
        	var records=$(GRID_DOM).grid('getCheckRows');
        	var ids='';
        	if(records==null||records.length<1){
        		return club.toast('warn', '请选择要删除的记录!');
        	}else{
        		for(var i=0;i<records.length;i++){
        			ids+=records[i].id;
        			if(i!=records.length-1){
        				ids+=',';
        			}
        		}
        		this.del(ids);
        	}
        },
        del:function(ids){
        	if(ids==null||ids==''||ids==undefined){
        		 club.toast('warn', '请先选择要删除的记录');
        		 return;
        	}else{
            	var me = this;
            	var t= club.confirm('您确定要删除选中的记录吗？');
            	 t.result.then(function resolve(){
            		 $.post(Portal.webRoot+'/carousel/delCarouselImg.do',{
             			conditionStr:JSON.stringify({
             				'ids':ids
             			})},function(result){
             				if(result!=null){
             					var resultCode=result.code;
             					if(resultCode=='000000'){
             						club.toast('info',result.msg );
             						me.refresh();
             					}else{
             						 club.toast('warn',result.msg );
             					}
             				}else{
             					club.toast('warn','操作失败');
             				}
             			});
         	 }, function reject(){
         		 return;
         	 });
        	}
        },
        search:function(){
        	 this.pageData(1);
        },
        refresh:function(){
        	$('#carousel_img_status').val('-1');
        	 $('input[name=carousel_img_param]').val('');
            this.pageData(1);
        },
        pageData :function (page, rowNum, sortname, sortorder) {
            var matchParam = $('input[name=carousel_img_param]').val();
            var status = $('select[name=carousel_img_status]').val();
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/carousel/queryCarouselImg.do',{
                conditionStr:JSON.stringify({
                    'matchParam':'%'+matchParam+'%','status':status}
                    ),start:(page-1)*rowNum,limit:rowNum},function(result){
                    result = Portal.convertPage(result);
                    result.page = page;
                    $(GRID_DOM).grid("reloadData", result);
            });
            return false;
        },
        _initListView:function(){
        	//参数配置
            var opt = {
        		height:$(window).height()-200,
                width:this.$el.width(),
                rownumbers: true, 
                colModel: [{
                    name: 'id',
                    sorttype: "int",
                    hidden:true,
                    key:true
                    
                }, {
                    name: 'icon',
                    hidden:true
                }, {
                    name: 'subClient',
                    hidden:true
                },{
                    name: 'remk',
                    label: '标题',
                    width: 120,
                    align: "center",
                    title: false 
                }, {
                    name: 'status',
                    label: '状态',
                    width:100,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval==0) {return '禁用'};
                        if (cellval==1) {return '启用'};
                        return cellval;
                    }
                },  {
                    name: 'lineStatus',
                    label: '广告类型',
                    width: 100,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval==0) {return '内容'};
	                    if (cellval==1) {return '商品推荐'};
                        return cellval;
                    }
                },{
                    name: 'category',
                    label: '类别',
                    width: 120,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval==0) {return '首页轮播图'};
                        return cellval;
                    }
                },{
                    name: 'sort',
                    label: '排序',
                    width: 100,
                    align: "center"
	            },{
                    name: 'createTime',
                    label: '创建时间',
                    width: 125,
                    align: "center"
	            },{
                    name: '',
                    label: '操 作',
                    width: 200,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                    	var text='<a class="btn" action="carousel-detail-btn" style="width:0px">明细</a>';
                    		/*text+='<a class="btn" action="carousel-sort-btn" style="width:50px">排序</a>';*/
                    		text+='<a class="btn" action="carousel-edit-btn" style="width:50px">编辑</a>';
                    		text+='<a class="btn" action="carousel-del-btn" style="width:50px">删除</a>';
                    		return '<div style="color:red;" id="'+opts.rowId+'">'+text+'</div>';
                    }
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