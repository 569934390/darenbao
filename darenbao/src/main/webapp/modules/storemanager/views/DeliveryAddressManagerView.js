define(
		['text!modules/storemanager/templates/DeliveryAddressManagerView.html',
		'Portal',
		'text!data/cityData.json' ],
		function(deliveryAddressViewTpl,  Portal,regionData) {
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
					$('.area').cascadeselect({
						dataSource : JSON.parse(regionData),
						selectors : [ '.provice', '.city', '.county' ]
					});
					$.post(Portal.webRoot+ '/DeliveryAddress/getDeliveryAddress.do',
							{}, function(result) {
								value=result;
								var temp = [];
								temp.push(value.lng)
								temp.push(value.lat)
								value.rateType = temp;
								$('form[name=deliveryAddress-form').form('value', value);
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
					var value = $('form[name=deliveryAddress-form').form('value');
					var provinceName=$('#province option:selected').html();
					var cityName=$('#city option:selected').html();
					var countyName=$('#county option:selected').html();
					
					$.post(Portal.webRoot+ '/DeliveryAddress/saveOrUpdateDeliveryAddress.do',
						{modelJson : JSON.stringify(value),provinceName:provinceName,cityName:cityName,countyName:countyName},function(result) {
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
