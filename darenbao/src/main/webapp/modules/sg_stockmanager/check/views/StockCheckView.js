define([
    'text!modules/sg_stockmanager/check/templates/StockCheckView.html',
    'Portal'
], function (StockCheckViewTpl,Portal) {
	var GRID_DOM = "#stock-chechk-rule-content-div";
	var options = {
	        height: $(window).height()*0.9,
	        modal: false,
	        draggable: false,
	        content: StockCheckViewTpl,
	        autoResizable: true
	};
    var popup;
    var inboundId='';
    var zNodes;
	return {
		openCheckWin:function(listView,action,recond){
            popup = club.popup($.extend({}, options, {
                modal: true
            }));
            inboundId=recond;
            this._initListView();
            this.initClick();
            this.initTree();
            $.post(Portal.webRoot+'/cargoBrand/findListAll.do',{},function(result){
            	if(result!=null&&result.length>0){
            		var text='';
            		for(var i=0;i<result.length;i++){
            			text+='<option value="'+result[i].id+'">'+result[i].name+'</option>'
            		}
            		$('#stock-chechk-brand').append(text);
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
	        		$("#treeDemoStockCheck").tree(options);

	        		$("#tree_select_menu_html_stock_check").position({ 
	        			of: $( "#stock-chechk-classify" ),
	        			my: "left top",
	        			at: "left bottom"
	        		}).hide();

	        		$("#stock-menuBtn-check").click(function(event) {
	        			showMenu();
	        			return false;
	        		});
	        		function beforeClick(e,treeNode) {
	        			var check = true;//(treeNode && !treeNode.isParent);
	        			/*if (!check) fish.info("只能选择城市...");*/
	        			return check;
	        		}
	        		function onClick(e, treeNode) {
	        			/*var nodes = $("#treeDemoStockCheck").tree("getSelectedNodes"),
	        				v = "";
	        			nodes.sort(function compare(a,b){return a.id-b.id;});
	        			for (var i=0, l=nodes.length; i<l; i++) {
	        				v += nodes[i].name + ",";
	        			}
	        			if (v.length > 0 ) {
	        				v = v.substring(0, v.length-1);
	        			}*/
	        			var cityObj = $("#stock-chechk-classify");
	        			cityObj.attr("value", treeNode.name);
	        			$("#stock-chechk-classify_val").val(treeNode.id)
	        			$('#stock-chechk-classify').val(treeNode.name)
	        			hideMenu();
	        		}

	        		function showMenu() {
	        			$("#tree_select_menu_html_stock_check").slideDown("fast");
	        			//$("body").on("mousedown", onBodyDown);
	        		}
	        		function hideMenu() {
	        			$("#tree_select_menu_html_stock_check").fadeOut("fast");
	        			//$("body").on("mousedown", onBodyDown);
	        		}
	        		$("body").on("mousedown", function(event){
	        			if (!(event.target.id == "stock-menuBtn-check" || event.target.id == "tree_select_menu_html_stock_check" || $(event.target).parents("#tree_select_menu_html_stock_check").length>0)) {
	        				hideMenu();
	        			}
	        		});
	        },
		initClick:function(){
			var me=this;
            $('.stock-check-manager button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'search-stock-check': 
                    	 me.pageData(1);
                         break;
                    case 'stock-chechk-act':
                    	 var records=$(GRID_DOM).grid('getCheckRows');
                     	if(records==null||records.length==0){
                    		return club.toast('warn', '请选择要核对的记录！')
                    	}else{
                    	  var ids='';
                    	  var nopay='';
                    	  var noSend='';
  		              	  for(var i=0;i<records.length;i++){
  		              		  ids+=records[i].id;
  		              		  nopay+=records[i].indentNopay;
  		              		  noSend+=records[i].indentNosend;
                  			if(i!=records.length-1){
                  				ids+=',';
                  				nopay+=',';
                  				noSend+=',';
                			}
		            	   }
                    		var t= club.confirm('您确定要执行库存核对吗？');
                    		t.result.then(function resolve(){
                    			$.post(Portal.webRoot+'/stock/modular/updateNormalStockMsg.do',{
                    				conditionStr:JSON.stringify({
                    					'ids':ids,'nopays':nopay,'noSends':noSend
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
                }
            });
		},
		refresh:function(){
        	 $('#stock-chechk-classify_val').val('0')
        	 $('#stock-chechk-classify').val('')
        	 $('#stock-chechk-brand').val('0')
             $('input[name=stock_chechk_param]').val('');
             this.pageData(1);
        },
        pageData:function (page, rowNum, sortname, sortorder) {
            var matchParam = $('input[name=stock_chechk_param]').val();
            var classify = $('input[name=stock-chechk-classify_val]').val();
            if(classify=='0'){
            	classify='';
            }
            var brand = $('select[name=stock_chechk_brand]').val();
            if(brand=='0'){
            	brand='';
            }
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/deal/indent/cargoCheck/list.do',{
                conditionStr:JSON.stringify({
                    'checkValue':'%'+matchParam+'%',
                    'clissfyId':classify,'brandId':brand}),start:(page-1)*rowNum,limit:rowNum},function(result){
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
                width:0,
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
                    width: 120,
                    align: "center",
                    title: false 
                },{
                    name: 'goodsName',
                    label: '货品名称',
                    width:120,
                    align: "center"
                },{
                    name: 'goodsCode',
                    label: '货品编码',
                    width: 120,
                    align: "center"
                },{
                    name: 'classifyName',
                    label: '分类',
                    width: 100,
                    align: "center"
	            },{
                    name: 'brandName',
                    label: '品牌',
                    width: 100,
                    align: "center"
                },{
	            	name: 'item',
	            	label: '规格',
	            	width: 160,
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
	            	name: 'shelveNopay',
	            	label: '已售未付款',
	            	align: "center",
	            	width: 95
	            },{
	            	name: 'indentNopay',
	            	label: '实际已售未付款',
	            	align: "center",
	            	width: 115
	            },{
	            	name: 'shelveNosend',
	            	label: '已售待发',
	            	align: "center",
	            	width: 85
	            },{
	            	name: 'indentNosend',
	            	label: '实际已售待发',
	            	align: "center",
	            	width: 105
	            }
                ],pager: true
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