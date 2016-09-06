define([
    'text!modules/sg_stockmanager/add/templates/addOutboundDetailView.html',
    'Portal'
], function (AddOutboundDetailViewTpl,Portal) {
	var GRID_DOM = "#add-outbound-deteil-rule-content-div-next";
	var options = {
	        height: $(window).height()*0.9,
	        modal: false,
	        draggable: false,
	        content: AddOutboundDetailViewTpl,
	        autoResizable: true
	};
    var popup;
    var goodsIds='';
    var sourceCode='';
    var remk='';
    var detailListView;
    var outboundId='';
    var chilAction;
	return {
		openGoodsDetailWin:function(listView,action,record,parAction){
            popup = club.popup($.extend({}, options, {
                modal: true
            }));
            chilAction=parAction;
            detailListView=listView;
            if(record!=null&&record!=''&&record!=undefined){
            	var str=record.split('|');
            	sourceCode=str[0];
            	remk=str[1];
            	goodsIds=str[2];
            	outboundId=str[3];
            }else{
            	outboundId='';
            	goodsIds='';
            }
            if(outboundId!=''&&outboundId!=null&&outboundId!=undefined){
            	$('#out-add-title-detail').text('添加明细');
            }
            this._initListView();
            this.initClick(action);
		},
		initClick:function(action){
            $('.add-outbound-detail-manager-next button').click(function(){
                var btnAction = $(this).attr('action');
                if(btnAction==undefined||btnAction==''||btnAction==null){
              	  	popup.close();
              	  	action.close();
                	return;
                }
                $(this).attr("disabled", true);
                var that=this;
                switch(btnAction){
                    case 'out-d-cancel-button':
                    	  popup.close();
    					  action.close();
    					  break;
                    case 'out-d-prev-button': 
                    	  popup.close();
                    	  break;
                    case 'out-d-del-button': 
                    	 $(this).attr("disabled", false);
                    	var records=$(GRID_DOM).grid('getCheckRows');
                    	var ids='';
                    	if(records==null||records.length<1){
                    		return club.toast('warn', '请选择要操作的记录!');
                    	}else{
                    		for(var i=0;i<records.length;i++){
                    			$(GRID_DOM).grid('delRowData',records[i].skuId);
                    		}
                    	}
                    	break;
                    case 'out-d-save-button':
                    	  var records=$(GRID_DOM).grid('getCheckRows');
                    	  if(records==null||records.length==0){
                    		$(this).attr("disabled", false);
                    		return club.toast('warn', '请选择要保存的记录！');
                    	  }
		              	  for(var i=0;i<records.length;i++){
		              		  	var skuCount=0;
		              		  	var indx=records[i].skuId;
		              		  	skuCount+=$('#addout'+indx).val();
		              		  	if(skuCount>records[i].skuCount){
		              		  		$(this).attr("disabled", false);
		              		  		return club.toast('warn', '选中的记录中存在输入的数量大于库存数量！');
		              		  	}
		            	   }
		              	  var skuIds='';
		              	  var skuCount='';
		              	  for(var i=0;i<records.length;i++){
		              		  var indx=records[i].skuId;
		              		  	skuIds+=indx
		              		  	skuCount+=$('#addout'+indx).val();
		            			if(i!=records.length-1){
		            				skuIds+=',';
		            				skuCount+=',';
		            			}
		            	   }
	                      $.post(Portal.webRoot+'/stock/outbound/saveOutboundMsg.do',{
	                        conditionStr:JSON.stringify({
	                            'skuIds':skuIds,'skuCount':skuCount,'sourceCode':sourceCode,'remk':remk,'outboundId':outboundId
	                            })},function(result){
	                            if(result!=null){
	                              var resultCode=result.code;
	                              if(resultCode=='000000'){
	                            	  detailListView.callback.callback.refresh();
	                            	  club.toast('info',result.msg );
	                            	  popup.close();
	            					  action.close();
	                              }else{
	                            	  $(that).attr("disabled", false);
	                            	  club.toast('warn',result.msg ); 
	                              }
	                            }else{
	                            	 $(that).attr("disabled", false);
	                            	 club.toast('warn','保存失败' ); 
	                            }
	                      });
	                      break;
                }
            });
		},
        refresh:function(){
            this.pageData(1);
       },
        pageData:function (page, rowNum, sortname, sortorder) {
        	 rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
            $.post(Portal.webRoot+'/stock/outbound/queryOutboundStockSkuMsg.do',{
                conditionStr:JSON.stringify({
                    'goodsIds':goodsIds,
                    }),start:(page-1)*rowNum,limit:rowNum},function(result){
                    result = Portal.convertPage(result);
                    result.page = page;
                    $(GRID_DOM).grid("reloadData", result);
            });
            return false;
        },
        _initListView:function(){
        	//参数配置
            var opt = {
        		height:$(window).height()-240,
                width:780,
                rownumbers: true, 
                cellEdit:true,
                colModel: [{
                    name: 'skuId',
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
                    width:150,
                    align: "center"
                },{
                    name: 'goodsName',
                    label: '货品名称',
                    width:120,
                    align: "center"
                }, {
                    name: 'goodsCode',
                    label: '货品编码',
                    width: 150,
                    align: "center"
                },{
                    name: 'brandName',
                    label: '品牌',
                    width: 100,
                    align: "center"
                },{
                    name: 'classifyName',
                    label: '分类',
                    width: 100,
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
	            	name: 'skuCount',
	            	label: '库存数量',
	            	width: 80,
	            	edittype:'text',
	            	editable:true,
	            	align: "center"    
	            },{
	            	name: 'goodCount',
	            	label: '数量',
	            	width: 80,
	            	edittype:'text',
	            	editable:true,
	            	align: "center",
	            	formatter: function(cellval, opts, rwdat, _act) {
                        return "<input type='text' class='form-control' style='color:red' id='addout"+rwdat.skuId+"' value='1' onkeyup='this.value=this.value.replace(/[^0-9]\D*$/,\"\")'/>";
                    }     
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