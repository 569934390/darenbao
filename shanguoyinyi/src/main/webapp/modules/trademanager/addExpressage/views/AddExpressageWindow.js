define(
		[ 'text!modules/trademanager/addExpressage/templates/addView.html',
				'Portal', 'modules/upload/Uploader' ],
		function(addViewTpl, Portal, uploader) {
			var options = {
				height : $(window).height() * 0.6,
				width : $(window).width() * 0.4,
				modal : false,
				draggable : false,
				content : addViewTpl,
				autoResizable : true
			};
			var popup, logoUploader, max_file_count = 1;
			var image_list = [];
			return {
				openAddWin : function(listView, action, editValue) {
					var me = this;
					popup = club.popup($.extend({}, options, {
						modal : true
					}))
					$('.expressageManagerWindow select[name=state]').combobox();
					$('.expressageManagerWindow input[field=spinner]').spinner({
						min : 0
					});
					if (action == 'edit' && editValue.logoUrl) {
						image_list = [{id:editValue.id,url:editValue.logoUrl}];
					}
					me.loadImgUrl();
					$('.expressageManagerWindow button').click(function() {
						var btnAction = $(this).attr('action');
						switch (btnAction) {
						case 'save-button':
							$('form[name=expressage-form]').isValid();
							var isValid = $('form[name=expressage-form]').isValid();
							var reg = /^(1)[0-9]{10}$/;
							var logoUrl = "";
							if (logoUploader.get().length > 0){
								logoUrl = logoUploader.get()[0].url;
							}
							$("input[name=logoUrl]").val(logoUrl);
							if (isValid) {
								var value = $('form[name=expressage-form]').form('value');
								$.post(Portal.webRoot+ '/Expressage/saveOrUpdateExpressage.do',{modelJson : JSON.stringify(value)},function(result) {
									if (result.success) {
										listView.callback.refresh();
										popup.close();
									} else {
										club.toast('error',result.msg);
									}
								});
							};
							break;
						case 'cancel-button':
							popup.close();
							break;
						case 'clear-button':
							$('form[name=expressage-form').form('clear');
							image_list = [];
							if (action == 'edit') {
								$('form[name=expressage-form').form('value',editValue);
								if (editValue.logoUrl) {
									image_list = [{id:editValue.id,url:editValue.logoUrl}];
								}
							};
							me.loadImgUrl();
							break;
						}
					});

					if (action == 'edit') {
						console.info(editValue);
						var temp = [];
						temp.push(editValue.lng)
						temp.push(editValue.lat)
						editValue.rateType = temp;
						$('form[name=expressage-form').form('value', editValue);
						$('.modal-title').html('编辑账号信息');
					}
				},
				loadImgUrl : function() {
					logoUploader = uploader.create({
						container : "logoUrl_img_container",
						max_file_count : max_file_count,
						image_list : image_list
					});
				}
			};
		});