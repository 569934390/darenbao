define([
    'text!modules/sg_stockmanager/add/templates/addInboundView.html',
    'modules/sg_stockmanager/add/views/AddInboundDetailWindow',
    'Portal'
], function (AddInboundViewTpl,AddInboundDetailWindow,Portal) {
	var GRID_DOM = "#add-inbound-rule-content-div";
	var options = {
	        height: $(window).height()*0.9,
	        modal: false,
	        draggable: false,
	        content: AddInboundViewTpl,
	        autoResizable: true
	};
	var hgt=$(window).height()-370;
    var popup;
    var det='';
    var parAction;
    var backListView;
    var zNodes;
	return {
		openAddWin:function(listView,action,valt,edtAct){
            popup = club.popup($.extend({}, options, {
                modal: true
            }));
            parAction=edtAct;
            if(valt!=''&&valt!=null&&valt!=undefined){
            	hgt=$(window).height()-280;
            	$('#add-title').text('添加明细');
            	$('#add-head').hide();
            	det=valt;
            }else{
            	hgt=$(window).height()-370;
            	det='';
            }
            backListView=listView;
            this._initListView();
            this.initClick();
            this.initTree();
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
        		$("#treeDemoInboundAdd").tree(options);

        		$("#tree_select_menu_html_inbound_add").position({ 
        			of: $("#inbound_add_classify_name"),
        			my: "left top",
        			at: "left bottom"
        		}).hide();

        		$("#inbound-add-menuBtn").click(function(event) {
        			showMenu();
        			return false;
        		});
        		function beforeClick(e,treeNode) {
        			var check = true;//(treeNode && !treeNode.isParent);
        			/*if (!check) fish.info("只能选择城市...");*/
        			return check;
        		}
        		function onClick(e, treeNode) {
        			/*var nodes = $("#treeDemoInboundAdd").tree("getSelectedNodes"),
        				v = "";
        			nodes.sort(function compare(a,b){return a.id-b.id;});
        			for (var i=0, l=nodes.length; i<l; i++) {
        				v += nodes[i].name + ",";
        			}
        			if (v.length > 0 ) {
        				v = v.substring(0, v.length-1);
        			}*/
        			var cityObj = $("#inbound_add_classify_name");
        			cityObj.attr("value", treeNode.name);
        			$("#inbound_add_classify").val(treeNode.id)
        			hideMenu();
        		}

        		function showMenu() {
        			$("#tree_select_menu_html_inbound_add").slideDown("fast");
        			//$("body").on("mousedown", onBodyDown);
        		}
        		function hideMenu() {
        			$("#tree_select_menu_html_inbound_add").fadeOut("fast");
        			//$("body").on("mousedown", onBodyDown);
        		}
        		$("body").on("mousedown", function(event){
        			if (!(event.target.id == "inbound-add-menuBtn" || event.target.id == "tree_select_menu_html_inbound_add" || $(event.target).parents("#tree_select_menu_html_inbound_add").length>0)) {
        				hideMenu();
        			}
        		});
        },
		initClick:function(){
			var me=this;
            $('.add-inbound-detail-manager button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'search-inbound-add': 
                    	 me.pageData(1);
                         break;
                    case 'cancel-button': popup.close();break;
                    case 'next-button': 
                    	var records=$(GRID_DOM).grid('getCheckRows');
                    	var crgoIds='';
                    	if(records==null||records.length==0){
                    		return club.toast('warn', '请选择要添加的货品记录！');
                    	}else if(records.length>999){
                    		return club.toast('warn', '一次添加不能超过999条记录！');
                    	}else{
                    		var sourceCode=$('#source_code').val();
                    		var remk=$('#remk').val();
                    		if(remk!=null&&remk!=''&&remk!=undefined){
                    			var reg=new RegExp('\\|','g'); 
                    			remk=remk.replace(reg,"");
                    			reg=new RegExp(',','g'); 
                    			remk=remk.replace(reg,""); 
                    		}
                    		if(sourceCode!=null&&sourceCode!=''&&sourceCode!=undefined){
                    			var reg=new RegExp('\\|','g'); 
                    			sourceCode=sourceCode.replace(reg,""); 
                    			reg=new RegExp(',','g'); 
                    			sourceCode=sourceCode.replace(reg,""); 
                    		}
                    		for(var i=0;i<records.length;i++){
                    			crgoIds+=records[i].id;
                    			if(i!=records.length-1){
                    				crgoIds+=',';
                    			}
                    		}
                    		AddInboundDetailWindow.openGoodsDetailWin({
                    			callback:backListView
                    		},popup,sourceCode+"|"+remk+"|"+crgoIds+"|"+det,parAction);
                    	}
                    	break;
                }
            });
		},
        pageData:function (page, rowNum, sortname, sortorder) {
            var matchParam = $('input[name=matchParam]').val();
            var classify = $('input[name=inbound_add_classify]').val();
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/stock/inbound/queryGoodsMsg.do',{
                conditionStr:JSON.stringify({
                    'matchParam':'%'+matchParam+'%',
                    'classify':classify}),start:(page-1)*rowNum,limit:rowNum},function(result){
                    result = Portal.convertPage(result);
                    result.page = page;
                    $(GRID_DOM).grid("reloadData", result);
            });
            return false;
        },
        refresh:function(){
             this.pageData(1);
        },
        _initListView:function(){
        	//参数配置
            var opt = {
        		height:hgt,
                width:350,
                rownumbers: true, 
                colModel: [{
                    name: 'id',
                    hidden:true,
                    key:true
                    
                }, {
                    name: 'icon',
                    hidden:true
                }, {
                    name: 'subClient',
                    hidden:true
                },{
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
                    name: 'typeName',
                    label: '分类',
                    width: 100,
                    align: "center"
                },{
                    name: 'brandName',
                    label: '品牌',
                    width: 100,
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