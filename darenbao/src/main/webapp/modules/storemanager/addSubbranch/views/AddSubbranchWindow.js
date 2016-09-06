define([ 'text!modules/storemanager/addSubbranch/templates/addView.html',
		'Portal', 
		'text!data/cityData.json',
		'modules/storemanager/addSubbranch/views/SubbranchLevelSelect',
		'modules/upload/Uploader', ], 
		function(addViewTpl, Portal, regionData,subbranchLevelSelect, uploader) {
	var options = {
		height : $(window).height() * 0.9,
		modal : false,
		draggable : false,
		content : addViewTpl,
		autoResizable : true
	};
	var popup;

	return {

		openAddWin : function(listView, action, editValue) {
			popup = club.popup($.extend({}, options, {
				modal : true
			}))
			$('.subbranchManagerWindow select[name=state]').combobox();
			$('.subbranchManagerWindow input[field=spinner]').spinner({
				min : 0
			});

			var optionheadImgUrl = {
				container : "headImgUrl_img_container",
				max_file_count : 1
			};

			if (action == 'edit') {
				if (editValue.headImgUrl) {
					optionheadImgUrl.image_list = [ {
						id : editValue.id,
						url : editValue.headImgUrl
					} ];
				}
			}
			var headImgUrlUploader = uploader.create(optionheadImgUrl);

			$('.subbranchManagerWindow button').click(
					function() {
						var btnAction = $(this).attr('action');
						switch (btnAction) {
						case 'save-button':
//							/* $('form[name=subbranch-form]').isValid(); */
							var isValid = $('form[name=subbranch-form]')
									.isValid();
							if(isValid){
								var reg = /^(1)[0-9]{10}$/;
								if (!reg.test($("#mobile").val())) {
									alert($("#mobile").val() + "手机号格式不正确");
									return false;
								}
								if ($("#password") == null) {
									alert('密码不能为空');
									$("#password").select;
									return false;
	
								}
								if ($("#number") == null) {
									alert('编号不能为空');
									$("#number").select;
									return false;
	
								}
								if ($("#departmentNum") == null) {
									alert('部门编号不能为空');
									$("#departmentNum").select;
									return false;
	
								}
								if ($("#levelId") == null) {
									alert('等级不能为空');
									$("#levelId").select;
									return false;
								}
								var headImgUrl;
								if (headImgUrlUploader.get().length > 0)
									headImgUrl = headImgUrlUploader.get()[0].url;
								$("input[name=headImgUrl]").val(headImgUrl);
	
								var levelId = subbranchLevelSelect.get();
	
								var value = $('form[name=subbranch-form').form(
										'value');
								if (levelId == null) {
									alert('等级不能为空');
									return false;
								}
							   if(action=='edit') {
	                                if (editValue.password!=value.password) {
	                                    value.password = club.MD5(value.password);
	                                };
	                            }else{
	                                value.password = club.MD5(value.password);
	                            }
							   if(value.province==null||value.province==''){
								   value.province=-1;
							   }
							   if(value.city==null||value.city==''){
								   value.city=-1;
							   }
							   if(value.country==null||value.country==''){
								   value.country=-1;
							   }
							   var provinceName=$('#province option:selected').html();
								var cityName=$('#city option:selected').html();
								var countryName=$('#country option:selected').html();
								console.log(provinceName);
								$.post(Portal.webRoot
										+ '/Subbranch/saveOrUpdateSubbranch.do', {
									modelJson : JSON.stringify(value),
									provinceName:provinceName,
									cityName:cityName,
									countryName:countryName,
									levelId : levelId
								}, function(result) {
									if (result.success) {
										listView.callback.refresh();
										popup.close();
									} else {
										club.toast('error', result.msg);
									}
	
								});
							}
							break;
						case 'cancel-button':
							popup.close();
							break;
						case 'clear-button':
							$('form[name=subbranch-form').form('clear');
							if (action == 'edit') {
								$('form[name=subbranch-form').form('value',
										editValue);
							}
							;
							break;
						}
					});

			$('.area').cascadeselect({
				dataSource : JSON.parse(regionData),
				selectors : [ '.provice', '.city', '.country' ]
			});
			if (action == 'add') {
				subbranchLevelSelect.init(".subbranchLevel-group", "default",
						false);
			}

			if (action == 'edit') {

				subbranchLevelSelect.initset(".subbranchLevel-group",
						"default", false, editValue.levelId);
				$('.area').cascadeselect(
						'value',
						[ parseInt(editValue.provice),
								parseInt(editValue.city),
								parseInt(editValue.country) ]);
			}
			if (action == 'edit') {
				console.info(editValue);
				var temp = [];
				temp.push(editValue.lng)
				temp.push(editValue.lat)
				editValue.rateType = temp;
				$('form[name=subbranch-form]').form('value', editValue);
			    $('.modal-title').html('编辑分店');
			}
		}
	};
});