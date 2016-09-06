define(
		[ 'text!modules/couponmanager/templates/AddCouponDetail.html',
				'css!styles/css/good.css', 'Portal' ],
		function(addViewTpl,addCss,Portal) {
			var mep = this;
			
			var options = {
				height : $(window).height() * 0.9,
				modal : false,
				draggable : false,
				content : addViewTpl,
				autoResizable : true,
				dialogClass : addCss
			};
			var objects=[];
			var GRID_DOM = "#addcouponDetail-info-content-div";
//	        var refresh=function() {
//				pageData(1);
//			};
//			this.refresh = refresh;
			$("#addcouponDetail-info-content-div .cbox").hide();
//		    var pageData=function (page, rowNum, sortname, sortorder) {
//		    	var conditions = $('input[name=conditions]').val();
//	            var status = $('select[name=status]').val();
//		    	var data = {
//		    			'couponId':record.id,
//		            	'conditions':'%'+conditions+'%',
//		                'statusConditions': status
//		            };
//	            
//	            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
//	           $.post(Portal.webRoot+'/couponManagerController/selectCouponDetail.do',{
//	                conditionStr:JSON.stringify(data),
//	                start:(page-1)*rowNum,limit:rowNum},function(result){
//	                result = Portal.convertPage(result);
//	                result.page = page;
//	                $(GRID_DOM).grid("reloadData", result);
//	            });
//	            return false;
//	        };
	        var _initListView=function(){
	            var me = this;
	            //参数配置
	            var opt = {
	                height:$(window).height()-300,
	                width:800,
	                cellEdit:true,
	                colModel: [
	                    {name: "shopCode", label: '店铺编号', width: 50, align: "center", title: false, sortable: false
//	                    	formatter: function(cellval, opts, rwdat, _act) {
//	                            return "<input type='text' class='form-control'  id='shop"+cellval+"' value='"+cellval+"' onkeyup='this.value=this.value.replace(/[^0-9]\D*$/,\"\")'/>";
//	                        }   
	                    },  //内容没有提示
	                    {name: "code", label: '兑换券编号', width:50, align: "center", sortable: false
//	                    	formatter: function(cellval, opts, rwdat, _act) {
//	                            return "<input type='text' class='form-control'  id='code"+cellval+"' value='"+cellval+"' onkeyup='this.value=this.value.replace(/[^0-9]\D*$/,\"\")'/>";
//	                        }   
	                    },             
	                    {name: "password", label: '兑换券密码', width:50, align: "center", sortable: false
//	                    	formatter: function(cellval, opts, rwdat, _act) {
//	                            return "<input type='text' class='form-control'  id='password"+cellval+"' value='"+cellval+"'  onkeyup='this.value=this.value.replace(/[^0-9]\D*$/,\"\")'/>";
//	                        }   
	                    }           
	                ], 
//	                pager: true, 
	                
//	                rowList: [10,20,50,100,200], 
	                datatype: "json" 
//	                recordtext:"{0} 到 {1} 总共: {2} ", 
//	                pgtext : " {0} / {1}", 
//	                rowtext:"{0}", 
//	                gotext:'跳转至第{0}页', 
//	                multiselect:true, 
//	                pageData:pageData
	            };
//	            pageData(1);
	            //加载grid
	            $grid = $(GRID_DOM).grid(opt);
	            $('.gotext').find('span').html('跳转至第<input class="ui-pagination-input">页');
	            //classifySelect.init(".cargo-manager-classify-group", "info");
	            //brandSelect.init(".btn-group-brand", "info");
	        }
			var popup;
	        
            var  record;
			return {
				refresh: function(){
					refresh();
				},
				openAddwin : function(listView,editValue) {
                    console.log(editValue);
					popup = club.popup($.extend({}, options, {
						modal : true
					}))
					record=editValue;
					_initListView();
					$('.addcouponDetailWindow button').click(function(){
		                var btnAction = $(this).attr('action');
		                switch(btnAction){
		                   case 'sure-button':
		                	   console.log(objects);
		                	   $.post(Portal.webRoot+'/couponManagerController/addCouponDetail.do',{modelJson:JSON.stringify(objects),
		                		   couponId:editValue.id},function(result){
                               if (result.success) {  
                            	   club.toast('info', '操作成功！');
                            	   listView.refresh();
		                		   popup.close();
                               }
                               else{
                                   club.toast('error', result.msg);
                               }
                               
                           });  
		                	   break;
		                   case 'cancel-button': popup.close();break;
		                   
		                }
		            });
					
				     $('.fileupload-select').fileupload({
			                url: Portal.webRoot+'/couponManagerController/importCoupon.do',
			                dataType: 'json',
			                xhrFields: {
			                    withCredentials: true
			                },
			                autoUpload: true,
			                previewCanvas: false,
			                maxFileSize: 2999000,
			                disableImageResize: /Android(?!.*Chrome)|Opera/
			                    .test(window.navigator.userAgent),
			                previewMaxWidth: 150,
			                previewMaxHeight: 150,
			                previewCrop: true
			            }).on('fileupload:add', function (e, data) {
			                data.context = $('.importExcel');
			            }) .on('fileupload:processalways', function (e, data) {
			                var index = data.index,
			                    file = data.files[index];
			                if (file.preview) {
			                    data.context.attr("src", file.preview.toDataURL());
			                }
			                if (file.error) {
			                    club.toast('info', file.error);
			                }
			                else{
			                    $.blockUI();
			                }
			            }).on('fileupload:done', function (e, data) {
			                $.unblockUI();
			                $.each(data.result, function (index, file) {
			                    if (file.url) {
			                        console.log(file.url);
			                        objects=file.url;
			                      for(var i=0;i<file.url.length;i++){
			                    	  $(GRID_DOM).grid('addRowData',file.url[i]);
            	  
			                      }
			                    } else if (file.error) {
			                        club.toast('info', file.error);
			                    }
			                });
			            }).on('fileupload:fail', function (e, data) {
			                $.unblockUI();
			                $.each(data.files, function (index) {
			                    club.toast('info', 'File upload failed.');
			                });
			            });

				}

			};
		});