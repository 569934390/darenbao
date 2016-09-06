define(
		['text!modules/storemanager/templates/SettleTimeManagerView.html',
		'Portal'
		 ],
		function(settlerTimeViewTpl,Portal) {
			
            return club.View
					.extend({
						template : club.compile(settlerTimeViewTpl),
						i18nData : club.extend({}),

						_afterRender : function() {
							this._initListView();
						},
						
						pageData : function() {
							
							$.post(Portal.webRoot+ '/timecycle/detail/settleTime.do',
									{}, function(result) {
										var editValue=result;
										var temp = [];
										temp.push(editValue.lng)
										temp.push(editValue.lat)
										editValue.rateType = temp;
										$('form[name=settlerTime-form').form('value', editValue);
										
									});
							return false;
						},
						_initListView : function() {
							this.pageData();
							


													$('.settlerTime-manager-main-view button')
									.click(
											function() {
												var btnAction = $(this).attr(
														'action');
												switch (btnAction) {
												case 'save-button':
												
													var value = $(
															'form[name=settlerTime-form')
															.form('value');
													
													$.post(Portal.webRoot+ '/timecycle/saveOrUpdate/settleTime.do',
																	{
																		modelJson : JSON.stringify(value)
																	},
																	function(
																			result) {
																		if (result.success) {
																			$('form[name=settlerTime-form] input[name=id]').val(result.msg);
																			club.toast('info','操作成功！');
																		} else {
																			club.toast('error',result.msg);
																		}

																	});break;
												case 'cancel-button':
													$(
															'form[name=settlerTime-form')
															.form('clear');
													if (action == 'edit') {
														$(
																'form[name=settlerTime-form')
																.form('value',
																		editValue);
													};break;
												}
											});
						}
					});
		});
