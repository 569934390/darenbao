Ext.define('component.dcc.model.MessageDetail', {
	extend : 'Ext.data.Model',
	idProperty : 'id',
	fields : [ {
		name : "id"
	}, {
		name : "text"
	}, {
		name : "code"
	}, {
		name : "type"
	}, {
		name : "src"
	}, {
		name : "avpValue"
	}, {
		name : "flags"
	}, {
		name : "vendorId"
	}, {
		name : "desc"
	}]
});
