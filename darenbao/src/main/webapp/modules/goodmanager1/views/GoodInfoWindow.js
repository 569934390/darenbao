define([
	'text!modules/goodmanager1/templates/goodInfoWindow.html',
    'modules/cargomanager/views/CargoSkuTypeWindow',
    'modules/cargomanager/views/ClassifySelect',
    'modules/cargomanager/views/BrandSelect',
    'modules/cargomanager/views/SupplierSelect',
    'modules/upload/Uploader',
    'Portal',
    'text!data/cityData.json'
], function(goodInfoWindow, cargoSkuTypeWindow, classifySelect, brandSelect, supplierSelect, uploader, Portal, regionData) {
	var options = {
	        height: $(window).height()*0.9,
	        modal: true,
	        draggable: false,
	        content:goodInfoWindow,
	        autoResizable: true
	    };
	    var popup;
	    var saveData = {
	    	deleteSkus: {},
	    
	    	getDeleteSkus: function(){
	    		var result = [];
	    		for(var key in this.deleteSkus)
	    			result.push(key);
	    		return result;
	    	}
	    }
	    
	    var popWin = {};
	    var init = function(goodRecord, readOnly){
	    	console.log(goodRecord);
	    	popWin.cargoId = goodRecord.cargoId || 0;
	    	popWin.readOnly = readOnly || false;
	    	popWin.main = $(".good-info-window");
	    	popWin.body = $(".good-info-window > .modal-body");
	    	popWin.title = $(".good-info-window > .modal-header > .modal-title");
	    	popWin.footer = $(".good-info-window > .modal-footer");
	    	popWin.btnAddSkuType = $(".good-info-window .btn-cargo-sku-type-add");
	    	popWin.skuContainer = $(".good-info-window .cargo-manager-sku-container");
	    	popWin.skuGeneratePanel = $(".good-info-window .cargo-manager-sku-generate-list");
	    	popWin.skuList = $(".good-info-window .cargo-manager-sku-list");
	    	popWin.grid = $(".good-info-window .cargo-manager-sku-list-content-div");
	    	popWin.smallImgId = "small_img_container";
	    	popWin.showImgId = "show_img_container";
	    	popWin.detailImgId = "detail_img_container";
			popWin.classifySelect = classifySelect.init(".good-info-window .classify-group", "default", false);
			popWin.brandSelect = brandSelect.init(".good-info-window .brand-group", "default", false);
			popWin.supplierSelect = supplierSelect.init(".good-info-window .supplier-group", "default", false);
	    	initSkuListGrid(goodRecord);
			initData(goodRecord, readOnly);
			console.log(goodRecord);
	    }
	    
	    var initData = function(goodRecord, readOnly){
	    	var data = loadData(goodRecord.cargoId);
	    	var skuList=loadGoodSku(goodRecord.tradeGoodId);
	    	console.log(skuList);
	    	if(readOnly){
	    		$("input[name='cargoNo']").attr("disabled", "disabled");
	    		$("input[name='cargoName']").attr("disabled", "disabled");
	    		$("input[name='goodName']").attr("disabled", "disabled");
		        $("input[name='describe']").attr("disabled", "disabled");
		        $("input[name='columnName']").attr("disabled", "disabled");
		        $("input[name='createTime']").attr("disabled", "disabled");
		        $("input[name='statusName']").attr("disabled", "disabled");
		        $("input[name='labelName']").attr("disabled", "disabled");
		        $("input[name='statusName']").attr("disabled", "disabled");
		        $("input[name=beginTime]").attr("disabled", "disabled");
		        $("input[name=endTime]").attr("disabled", "disabled");
		        $("select[name=postid]").attr("disabled", "disabled");
	    	}
	    	    
	    	    $("input[name='goodName']").val(goodRecord.name);
	    	    $("input[name='describe']").val(goodRecord.describe);
	    	    $("input[name='columnName']").val(goodRecord.columnName);
    	        $("input[name='createTime']").val(goodRecord.createTime);
    	        $("input[name='statusName']").val(goodRecord.statusName);
    	        if(goodRecord.beginTime!=null && goodRecord.beginTime !=undefined && goodRecord.beginTime!=""){
    	        	$("input[name=beginTime]").val(goodRecord.beginTime);
              	    $("input[name=endTime]").val(goodRecord.endTime);
    	        }else{
    	        	$(".beginTime").hide();
            		$(".endTime").hide();
    	        }
    	        $("input[name=beginTime]").val(goodRecord.beginTime);
          	    $("input[name=endTime]").val(goodRecord.endTime);
    
    	       
                $.post(Portal.webRoot+'/good/getGoodLabels.do',{goodId:goodRecord.tradeGoodId},function(result){
                	var labelStr="";
             	   console.log(result);
       	        for(var i=0;i<result.length;i++){
   	        	if(i != result.length-1){
   	        		labelStr+= result[i].labelName+"、"
   	        	 }else{
   	        		labelStr+= result[i].labelName;
   	        	  }   	        	
   	            }
       	          $("input[name='labelName']").val(labelStr);
  	          });     
                
                $.post(Portal.webRoot+'/carriageRuleController/getCarriageRuleList.do',function(result){
 	          	   console.log(result);
 	          	   var str="";
 	                 for(var j=0;j<result.length;j++){
 	                	 if(goodRecord !=null && goodRecord != undefined && result[j].id==goodRecord.postid){
 	                		 str+="<option value='"+result[j].id+"' selected='selected'>"+result[j].templateName+"</option>";
 	                	 }else{
 	                		 str+="<option value='"+result[j].id+"'>"+result[j].templateName+"</option>";
 	                	 }	                  
 	                 }        
 	              $(".postid").append(str);
 	 		     });
    	        
    	        
	    	if(data.cargoNo)
	    		$("input[name='cargoNo']").val(data.cargoNo);
	    	if(data.cargoName)
	    		$("input[name='cargoName']").val(data.cargoName);
	    	if(data.classifyList)
	    		popWin.classifySelect.set(data.classifyList, readOnly);
	    	if(data.brand)
	    		popWin.brandSelect.set(data.brand.id, data.brand.name, readOnly);
	    	if(data.supplier)
	    		popWin.supplierSelect.set(data.supplier.id, data.supplier.name, readOnly);
	    	
	    	popWin.smallImageUploader = uploader.create({
	    		container: popWin.smallImgId, 
	    		max_file_count: 1,
	    		image_list: data.smallImage ? [data.smallImage] : [], 
	    		read_only: readOnly
	    	});
	    	
	    	$("#"+popWin.showImgId).attr("groupId", data.showImages ? data.showImages.groupId : 0);
	    	popWin.showImageUploader = uploader.create({
	    		container: popWin.showImgId, 
	    		max_file_count: 99, 
	    		image_list: data.showImages ? data.showImages.images : [], 
	    		read_only: readOnly
	    	});
	    	
	    	$("#"+popWin.detailImgId).attr("groupId", data.detailImages ? data.detailImages.groupId : 0);
	    	popWin.detailImageUploader = uploader.create({
	    		container: popWin.detailImgId, 
	    		max_file_count: 99, 
	    		image_list: data.detailImages ? data.detailImages.images : [], 
	    		read_only: readOnly
	    	});
	    	
	    	initSkuData(skuList);
	    	initSkuTypes(data.skuTypes)
			checkSku();
	    	if(readOnly) {
	    		popWin.skuGeneratePanel.find(".cargo-manager-sku-type-list-btn").attr("disabled", "disabled");
	    		popWin.skuContainer.find(".btn-cargo-manager-sku-list-delete").parent().parent().addClass("hidden");
	    	}
	    	
	    }
	    
	    var loadData = function(cargoId){
	    	var result;
	    	if(cargoId)
	    		$.ajax({
	    			url: Portal.webRoot+'/cargo/getInfo.do', 
	    			type: "post",
	    			data: {cargoId: cargoId},
	    			async: false, 
	    			success: function(data){
	    				result = data;
	    			}
	    		});
	    	return result || {};
	    }
	    
	    /**
	     * 加载商品对应的sku的数据
	     */
	    var loadGoodSku = function(tradeGoodId){
	    	var result;
	    	if(tradeGoodId)
	    		$.ajax({
	    			url: Portal.webRoot+'/good/selectGoodSku.do', 
	    			type: "post",
	    			data: {tradeGoodId: tradeGoodId},
	    			async: false, 
	    			success: function(data){
	    				result = data;
	    			}
	    		});
	    	return result || {};
	    }
	    
	    var initSkuTypes = function(data){
	    	data = data || [];
			cargoSkuTypeSelectCallback({list:data});
			$.each(data, function(index, skuType){
				cargoSkuItemSelectCallback({parent: skuType.id, list: skuType.items}, true);
			});
	    }
	    
	    var initSkuData = function(data){
	    	data = data || {};
	    	popWin.grid.grid("clearGridData");
	    	$.each(data, function(i, row){
	    		popWin.grid.grid("addRowData", row);
	    	});
	    }
	    
	    var initSkuListGrid = function(){
	        //参数配置
	        var opt = {
	            height: popWin.readOnly?"300px":"271px",
	            width: "100%",
	            colModel: [
	                {name: 'id', hidden:true}, 
	                {name: 'goodId', hidden:true}, 
	                {name: 'code', label: '货品SKU编码', width:60, align: "center", sortable: false,
	                	formatter: function(cellval, opts, rwdat, _act){
	                		if(popWin.readOnly)
	                			return cellval;
	                		var input = "<input type='text' class='form-control' name='code' data-rule='required' value='"+cellval+"' />";
	                        return input;
	                	}
	                },  

	                {name: 'cargoSkuName', label: 'SKU', width: 60, align: "center", sortable: false}, 
	                {name: 'nums', label: '上架数量', width: 60, align: "center", sortable: false}, 
	                {name: 'marketPrice', label: '原价', width: 60, align: "center", sortable: false	},
	                {name: 'salePrice', label: '现价', width: 60, align: "center", sortable: false}
	                
	            ], 
	            pager: false, 
	            datatype: "json", 
	            multiselect: !popWin.readOnly, 
	            pageData: function(){return false;}, 
	            beforeSelectRow: function(e, rowid){
	        		if(popWin.readOnly)
	        			return false;
	            	var btn = $(e.toElement);
	            	if(btn.attr("type")!="checkbox")
	                	return false;
	        		return true;
	            }, 
	            onSelectRow: function(e, rowid) {
	            	$("#"+rowid).removeClass("ui-state-highlight");
	            	$("#"+rowid).attr("aria-selected", false);
	            }
	        };
	        //加载grid
	        popWin.grid.grid(opt);
	    	popWin.grid.on("mousemove", ".ui-jqgrid-view .ui-jqgrid-bdiv table.ui-jqgrid-btable", function(){
	    		repaintScroll();
	    	});
	    }
	    
	    var initTrigger = function(cargoId){
	    	// 增加货品规格按钮事件
	    	popWin.btnAddSkuType.on("click", "button", function(){
	    		var selected = {};
	    		popWin.main.find(".cargo-manager-spec-row").each(function(){
	    			selected[$(this).attr("data-id")] = 1;
	    		});
	    		cargoSkuTypeWindow.openSkuType(cargoId, selected, cargoSkuTypeSelectCallback);
	    	});
	    	// 增加货品规格选项按钮事件
	    	popWin.main.on("click", ".cargo-manager-spec-item-add", function(){
	    		var btn = $(this);
	    		var id = btn.attr("data-id");
	    		var selected = {};
	    		popWin.main.find(".cargo-manager-spec-row[data-id="+id+"] .cargo-manager-spec-item").each(function(){
	    			selected[$(this).attr("data-id")] = 1;
	    		});
	    		cargoSkuTypeWindow.openSkuItem(id, btn.attr("data-type"), selected, cargoSkuItemSelectCallback);
	    	});
	    	// SKU列表候选项
	    	popWin.skuGeneratePanel.on("click", ".cargo-manager-sku-type-list-btn", function(){
	    		addSku($(this));
	    	});
	    	// SKU列表删除按钮
	    	popWin.skuContainer.on("click", ".btn-cargo-manager-sku-list-delete", function(){
	    		deleteSku(popWin.grid.grid('getCheckRows'));
	    	});
	    }
	    
	    var initTitle = function(name) {
	    	popWin.title.html(name);
	    }
	    
	    var initBtn = function(options){
	    	options = options || {};
	    	popWin.footer.html("");
	    	if(options["save"])
	    		popWin.footer.append("<button type='button' class='btn btn-primary btn-save'>"+options["save"]+"</button>");
	    	if(options["cancel"])
	    		popWin.footer.append("<button type='button' class='btn btn-default btn-cancel'>"+options["cancel"]+"</button>");
	    	popWin.footer.on("click", "button.btn-cancel", function(){
	    		popup.close();
	    	});
	    	popWin.footer.on("click", "button.btn-save", function(){
	    		if(save()===false)
	    			club.toast("warning", "信息填写不完整");
	    	});
	    }
	    
	    var _remove_more = function(data_src, data_target, src_attr, target_field, beforeRemove){
	    	for(var i=0; i<data_src.length; i++){
	    		var src = data_src[i];
				var flag = false;
				src = $(src);
	    		$.each(data_target, function(i, obj){
	    			if(src.attr(src_attr) == obj[target_field])
	    				flag = true;
	    		});
	    		if(!flag)
	    			if(beforeRemove && beforeRemove(src)===false)
	    				return false;
	    			else
	    				src.remove();
	    	}
	    }
	    
	    // SKU规格类型选定回调
	    var cargoSkuTypeSelectCallback = function(data){
	    	if(!data || !(data=data.list))
	    		return;
	    	// 删除不需要的
	    	_remove_more(popWin.main.find(".cargo-manager-spec-row"), data, "data-id", "id");
			// 增加新的
			$.each(data, function(index, obj){
				if(popWin.main.find(".cargo-manager-spec-row[data-id="+obj.id+"]").length<=0){
	    			var specRow = $("<div class='cargo-manager-spec-row' data-id='"+obj.id+"' data-name='"+obj.name+"' ></div>");
	    			popWin.btnAddSkuType.before(specRow);
	    			var specSpan = $("<a class='btn btn-success disabled cargo-manager-spec-span' href='javascript:void(0);'>"+obj.name+"</a>");
	    			specRow.append(specSpan);
	    			var moreSkuItem = $("<a class='btn btn-link cargo-manager-spec-item-add' data-id='"+obj.id+"' data-type='"+obj.type+"' href='javascript:void(0);'>＋规格项</a>");
	    			specRow.append(moreSkuItem);
	    			if(popWin.readOnly)
	    				moreSkuItem.addClass("hidden");
				}
			});
	    	checkSpecItems();
	    }
	    
	    // SKU规格选项选定回调
	    var cargoSkuItemSelectCallback = function(data, forceAdd){
	    	var parent = data.parent;
	    	if(!data || !(data=data.list))
	    		return;
	    	// 删除不需要的
	    	var flag = _remove_more(popWin.main.find(".cargo-manager-spec-row[data-id='"+parent+"'] .cargo-manager-spec-item"), data, "data-id", "id", function(src){
	        	var rows = popWin.grid.grid("getRowData");
	        	var id = src.attr("data-id");
	        	for(var i=0; i<rows.length; i++) {
	        		var values = rows[i].skuValue.split(",");
	        		for(var j=0; j<values.length; j++) {
	        			if(id==values[j]){
	        				club.toast("warning", "SKU规格选项被引用，不能删除");
	        				return false;
	        			}
	        		}
	        	}
	    	});
	    	if(!flag && !forceAdd)
	    		return false;
			// 增加新的
	    	var btnAdd = popWin.main.find(".cargo-manager-spec-row[data-id='"+parent+"'] .cargo-manager-spec-item-add");
	    	$.each(data, function(index, obj){
				if(popWin.main.find(".cargo-manager-spec-row[data-id='"+parent+"'] .cargo-manager-spec-item[data-id='"+obj.id+"']").length<=0){
//		    		var specItem = $("<a class='btn btn-link disabled cargo-manager-spec-item' href='javascript:void(0);' data-id='"+obj.id+"' data-name='"+obj.name+"' data-value='"+obj.value+"' >"+obj.name+"</a>");
		    		var specItem = $("<label class='cargo-manager-spec-item' data-id='"+obj.id+"' data-name='"+obj.name+"' data-value='"+obj.value+"' >"+obj.name+"</label>");
		    		btnAdd.before(specItem);
				}
	    	});
	    	checkSpecItems();
	    }
	    
	    var checkSpecItems = function(){
	    	var rows = popWin.main.find(".cargo-manager-spec-row");
	    	if(!rows || rows.length<=0)
	    		return false;
	    	var flag = true;
	    	var skuTypes = [];
	    	rows.each(function(index, row){
	    		if(!flag)
	    			return;
	    		row = $(row);
	    		var skuType = {id: row.attr("data-id"), name: row.attr("data-name"), items:[]};
	    		skuTypes.push(skuType);
	    		var items = row.find(".cargo-manager-spec-item");
	    		if(items.length<=0) {
	    			flag = false;
	    			return;
	    		}
	    		items.each(function(index, item){
	    			item = $(item);
	    			skuItem = {id: item.attr('data-id'), name: item.attr('data-name'), value: item.attr('data-value')};
	    			skuType.items.push(skuItem);
	    		});
	    	});
	    	if(!flag){
	    		popWin.skuContainer.addClass("hidden");
	    		location.href="#position-cargo-manager-sku-type";
	    	} else {
	    		popWin.skuContainer.removeClass("hidden");
	    		location.href = "#position-cargo-manager-sku-container";
	    		generateSkuList(skuTypes);
	    	}
	    }
	    
		function keysrt(key,desc) {
			return function(a,b){
				return desc ? ~~(a[key] < b[key]) : ~~(a[key] > b[key]);
			}
		}
		
	    var skulistAppend = function(skulist, name, items){
	    	var newlist = [];
	    	items = items.sort(keysrt("id", false));
	    	if(!skulist || skulist.length==0)
	    		$.each(items, function(index, item){
	    			newlist.push({
	    				ids: [item.id], 
//	    				name: name+": "+item.name
	    				name: item.name
	    			});
	    		});
	    	else
		    	$.each(skulist, function(i, sku){
		    		$.each(items, function(j, item){
			    		var newitem = $.extend(true, {}, sku);
			    		newitem.ids.push(item.id);
//			    		newitem.name = newitem.name + ", " + name+": "+item.name;
			    		newitem.name = newitem.name + ", " + item.name;
		    			newlist.push(newitem);
		    		});
		    		
		    	});
	    	return newlist;
	    }
		
	    /**
	     * 自动生成SKU待选列表
	     */
	    var generateSkuList = function(skuTypes){
	    	var panel = popWin.skuGeneratePanel;
	    	panel.html("");
	    	if(!skuTypes)
	    		return;
	    	skuTypes = skuTypes.sort(keysrt("id", false));
	    	var skulist = [];
	    	$.each(skuTypes, function(i, skuType){
	    		if(skuType && skuType.items)
	    			skulist = skulistAppend(skulist, skuType.name, skuType.items);
	    	});
	    	$.each(skulist, function(i, sku){
	    		var ids = sku.ids.join(",");
	    		var btn = $("<button type='button' class='btn btn-default btn-block cargo-manager-sku-type-list-btn' data-ids='"+ids+"' title='"+sku.name+"' >"+sku.name+"</button>");
				panel.append(btn);
	    		if(popWin.grid.find("tr > td[title='"+ids+"']").length>0)
	    			btn.addClass("hidden");
	    	});
	    }
	    
	    /**
	     * 增加SKU
	     */
	    var addSku = function(btn){
			popWin.grid.grid("addRowData", {"id":0, "code":"", "skuValue": btn.attr("data-ids"), "sku": btn.html(), "price": 0});
			btn.addClass("hidden");
			repaintScroll();
			checkSku();
	    }
	    
	    /**
	     * 删除SKU
	     */
	    var deleteSku = function(records){
	        if (records.length==0) {
	        	return false;
	        };
	        var me = this, bizIds = [];
	        for (var i = records.length - 1; i >= 0; i--) {
	        	popWin.grid.grid("delRowData", records[i]._id_);
	        	popWin.skuGeneratePanel.find(".cargo-manager-sku-type-list-btn[data-ids='"+records[i].skuValue+"']").removeClass("hidden");
	        	if(records[i].id)
	        		saveData.deleteSkus[records[i].id] = "";
	        };
			repaintScroll();
			checkSku();
	    }
	    
	    var checkSku = function(){
	    	var rows = popWin.grid.grid("getRowData");
	    	if(rows.length>0) {
	    		popWin.btnAddSkuType.children("button").addClass("hidden");
	    		popWin.btnAddSkuType.children("span").removeClass("hidden");
	    	} else {
	    		popWin.btnAddSkuType.children("button").removeClass("hidden");
	    		popWin.btnAddSkuType.children("span").addClass("hidden");
	    	}
	    }
	    
	    /**
	     * 重新绘制滚动条 -- 移动滚动条位置
	     */
	    var repaintScroll = function(){
	    	var scroll = popWin.body.find(".nicescroll-rails.nicescroll-rails-vr");
	        var tablediv = popWin.grid.find(".ui-jqgrid-view");
	        scroll.css({top: "33px", left: (tablediv.width()-14)+"px"});
	        if(tablediv.find(".nicescroll-rails.nicescroll-rails-vr").length>0)
	        	return;
	        tablediv.append(scroll.remove());
	    }
	    
	    var getSkuItems = function(){
	    	var rows = popWin.main.find(".cargo-manager-spec-row");
	    	if(!rows || rows.length<=0)
	    		return false;
	    	var flag = true;
	    	var result = [];
	    	rows.each(function(index, row){
	    		row = $(row);
	    		var data = {
	    			skuTypeId: row.attr("data-id"), 
	    			skuItemIds: []
	    		};
	    		result.push(data);
	    		var items = row.find(".cargo-manager-spec-item");
	    		if(items.length<=0) {
	    			flag = false;
	    			return false;
	    		}
	    		items.each(function(index, item){
	    			data.skuItemIds.push($(item).attr('data-id'));
	    		});
	    	});
	    	if(!flag)
	    		return false;
	    	return result;
	    }
	    
	    var getSkuInfos = function(){
	    	var rows = popWin.grid.grid("getRowData");
	    	var skuChange = [];
	    	var skuAdd = [];
	    	var flag = true;
	    	$.each(rows, function(index, row){
	    		var rowtr = $("#"+row._id_);
	    		var code = rowtr.find("input[name='code']").val();
	    		var price = rowtr.find("input[name='price']").val();
	    		if(!row.skuValue){
	    			flag = false;
	    			return false;
	    		}
	    		var skuItems = row.skuValue.split(",");
	    		var skuId = row.id;
	    		if(!skuId){
	    			skuAdd.push({
	    				skuCode: code,
	    				price: price, 
	    				skuItems: skuItems
	    			});
	    		}else{
	    			skuChange.push({
	    				skuId: skuId,
	    				skuCode: code,
	    				price: price
	    			});
	    		}
	    	});
	    	if(!flag)
	    		return false;
	    	return {
	    		skuDelete: saveData.getDeleteSkus(), 
	    		skuChange: skuChange,
	    		skuAdd: skuAdd
	    	}
	    }
	    
	    var detail = function(cargoManagerView, cargoId){
	    	initTitle("商品详情");
	    	initBtn({cancel: "返回"});
	    	popWin.btnAddSkuType.addClass("hidden");
	    }
	    
		return {
			open: function(cargoManagerView, goodRecord, readOnly /* action, editValue */){
				popup = club.popup(options);
				init(goodRecord, readOnly);
				console.log(goodRecord);
				   var optionimage ={
			            	container: "square_img_container", 
			            	max_file_count: 1,
			            	image_list:goodRecord.imgSquare?[{id:goodRecord.imgSquare,url : goodRecord.squareUrl}]:[],
			            	read_only : true
			            };
			        var squareUploader = uploader.create(optionimage);
			        var optionimage1 ={
			            	container: "rectangle_img_container", 
			            	max_file_count: 1,
			             	image_list:goodRecord.imgRectangle?[{id:goodRecord.imgRectangle,url : goodRecord.rectangleUrl}]:[],
			             	read_only : true
			            };
					var rectangleUploader = uploader.create(optionimage1);
					var largeImgUploader = uploader.create({
		            	container: "large_img_container", 
		            	max_file_count: 1,
		            	image_list:goodRecord.imgLarge?[{id:goodRecord.imgLarge,url : goodRecord.largeUrl}]:[],
		             	read_only : true
					});
					
	    		if(readOnly)
	    			detail(cargoManagerView, goodRecord.cargoId);
//	    		else if(cargoId && cargoId>0)
//	    			edit(cargoManagerView, goodRecord.cargoId);
//	    		else
//	    			add(cargoManagerView);
			}
		}
});