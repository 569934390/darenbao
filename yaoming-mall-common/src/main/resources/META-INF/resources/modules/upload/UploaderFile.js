define([
    'common/jslibs/club-desktop/third-party/plupload/ui.js',
     'Portal'
],function(plUI,Portal){
	var IMG_URL_FIX = "-img";
	var ImageUploader = function(){ 
		var DEFAULT_FILE_COUNT = 10; 
		var default_options = { 
				max_file_count: DEFAULT_FILE_COUNT, 
				container: "", 
				upload_btn: "", 
				cur_class: { 
			        div_class:"upload-file-btn-group", 
			        close_class:"upload-file-close" 
			    }, 
//			    image_list: [{id:"", url: ""}], 
			    read_only: false
			};
		var _options;
		var _uploader;
		var _uploaderfile;
		
		var _getCount = function(){
			return $("#"+_options.container).find("."+_options.cur_class.close_class).length;
		}
		
		var _check = function(){
			if(_options.read_only) {
				_btn_hidden();
            	$("#"+_options.container).find("."+_options.cur_class.close_class).addClass("hidden");
				return false;
			}
            if(_getCount()>=_options.max_file_count) {
				_btn_hidden();
            	_uploader.refresh();
            	return false;
            }
            _btn_show();
        	_uploader.refresh();
        	return true;
		}
		
		var _btn_hidden = function(){
        	var btn = $("#"+_options.upload_btn);
        	btn.addClass("hidden");
        	btn.next("div").addClass("hidden");
		}
		
		var _btn_show = function(){
        	var btn = $("#"+_options.upload_btn);
        	btn.removeClass("hidden");
        	btn.next("div").removeClass("hidden");
		}
		
		var _init = function(options){
			if(options.read_only===true)
				options.read_only = true;
			else
				options.read_only = false;
			_options = $.extend(true, default_options, options);
			_options.max_file_count = _options.max_file_count<=0 ? DEFAULT_FILE_COUNT: _options.max_file_count;
			
			_options.upload_btn = _create_btn_id();
			
			$("#"+_options.container).html("");
			
			var uploadBtn = $("<div class='"+_options.cur_class.div_class+"' id='"+_options.upload_btn+"' ></div>");
			uploadBtn.append("<img src='./image/add.png' />");
			uploadBtn.css({cursor: "pointer"});
			$("#"+_options.container).append(uploadBtn);
		}
		
		var _create_btn_id = function() {
			var id = 0;
			do{
				id = "__upload_file_btn_"
					+ (new Date().getTime().toString(36))
					+ (Math.floor(Math.random()*1000000).toString(36));
			} while(id && $("#"+id).length>0);
			return id;
		}
		
		var getCharFromUtf8=function(str) {  
		    var cstr = "";  
		    var nOffset = 0;  
		    if (str == "")  
		        return "";  
		    str = str.toLowerCase();  
		    nOffset = str.indexOf("%e");  
		    if (nOffset == -1)  
		        return str;  
		    while (nOffset != -1) {  
		        cstr += str.substr(0, nOffset);  
		        str = str.substr(nOffset, str.length - nOffset);  
		        if (str == "" || str.length < 9)  
		            return cstr;  
		        cstr += utf8ToChar(str.substr(0, 9));  
		        str = str.substr(9, str.length - 9);  
		        nOffset = str.indexOf("%e");  
		    }  
		    return cstr + str;  
		}  
		
//		var _after_create = function(){
//			_check();
//		}
		var _create_words = function(options){
			_init(options);
	    	//默认样式 upload-btn-group upload-close
			_uploader = QiniuFile.uploader({
		        browse_button: _options.upload_btn,
		        container: _options.container,
		        upload_btn: _options.upload_btn, 
		        drop_element: _options.container,
		        max_file_size: '10mb',
		        chunk_size: '4mb',
		        imageList: _options.image_list,
		        uptoken_url: Portal.webRoot+"/qiNiuController/getQiNiuUploadFileToken.do",
		        domain: '${qiniu.file.domain}',
		        log_level: 1,
				filters : {
					max_file_size : '10mb',
					prevent_duplicates: true,
					// Specify what files to browse for
					mime_types: [
						{title : "上传word文档", extensions : "doc,docx"} 
					]
				},
		        init: {
		        	'Init':function(up,op){
		        		plUI.InitFileList(up,_options.cur_class);
		    			_check();
		        	},
		            'FilesAdded': function(up, files) {
		                plupload.each(files, function(file) {
		                	if(_getCount()>=_options.max_file_count) {
		                		up.removeFile(file);
		                	}else{
			                    var progress = new plUI.FileProgress(file, _options.container,_options.cur_class, _options.upload_btn);
			                    progress.bindUploadCancel(up);
		                	}
		                	up.refresh();
		                    _check();
		                });
		            },
		            'FileUploaded': function(up, file, info) {
		                var progress = new plUI.FileProgress(file, _options.container,_options.cur_class, _options.upload_btn);
		                progress.setComplete(up, info, IMG_URL_FIX);
		                _check();
		            },
//		            'UploadComplete':function(){
//		                  plUI.GetImgList('container');
//		            },
		            'Error': function(up, err, errTip) {
						console.log(errTip);
						console.log(err);
						if(err){
							if(err.code=='-600'){
								alert("上传文件过大，最大可上传10mb。");
							}else{
								alert(errTip);
							}
						}
						$('#'+err.file.id+"_CLOSE").trigger("click");
		            },
		            'Key': function(up, file) {
	                     var key = "";
	                     var timestamp=new Date().getTime();
	                     var imgType=file.name.substr(file.name.lastIndexOf(".")).toLowerCase();
	                     key=timestamp+Portal.randomStr(7)+imgType;
//	                     return key;
	                     return decodeURI(file.name);
		            }
		        }
			});
//			_after_create();
		}
		return {
			create_words:function(options){
				return _create_words(options);
			},
			getFiles:function(){
				return  plUI.GetFileList(_options.container);
			}
		}
	};
	
	return {
		create_words:function(options){
			var uploader = new ImageUploader();
			uploader.create_words(options);
			return uploader;
		}
	};
});
