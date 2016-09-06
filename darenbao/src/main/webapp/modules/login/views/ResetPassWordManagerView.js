define(
		['text!modules/login/templates/ResetPassWordManagerView.html',
		'Portal'
		 ],
		function(deliveryAddressViewTpl,  Portal) {
			var value = undefined;
            return club.View.extend({
				template : club.compile(deliveryAddressViewTpl),
				i18nData : club.extend({}),
				listView : {},
				views : {},
				events : {
					"click .deliveryAddress-manager-main-view .btn" : "tbarHandler"
				},
				_afterRender : function() {
					
					$.post(Portal.webRoot+ '/resetPassWord/getStaff.do',
							{}, function(result) {
								debugger;
								$("#passwordOld").val(result.password);
								$('form[name=deliveryAddress-form').form('value', result);
							});
				},
				// 刷新Grid列表
				tbarHandler : function(event) {
					var action = $(event.currentTarget).attr("action"), selectRecord;
					switch (action) {
					case 'save':
						this.saveOrUpdate();
						break;
					case 'clear':
						this.clear();
						break;
					}
				},
				saveOrUpdate : function() {
					debugger;
					
	                 if ($("#password").val()!=$("#passwordOld").val()) {
	                	 $("#password").val(club.MD5($("#password").val()));
	                  };
	                  var value = $('form[name=deliveryAddress-form').form('value');
	                  
					$.post(Portal.webRoot+ '/resetPassWord/updatePassWord.do',
						{modelJson : JSON.stringify(value)},function(result) {
							if (result.success) {
								club.toast('info','保存成功！');
							} else {
								club.toast('error',result.msg);
							}

						}
					);
				},
				clear : function() {
					$('form[name=deliveryAddress-form').form('clear');
					if (value != undefined) {
						$('form[name=deliveryAddress-form').form('value',value);
					};
				}
			});
		});
