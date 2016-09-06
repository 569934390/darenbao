define([
    'text!modules/sg_stockmanager/templates/InboundView.html',
    'modules/sg_stockmanager/add/views/AddInboundWindow',
    'modules/sg_stockmanager/detail/views/InboundDetailView',
    'modules/sg_stockmanager/update/views/InboundUpdateView',
    'Portal'
], function (InboundViewTpl,AddInboundWindow,InboundDetailView,InboundUpdateView,Portal) {
	var GRID_DOM = "#inbound-rule-content-div";
	return club.View.extend({
        template: club.compile(InboundViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {
        },
        events:{
        	"click .inbound-manager .btn": "tbarHandler"
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
                case 'search-inbound' : this.search();break;
                case 'add-rule': this.add(action);break;
                case 'query-detail': this.queryDetail(action,id,desc);break;
                case 'apply-inbound': this.applyInbound(action,id);break;
                case 'sure-inbound': this.sureInbound(action,id);break;
                case 'inbound-del': this.delInbound(action,id);break;
                case 'cancle-apply': this.cancleApply(action,id);break;
                case 'sure-rule-all': this.sureInboundAll(action);break;
                case 'apply-rule-all': this.applyInboundAll(action);break;
                case 'cancle-rule-all': this.cancleApplyAll(action);break;
                case 'delete-rule-all': this.delInboundAll(action);break;
                case 'edit-rule': this.edt(action,id,desc);break;
                case 'in-bound-refresh':this.refresh();break;
            }
        },
        queryDetail:function(action,id,desc){
        	InboundDetailView.openDetailWin({
                callback:this
            },desc,id);
        },
        edt:function(action,id,desc){
        	InboundUpdateView.openUpdateWin({
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
		    		 $.post(Portal.webRoot+'/stock/inbound/updateInboundStatus.do',{
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
        applyInbound:function(action,id){
        	 var me = this
        	var t= club.confirm('您确定要申请入库吗？');
        	 t.result.then(function resolve(){
         		 $.post(Portal.webRoot+'/stock/inbound/updateInboundStatus.do',{
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
        sureInboundAll:function(action){
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
            		this.sureInbound(action,ids);
        		}
        	}
        	
        },
        applyInboundAll:function(action){
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
            		this.applyInbound(action,ids);
        		}
        	}
        	
        },
        delInboundAll:function(action){
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
            		this.delInbound(action,ids);
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
        			if(records[i].status!='2'){
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
        sureInbound:function(action,id){
        	var me = this;
        	var t= club.confirm('您确定要执行此操作吗？');
	       	 t.result.then(function resolve(){
	      		$.post(Portal.webRoot+'/stock/inbound/updateInboundStatus.do',{
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
        delInbound:function(action,id){
        	var me = this;
        	var t= club.confirm('您确定要执行此操作吗？');
        	 t.result.then(function resolve(){
        		 $.post(Portal.webRoot+'/stock/inbound/delInboundOrder.do',{
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
        	$('#first-sta').val('0')
             $('input[name=inboundName]').val('');
             this.pageData(1);
        },
        add:function(action){
            AddInboundWindow.openAddWin({
                callback:this
            },action,'','');
        },
        pageData :function (page, rowNum, sortname, sortorder) {
            var inboundName = $('input[name=inboundName]').val();
            var status = $('select[name=inbound_status]').val();
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/stock/inbound/queryInboundOrderList.do',{
                conditionStr:JSON.stringify({
                    'inboundName':'%'+inboundName+'%',
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
                    label: '入库单号',
                    width: 130,
                    align: "center",
                    title: false 
                }, {
                    name: 'sourceNo',
                    label: '来源单号',
                    width:130,
                    align: "center"
                },  {
                    name: 'status',
                    label: '状态',
                    width: 65,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                        if (cellval==1) {return '未审核'};
                        if (cellval==2) {return '待入库'};
                        if (cellval==3) {return '已入库'};
                        return cellval;
                    }
                },{
                    name: 'createBy',
                    label: '入库人',
                    width: 100,
                    align: "center"
                },{
                    name: 'subTime',
                    label: '提交时间',
                    width: 125
	            },{
	            	name: 'inboundTime',
	            	label: '入库时间',
	            	width: 125
	            },{
	            	name: 'remarks',
	            	label: '备注',
	            	width: 110
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
                    	var text='<a class="btn" action="query-detail" style="width:40px">明细</a>';
                    	if(rwdat.status==1||rwdat.status==2){
                    		//text+='<a class="btn" action="apply-inbound" style="width:80px;padding-left:10px;">申请入库</a><a class="btn" action="edit-rule" style="width:40px;margin-left:0px;padding-left:0px">编辑</a><a class="btn" action="inbound-del" style="width:40px;margin-left:0px;padding-left:0px">删除</a>';
                    		text+='<a class="btn" action="edit-rule" style="width:40px;">编辑</a>';
                    		//text+='<a class="btn" action="sure-inbound" style="width:50px">确认</a><a class="btn" action="cancle-apply" style="width:80px">取消入库</a>';
                    		text+='<a class="btn" action="sure-inbound" style="width:30px">确认</a>';
                    		text+='<a class="btn" action="inbound-del" style="width:30px;">删除</a>';
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