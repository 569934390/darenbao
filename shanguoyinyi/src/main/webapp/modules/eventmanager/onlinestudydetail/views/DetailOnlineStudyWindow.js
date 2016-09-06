define([
	'text!modules/eventmanager/onlinestudydetail/templates/detailView.html',
    'Portal'
], function(addViewTpl,Portal) {
	var options = {
	    height: $(window).height()*0.9,
        modal: false,
        draggable: true,
        content: addViewTpl,
        autoResizable: true
    };
    var popup;

	return {
		openAddWin:function(listView,action,editValue){
			
            popup = club.popup($.extend({}, options, {
                modal: true
            }))
            $(".DetailOnlineStudyWindow #title").html(editValue.title);
            $(".DetailOnlineStudyWindow #studyTypeName").html(editValue.studyTypeName);
            $(".DetailOnlineStudyWindow #studyChildTypeName").html(editValue.studyChildTypeName);
            $(".DetailOnlineStudyWindow #author").html(editValue.author);
            $(".DetailOnlineStudyWindow #readNum").html(editValue.readNum);
            $(".DetailOnlineStudyWindow #typeName").html(editValue.type==1?'文章':'视频');
            $(".DetailOnlineStudyWindow #covePic").html(editValue.covePicUrl!=null&&editValue.covePicUrl!=''?'<img src="'+editValue.covePicUrl+'"/>':'');
            if(editValue.type==2){
            	$(".DetailOnlineStudyWindow #videoUrl").attr("href",editValue.videoUrl);
//            	$(".DetailOnlineStudyWindow #videoUrl").html(editValue.videoUrl);
            	$(".DetailOnlineStudyWindow .videoUrl").show();
            }else{
            	$(".DetailOnlineStudyWindow .videoUrl").hide();
            }
            if(editValue.type==1&&editValue.file!=null&&editValue.file!=""){
            	$(".DetailOnlineStudyWindow #file").attr("href",editValue.file);
            	$(".DetailOnlineStudyWindow .file").show();
            }else{
            	$(".DetailOnlineStudyWindow .file").hide();
            }
            $(".DetailOnlineStudyWindow #content").html(editValue.content);
            
            $('.DetailOnlineStudyWindow button').click(function(){
                var btnAction = $(this).attr('action');
                switch(btnAction){
                    case 'cancel-button': popup.close();break;
                }
            });
		}
	};
});