define([
	'text!modules/goodmanager1/addgood/templates/evaluateDetail.html',
	'css!styles/css/good.css',
	'modules/upload/Uploader',
    'Portal'
], function(addViewTpl,addCss,uploader,Portal) {
	var options = {
        height: $(window).height()*0.9,
        modal: false,
        draggable: false,
        content: addViewTpl,
        autoResizable: true,
        dialogClass:addCss
    };
    var popup;
    var that;
    var goodData;
	return {
		openAddWin:function(listView,editValue){
		      popup = club.popup($.extend({}, options, {
	                modal: true
	            }))
	           goodData=editValue;
             $("input[name=tradeGoodId]").val(editValue.tradeGoodId);
		      this.initData();
		      this.initButton();
		},
		initData:function(){
	             that=this;
	            $.post(Portal.webRoot+'/good/getEvaluation.do',{tradeGoodId:goodData.tradeGoodId},function(result){
	               var length =result.evaluationList.length;
	               var evaluationList=result.evaluationList;
	               console.log(evaluationList);
	               var str="";
	               for(var i=0;i<length;i++){
	            	   
	               str+='<div class="col-md-4">';
	               str+=' <div class="form-group required">';
	               str+='<label class="col-md-5 control-label">评价用户：</label>';
	               str+='<div class="input-group col-md-7">';
	               str+=evaluationList[i].username;
	               str+='</div>';
	               str+='</div>';
	               str+='</div>';
	               str+='<div class="col-md-4">' ;
	               str+='<div class="form-group required">' ;
	               str+='<label class="col-md-5 control-label">商品规格：</label>' ;
	               str+='<div class="input-group col-md-7">';
	               str+=evaluationList[i].skuName;
	               str+='</div>';
	               str+='</div>';
	               str+='</div>';
	               str+='<div class="col-md-4">';
	               str+='<div class="form-group required">' ;
	               str+='<label class="col-md-5 control-label">评价等级：</label>' ;
	               str+='<div class="input-group col-md-7">' ;
	               str+=evaluationList[i].score;
	               str+='</div>' ;
	               str+='</div>';
	               str+='</div>';
	               str+='<div class="col-md-12">';
	               str+='<div class="form-group required">';
	               str+='<label class="col-md-2 control-label">评价内容：</label>';
	               str+='<div class="input-group col-md-10" >';
	               str+=evaluationList[i].content;
	               str+='</div>';
	               str+='</div>';
	               str+='</div>';
	               
	               str+='<div class="col-md-12">' ;
	               str+='<div class="form-group">';
	               str+='<label class="col-md-2 control-label">图片详情：</label>';

	               str+='<div class="input-group col-md-10" >' ;
	               for(var j=0;j<evaluationList[i].imageVoList.length;j++){
	                   str+='<img width="60px" src='+evaluationList[i].imageVoList[j].url+'>';
	               }
	               str+='</div>';
	               str+='</div>';
	               str+='<div class="col-md-12">' ;
	               str+='<div class="form-group">';
	               str+='<button type="button" class="btn btn-primary" id="'+evaluationList[i].id+'" action="evaluate-del-button">删除</button></div></div></div>';
	               $(".goodManagerWindow .panel-body").html(str);
	               that.initButton();
	  		     /* var optionimage ={
	  		            	container: "logo_img_container"+evaluationList[i].id, 
	  		            	max_file_count: 5,
	  		            	image_list: evaluationList[i].imageVoList ? evaluationList[i].imageVoList : [],
	  		            	read_only: true
	  		            };
	  		          var brandLogoUploader = uploader.create(optionimage);*/
	               }
	            });
		},
		initButton:function(){
			var me = this;
            $('.goodManagerWindow button').click(function(){
          	  var btnAction = $(this).attr('action');
              switch(btnAction){
                  case 'cancel-button': popup.close();break;
                  case 'evaluate-del-button':
                	var id=$(this).attr('id');
                	var t= club.confirm('您确定要执行删除操作吗？');
                	 t.result.then(function resolve(){
                       	$.post(Portal.webRoot+'/good/deleteEvaluation.do',{"id":id},
                       		function(result){
                       		if(result!=null){
                       			if(result.code=='0'){
                       				club.toast('info',result.msg );
                       				$(".goodManagerWindow .panel-body").html('');
                       				me.initData()
                       			}else{
                       				 club.toast('warn',result.msg ); 
                       			}
                       		}else{
                       			 club.toast('warn','操作失败' );
                       		}
                       	});
             	 }, function reject(){
             		 return;
             	 });  
                  	break;
              }
          });	
		}
	};
});