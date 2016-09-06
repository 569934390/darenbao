define([ 'text!modules/storemanager/detailSubbranch/templates/addView.html',
		'Portal', 
		'text!data/cityData.json',
		'modules/upload/Uploader'], 
		function(addViewTpl, Portal, regionData, uploader) {
	var GRID_DOM = "#detailsubbranch-manager-content-div";
	var options = {
		height : $(window).height() * 0.9,
		modal : false,
		draggable : false,
		content : addViewTpl,
		autoResizable : true
	};
	var popup;
	
	var connectId;
	var initDate=function(editValue){
		connectId=editValue.id;
		initImage(editValue);
		initArea(editValue);
		initQrcode(editValue.id);
		$('form[name=subbranch-detail-form]').form('value', editValue);
		$('form[name=subbranch-detail-form] input').attr('disabled', "disabled");
		$('form[name=subbranch-detail-form] select').attr('disabled', "disabled");
		$('form[name=subbranch-detail-form] textarea').attr('disabled', "disabled");
	}
	var initImage=function(editValue){
		var optionheadImgUrl = {
				container : "headImgUrl_img_container",
				max_file_count : 1,
				image_list:editValue.headImgUrl==null||editValue.headImgUrl==''?[]:[{id : editValue.id,url : editValue.headImgUrl}],
				read_only: true
		};
		var headImgUrlUploader = uploader.create(optionheadImgUrl);
	}
	var initArea=function(editValue){
		$('.area').cascadeselect({
			dataSource : JSON.parse(regionData),
			selectors : [ '.provice', '.city', '.country' ]
		});
		$('.area').cascadeselect('value',
		[ parseInt(editValue.provice),parseInt(editValue.city),parseInt(editValue.country) ]);
	}
	var initQrcode=function(id){
		$.post(Portal.webRoot+ '/Subbranch/getSubbranchQrcode.do',{id:id
		}, function(result) {
			$('form[name=subbranch-detail-form] .qrcodeimg').qrcode({width:100,height:100,text:result});
		});
	}

	return {
		openAddWin : function(listView, action, editValue) {
			popup = club.popup($.extend({}, options, {
				modal : true
			}))
			
			//初始化数据
			initDate(editValue);
			this._initListView();
		
			$('.detailsubbranchManagerWindow button').click(function() {
				var btnAction = $(this).attr('action');
				switch (btnAction) {
				case 'cancel-button':
					popup.close();
					break;
				}
			});
		},
		pageData : function(page, rowNum, sortname, sortorder) {
			rowNum = rowNum|| $(GRID_DOM).grid("getGridParam","rowNum");
			$.post(Portal.webRoot+ '/BankCard/getBankCardList.do',{
				start : (page - 1) * rowNum,
				limit : page * rowNum,
				conditionStr : JSON.stringify({
					'connectId' : connectId,
				})
			}, function(result) {
				result = Portal.convertPage(result);
				result.page = page;
				$(GRID_DOM).grid("reloadData", result);
			});
			return false;
		 },_initListView:function(){
			 var opt = {
                 height:$(window).height()-125,
                 width: $(window).width(),
                 rownumbers:true,
                 colModel: [{
                     name: 'bankCardId',
                     sorttype: "int",
                     hidden:true,
                     key:true
                 },{
                     name: 'bankCardIdString',
                     hidden:true
                 },{
                     name: 'name',
                     width:20,
                     label: '姓名',
                     align: "center"
                 },  {
                     name: 'mobile',
                     label: '手机号',
                     width: 30,
                     align: "center",
                 },  {
                     name: 'idCard',
                     label: '身份证',
                     width: 30,
                     align: "center",
                 }, {
                     name: 'bankName',
                     label: '银行全称',
                     width: 30,
                     align: "center",
                     title: false //内容没有提示
                 }, {
                     name: 'bankCard',
                     width:30,
                     align: "center",
                     label: '银行卡号'
                 },{
                     name: 'bankAddress',
                     width:30,
                     align: "center",
                     label: '开户行'
                 },{
                     name: 'state',
                     label: '禁用状态',
                     width: 30,
                     align: "center",
                     title: false, //内容没有提示
                     formatter: function(state) {
                         if (state=='1') {return '是'};
                         if (state=='0') {return '否'};
                         return state;
                     }
                 }],pager: true
                 ,rowNum: 20
                 ,rowList: [10,20,50,100,200]
                 ,datatype: "json"
                 ,recordtext:"{0} 到 {1} 总共: {2} "
                 ,pgtext : " {0} / {1}"
                 ,rowtext:"{0}"
                 ,gotext:'跳转至第{0}页'
                 ,multiselect:false
                 ,pageData:this.pageData
             };
            this.pageData(1);
          
            //加载grid
            $grid = $(GRID_DOM).grid(opt);
            $('.gotext').find('span').html('跳转至第<input class="ui-pagination-input">页');
       }
	};
});