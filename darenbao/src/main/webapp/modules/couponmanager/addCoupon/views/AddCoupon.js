define(
		[ 'text!modules/couponmanager/addCoupon/templates/AddCoupon.html',
				'css!styles/css/good.css', 'Portal' ],
		function(addViewTpl,addCss,Portal) {
			var options = {
				height : $(window).height() * 0.9,
				modal : false,
				draggable : false,
				content : addViewTpl,
				autoResizable : true,
				dialogClass : addCss
			};
			
			var GRID_DOM = "#selectgood-info-content-div";
			var search=function(){
	            pageData(1);
	        };
			$("#selectgood-info-content-div .cbox").hide();
		    var pageData =function (page, rowNum, sortname, sortorder) {
	            var conditions = $('input[name=good-select-conditions]').val();
	            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
	           // var classifyId = classifySelect.get();
	            var data = {
	            	'name':'%'+conditions+'%',
	                'classifyId': "0", 
	                'brandId':  "0",
	                'type':"1"
	                	
	            };
	            
	            $.post(Portal.webRoot+'/spreadManager/getList.do',{
	                conditionStr:JSON.stringify(data),
	                start:(page-1)*rowNum,limit:page*rowNum,name:'client'},function(result){
	                result = Portal.convertPage(result);
	                result.page = page;
	                $(GRID_DOM).grid("reloadData", result);
	            });
	            return false;
	        }
	        var _initListView=function(){
	            var me = this;
	            //参数配置
	            var opt = {
	                height:$(window).height()-320,
	                width:$(window).width(),
	                colModel: [
	                    {name: 'id', hidden:true, key:true}, 
	                    {name: 'name', label: '商品名称', width: 80, align: "center", title: false, sortable: false},  //内容没有提示
	                    {name: 'cargoNo', label: '货品编号', width:60, align: "center", sortable: false},  
	                    {name: 'smallImage', label: '商品缩略图', width: 60, align: "center", headertitle : '名称', sortable: false,  //列头的提示
	                    	formatter: function(cellval, opts, rwdat, _act){
		                        var imagUrl =  "./image/user5-128x128.jpg";
		                        if (rwdat.smallImage) {
		                            imagUrl = Portal.webRoot+'/upload.do?getthumb='+rwdat.smallImage+'&size=150';
		                        };
		                        return '<img src = "'+imagUrl+'" class="client-face-img"/>';
	                    	}
	                    },              
	                    {name: 'classify', label: '分类', width: 80, align: "center", sortable: false}, 
	                    {name: 'brand', label: '品牌', width: 80, align: "center", sortable: false}              
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
	            //classifySelect.init(".cargo-manager-classify-group", "info");
	            //brandSelect.init(".btn-group-brand", "info");
	        }
			var popup;
            
			return {
				openAddwin : function(listView, action, editValue) {

					popup = club.popup($.extend({}, options, {
						modal : true
					}))
					_initListView();
					console.log(editValue);
				   $('.selectCargoWindow input[field=datetime]').datetimepicker({
					   format: 'yyyy-mm-dd hh:ii:ss'
		            });
				   //判断是不是编辑，如果是，赋上默认值
	                  if(editValue && editValue.id){
	                	  $("input[name=beginTime]").val(editValue.beginTime);
	                	  $("input[name=endTime]").val(editValue.endTime);
	                	  $("input[name=name]").val(editValue.name);
	                	  $("input[name=id]").val(editValue.id);
	                	  $("input[name=nums]").val(editValue.nums);
	                  }
							
					$('.selectCargoWindow button').click(function(){
		                var btnAction = $(this).attr('action');
		                switch(btnAction){
		                   case 'save-button':
		                  var record=$(GRID_DOM).grid('getSelection');
		                
		                  if((record==null||record.id==null||record.id=='')){
		                	  club.toast('warn', "请选择商品");
		                  }else{
		                	  console.log(record);
		                	  var record1={};
		                	  record1.beginTimeStr=$("input[name=beginTime]").val();
		                	  record1.endTimeStr=$("input[name=endTime]").val();
		                	  record1.name=$("input[name=name]").val();
		                	  record1.id=$("input[name=id]").val();
		                	  record1.nums=$("input[name=nums]").val();
		                	  record1.goodId=record.id;
		                	  console.log(record1);
		                	  if(record1.name==null || record1.name==''){
		                		  club.toast('warn', "请输入兑换券名称");
		                	  }
		                	  else if(record1.beginTimeStr==null||record1.beginTimeStr==''){
		                		  club.toast('warn', "请选择兑换券有效开始日期");
		                	  }
		                	  else if(record1.endTimeStr==null||record1.endTimeStr==''){
		                		  club.toast('warn', "请选择兑换券有效截止日期");
		                	  }
		                	  else{
		                        	  $.post(Portal.webRoot+'/couponManagerController/addOrUpdateCoupon.do',{modelJson:JSON.stringify(record1)		                       
		                            	},function(result){
		                                if (result.success) {
		                                    listView.callback.refresh();
		                                    popup.close();
		                                }
		                                else{
		                                    club.toast('error', result.msg);
		                                }
		                                
		                            });  
		                       	  
		                         
		                	  }
//		                	  console.log(typeof($("input[name=endTime]").val()));
//		                	   AddSpreadWindow.openAddWin(listView,action,record,spreadContentType);
//			                    popup.close();
		                  }break;  
		                   case 'cancel-button': popup.close();break;
		                   case 'search' : search(); break;
		                }
		            });

				}

			};
		});