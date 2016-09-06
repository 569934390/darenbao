define(
		[ 'text!modules/couponmanager/templates/CouponDetail.html',
		  'modules/couponmanager/views/SelectShopView',
		  'modules/couponmanager/views/AddCouponDetail',
				'css!styles/css/good.css', 'Portal' ],
		function(addViewTpl,selectShop,AddCouponDetail,addCss,Portal) {
			var mep = this;
			
			var options = {
				height : $(window).height() * 0.9,
				modal : false,
				draggable : false,
				content : addViewTpl,
				autoResizable : true,
				dialogClass : addCss
			};
			
			var GRID_DOM = "#couponDetail-info-content-div";
			var search=function(){
	            pageData(1);
	        };
	        var refresh=function() {
				pageData(1);
			};
			this.refresh = refresh;
			$("#couponDetail-info-content-div .cbox").hide();
		    var pageData=function (page, rowNum, sortname, sortorder) {
		    	var conditions = $('input[name=conditions]').val();
	            var status = $('select[name=status]').val();
		    	var data = {
		    			'couponId':record.id,
		            	'conditions':'%'+conditions+'%',
		                'statusConditions': status
		            };
	            
	            rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
	           $.post(Portal.webRoot+'/couponManagerController/selectCouponDetail.do',{
	                conditionStr:JSON.stringify(data),
	                start:(page-1)*rowNum,limit:rowNum},function(result){
	                	console.log(result);
	                $('#detailCount').text(result.totalRecords);
	                result = Portal.convertPage(result);
	                result.page = page;
	                $(GRID_DOM).grid("reloadData", result);
	            });
	            return false;
	        };
	        var _initListView=function(){
	            var me = this;
	            //参数配置
	            var opt = {
	                height:$(window).height()-250,
	                width:$(window).width(),
	                colModel: [
	                    {name: 'id', hidden:true, key:true}, 
	                    {name: 'shopCode', label: '店铺编号', width: 80, align: "center", title: false, sortable: false},  //内容没有提示
	                    {name: 'code', label: '兑换券编号', width:60, align: "center", sortable: false},             
	                    {name: 'password', label: '兑换券密码', width:60, align: "center", sortable: false},
	                    {name: 'statusName', label: '兑换状态', width: 60, align: "center", sortable: false},  
	                    {name: 'updatetime', label: '操作时间', width: 60, align: "center", sortable: false}  
	                ], 
	                pager: true, 
	                rowNum: 20, 
	                rowList: [10,20,50,100,200], 
	                datatype: "json", 
	                recordtext:"{0} 到 {1} 总共: {2} ", 
	                pgtext : " {0} / {1}", 
	                rowtext:"{0}", 
	                gotext:'跳转至第{0}页', 
	                multiselect:true, 
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
					$('.couponDetailWindow button').click(function(){
		                var btnAction = $(this).attr('action');
		                switch(btnAction){
		                   case 'add': AddCouponDetail.openAddwin(mep,editValue);break;
		                   case 'cancel-button': popup.close();break;
		                   case 'search' : search(); break;
		                   case 'allocate':
		                	   var date=new Date();
		                	   var selectRecords=$(GRID_DOM).grid('getCheckRows');
		                	   if(selectRecords==null ||selectRecords.length==0){
		                		   return club.toast('warn', '请选择记录！');
		                	   }
		                	   for(var i=0;i<selectRecords.length;i++){
	                			   if(selectRecords[i].status !=0){
	                				   return club.toast('warn', '未分配状态的兑换券才可分配');
	                			   }
	                		   }
		                	   if(Date.parse(editValue.beginTime)>date || Date.parse(editValue.endTime)<date){
		                		   return club.toast('warn', '兑换券不在有效期，不可分配');
		                	   }
		                	   selectShop.openAddwin(mep, selectRecords);
		                	   console.log(selectRecords);
		                	   break;
		                   case 'forbidden':  
		                	   var selectRecords=$(GRID_DOM).grid('getCheckRows');
		                	   if(selectRecords==null ||selectRecords.length==0){
		                		   return club.toast('warn', '请选择兑换券！');
		                	   }else{
		                		   for(var i=0;i<selectRecords.length;i++){
		                			   if(selectRecords[i].status !=1){
		                				   return club.toast('warn', '未兑换状态的兑换券才可禁用');
		                			   }
		                		   }
		                		   var ids = [];
		                			 for (var i = selectRecords.length - 1; i >= 0; i--) {
		                			 ids.push(selectRecords[i].id);
		                			 };           
		                			 var r= club.confirm('您确定要禁用吗？');
		                			 r.result.then(function resolve(){
		                			 $.post(Portal.webRoot+'/couponManagerController/forbidCouponDetail.do',{
		                			 IdStr:ids.join(',')},function(result){
		                			 if (result.success) {
		                			 club.toast('info', '操作成功！');
		                		
		                			 }
		                			 else{
		                			 club.toast('error', result.msg);
		                			 }
		                			 refresh();
		                			 });
		                			 }, function reject(){
		                			 return;
		                			 });
		                	   }
		                	   break;
		                	   
		                   case 'delete':
		                	   
		                	   var selectRecords=$(GRID_DOM).grid('getCheckRows');
		                	   if(selectRecords==null ||selectRecords.length==0){
		                		   return club.toast('warn', '请选择兑换券！');
		                	   }else{
		                		   for(var i=0;i<selectRecords.length;i++){
		                			   if(selectRecords[i].status !=0){
		                				   return club.toast('warn', '未分配状态的兑换券才可删除');
		                			   }
		                		   }
		                		     var ids = [];
		                			 for (var i = selectRecords.length - 1; i >= 0; i--) {
		                			 ids.push(selectRecords[i].id);
		                			 };           
		                			 var r= club.confirm('您确定要删除吗？');
		                			 r.result.then(function resolve(){
		                			 $.post(Portal.webRoot+'/couponManagerController/deleteCouponDetail.do',{
		                			 IdStr:ids.join(',')},function(result){
		                			 if (result.success) {
		                			 club.toast('info', '操作成功！');
		                		
		                			 }
		                			 else{
		                			 club.toast('error', result.msg);
		                			 }
		                			 refresh();
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