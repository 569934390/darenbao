define([
	'text!modules/cargomanager/templates/CargoInfoWindow.html',
    'i18n!modules/cargomanager/i18n/cargomanager.i18n',
    'modules/cargomanager/views/CargoSkuTypeWindow',
    'modules/cargomanager/views/ClassifySelect',
    'modules/cargomanager/views/BrandSelect',
    'modules/cargomanager/views/SupplierSelect',
    'modules/upload/Uploader',
    'Portal',
    'text!data/cityData.json'
], function(cargoInfoWindow, i18n, cargoSkuTypeWindow, classifySelect, brandSelect, supplierSelect, uploader, Portal, regionData) {
	var options = {
        height: $(window).height()*0.9,
        modal: true,
        draggable: false,
        content: club.compile(cargoInfoWindow)(i18n),
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
    var init = function(cargoId, readOnly){
    	popWin.cargoId = cargoId || 0;
    	popWin.readOnly = readOnly || false;
    	popWin.main = $(".cargo-info-window");
    	popWin.body = $(".cargo-info-window > .modal-body");
    	popWin.title = $(".cargo-info-window > .modal-header > .modal-title");
    	popWin.footer = $(".cargo-info-window > .modal-footer");
    	popWin.btnAddSkuType = $(".cargo-info-window .btn-cargo-sku-type-add");
    	popWin.skuContainer = $(".cargo-info-window .cargo-manager-sku-container");
    	popWin.skuGeneratePanel = $(".cargo-info-window .cargo-manager-sku-generate-list");
    	popWin.skuList = $(".cargo-info-window .cargo-manager-sku-list");
    	popWin.grid = $(".cargo-info-window .cargo-manager-sku-list-content-div");
    	popWin.smallImgId = "small_img_container";
    	popWin.showImgId = "show_img_container";
    	popWin.detailImgId = "detail_img_container";
		popWin.classifySelect = classifySelect.init(".cargo-info-window .classify-group", "default", false, classifySelect.statu.START);
		popWin.brandSelect = brandSelect.init(".cargo-info-window .brand-group", "default", false);
		popWin.supplierSelect = supplierSelect.init(".cargo-info-window .supplier-group", "default", true);
    	initSkuListGrid(cargoId);
		initData(cargoId || 0, readOnly);
    }
    
    var initData = function(cargoId, readOnly){
    	var data = loadData(cargoId);
    	
    	if(readOnly){
    		$("input[name='cargoNo']").attr("disabled", "disabled");
    		$("input[name='cargoName']").attr("disabled", "disabled");
    	}
    	
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
    		read_only: readOnly, 
    		cur_class: {
		        div_class: "cargo-upload-btn-group"
    		}
    	});
    	
    	$("#"+popWin.detailImgId).attr("groupId", data.detailImages ? data.detailImages.groupId : 0);
    	popWin.detailImageUploader = uploader.create({
    		container: popWin.detailImgId, 
    		max_file_count: 99, 
    		image_list: data.detailImages ? data.detailImages.images : [], 
    		read_only: readOnly, 
    		cur_class: {
		        div_class: "cargo-upload-btn-group"
    		}
    	});
    	
    	initSkuData(data.skus);
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
    		row.sku = row.skuLong;
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
                {name: 'cargoId', hidden:true}, 
                {name: 'code', label: 'SKU编码', width:60, align: "center", sortable: false,
                	formatter: function(cellval, opts, rwdat, _act){
                		if(popWin.readOnly)
                			return cellval;
                		var input = "<input type='text' class='form-control' name='code' data-rule='required' value='"+cellval+"' />";
                        return input;
                	}
                },  
                {name: 'skuValue', hidden: true}, 
                {name: 'sku', label: 'SKU', width: 60, align: "center", sortable: false}, 
                {name: 'price', label: '进货价', width: 60, align: "center", sortable: false, hidden: true,
                	formatter: function(cellval, opts, rwdat, _act){
                		if(popWin.readOnly)
                			return cellval;
                		var input = "<input type='text' class='form-control' name='price' data-rule='required' value='"+cellval+"' />";
                        return input;
                	}
                }
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
		popWin.skuContainer.addClass("hidden");
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
    		popWin.footer.append("<button type='button' class='btn btn-info btn-save'>"+options["save"]+"</button>");
    	if(options["cancel"])
    		popWin.footer.append("<button type='button' class='btn btn-default btn-cancel'>"+options["cancel"]+"</button>");
    	popWin.footer.on("click", "button.btn-cancel", function(){
    		popup.close();
    	});
    	popWin.footer.on("click", "button.btn-save", function(){
    		save();
//    		if(save()===false)
//    			club.toast("warning", "信息填写不完整");
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
    	return true;
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
        		var values;
        		if(rows[i].skuValue)
        			values = rows[i].skuValue.split(",");
        		else
        			values = [];
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
//	    		var specItem = $("<a class='btn btn-link disabled cargo-manager-spec-item' href='javascript:void(0);' data-id='"+obj.id+"' data-name='"+obj.name+"' data-value='"+obj.value+"' >"+obj.name+"</a>");
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
    				name: name+": "+item.name
//    				name: item.name
    			});
    		});
    	else
	    	$.each(skulist, function(i, sku){
	    		$.each(items, function(j, item){
		    		var newitem = $.extend(true, {}, sku);
		    		newitem.ids.push(item.id);
		    		newitem.name = newitem.name + ", " + name+": "+item.name;
//		    		newitem.name = newitem.name + ", " + item.name;
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
        	if(records[i].id) {
            	$.ajax({
            		url: Portal.webRoot+"/cargo/isSkuCanDelete.do",
            		data: {skuId: records[i].id}, 
            		type: "post",
            		async: false, 
            		success: function(data){
            			if(data.code){
            	        	popWin.grid.grid("delRowData", records[i]._id_);
            	        	popWin.skuGeneratePanel.find(".cargo-manager-sku-type-list-btn[data-ids='"+records[i].skuValue+"']").removeClass("hidden");
            				saveData.deleteSkus[records[i].id] = "";
            			} else {
            				club.toast("warning", data.msg);
            			}
            		}
            	});
        	}else{
	        	popWin.grid.grid("delRowData", records[i]._id_);
	        	popWin.skuGeneratePanel.find(".cargo-manager-sku-type-list-btn[data-ids='"+records[i].skuValue+"']").removeClass("hidden");
        	}
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
        		club.toast("warning", "SKU错误");
    			flag = false;
    			return false;
    		}
    		if(!code){
        		club.toast("warning", "SKU编号填写错误");
    			flag = false;
    			return false;
    		}
    		if(!price){
        		club.toast("warning", "SKU进货价填写错误");
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
    
    var save = function(){
    	var flag = true;
    	var msg = [];
    	var data = {};
    	data.cargoId = popWin.cargoId+"";
    	data.cargoNo = $("input[name='cargoNo']").val();
    	if(!data.cargoNo) {
    		club.toast("warning", "必须填写货品编号");
    		return false;
    	}
    	data.cargoName = $("input[name='cargoName']").val();
    	if(!data.cargoName) {
    		club.toast("warning", "必须填写货品名称");
    		return false;
    	}
    	try{
        	data.classifyId = popWin.classifySelect.get()+"";
    	} catch (e) {
    		club.toast("warning", "必须选择分类");
    		return false;
    	}
    	data.supplierId = popWin.supplierSelect.get()+"";
//    	if(!data.supplierId) {
//    		club.toast("warning", "必须选择供应商");
//    		return false;
//    	}
    	data.brandId = popWin.brandSelect.get()+"";
    	if(!data.brandId) {
    		club.toast("warning", "必须选择品牌");
    		return false;
    	}
    	if(popWin.smallImageUploader.get().length<1) {
    		club.toast("warning", "必须选择缩略图");
    		return false;
    	}
		data.smallImage = popWin.smallImageUploader.get()[0];
    	if(popWin.showImageUploader.get().length<1) {
    		club.toast("warning", "必须选择显示图片");
    		return false;
    	}
    	data.showImages = {
    		groupId: $("#"+popWin.showImgId).attr("groupId"), 
    		images: popWin.showImageUploader.get()
    	}
    	if(popWin.detailImageUploader.get().length<1) {
    		club.toast("warning", "必须选择详情图片");
    		return false;
    	}
    	data.detailImages = {
    		groupId: $("#"+popWin.detailImgId).attr("groupId"), 
    		images: popWin.detailImageUploader.get()
    	}
    	var skuTypes = getSkuItems();
    	if(!skuTypes) {
    		club.toast("warning", "必须选择货品规格");
    		return false;
    	}
    	if(!flag) {
    		club.toast("error", msg.join("\r\n<br />"));
    		return false;
    	}
    	data.skuTypes = skuTypes;
    	var skuInfo = getSkuInfos();
    	if(!skuInfo)
    		return false;
    	data.skuDelete = skuInfo.skuDelete;
    	data.skuChange = skuInfo.skuChange;
    	data.skuAdd = skuInfo.skuAdd;
		$.post(Portal.webRoot+"/cargo/save.do", {cargo: encodeURI(JSON.stringify(data))}, function(data){
			if(!data.code) {
        		club.toast('error', data.msg);
			} else {
				club.toast('info', data.msg);
				popWin.cargoManagerView.pageData(1);
				popup.close();
			}
		});
    }
    
    var getMessage = function(msgs){
		var temp = "";
		for(var i=0; i<msgs.length; i++)
			temp += "<div>"+msgs[i]+"</div>"
		return temp;
    }
    
    var add = function(){
    	initTitle("新增货品");
    	initBtn({save: "保存", cancel: "取消"});
    	initTrigger(0);
    };
    
    var edit = function(cargoId){
    	initTitle("编辑货品");
    	initBtn({save: "保存", cancel: "取消"});
    	initTrigger(cargoId);
    };
    
    var detail = function(cargoId){
    	initTitle("货品详情");
    	initBtn({cancel: "返回"});
    	popWin.btnAddSkuType.addClass("hidden");
    }
    
	return {
		open: function(data, cargoId, readOnly){
			popWin.cargoManagerView = data.callback;
			popup = club.popup(options);
			init(cargoId, readOnly);
    		if(readOnly)
    			detail(cargoId);
    		else if(cargoId && cargoId>0)
    			edit( cargoId);
    		else
    			add();
    		location.href="#position-cargo-manager-window-begin";
		}
	}
});