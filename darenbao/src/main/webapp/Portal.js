/**
 * [app的全局信息，是一个Model，同时其是单例的]
 * @author [lyhcn]
 */
define(function() {
	function getWebRootPath() {
		var webroot = document.location.href;
		webroot = webroot.substring(webroot.indexOf('//') + 2, webroot.length);
		webroot = webroot.substring(webroot.indexOf('/') + 1, webroot.length);
		webroot = webroot.substring(0, webroot.indexOf('/'));
		if(webroot)
			return "/" + webroot;
		return "";
	};
	function convertPage(page){
		return {
			"rows": page.resultList,
	        "page": page.totalPages,
	        // "total": Math.ceil(mydataServer.length/rowNum),  传了total就不需要传records了,除非treeGrid不知道records的场景需要自己控制total
	        "records": page.totalRecords,
	        "id": "id"
	    };
	}
	return {
		webRoot:getWebRootPath(),
		convertPage:convertPage,
		extend:function(target,src){
			target.colModel=_.map(target.colModel, function(model,index){
				model.order = index+10;
				return model;
			});
			var _colModel = src.colModel;
			delete src.colModel;
			_.extend(target,src);
			if (_colModel!=null) {
				for (var j = _colModel.length - 1; j >= 0; j--) {
					var _c = _colModel[j];
					var append = false;
					for (var i = target.colModel.length - 1; i >= 0; i--) {
						var c = target.colModel[i];
						if(c.name==_c.name){
							_.extend(c,_c);
							append = true;
							break;
						}
					};
					if (!append) {
						target.colModel.push(_c);
					};
				};
				target.colModel = _.sortBy(target.colModel, function(col){ 
					return col.order; 
				});
			};
			return target;
		},
	 	randomStr:function(len){
	 		var targetStr="";
	 		var hexDigits = "0123456789abcdef";
	 		for(var i=0;i<len;i++){
	 			targetStr+=hexDigits.substr(Math.floor(Math.random() * 0x10), 1);
	 		}
	 		return targetStr;
	 	}
	};
});