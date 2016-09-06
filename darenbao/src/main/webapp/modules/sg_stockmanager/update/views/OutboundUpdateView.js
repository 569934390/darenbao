define([
    'text!modules/sg_stockmanager/update/templates/OutboundUpdateView.html',
    'modules/sg_stockmanager/add/views/AddOutboundWindow',
    'Portal'
], function (OutboundUpdateViewTpl,AddOutboundWindow,Portal) {
	var GRID_DOM = "#outbound-update-rule-content-div";
	var options = {
	        height: $(window).height()*0.9,
	        modal: false,
	        draggable: false,
	        content: OutboundUpdateViewTpl,
	        autoResizable: true
	};
    var popup;
    var outboundId='';
    var zNodes;
	return {
		openUpdateWin:function(listView,action,recond){
            popup = club.popup($.extend({}, options, {
                modal: true
            }));
            outboundId=recond;
            this._initListView();
            this.initClick();
            this.initTree();
            $.post(Portal.webRoot+'/cargoBrand/findListAll.do',{},function(result){
            	if(result!=null&&result.length>0){
            		var text='';
            		for(var i=0;i<result.length;i++){
            			text+='<option value="'+result[i].id+'">'+result[i].name+'</option>'
            		}
            		$('#out-update_brand').append(text);
            	}
            });
		},
        initTree:function(){
        	/*$.ajax({  
	               type: "post",  
	               url: Portal.webRoot+'/stock/tree/queryTreeMenu.do',  
	               cache:false,  
	               async:false,  
	               dataType: "json",  
	               success: function(result){  
	                	 if(result!=null&&result!=''){
	                		  zNodes = eval("("+result+")");
	                 	}
	                }  
	        });*/
        	var options = {
        			view: {
        				dblClickExpand: true,
        				showIcon:true,
        				showLinel:true
        			},
                    async: {
                        enable: true,
                        contentType: "application/x-www-form-urlencoded",
                        type: "post",
                        dataType: "text",
                        url: Portal.webRoot+'/stock/tree/queryTreeMenu.do',
                        autoParam: [],
                        otherParam: [],
                        dataFilter: function(treeId, parentNode, data){
                        	return data;
                        }
                    },
        			data: {
        				simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "pId",
                            rootPId: ""    
        				}
        			},
        			callback: {
        				beforeClick: beforeClick,
        				onClick: onClick,
        	            onAsyncSuccess : function(event, treeId, treeNode, msg){    
        	            }
        			}/*,
        			fNodes:zNodes*/
        		};
        		$("#treeDemoOutboundUpdate").tree(options);

        		$("#tree_select_menu_html_outbound_update").position({ 
        			of: $( "#outbound-update-classify_name" ),
        			my: "left top",
        			at: "left bottom"
        		}).hide();

        		$("#outbound-update-menuBtn").click(function(event) {
        			showMenu();
        			return false;
        		});
        		function beforeClick(e,treeNode) {
        			var check = true;//(treeNode && !treeNode.isParent);
        			/*if (!check) fish.info("只能选择城市...");*/
        			return check;
        		}
        		function onClick(e, treeNode) {
        			/*var nodes = $("#treeDemoOutboundUpdate").tree("getSelectedNodes"),
        				v = "";
        			nodes.sort(function compare(a,b){return a.id-b.id;});
        			for (var i=0, l=nodes.length; i<l; i++) {
        				v += nodes[i].name + ",";
        			}
        			if (v.length > 0 ) {
        				v = v.substring(0, v.length-1);
        			}*/
        			var cityObj = $("#outbound-update-classify_name");
        			cityObj.attr("value", treeNode.name);
        			$("#outbound-update-classify").val(treeNode.id)
        			hideMenu();
        		}

        		function showMenu() {
        			$("#tree_select_menu_html_outbound_update").slideDown("fast");
        			//$("body").on("mousedown", onBodyDown);
        		}
        		function hideMenu() {
        			$("#tree_select_menu_html_outbound_update").fadeOut("fast");
        			//$("body").on("mousedown", onBodyDown);
        		}
        		$("body").on("mousedown", function(event){
        			if (!(event.target.id == "outbound-update-menuBtn" || event.target.id == "tree_select_menu_html_outbound_update" || $(event.target).parents("#tree_select_menu_html_outbound_update").length>0)) {
        				hideMenu();
        			}
        		});
        },
		initClick:function(){
			var me=this;
            $('.outbound-update-manager button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'search-outbound-update': 
                    	 me.pageData(1);
                         break;
                    case 'cancel-button-update':
                    	popup.close();break;
                    case 'out-delete-edt-all':
                    	var records=$(GRID_DOM).grid('getCheckRows');
                    	var ids='';
                    	if(records==null||records.length<1){
                    		return club.toast('warn', '请选择要操作的记录!');
                    	}else{
                    		for(var i=0;i<records.length;i++){
                    			ids+=records[i].id;
                    			if(i!=records.length-1){
                    				ids+=',';
                    			}
                    		}
                    		var t= club.confirm('您确定要执行此操作吗？');
                    		t.result.then(function resolve(){
                    			$.post(Portal.webRoot+'/stock/outbound/delOutboundOrderDetail.do',{
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
                    						club.toast('warn','操作失败' );
                    					}
                    				});
                    		}, function reject(){
                    			return;
                    		});
                    	}
                    	break;
                    case 'out-add-edt-detail':
                    	AddOutboundWindow.openAddWin({
                            callback:me
                        },'',outboundId,popup);
                    	break;
                }
            });
		},
		refresh:function(){
        	 $('#outbound-update-classify').val('0')
        	 $('#outbound-update-classify_name').val('')
        	 $('#out-update_brand').val('0')
             $('input[name=out-update_param]').val('');
        	 this.initTree();
             this.pageData(1);
        },
        pageData:function (page, rowNum, sortname, sortorder) {
            var matchParam = $('input[name=out-update_param]').val();
            var classify = $('input[name=outbound-update-classify]').val();
            var brand = $('select[name=out-update_brand]').val();
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/stock/outbound/queryOutboundOrderDetail.do',{
                conditionStr:JSON.stringify({
                	'matchParam':'%'+matchParam+'%',
                    'classify':classify,'brand':brand,'outboundId':outboundId}),start:(page-1)*rowNum,limit:rowNum},function(result){
                    result = Portal.convertPage(result);
                    result.page = page;
                    $(GRID_DOM).grid("reloadData", result);
            });
            return false;
        },
        _initListView:function(){
        	//参数配置
            var opt = {
        		height:$(window).height()-210,
                width:400,
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
                    name: 'skuCode',
                    label: 'SKU编码',
                    width: 150,
                    align: "center",
                    title: false 
                }, {
                    name: 'goodsName',
                    label: '货品名称',
                    width:150,
                    align: "center"
                },  {
                    name: 'goodsNo',
                    label: '货品编码',
                    width: 150,
                    align: "center"
                },{
                    name: 'brandName',
                    label: '品牌',
                    width: 120,
                    align: "center"
                },{
                    name: 'typeName',
                    label: '分类',
                    width: 120,
                    align: "center"
	            },{
	            	name: 'item',
	            	label: '规格',
	            	width: 200,
                    formatter: function(cellval, opts, rwdat, _act) {
                    	var text='';
                    	if(cellval!=null&&cellval.length>0){
                    		for(var i=0;i<cellval.length;i++){
                    			text+=cellval[i].type+":"+cellval[i].skuName;
                    			if(i!=cellval.length-1){
                    				text+=" , ";
                    			}
                    		}
                    	}
                        return text;
                    }          	
	            },{
	            	name: 'goodCount',
	            	label: '数量',
	            	width: 80,
	            	 align: "center"
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
	};
});
var rowId='';
function getRowId(id){
	rowId=id;
}