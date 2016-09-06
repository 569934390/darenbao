define(['text!modules/cargoclassifymanager/addcargoclassify/templates/addCargoClassify.html',
		'Portal', 'modules/upload/Uploader' ],
		function(addCargoClassifyTpl, Portal, Uploader) {
			var options = {
				height : $(window).height() * 0.6,
				width: $(window).width()*0.3,
				modal : false,
				draggable : false,
				content : addCargoClassifyTpl,
				autoResizable : true
			};
			var popup, logoUploader, max_file_count = 1;
			var image_list = [];

			return {
				openAddWin : function(listView, action, editValue) {
					image_list = [];
					var me = this;
					var checkValue = $.map(editValue, function(i) {
						return i;
					}).join(",");
					popup = club.popup($.extend({}, options, {
						modal : true
					}))
					$('.cargoClassifyWindow select[name=status]').combobox();
					$('.cargoClassifyWindow input[field=spinner]').spinner({
						min : 0
					});
					if (checkValue != undefined && checkValue != "") {
						image_list.splice(0, image_list.length);
						if (action == 'edit') {
							if (editValue.parent != undefined) {
								$('.cargoClassifyWindow input[name=parentId]').val(editValue.parentId);
								$('.cargoClassifyWindow input[name=parentName]').val(editValue.parentName);
								$('.cargoClassifyWindow #parent-div').show();
							} else {
								$('.cargoClassifyWindow input[name=parentId]').val(editValue.parentId);
							}
							if(editValue.imgUrl != undefined && editValue.imgUrl != ''){
								image_list.push({
									"id" : editValue.id,
									"url" : editValue.imgUrl
								});
							}
						} else {
							$('.cargoClassifyWindow input[name=parentId]').val(editValue.id);
							$('.cargoClassifyWindow input[name=parentName]').val(editValue.name);
							$('.cargoClassifyWindow #parent-div').show();
						}
					} else if (action != 'edit') {
						$('.cargoClassifyWindow input[name=parentId]').val('1');
					}
					me.loadImgUrl();
					$('.cargoClassifyWindow button').click(function() {
						var btnAction = $(this).attr('action');
						switch (btnAction) {
						case 'save-button':
							var imgUrl = logoUploader.get()[0];
							var imgUrlValue = '';
							if(imgUrl != undefined && imgUrl.url != undefined){
								imgUrlValue = imgUrl.url;
							}
							$('form[name=cargo-classify-form] input[name=imgUrl]').val(imgUrlValue);												
							var isValid = $('form[name=cargo-classify-form]').isValid();
							if (isValid) {
								var value = $('form[name=cargo-classify-form').form('value');
								var url = Portal.webRoot+ '/cargo/classify/'+ (action == 'add' ? 'add.do': 'modify.do');
								$.post(url, {modelJson : JSON.stringify(value)}, function(result) {
									if (result.code == 1) {
										listView.callback.refresh();
										popup.close();
									} else {
										club.toast('error',result.msg);
									}

								});
							}
							;
							break;
						case 'cancel-button':
							popup.close();
							break;
						case 'clear-button':
							image_list.splice(0,image_list.length);
							$('form[name=cargo-classify-form').form('clear');
							if (action == 'add') {
								if (checkValue != undefined && checkValue != "") {
									$('.cargoClassifyWindow input[name=parentId]').val(editValue.id);
									$('.cargoClassifyWindow input[name=parentName]').val(editValue.name);
									$('.cargoClassifyWindow input[name=name]').val('');
									$('.cargoClassifyWindow input[name=orderIndex]').val(0);
								} else {
									$('.cargoClassifyWindow input[name=parentId]').val('1');
								}
							} else {
								$('form[name=cargo-classify-form').form('value',editValue);
								if(editValue.imgUrl != undefined && editValue.imgUrl != ''){
									image_list.push({
										"id" : editValue.id,
										"url" : editValue.imgUrl
									});
								}
							}
							me.loadImgUrl();
							break;
						}
					});
					if (action == 'edit') {
						$('form[name=cargo-classify-form').form('value',editValue);
						if (editValue.parent == undefined) {
							$('.modal-title').html('编辑货品父分类');
						} else {
							$('.modal-title').html('编辑货品子分类');
						}
					}else{
						if (editValue == undefined || editValue.id == undefined) {
							$('.modal-title').html('添加货品父分类');
						} else {
							$('.modal-title').html('添加货品子分类');
						}
					}
				},
				loadImgUrl : function() {
					logoUploader = Uploader.create({
						container : "small_img_container",
						max_file_count : max_file_count,
						image_list : image_list
					});
				}
			};
		});
