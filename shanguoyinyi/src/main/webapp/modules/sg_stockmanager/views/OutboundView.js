define([
    'text!modules/sg_stockmanager/templates/OutboundView.html',
    'modules/sg_stockmanager/detail/views/OutboundDetailView',
    'modules/sg_stockmanager/add/views/AddOutboundWindow',
    'modules/sg_stockmanager/update/views/OutboundUpdateView',
    'Portal'
], function (OutboundViewTpl,OutboundDetailView,AddOutboundWindow,OutboundUpdateView,Portal) {
	var GRID_DOM = "#outbound-rule-content-div";
	return club.View.extend({
        template: club.compile(OutboundViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {
        },
        events:{
        	"click .outbound-manager .btn": "tbarHandler"
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
            var desc=record.applyDesc;
            switch(action){
                case 'search-outbound' : this.search();break;
                case 'outbound-add-rule': this.add(action);break;
                case 'out-query-detail': this.queryDetail(action,id,desc);break;
                case 'apply-outbound': this.applyOutbound(action,id);break;
                case 'sure-outbound': this.sureOutbound(action,id);break;
                case 'outbound-del': this.delOutbound(action,id);break;
                case 'out-cancle-apply': this.cancleApply(action,id);break;
                case 'outbound-sure-rule-all': this.sureOutboundAll(action);break;
                case 'outbound-apply-rule-all': this.applyOutboundAll(action);break;
                case 'outbound-cancle-rule-all': this.cancleApplyAll(action);break;
                case 'outbound-delete-rule-all': this.delOutboundAll(action);break;
                case 'out-edit-rule': this.edt(action,id,desc);break;
                case 'out-bound-refresh':
                		this.refresh();
                		break;
            }
        },
        queryDetail:function(action,id,desc){
        	OutboundDetailView.openDetailWin({
                callback:this
            },desc,id);
        },
        edt:function(action,id,desc){
        	OutboundUpdateView.openUpdateWin({
        		callback:this
        	},action,id);
        },
        cancleApply:function(action,id){
        	 var me = this;
        	var t=club.prompt('请填写取消入库原因:');
        	t.result.then(function resolve(val){
        		if(val==''||val==null||val==undefined){
        			club.toast('warn', '请填写取消的原因!');
        		}else{
		    		 $.post(Portal.webRoot+'/stock/outbound/updateOutboundStatus.do',{
		                 conditionStr:JSON.stringify({
		                     'ids':id,'status':1,'desc':val
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
		                    	 club.toast('warn','操作失败' );
		                     }
		             });
        		}
       	 	}, function reject(){
       	 		return;
       	 	});
        },
        applyOutbound:function(action,id){
        	 var me = this
        	var t= club.confirm('您确定要申请出库吗？');
        	 t.result.then(function resolve(){
         		 $.post(Portal.webRoot+'/stock/outbound/updateOutboundStatus.do',{
                     conditionStr:JSON.stringify({
                         'ids':id,'status':2
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
                        	 club.toast('warn','操作失败' );
                         }
                   });
        	 }, function reject(){
        		 return;
        	 });
        },
        sureOutboundAll:function(action){
        	var records=$(GRID_DOM).grid('getCheckRows');
        	var ids='';
        	if(records==null||records.length<1){
        		return club.toast('warn', '请选择要操作的记录!');
        	}else{
        		var flag=false;
        		for(var i=0;i<records.length;i++){
        			if(records[i].status!='2'||records[i].outboundType=='0'){
        				flag=true;
        				break;
        			}
        		}
        		if(flag){
        			club.toast('warn', '操作选中记录包含有其它状态，请确认！');
        		}else{
            		for(var i=0;i<records.length;i++){
            			ids+=records[i].id;
            			if(i!=records.length-1){
            				ids+=',';
            			}
            		}
            		this.sureOutbound(action,ids);
        		}
        	}
        	
        },
        applyOutboundAll:function(action){
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
        			club.toast('warn', '操作选中记录包含有其它状态，请确认！');
        		}else{
            		for(var i=0;i<records.length;i++){
            			ids+=records[i].id;
            			if(i!=records.length-1){
            				ids+=',';
            			}
            		}
            		this.applyOutbound(action,ids);
        		}
        	}
        	
        },
        delOutboundAll:function(action){
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
        			club.toast('warn', '操作选中记录包含有其它状态，请确认！');
        		}else{
            		for(var i=0;i<records.length;i++){
            			ids+=records[i].id;
            			if(i!=records.length-1){
            				ids+=',';
            			}
            		}
            		this.delOutbound(action,ids);
        		}
        	}
        	
        },
        cancleApplyAll:function(action){
        	var records=$(GRID_DOM).grid('getCheckRows');
        	var ids='';
        	if(records==null||records.length<1){
        		return club.toast('warn', '请选择要操作的记录!');
        	}else{
        		var flag=false;
        		for(var i=0;i<records.length;i++){
        			if(records[i].status!='2'||records[i].outboundType=='0'){
        				flag=true;
        				break;
        			}
        		}
        		if(flag){
        			club.toast('warn', '操作选中记录包含有其它状态，请确认！');
        		}else{
            		for(var i=0;i<records.length;i++){
            			ids+=records[i].id;
            			if(i!=records.length-1){
            				ids+=',';
            			}
            		}
            		this.cancleApply(action,ids);
        		}
        	}
        	
        },
        sureOutbound:function(action,id){
        	var me = this;
        	var t= club.confirm('您确定要执行此操作吗？');
	       	 t.result.then(function resolve(){
	      		$.post(Portal.webRoot+'/stock/outbound/updateOutboundStatus.do',{
	    			conditionStr:JSON.stringify({
	    				'ids':id,'status':3
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
	    					club.toast('warn','操作失败' );
	    				}
    			});
    	 }, function reject(){
    		 return;
    	 });
        },
        delOutbound:function(action,id){
        	var me = this;
        	var t= club.confirm('您确定要执行此操作吗？');
        	 t.result.then(function resolve(){
        		 $.post(Portal.webRoot+'/stock/outbound/delOutboundOrder.do',{
         			conditionStr:JSON.stringify({
         				'ids':id
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
         					club.toast('warn','操作失败' ); 
         				}
         			});
     	 }, function reject(){
     		 return;
     	 });
        },
        search:function(){
        	 this.pageData(1);
        },
        refresh:function(){
        	$('#outbound_status').val('0')
             $('input[name=outbound_match_param]').val('');
             this.pageData(1);
        },
        add:function(action){
        	AddOutboundWindow.openAddWin({
                callback:this
            },action,'','');
        },
        pageData :function (page, rowNum, sortname, sortorder) {
            var matchParam = $('input[name=outbound_match_param]').val();
            var status = $('select[name=outbound_status]').val();
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/stock/outbound/queryOutboundOrderList.do',{
                conditionStr:JSON.stringify({
                    'matchParam':'%'+matchParam+'%',
                    'status':status}),start:(page-1)*rowNum,limit:rowNum},function(result){
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
                    name: 'id',
                    label: '出库单号',
                    width: 150,
                    align: "center",
                    title: false 
                },{
                    name: 'sourceNo',
                    label: '来源单号',
                    width:150,
                    align: "center"
                }, {
                    name: 'outboundType',
                    label: '出库类型',
                    width: 65,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval==0) {return '订单出库'};
                        if (cellval==1) {return '手动出库'};
                        return cellval;
                    }
                },{
                    name: 'status',
                    label: '状态',
                    width: 65,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval==1) {return '待申请'};
                        if (cellval==2) {return '待出库'};
                        if (cellval==3) {return '已出库'};
                        if (cellval==4) {return '已取消'};
                        return cellval;
                    }
                },{
                    name: 'createBy',
                    label: '出库人',
                    width: 100,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval==''||cellval==null) {
                        	return '系统'
                        }
                        return cellval;
                    }
                },{
                    name: 'subTime',
                    label: '提交时间',
                    width: 125,
                    align: "center"
	            },{
	            	name: 'outboundTime',
	            	label: '出库时间',
	            	width: 125,
	            	align: "center"
	            },{
	            	name: 'remarks',
	            	label: '备注',
	            	width: 100,
	            	align: "center"
	            },{
	            	name: 'applyDesc',
	            	label: '取消原因',
	            	hidden:true,
	            	width: 10
	            },{
                    name: '',
                    label: '操 作',
                    width: 240,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                    	var text='<a class="btn" action="out-query-detail" style="width:50px">明细</a>';
                    	if(rwdat.outboundType==1){
	                    	if(rwdat.status==1){
	                    		text+='<a class="btn" action="apply-outbound" style="width:80px;padding-left:10px;">申请出库</a><a class="btn" action="out-edit-rule" style="width:40px;margin-left:0px;padding-left:0px">编辑</a><a class="btn" action="outbound-del" style="width:40px;margin-left:0px;padding-left:0px">删除</a>';
	                    	}else if(rwdat.status==2){
	                    		text+='<a class="btn" action="sure-outbound" style="width:50px">确认</a><a class="btn" action="out-cancle-apply" style="width:80px">取消出库</a>';
	                    	}
                    	}
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
        },
 	});
});