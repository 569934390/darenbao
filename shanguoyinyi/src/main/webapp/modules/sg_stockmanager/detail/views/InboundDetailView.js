define([
    'text!modules/sg_stockmanager/detail/templates/InboundDetailView.html',
    'Portal'
], function (InboundDetailViewTpl,Portal) {
	var GRID_DOM = "#inbound-detail-rule-content-div";
	var options = {
	        height: $(window).height()*0.9,
	        modal: false,
	        draggable: false,
	        content: InboundDetailViewTpl,
	        autoResizable: true
	};
    var popup;
    var inboundId='';
    var zNodes;
	return {
		openDetailWin:function(listView,action,recond){
            popup = club.popup($.extend({}, options, {
                modal: true
            }));
            if(action!=null&&action!=''&&action!=undefined){
            	$('#canale-item').text(action);
            }else{
            	$('#cancle-why').hide();
            }
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
            		$('#inbound_detail_brand').append(text);
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
        			data: {
        				simpleData: {
                            enable: true,
                            idKey: "id",
                            pIdKey: "pId",
                            rootPId: ""    
        				}
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
        			callback: {
        				beforeClick: beforeClick,
        				onClick: onClick,
        	            onAsyncSuccess : function(event, treeId, treeNode, msg){    
        	            }
        			}/*,
        			fNodes:zNodes*/
        		};
        		$("#treeDemoInboundDetail").tree(options);

        		$("#tree_select_menu_html_inbound_detail").position({ 
        			of: $( "#inbound_detail_classify_name" ),
        			my: "left top",
        			at: "left bottom"
        		}).hide();

        		$("#inbound-detail-menuBtn").click(function(event) {
        			showMenu();
        			return false;
        		});
        		function beforeClick(e,treeNode) {
        			var check = true;//(treeNode && !treeNode.isParent);
        			/*if (!check) fish.info("只能选择城市...");*/
        			return check;
        		}
        		function onClick(e, treeNode) {
        			/*var nodes = $("#treeDemoInboundDetail").tree("getSelectedNodes"),
        				v = "";
        			nodes.sort(function compare(a,b){return a.id-b.id;});
        			for (var i=0, l=nodes.length; i<l; i++) {
        				v += nodes[i].name + ",";
        			}
        			if (v.length > 0 ) {
        				v = v.substring(0, v.length-1);
        			}*/
        			var cityObj = $("#inbound_detail_classify_name");
        			cityObj.attr("value", treeNode.name);
        			$("#inbound_detail_classify").val(treeNode.id)
        			hideMenu();
        		}

        		function showMenu() {
        			$("#tree_select_menu_html_inbound_detail").slideDown("fast");
        			//$("body").on("mousedown", onBodyDown);
        		}
        		function hideMenu() {
        			$("#tree_select_menu_html_inbound_detail").fadeOut("fast");
        			//$("body").on("mousedown", onBodyDown);
        		}
        		$("body").on("mousedown", function(event){
        			if (!(event.target.id == "inbound-detail-menuBtn" || event.target.id == "tree_select_menu_html_inbound_detail" || $(event.target).parents("#tree_select_menu_html_inbound_detail").length>0)) {
        				hideMenu();
        			}
        		});
        },
		initClick:function(){
			var me=this;
            $('.inbound-manager-detail button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'search-detail-inbound': 
                    	 me.pageData(1);
                         break;
                }
            });
		},
        pageData:function (page, rowNum, sortname, sortorder) {
            var matchParam = $('input[name=matchParam]').val();
            var classify = $('input[name=inbound_detail_classify]').val();
            var brand = $('select[name=inbound_detail_brand]').val();
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/stock/inbound/queryInboundOrderDetail.do',{
                conditionStr:JSON.stringify({
                    'matchParam':'%'+matchParam+'%',
                    'classify':classify,'brand':brand,'inboundId':inboundId}),start:(page-1)*rowNum,limit:rowNum},function(result){
                    result = Portal.convertPage(result);
                    result.page = page;
                    $(GRID_DOM).grid("reloadData", result);
            });
            return false;
        },
        _initListView:function(){
        	//参数配置
            var opt = {
        		height:$(window).height()-180,
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
	            	width: 120,
	            	 align: "center"
	            }],pager: true
                ,rowNum: 20
                ,rowList: [10,20,50,100,200]
                ,datatype: "json"
                ,recordtext:"{0} 到 {1} 总共: {2} "
                ,pgtext : " {0} / {1}"
                ,rowtext:"{0}"
                ,gotext:'跳转至第{0}页'
                ,multiselect:false
                ,pageData:this.pageData
            };
            this.pageData(1);
            //加载grid
            $grid = $(GRID_DOM).grid(opt);
            $('.gotext').find('span').html('跳转至第<input class="ui-pagination-input">页');
        }
	};
});