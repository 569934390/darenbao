define(['Portal'], function(Portal) {
	
	var ClassifySelect = function(){
		
		var _group;
		var _selectLeaf = false;
		var btnSelector = "btn-classify-value";
		var name = "name";
		var value = "id";
		var _btnType;
		var _readOnly;
		var _classifyStatu;
		
	    var getChineseNumber = function(n){
	    	switch(n){
	    	case 0: return "零";
	    	case 1: return "一";
	    	case 2: return "二";
	    	case 3: return "三";
	    	case 4: return "四";
	    	case 5: return "五";
	    	case 6: return "六";
	    	case 7: return "七";
	    	case 8: return "八";
	    	case 9: return "九";
	    	}
	    };

	    var getChinese = function(n){
	    	var temp = "";
	    	n = n+"";
	    	for(var i=0; i<n.length; i++)
	    		temp += getChineseNumber(parseInt(n.charAt(i))); 
	    	return temp;
	    };
	    
	    var _init = function(group, btn, default_data){
	    	_group = group;
	    	group.on("click", ".group-classify-filter a", function(){
	        	var li = $(this).parent();
	        	var ul = li.parent();
	        	var groupdiv = ul.parent();
	            var id = $(this).attr('data');
	            var deep = parseInt(groupdiv.attr("deep"));
	            groupdiv.siblings("[deep]").each(function(){
	        		if(parseInt($(this).attr("deep"))>deep)
	        			$(this).remove();
	        	});
	        	groupdiv.find('.'+btnSelector).attr('data', id).html($(this).html());
	        	if(id)
	        		_init_classify(group, id, deep+1);
	        });
			btn.click(function(){
				var btn = $(this);
				var temp_default = default_data;
				var filter = btn.parent().find(".group-classify-filter");
				filter.children().remove();
				// 获取第一级分类
		        $.post(Portal.webRoot+'/cargo/classify/queryParents.do', {status: _classifyStatu}, function(data){
		        	if(data && data.length>0) {
		    			_setlist(data, temp_default, filter);
		        	}
		        });
			});
	    }
	    
	    var _setlist = function(data, default_data, ul){
			ul.append("<li><a href='javascript:void(0);' data='"+default_data[value]+"'>"+default_data[name]+"</a></li>");
	    	if(data)
	        	for(var i=0; i<data.length; i++)
	        		ul.append("<li><a href='javascript:void(0);' data='"+data[i][value]+"'>"+data[i][name]+"</a></li>");
	    }
	    
	    var _create_classify = function(group, default_data, data, parent, deep, btn){
	    	if(group.children("[deep='"+deep+"']").length>0)
	    		return;
	    	var btnGroup = $("<div class='btn-group' parent='"+parent+"' deep='"+deep+"'></div>");
	    	if(parent==0 && deep==1)
	    		group.prepend(btnGroup);
	    	else
	    		group.children("[deep='"+(deep-1)+"']").after(btnGroup);
	    	btnGroup.append(btn);
	    	var ul = $("<ul class='dropdown-menu group-classify-filter'></ul>");
	    	btnGroup.append(ul);
			_setlist(data, default_data, ul, name, value);
	    }
	    
	    var _init_classify = function(group, parent, deep, forceGenerate){
	    	forceGenerate = forceGenerate===true? true: false;
	    	if(typeof group == "string" )
	    		group = $(group);
	    	parent = parent || 0;
	    	deep = deep || 1;
	    	var default_data = {};
	    	default_data[name] = getChinese(deep)+"级分类";
	    	default_data[value] = "";
	    	var btn = $("<button type='button' class='btn btn-"+_btnType+" dropdown-toggle' data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'></button>");
	    	btn.append("<span class='"+btnSelector+"' data='"+default_data[value]+"' deep='"+deep+"' >"+default_data[name]+"</span>");
	    	if(parent==0 && deep==1) {
	    		_init(group, btn, default_data);
		    	_create_classify(group, default_data, data || {}, parent, deep, btn);
	    	}
	    	var data = {};
	    	if(forceGenerate || (parent!=0 && deep!=1))
				// 获取分类
	    		$.ajax({
	    			url: Portal.webRoot+'/cargo/classify/queryByParentId.do',
	    			data: {parentId: parent+"", status: _classifyStatu}, 
	    			type: "post", 
	    			async: false, 
	    			success: function(data){
			        	if(forceGenerate || (data && data.length>0))
			    	    	_create_classify(group, default_data, data || [], parent, deep, btn);
			        }
	    		});
	    };
	    
	    var _getSelectedValue = function(){
	        var deep = 0;
	        var value = "";
	        if(_group)
		        _group.find("."+btnSelector).each(function(){
		        	var btnDeep = parseInt($(this).attr("deep"));
		        	var btnData = $(this).attr("data");
		        	if(btnDeep>deep){
			        	if(_selectLeaf && !btnData)
//			        		club.toast("warning", "必须选择分类");
			        		throw new Error("必须选择分类");
			        	if(btnData){
			        		deep = btnDeep;
			        		value = btnData;
			        	}
		        	}
		        });
	        return value;
	    }
	    
	    var _get_default = function(deep){
	    	var default_data = {};
	    	default_data[name] = getChinese(deep)+"级分类";
	    	default_data[value] = "";
	    	return default_data;
	    }
	    
	    var _set_classify = function(classifyList){
	    	if(classifyList.length==0)
	    		return;
			var btn = _group.find("span[deep='1']").parent();
			var filter = btn.parent().find(".group-classify-filter");
			filter.children().remove();
			$.ajax({
				url: Portal.webRoot+'/cargo/classify/queryParents.do', 
				type: "post",
				async: false,
				success: function(data){
	    			_setlist(data, _get_default(1), filter);
				}
			});
			var parent = 0;
	    	for(var index=0; index<classifyList.length; index++) {
	    		classify = classifyList[index];
	    		if(!classify)
	    			return false;
	    		var span = _group.find("span[deep='"+(index+1)+"']");
	    		if(!span) {
	    			_init_classify(_group, parent, index+1, true);
	        		span = _group.find("span[deep='"+(index+1)+"']");
	    		}
	    		var btn = span.parent();
	    		var ul = btn.next();
	        	var groupdiv = ul.parent();
	    		var a = ul.find("a[data='"+classify[value]+"']");
	    		if(_readOnly) {
	    			btn.attr("data-toggle", "readonly");
	    			btn.parent().siblings("a").addClass("hidden");
	    		}
	    		if(a.length>0){
		        	var li = a.parent();
		            var id = a.attr('data');
		            var deep = parseInt(groupdiv.attr("deep"));
		            groupdiv.siblings("[deep]").each(function(){
		        		if(parseInt(a.attr("deep"))>deep)
		        			a.remove();
		        	});
		        	if(id)
		        		_init_classify(_group, id, deep+1);
	    		}
    			span.attr('data', classify[value]).html(classify[name]);
	    		parent = classifyList[index][value];
	    	}
	    	if(_readOnly) {
	    		_group.children("div[parent][deep]").each(function(){
	    			var btn = $(this);
	    			btn.find("span.btn-classify-value").each(function(){
	    				if($(this).attr("data")=="")
	    					btn.remove();
	    			});
	    		});
	    	}
	    }
		
		return {
	        init: function(group, btnType, selectLeaf, classifyStatu){
	        	_selectLeaf = selectLeaf===true ? true : false;
	        	_btnType = btnType || "default";
	        	_classifyStatu = classifyStatu;
	        	_init_classify(group, 0, 1);
	        },
	        get: function(){
	            return _getSelectedValue();
	        }, 
	        set: function(classifyList, readOnly){
	        	_readOnly = readOnly===true? true: false;
	        	_set_classify(classifyList || []);
	        }
		}
	}
	
	return {
		statu: {
			ALL: undefined, 
			START: 1,
			STOP: 0
		}, 
		init: function(group, btnType, selectLeaf, classifyStatu){
			var select = new ClassifySelect();
			select.init(group, btnType, selectLeaf, classifyStatu);
			return select;
		}
	}
});