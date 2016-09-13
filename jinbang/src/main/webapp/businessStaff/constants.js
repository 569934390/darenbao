Ext.define('business.staff.constants',{
	singleton:true,
	businessSystem:function(){
		var businessSystemArr=appconfig.business_system;
		var data=[];
		Ext.each(businessSystemArr,function(businessSystem,index){
			var name=businessSystem.split("@")[0];
			data.push([name,businessSystem]);
		});
		return data;
	}()
});