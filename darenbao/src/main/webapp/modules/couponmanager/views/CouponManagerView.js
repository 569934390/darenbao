define([ 'text!modules/couponmanager/templates/CouponManager.html',
 'modules/couponmanager/views/CouponDetailView',
 'modules/couponmanager/addCoupon/views/AddCoupon',
// 'modules/spreadmanager/editSpread/views/EditSpreadWindow',
'Portal' ], function(spreadViewTpl,CouponDetail,addCoupon,Portal) {
	var GRID_DOM = "#coupon-info-content-div";
	var coupons = {};
	return club.View.extend({
		template : club.compile(spreadViewTpl),
		i18nData : club.extend({}),
		listView : {},
		views : {},
		events : {
			"click .coupon-info-main-btngroup .btn" : "tbarHandler"
		},
		// 这里用来初始化页面上要用到的club组件
		_afterRender : function() {
			this._initListView();
		},
		tbarHandler : function(event) {
			var action = $(event.currentTarget).attr("action"), selectRecord;
			console.info('12344');
			switch (action) {
			case 'search':
				this.search();
				break;
			case 'refresh':
				this.refresh();
				break;
			case 'edit':
				this.edit(action, this.selectRecord());
				break;
			case 'add':
				this.add(action, this.selectRecord());
				break;
			case 'del':
				this.doDelete(action, this.selectRecords());
				break;
			case 'exportExcel':
				this.exportExcel(action, this.selectRecords());
				break;
			}
		},
		selectRecord : function() {
			return $(GRID_DOM).grid('getSelection');
		},
		selectRecords : function() {
			return $(GRID_DOM).grid('getCheckRows');
		},
		search : function() {
			this.pageData(1);
		},
		refresh : function() {
			console.log("刷新Grid列表");
			$('input[name=coupon-conditions]').val('');
			this.pageData(1);
		},
		 //新增
		 add:function(action,record){
		 console.log(record);
		 addCoupon.openAddwin({
		 callback:this
		 },action,record);
		 },
		 //编辑
		 edit:function(action,record){
		 if (this.selectRecords().length>1) {
		 return club.toast('warn', '有歧义，不能选择多条记录！');
		 };
		 console.log(record);
		 if (action=='edit'&&record&&this.selectRecords().length==0) {
		 return club.toast('warn', '请选择记录！');
		 };
		 addCoupon.openAddwin({
		 callback:this
		 },action,record);
		 },
		 //删除
		 doDelete:function(action,records){
		 console.log(records);
		 if (this.selectRecords().length==0) {
		 return club.toast('warn', '请选择兑换券！');
		 }
		 var me = this,ids = [];
		 for (var i = records.length - 1; i >= 0; i--) {
		 ids.push(records[i].id);
		 };
		             
		 var r= club.confirm('您确定要删除选中的兑换券吗？');
		 r.result.then(function resolve(){
		 $.post(Portal.webRoot+'/couponManagerController/deleteCoupon.do',{
		 IdStr:ids.join(',')},function(result){
		 if (result.success) {
		 club.toast('info', '操作成功！');
		 }
		 else{
		 club.toast('error', result.msg);
		 }
		 me.refresh();
		 });
		 }, function reject(){
		 return;
		 });
		 },
		 
		 //导出excel
		 exportExcel:function(action,records){
		 console.log(records);
		 if (this.selectRecords().length==0) {
		 return club.toast('warn', '请选择兑换券！');
		 }
		 var me = this,ids = [];
		 for (var i = records.length - 1; i >= 0; i--) {
		 ids.push(records[i].id);
		 };
		             
		 var r= club.confirm('您确定要导出选中的兑换券吗？');
		 r.result.then(function resolve(){
		 $.post(Portal.webRoot+'/couponManagerController/exportCoupon.do',{
		 IdStr:ids.join(',')},function(result){
		 if (result.success) {
			 console.log(result.msg);
			 location.href = Portal.webRoot+result.msg;
		 }
		 else{
		 club.toast('error', result.msg);
		 
		 }
		 me.refresh();
		 });
		 }, function reject(){
		 return;
		 });
		 },

		pageData : function(page, rowNum, sortname, sortorder) {
			var conditions = $('input[name=coupon-conditions]').val();
			rowNum = rowNum || $(GRID_DOM).grid("getGridParam", "rowNum");
			$.post(Portal.webRoot + '/couponManagerController/selectCoupon.do',
					{
						conditionStr : JSON.stringify({
							'conditions' : '%' + conditions + '%'
						}),
						start : (page - 1) * rowNum,
						limit :  rowNum
					}, function(result) {
						result = Portal.convertPage(result);
						result.page = page;
						$(GRID_DOM).grid("reloadData", result);
                        console.log(result); 
                        for(var i=0;i<result.rows.length;i++){
                        	$('.qrcodeimg'+result.rows[i].id).qrcode({
    							width   : 140,
    							height  : 140,
    							/*text	: "http://115.159.25.170/shanguoyinyi/weixin/weixinClient/index.do?" +
                    "fromshanguo=weixinfront/html/customer/details/detailsCoupon.html?id="+result.rows[i].goodId+"&type=weixinIndex&storeId="+result.rows[i].shopId*/
    							text	: result.rows[i].qrcode
    						});
                        }		
					});
			return false;
		},
		_initListView : function() {
			// 参数配置
			var opt = {
				height : $(window).height() - 180,
				width : this.$el.width(),
				rownumbers : true,
				colModel : [
						{
							name : 'id',
							hidden : true,
							key : true
						},
						{
							name : 'goodId',
							hidden : true,
						},
						{
							name : 'shopId',
							hidden : true,
						},

						{
							name : 'name',
							label : '兑换券名称',
							width : 80,
							align : "center"
						},
						{
							name : 'goodName',
							label : '商品名称',
							width : 80,
							align : "center",
						},
						{
							name : 'beginTime',
							width : 80,
							align : "center",
							label : '开始期限',
							formatter : function(cellval, opts, rwdat, _act) {
								return cellval;
							}
						},
						{
							name : 'endTime',
							width : 80,
							align : "center",
							label : '截止期限',
							formatter : function(cellval, opts, rwdat, _act) {
								return cellval;
							}
						}, {
							name : 'nums',
							width : 80,
							align : "center",
							label : '兑换券个数'
						},{
							name : 'erweima',
							width : 140,
							align : "center",
							label : '二维码',
							formatter : function(cellval, opts, rwdat, _act) {

							
								return "<div class='qrcodeimg"+rwdat.id+"' style='padding:35px'></div>" 
							}
						},
				 {name: 'state', label: '操作', width: 180, align: "center",
				 sortable: false,
				 formatter: function(cellval, opts, rwdat, _act) {
				 var detail = ' <a href="javascript:void(0);" coupon-data-operation="detail" data-value="'+rwdat.id+'" title="兑换券编码">兑换券编码</a> ';
				 coupons[rwdat.id] = rwdat;
				 return detail;
				 }
				 }

				],
				pager : true,
				rowNum : 20,
				rowList : [ 10, 20, 50, 100, 200 ],
				datatype : "json",
				recordtext : "{0} 到 {1} 总共: {2} ",
				pgtext : " {0} / {1}",
				rowtext : "{0}",
				gotext : '跳转至第{0}页',
				multiselect : true,
				pageData : this.pageData
			};
			this.pageData(1);
			// 加载grid
			$grid = $(GRID_DOM).grid(opt);
			$('.gotext').find('span').html(
					'跳转至第<input class="ui-pagination-input">页');
			 this._init_operations();
		},
	 _init_operations: function(){
	 var me = this;
	 $(document).on("click", "a[coupon-data-operation]", function(){
	 var operation = $(this).attr("coupon-data-operation");
	 switch(operation){
	 case "detail":
	 me._open_coupon_info_window(coupons[$(this).attr("data-value")]);
	 console.log(coupons[$(this).attr("data-value")].id);
	 break;
	 }
	 });
	 },
	 _open_coupon_info_window: function(Record) {
	 CouponDetail.openAddwin({
	 callback:this
	 }, Record);
	 }
	});
});