define([
	'text!modules/sg_bannermanager/detail/templates/DetailCarouselImgView.html',
    'Portal',
], function(DetailCarouselImgViewTpl,Portal) {
	var options = {
        height: $(window).height()*0.9,
        modal: false,
        draggable: true,
        content: DetailCarouselImgViewTpl, 
        autoResizable: true
    };
    var popup;
	return {
		openDetWin:function(listView,action,record){
            popup = club.popup($.extend({}, options, {
                modal: true
            }));
            $('#carouse-detail-img').attr("src",record.picUrl);
            if(record.lineStatus==0){
            	$('#carousel-nine-t-text').html(record.richText);
            	$('#carousel-nine-t').show();
            	$('#carousel-nine-d').hide();
            }else{
            	$('#carousel-nine-d-text').text(record.richText);
            	$('#carousel-nine-t').hide();
            	$('#carousel-nine-d').show();
            }
		}
	};
});