define(
		[
				'text!modules/spreadmanager/templates/spreadDetail.html',
				'modules/upload/Uploader',
				'Portal',
				'common/jslibs/club-desktop/third-party/ueditor/ueditor.all.new',
				'css!common/jslibs/club-desktop/third-party/ueditor/themes/default/css/ueditor' ],
		function(addViewTpl, uploader, Portal, UE) {
			var options = {
				height : $(window).height() * 0.9,
				modal : false,
				draggable : false,
				content : addViewTpl,
				autoResizable : true,
			};
			var popup;

			return {
				openAddWin : function(listView, editValue, readOnly) {

					popup = club.popup($.extend({}, options, {
						modal : true
					}))
					var optionimage = {
						container : "logo_img_container",
						max_file_count : 1,
						read_only : true
					};
					$("input[name=id]").val(editValue.id);
					$("input[name=goodName]").val(editValue.goodName);
					$("input[name=cargoNo]").val(editValue.cargoNo);
					$("input[name=goodId]").val(editValue.id);
					$("input[name=author]").val(editValue.author);
					$("input[name=name]").val(editValue.name);
					if(editValue.readNum==null){
						$("input[name=readNum]").val(0);
					}else{
						$("input[name=readNum]").val(editValue.readNum);
					}
					$("div[name=content]").html(editValue.content);
					if (editValue.logo) {
						optionimage.image_list = [ {
							id : editValue.logo,
							url : editValue.url
						} ];
					}
					var brandLogoUploader = uploader.create(optionimage);
					console.log(editValue);
					console.log(editValue);
					var str = '';
					if (editValue.spreadContentType == 1) {
						str += '<option value="1">' + '商品' + '</option>';
					} else {
						str += '<option value="0">' + '店铺' + '</option>';
					}
					$('select[name=spreadContentType]').append(str);
					console.log(editValue);

					$
							.post(
									Portal.webRoot
											+ '/spreadClassify/findAllSpreadClassify.do',
									{},
									function(result) {
										if (result != null && result.length > 0) {
											var text = '';
											for (var i = 0; i < result.length; i++) {
												if (result[i].id == editValue.classifyId) {
													text += '<option value="'
															+ result[i].id
															+ '" selected="selected">'
															+ result[i].name
															+ '</option>';
												} else {
													text += '<option value="'
															+ result[i].id
															+ '">'
															+ result[i].name
															+ '</option>';
												}
											}
											$('select[name=classifyId]')
													.append(text);
										}
									});

					$('.goodManagerWindow button').click(function() {
						var btnAction = $(this).attr('action');
						switch (btnAction) {
						case 'cancel-button':
							popup.close();
							break;

						}
					});

				}
			};
		});