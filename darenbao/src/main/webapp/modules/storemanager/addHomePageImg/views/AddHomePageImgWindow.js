define([ 'text!modules/storemanager/addHomePageImg/templates/addView.html',
		'Portal', 
		'modules/upload/Uploader' ], 
		function(addViewTpl, Portal, uploader) {
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
			$('.homePageImgManagerWindow select[name=state]').combobox();
			$('.homePageImgManagerWindow input[field=spinner]').spinner({
				min : 0
			});

			var optionpicUrl = {
				container : "picUrl_img_container",
				max_file_count : 5
			};

			var picUrlUploader = uploader.create(optionpicUrl);

			$('.homePageImgManagerWindow button').click(
					function() {
						var btnAction = $(this).attr('action');
						switch (btnAction) {
						case 'save-button':
							/* $('form[name=subbranch-form]').isValid(); */
							var isValid = $('form[name=homePageImg-form]')
									.isValid();
						
							var picUrls = [];
				            for (var i = picUrlUploader.get().length - 1; i >= 0; i--) {
				            	picUrls.push(picUrlUploader.get()[i].url);
				            };

							var picUrl='', qrcode;
							qrcode=picUrls.join(',');
							if (picUrlUploader.get().length > 0)
								picUrl = picUrlUploader.get()[0].url;
							$("input[name=picUrl]").val(picUrl);
							
                            
							if (picUrl == null||picUrl=='') {
								alert('稍等！图片较大还没完全上传');
								
								return

							}
							;

							var value = $('form[name=homePageImg-form').form('value');
							
							$.post(Portal.webRoot
									+ '/HomePageImg/saveHomePageImg.do', {
								modelJson : JSON.stringify(value),
								imgUrls:qrcode
							}, function(result) {
								if (result.success) {
									listView.callback.refresh();
									popup.close();
								} else {
									club.toast('error', result.msg);
								}

							});

							/* }; */
							break;
						case 'cancel-button':
							popup.close();
							break;
						case 'clear-button':
							$('form[name=homePageImg-form').form('clear');
							if (action == 'edit') {
								$('form[name=homePageImg-form').form('value',
										editValue);
							}
							;
							break;
						}
					});

			$('.fileupload-selectpicUrl').fileupload(
					{
						url : Portal.webRoot + '/upload.do',
						dataType : 'json',
						xhrFields : {
							withCredentials : true
						},
						autoUpload : true,
						previewCanvas : false,
						acceptFileTypes : /(\.|\/)(gif|jpe?g|png)$/i,
						maxFileSize : 2999000,
						disableImageResize : /Android(?!.*Chrome)|Opera/
								.test(window.navigator.userAgent),
						previewMaxWidth : 150,
						previewMaxHeight : 150,
						previewCrop : true
					}).on('fileupload:add', function(e, data) {
				data.context = $('.picUrl');
			}).on('fileupload:processalways', function(e, data) {
				var index = data.index, file = data.files[index];
				if (file.preview) {
					data.context.attr("src", file.preview.toDataURL());
				}
				if (file.error) {
					club.toast('info', file.error);
				} else {
					$.blockUI();
				}
			}).on('fileupload:done', function(e, data) {
				$.unblockUI();
				$.each(data.result, function(index, file) {
					if (file.url) {
						console.info(file)
						$("input[name=picUrl]").val(file.name);
						
					} else if (file.error) {
						club.toast('info', file.error);
					}
				});
			}).on('fileupload:fail', function(e, data) {
				$.unblockUI();
				$.each(data.files, function(index) {
					club.toast('info', 'File upload failed.');
				});
			});
		
		}
	};
});