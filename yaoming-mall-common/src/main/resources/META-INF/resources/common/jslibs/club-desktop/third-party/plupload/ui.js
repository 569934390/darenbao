define(function(){
function InitImgList(up,declass){
	var op=up.getOption();
	var targetID=op.container;
	var uploadBtn = op.upload_btn;
	var imageList=op.imageList;
	var defalut_class={
        div_class:"upload-btn-group",
        close_class:"upload-close"
	};
	
	if(!imageList){
		return;
	}
	$.extend(defalut_class, declass);
	for(var i=0;i<imageList.length;i++){
		var fileProgressWrapper = $('<div></div>');
        var Wrappeer = fileProgressWrapper;
        Wrappeer.attr('id', imageList[i].id).addClass(defalut_class.div_class);
        var choseLogo =$('<div id="'+imageList[i].id+'_CLOSE" class="'+defalut_class.close_class+'">X</div>');
        var progressText = $('<img id="'+imageList[i].id+'_IMG" img-rel="'+imageList[i].id+'" src="'+imageList[i].url+'" class="default_uploadImg"/>');
 		Wrappeer.append(choseLogo);
        Wrappeer.append(progressText);
        $('#' + uploadBtn).before(Wrappeer);
//        $('#' + targetID).prepend(Wrappeer);
        $('#'+imageList[i].id+'_CLOSE').on('click',function(){
//      	$('#'+imageList[i].id).remove();
           console.log(this.parentNode);
           this.parentNode.remove();
           up.refresh();
           $(op.browse_button).removeClass("hidden");
		   $(op.browse_button).next("div").removeClass("hidden");
        });
        up.refresh();
	}
}
function InitFileList(up,declass){
	var opfile=up.getOption();
	var targetID=opfile.container;
	var uploadBtn = opfile.upload_btn;
	var imageList=opfile.imageList;
	var defalut_class={
        div_class:"upload-btn-group",
        close_class:"upload-close"
	};
	
	if(!imageList){
		return;
	}
	$.extend(defalut_class, declass);
	for(var i=0;i<imageList.length;i++){
		var fileProgressWrapper = $('<div></div>');
        var Wrappeer = fileProgressWrapper;
        Wrappeer.attr('id', imageList[i].id).addClass(defalut_class.div_class);
        var choseLogo =$('<div id="'+imageList[i].id+'_CLOSE" class="'+defalut_class.close_class+'">X</div>');
		var downloadUrl=imageList[i].url;
		var fileName=downloadUrl.substring(downloadUrl.lastIndexOf('/')+1);
        var progressText = $('<img id="'+imageList[i].id+'_IMG" img-rel="'+imageList[i].id+'" src="./image/file.png" class="default_uploadImg"/>');
		var spanText="<span id='"+imageList[i].id+"_SPAN' class='default_uploadFile' file-url='"+downloadUrl+"'>"+ fileName+"</span>";
 		Wrappeer.append(choseLogo);
        Wrappeer.append(progressText);
        Wrappeer.append(spanText);
        $('#' + uploadBtn).before(Wrappeer);
//        $('#' + targetID).prepend(Wrappeer);
        $('#'+imageList[i].id+'_CLOSE').on('click',function(){
//      	$('#'+imageList[i].id).remove();
           console.log(this.parentNode);
           this.parentNode.remove();
           up.refresh();
           $(opfile.browse_button).removeClass("hidden");
		   $(opfile.browse_button).next("div").removeClass("hidden");
        });
        up.refresh();
	}
}
function FileProgress(file, targetID,declass, uploadBtn) {
    this.fileProgressID = file.id;
    this.targetID =targetID;
    this.file = file;
	var defalut_class={
        div_class:"upload-btn-group",
        close_class:"upload-close"
	};
    this.opacity = 100;
    this.height = 0;
    this.fileProgressWrapper = $('#' + this.fileProgressID);
    if (!this.fileProgressWrapper.length) {
		$.extend(defalut_class, declass);
        this.fileProgressWrapper = $('<div></div>');
        var Wrappeer = this.fileProgressWrapper;
        Wrappeer.attr('id', this.fileProgressID).addClass(defalut_class.div_class);
        var choseLogo =$('<div id="'+this.fileProgressID+'_CLOSE" class="'+defalut_class.close_class+'">X</div>');
        var progressText = $('<img id="'+this.fileProgressID+'_IMG" src="./image/load.gif" img-rel="-1" class="default_uploadImg"/>');
 		Wrappeer.append(choseLogo);
        Wrappeer.append(progressText);
        $('#' + uploadBtn).before(Wrappeer);
//        $('#' + targetID).prepend(Wrappeer);
    }
    this.height = this.fileProgressWrapper.offset().top;
    this.setTimer(null);
}

FileProgress.prototype.setTimer = function(timer) {
    this.fileProgressWrapper.FP_TIMER = timer;
};

FileProgress.prototype.getTimer = function(timer) {
    return this.fileProgressWrapper.FP_TIMER || null;
};

FileProgress.prototype.setChunkProgess = function(chunk_size) {
    var chunk_amount = Math.ceil(this.file.size / chunk_size);
    if (chunk_amount === 1) {
        return false;
    }

};

FileProgress.prototype.setProgress = function(percentage, speed, chunk_size) {
   
    var file = this.file;
    var uploaded = file.loaded;

    var size = plupload.formatSize(uploaded).toUpperCase();
    var formatSpeed = plupload.formatSize(speed).toUpperCase();
   
};

FileProgress.prototype.setComplete = function(up, info,imgType) {
    var img =$('#'+this.fileProgressID+'_IMG');
    var res = $.parseJSON(info);
    var url;
    if (res.url) {
        url = res.url;
    } else {
        var domain = up.getOption('domain');
        url = domain + encodeURI(res.key);
        var link = domain + res.key;
    }
//  var imageView = '-img';
    var isImage = function(url) {
        var res, suffix = "";
        var imageSuffixes = ["png", "jpg", "jpeg", "gif", "bmp"];
        var suffixMatch = /\.([a-zA-Z0-9]+)(\?|\@|$)/;
        if (!url || !suffixMatch.test(url)) {
            return false;
        }
        res = suffixMatch.exec(url);
        suffix = res[1].toLowerCase();
        for (var i = 0, l = imageSuffixes.length; i < l; i++) {
            if (suffix === imageSuffixes[i]) {
                return true;
            }
        }
        return false;
    };
    var isImg = isImage(url);
    if (!isImg) {
	
        img.attr('src', "./image/file.png");
		img.after("<span id='"+this.fileProgressID+"_SPAN' class='default_uploadFile' file-url='"+url+"'>"+ res.key+"</span>");
		
    } else {
        url += imgType;
        img.on('load', function() {
            img.attr('src', url);
        }).on('error', function() {
        });
        img.attr('img-rel','0');
        img.attr('src', url);
    }
};
FileProgress.prototype.setError = function() {};

FileProgress.prototype.setCancelled = function(manual) {};

FileProgress.prototype.setStatus = function(status, isUploading) {};

// 绑定取消上传事件
FileProgress.prototype.bindUploadCancel = function(up) {
    var self = this;
    if (up) {
        $('#'+this.fileProgressID+'_CLOSE').on('click',function(){
        	up.removeFile(self.file);
        	$('#'+self.fileProgressID).remove();
//      	var img=$("#"+self.targetID+" > div > .default_uploadImg");
//      	if(img.length)
        	up.refresh();
        	console.log(up.files);
        	var op=up.getOption();
        	$(op.browse_button).removeClass("hidden");
		   	$(op.browse_button).next("div").removeClass("hidden");
        });
    }
};

FileProgress.prototype.appear = function() {
    if (this.getTimer() !== null) {
        clearTimeout(this.getTimer());
        this.setTimer(null);
    }
   	this.fileProgressWrapper.css('opacity', 1);
    this.fileProgressWrapper.css('height', '');
    this.height = this.fileProgressWrapper.offset().top;
    this.opacity = 100;
    this.fileProgressWrapper.show();
};
function GetImgList(container){
	var img=$("#"+container+" > div > .default_uploadImg");
	var imgList=[];
	for(var i=0; i<img.length;i++){
		var rel=$('#'+img[i].id).attr("img-rel");
		var src=$('#'+img[i].id).attr("src");
		if(!rel ||rel=='-1')
		   continue;
		if(src && (new RegExp("\\-img$").test(src))) 
			src = src.substring(0, src.length-4);
		imgList.push({id:rel,url:src});   
	}
	console.log(imgList);
	return imgList;
};
function GetFileList(container){
	var files=$("#"+container+" > div > .default_uploadFile");
	var filesList=[];
	for(var i=0; i<files.length;i++){
		//var rel=$('#'+files[i].id).attr("img-rel");
		var rel=files[i].id;
		var src=decodeURI($('#'+files[i].id).attr("file-url"));
		if(!rel ||rel=='-1')
		   continue;
		if(src && (new RegExp("\\-img$").test(src))) 
			src = src.substring(0, src.length-4);
		filesList.push({id:rel,url:src});   
	}
	console.log(filesList);
	return filesList;
};
return {
		FileProgress :FileProgress,
       	InitImgList :InitImgList,
		InitFileList : InitFileList,
       	GetImgList:GetImgList,
		GetFileList:GetFileList
};
});
