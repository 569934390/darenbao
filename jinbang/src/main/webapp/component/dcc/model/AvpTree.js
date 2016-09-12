Ext.define('component.dcc.model.AvpTree', {
	extend : 'Ext.data.Model',
	idProperty : 'avpId',
	fields : [ {
		name : "avpId"
	}, {
		name : "avpName"
	}, {
		name : "avpCode"
	}, {
		name : "avpType"
	}, {
		name : "avpValue"
	}, {
		name : "avpDesc"
	}, {
		name : "parentAvpId"
	}, {
		name : "sort"
	}, {
		name : "mFlag"
	}, {
		name : "pFlag"
	}, {
		name : "checked",
		type: 'bool'
	}]
});
