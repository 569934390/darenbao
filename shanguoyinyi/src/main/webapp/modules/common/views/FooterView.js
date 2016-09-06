define([
	'text!modules/common/templates/FooterView.html',
	'i18n!modules/common/i18n/FooterView.i18n'
], function(FooterViewTpl,i18nFooterView) {
	return club.View.extend({
		template: club.compile(FooterViewTpl),
		i18nData: club.extend({},i18nFooterView),
		events: {
		},
		//这里用来进行dom操作
		_render: function() {
			this.$el.html(this.template(this.i18nData));
			return this;
		},
		
		//这里用来初始化页面上要用到的club组件
		_afterRender: function() {
			// club.alert(this.i18nData.KEY);
		}
	});
});