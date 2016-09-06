define(['Portal'], function(Portal) {
	var orginData;
	var dataMap = {};
	
	var init = function(cityData, parent){
		if(cityData && cityData.length>0)
			for(var i=0; i<cityData.length; i++) {
				var data = cityData[i];
				dataMap["ID_"+data.id] = data;
				if(parent !== undefined)
					data.parent = parent;
				init(data.children, data)
			}
	}
	
	var initDataObj = function(obj, data, key) {
		var temp = data[key];
		dataMap["ID_"+key] = obj;
		if(!temp)
			return;
		obj.children = [];
		for(var id in temp) {
			console.log("id:"+id);
			var child = dataMap["ID_"+id]||{};
			child.id = id;
			child.name = temp[id];
			child.parent = obj;
			obj.children.push(child);
			initDataObj(child, data, id);
		}
	}
	
//    $.post(Portal.webRoot+'/data/cityData.json', function(cityData){
//    	orginData = cityData;
//    	init(cityData);
//    });
    
    $.ajax({
    	url: Portal.webRoot+'/data/city-picker.json',
    	type:"post",
    	dataType: "text", 
    	success: function(data){
    		initDataObj({id:"86"}, eval("("+data+")"), "86");
    		orginData = dataMap["ID_86"].children;
	    }
    });
    
    var _get = function(id){
		return dataMap["ID_"+id];
    }
    
    return {
    	get: function(id) {
    		return _get(id);
    	}, 
    	getAll: function() {
    		return orginData;
    	},
    	getParentId: function(id) {
    		var obj = _get(id);
    		if(obj !== undefined){
    			var parent = obj.parent;
    			if(parent !== undefined){
    				return parent.id;
    			} else{
    				return "";
    			}
    		} else
    			return "";
    	},
    	getName: function(ids) {
    		if(ids != undefined && ids != ""){
    			var names = "";
    			var idArray = ids.split(",");
    			for(var tmp in idArray){
    				names = names + _get(idArray[tmp]).name + ",";
    			}
    			if(names.length > 0){
    				return names.substr(0,names.length-1);
    			} else{
    				return "";
    			}    			
    		} else{
    			return "";
    		}
    	},
    	getProvinceName: function(ids) {
    		if(ids != undefined && ids != ""){
    			var names = "";
    			var idArray = ids.split(",");
    			for(var tmp in idArray){
    				if(_get(idArray[tmp]).parent.id == '86'){
    					names = names + _get(idArray[tmp]).name + ",";    					
    				}
    			}
    			if(names.length > 0){
    				return names.substr(0,names.length-1);
    			} else{
    				return "";
    			}    			
    		} else{
    			return "";
    		}
    	}
    }
});