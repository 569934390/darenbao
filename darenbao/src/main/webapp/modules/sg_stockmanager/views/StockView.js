define([
    'text!modules/sg_stockmanager/templates/StockView.html',
    'modules/sg_stockmanager/check/views/StockCheckView',
    'Portal'
], function (StockViewTpl,StockCheckViewTpl,Portal) {
	var GRID_DOM = "#stock-goods-rule-content-div";
	var zNodes;
	return club.View.extend({
        template: club.compile(StockViewTpl),
        i18nData: club.extend({}),
        listView: {},
        views: {
        },
        events:{
        	"click .stock-goods-manager .btn": "tbarHandler"
        },
        //这里用来初始化页面上要用到的club组件
        _afterRender: function () {
            this._initListView();
            this.initCheck();
            this.initTree();
            $.post(Portal.webRoot+'/cargoBrand/findListAll.do',{},function(result){
            	if(result!=null&&result.length>0){
            		var text='';
            		for(var i=0;i<result.length;i++){
            			text+='<option value="'+result[i].id+'">'+result[i].name+'</option>'
            		}
            		$('#stock-second-sta').append(text);
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
        			}
        		};
        		$("#treeDemoStock").tree(options);

        		$("#tree_select_menu_html_stock").position({ 
        			of: $( "#stock_classify_name" ),
        			my: "left top",
        			at: "left bottom"
        		}).hide();

        		$("#stock-menuBtn").click(function(event) {
        			//var dv=$("#tree_select_menu_html_stock");
        			showMenu();
        			return false;
        		});
        		function beforeClick(e,treeNode) {
        			var check = true;//(treeNode && !treeNode.isParent);
        			/*if (!check) fish.info("只能选择城市...");*/
        			return check;
        		}
        		function onClick(e, treeNode) {
        			/*var nodes = $("#treeDemoStock").tree("getSelectedNodes"),
        				v = "";
        			nodes.sort(function compare(a,b){return a.id-b.id;});
        			for (var i=0, l=nodes.length; i<l; i++) {
        				v += nodes[i].name + ",";
        			}
        			if (v.length > 0 ) {
        				v = v.substring(0, v.length-1);
        			}*/
        			var cityObj = $("#stock_classify_name");
        			cityObj.attr("value", treeNode.name);
        			$("#stock_classify").val(treeNode.id)
        			$('input[name=stock_classify_name]').val(treeNode.name);
        			hideMenu();
        		}

        		function showMenu() {
        			$("#tree_select_menu_html_stock").slideDown("fast");
        			//$("body").on("mousedown", onBodyDown);
        		}
        		function hideMenu() {
        			$("#tree_select_menu_html_stock").fadeOut("fast");
        			//$("body").on("mousedown", onBodyDown);
        		}
        		$("body").on("mousedown", function(event){
        			if (!(event.target.id == "stock-menuBtn" || event.target.id == "tree_select_menu_html_stock" || $(event.target).parents("#tree_select_menu_html_stock").length>0)) {
        				hideMenu();
        			}
        		});
        },
        tbarHandler: function (event) {
            var action = $(event.currentTarget).attr("action"),selectRecord;
            var rowId= $(event.currentTarget).attr("id");
            switch(action){
                case 'search-stock-goods' : 
                	this.search();break;
                case 'update-remain' :
                	this.update(rowId);
                	break;
                case 'chechk-stock-btn' :
                	StockCheckViewTpl.openCheckWin({
                        callback:this
                    },action,null);
                	break;
                case 'stock-refresh':
                	this.refresh();
                	break;
            }
        },
        initCheck:function(){
            $.post(Portal.webRoot+'/deal/indent/cargoCheck/count.do',{},function(result){
				if(result!=null){
					$('#stock-check-total').text(result); 
				}else{
					$('#stock-check-total').text('0');
				}
            });
        },
        update:function(rowId){
        	var me=this;
        	var record= $(GRID_DOM).grid("getRowData", rowId);
        	var count=record.noShelve;
        	var remainCount=record.remainCount;
        	var id=record.id;
        	var t=club.prompt('请填写留存数量:');	
        	$('.ui-draggable .form-control').keyup(function(){
        		this.value=this.value.replace(/[^0-9\.]/,''); 
        		if(!(/^\d+$/.test(this.value))){
        			this.value='';
        		}else{
        		}
        		if(this.value>(count+remainCount)){
        			 club.toast('warn', '留存数量不能大于未上架数量,请重新输入');
        			 this.value='';
        		}
        	});
        	t.result.then(function resolve(val){
        		if(val==''||val==null||val==undefined){
        			club.toast('warn', '请填写留存数量!');
        		}else{
        			var vd=parseInt(val);
        			if(vd<0){
        				club.toast('warn', '留存数量不能小于0');
        			}else if(vd==remainCount){
        				return;
        			}else if(vd>(count+remainCount)){
        				club.toast('warn', '留存数量不能大于未上架数量,请重新输入');
        			}else{
			    		 $.post(Portal.webRoot+'/stock/modular/updateStockRemain.do',{
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
			                    	 club.toast('warn','操作失败' );
			                     }
			             });
        			}
        		}
       	 	}, function reject(){
       	 		return;
       	 	});
        },
        search:function(){
        	 this.initCheck();
        	 this.pageData(1);
        },
        refresh:function(){
        	$('#stock-second-sta').val('0')
            $('input[name=stock_param]').val('');
        	$('input[name=stock_classify_name]').val('');
        	$('input[name=stock_classify]').val('0');
        	this.initCheck();
            this.pageData(1);
        },
        pageData :function (page, rowNum, sortname, sortorder) {
            var matchParam = $('input[name=stock_param]').val();
            var classify = $('input[name=stock_classify]').val();
            var brand = $('select[name=stock_brand]').val();
            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/stock/modular/queryStockMsg.do',{
                conditionStr:JSON.stringify({
                    'matchParam':'%'+matchParam+'%','classify':classify,'brand':brand}
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
                width:this.$el.width()-100,
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
                    width: 90,
                    align: "center",
                    title: false 
                }, {
                    name: 'goodsName',
                    label: '货品名称',
                    width:100,
                    align: "center"
                },  {
                    name: 'goodsCode',
                    label: '货品编号',
                    width: 90,
                    align: "center"
                },{
                    name: 'classyName',
                    label: '分类',
                    width: 70,
                    align: "center"
                },{
                    name: 'brandName',
                    label: '品牌',
                    align: "center",
                    width: 80
	            },{
	            	name: 'item',
	            	label: '规格',
	                align: "center",
	            	width: 150,
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
	            	name: 'noShelve',
	            	label: '未上架',
	            	align: "center",
	            	width: 70
	            },{
	            	name: 'shelveNoShale',
	            	label: '已上架未售',
	            	align: "center",
	            	width: 75
	            },{
	            	name: 'shelveNoPay',
	            	label: '已售未付款',
	            	align: "center",
	            	width: 75
	            },{
	            	name: 'shelveNoSend',
	            	label: '已售待发',
	            	align: "center",
	            	width: 60
	            },{
	            	name: 'remainCount',
	            	label: '库存留存',
	            	align: "center",
	            	width: 60
	            },{
	            	name: 'total',
	            	label: '总库存量',
	            	align: "center",
	            	width: 70
	            },{
                    name: '',
                    label: '操 作',
                    width: 120,
                    align: "center",
                    formatter: function(cellval, opts, rwdat, _act) {
                    	var text='<a class="btn" id="'+opts.rowId+'" action="update-remain">修改留存</a>';
                    	return text
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