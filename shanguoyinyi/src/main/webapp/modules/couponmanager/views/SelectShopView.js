define(
		[ 'text!modules/couponmanager/templates/SelectShop.html',
				'css!styles/css/good.css', 'Portal'],
		function(addViewTpl,addCss,Portal) {
			var options = {
				height : $(window).height() * 0.9,
				modal : false,
				draggable : false,
				content : addViewTpl,
				autoResizable : true,
				dialogClass : addCss
			};
			
			var GRID_DOM = "#selectShop-info-content-div";
			var refresh=function(){
	            pageData(1);
	        };
			var search=function(){
	            pageData(1);
	        };

		    var pageData=function (page, rowNum, sortname, sortorder) {
		    	var conditions = $('.selectShop-info-main-btngroup input[name=conditions]').val();
		    	var data = {
		            	'Condition':conditions
		            };
	            
	    		rowNum = rowNum
				|| $(GRID_DOM).grid("getGridParam",
						"rowNum");
	           $.post(Portal.webRoot+'/Subbranch/getSubbranceName.do',{              
	                start:(page-1)*rowNum,
	                limit:rowNum,
	                conditionStr:JSON.stringify(data)
	           },function(result){
	                result = Portal.convertPage(result);
	                result.page = page;
	                $(GRID_DOM).grid("reloadData", result);
	                console.log(result);
	            });
	            return false;
	        };
	        var _initListView=function(){
	            var me = this;
	            //参数配置
	            var opt = {
	                height:$(window).height()-125,
	                width:$(window).width(),
	                colModel: [
	                    {name: 'id', hidden:true, key:true}, 
	                    {name: 'number', label: '店铺编号', width: 80, align: "center", title: false, sortable: false},  //内容没有提示
	                    {name: 'name', label: '店铺名称', width:60, align: "center", sortable: false},                     
	                ], 
	                pager: true, 
	                rowNum: 20, 
	                rowList: [10,20,50,100,200], 
	                datatype: "json", 
	                recordtext:"{0} 到 {1} 总共: {2} ", 
	                pgtext : " {0} / {1}", 
	                rowtext:"{0}", 
	                gotext:'跳转至第{0}页', 
	                multiselect:false, 
	                pageData:pageData
	            };
	            pageData(1);
	            //加载grid
	            $grid = $(GRID_DOM).grid(opt);
	            $('.gotext').find('span').html('跳转至第<input class="ui-pagination-input">页');
	        }		
	        var popup;
			return {
				openAddwin : function(listView,editValue) {
					
                    console.log(editValue);
                    
					popup = club.popup($.extend({}, options, {
						modal : true
					}))
					_initListView();				
					$('.selectShopWindow button').click(function(){
		                var btnAction = $(this).attr('action');
		                switch(btnAction){
		                   case 'cancel-button': popup.close();break;
		                   case 'search' : search(); break;
		                   case 'sure-button':
		                	   var selectRecord=$(GRID_DOM).grid('getSelection');
		                	   if(selectRecord==null ||selectRecord.length==0){
		                		   return club.toast('warn', '请选择店铺！');
		                	   }else if(selectRecord!=null && selectRecord.length >1){
		                		   return club.toast('warn', '只能选择一家店铺！');
		                	   }else{
		                		     var ids = [];
		                			 for (var i = editValue.length - 1; i >= 0; i--) {
		                			 ids.push(editValue[i].id);
		                			 };           
		                			 var r= club.confirm('您确定要分配到此店铺吗？');
		                			 r.result.then(function resolve(){
		                			 $.post(Portal.webRoot+'/couponManagerController/allocateShop.do',{
		                			 IdStr:ids.join(','),
		                			 shopId:selectRecord.id,
		                			 shopCode:selectRecord.number},function(result){
		                			 if (result.success) {
		                			 club.toast('info', '操作成功！');
		                			 listView.refresh();
		                			 popup.close();
		                			 }
		                			 else{
		                			 club.toast('error', result.msg);
		                			 }
		                			 
		                			 });
		                			 }, function reject(){
		                			 return;
		                			 });
		                		   
		                	      }
		                	   break;
		                   
		                }
		            });

				}

			};
		});