define([ 'text!modules/storemanager/addSalesReturnReason/templates/addView.html',
		'Portal'
		], 
		function(addViewTpl, Portal) {
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
			$('.salesReturnReasonWindow select[name=state]').combobox();
			$('.salesReturnReasonWindow input[field=spinner]').spinner({
				min : 0
			});

			

			if (action == 'edit') {
				if (editValue.headImgUrl) {
					optionheadImgUrl.image_list = [ {
						id : editValue.id,
						url : editValue.headImgUrl
					} ];
				}
			}
			

			if (action == 'edit') {
				if (editValue.qrcode) {
					optionQrcode.image_list = [ {
						id : editValue.id,
						url : editValue.qrcode
					} ];
				}
			}
			

			$('.salesReturnReasonManagerWindow button').click(
					function() {
						var btnAction = $(this).attr('action');
						switch (btnAction) {
						case 'save-button':
							
							var isValid = $('form[name=salesReturnReason-form]')
									.isValid();
							var reg = /^(1)[0-9]{10}$/;

							if ($("#reason") == null) {
								alert('原因不能为空');
								$("#reason").select;
								return

							}
							;
							if ($("#rank") == null) {
								alert('序号不能为空');
								$("#rank").select;
								return

							}
							;
							
							var value = $('form[name=salesReturnReason-form').form(
							'value');
							$.post(Portal.webRoot
									+ '/SalesReturnReason/saveOrUpdateSalesReturnReason.do', {
								modelJson : JSON.stringify(value)
								
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
							$('form[name=salesReturnReason-form').form('clear');
							if (action == 'edit') {
								$('form[name=salesReturnReason-form').form('value',
										editValue);
							}
							;
							break;
						}
					});
			if (action == 'edit') {
				console.info(editValue);
				
				var temp = [];
				temp.push(editValue.lng)
				temp.push(editValue.lat)
				editValue.rateType = temp;
				$('form[name=salesReturnReason-form').form('value', editValue);
				
			}
		}
	};
});