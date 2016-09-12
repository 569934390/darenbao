/**
 * IpField
 */

Ext.define('component.public.IpField', {
	extend : 'Ext.form.FieldContainer',
    alias: 'widget.ipField',
	layout : 'column',
    combineErrors: true,
    msgTarget: 'under',
    defaults: {
        hideLabel: true
    },
	initComponent : function(condition) {
    	var me = this;
    	var ipWidth = parseInt((me.width-me.labelWidth-41)/4);

    	me.items = [{
    		xtype : "textfield",maxLength:3,minLength:1,name:'dm_Ip1',width:ipWidth,
    		regex:/^\d+$/,regexText:'请输入(0-255)IP段',enableKeyEvents : true,
    		fieldStyle:'background:none; border-right: 0px solid;text-align:center;', 
			listeners : {
				keydown : me.keyDownValue.bind(me.onScroll),
				keyup : me.keyUpValue.bind(me.onScroll)
			}
    	},{
    		xtype: 'textfield',value:'.',name:'dm_Ip1-2',width:12,readOnly : true,
    		fieldStyle:'background:none; border-left: 0px solid;background:none; border-right: 0px solid;'
    	},{
    		xtype : "textfield",maxLength : 3,minLength : 1,name:'dm_Ip2',width:ipWidth,
    		fieldStyle:'background:none; border-left: 0px solid;background:none; border-right: 0px solid;text-align:center;', 
    		regex:/^\d+$/,regexText:'请输入(0-255)IP段',enableKeyEvents : true,listeners : {
				keydown : me.keyDownValue.bind(me.onScroll),
				keyup : me.keyUpValue.bind(me.onScroll)
			}
    	},{
    		xtype: 'textfield',value:'.',name:'dm_Ip2-3',width:12,readOnly : true,
    		fieldStyle:'background:none; border-left: 0px solid;background:none; border-right: 0px solid;text-align:center;'
    	},{
    		xtype : "textfield",maxLength:3,minLength : 1,name : 'dm_Ip3',width:ipWidth,
    		fieldStyle:'background:none; border-left: 0px solid;background:none; border-right: 0px solid;text-align:center;', 
    		regex:/^\d+$/,regexText:'请输入(0-255)IP段',enableKeyEvents : true,
			listeners : {
				keydown : me.keyDownValue.bind(me.onScroll),
				keyup : me.keyUpValue.bind(me.onScroll)
			}
    	},{
    		xtype: 'textfield',value:'.',name:'dm_Ip3-4',width:12,readOnly : true,
    		fieldStyle:'background:none; border-left: 0px solid;background:none; border-right: 0px solid;text-align:center;'
    	},{
    		xtype : "textfield",maxLength:3,minLength:1,name:'dm_Ip4',width:ipWidth,
    		fieldStyle:'background:none; border-left: 0px solid;background:none;text-align:center;', 
    		regex:/^\d+$/,regexText:'请输入(0-255)IP段',enableKeyEvents : true,
    		listeners : {
				keydown : me.keyDownValue.bind(me.onScroll),
				keyup : me.keyUpValue.bind(me.onScroll)
			}
    	}]
    	
		me.superclass.initComponent.call(me);
	},
	keyTabValue : function(src,evt){
		if(src.name=="dm_Ip1-2"){
			this.find('name','ip2')[0].selectText();
		}else if(src.name=="dm_Ip2-3"){
			Ext.getCmp('dm_Ip3').selectText();
		}else if(src.name=="dm_Ip3-4"){
			Ext.getCmp('dm_Ip4').selectText();
		}
	},
	keyDownValue : function(src,evt){
		var me = this;

		var val = src.getValue().toString().replace(/^d+$/,'');
		if(val>255){
			val=255;
		}else if(val==""){
			val="";
		}
		if(val.length>3){
			val = val.substr(0,2);
		}
		src.setValue(val);
		
		if(evt.getKey()=='9'){
			if(src.name=="dm_Ip1"){
				me.query('textfield')[1].focus();
			}else if(src.name=="dm_Ip2"){
				me.query('textfield')[3].focus();
			}else if(src.name=="dm_Ip3"){
				me.query('textfield')[5].focus();
			}
		}
	},
	keyUpValue : function(src,evt){
		var me = this;
		
		var val = src.getValue().toString().replace(/^d+$/,'');
		if(val>255){
			val=255;
		}else if(val==""){
			val="";
		}
		if(val.length>3){
			val = val.substr(0,2);
		}
		src.setValue(val);
		if(evt.getKey()=='190'||evt.getKey()==evt.ENTER){//||evt.getKey()=='9'
			if(src.name!="ip4"){
				if(val==""){
					src.setValue("0");
				}
			}else{
			}
			if(src.name=="dm_Ip1"){
				me.query('textfield')[2].selectText();
				if(me.query('textfield')[2].getValue()==""){
					me.query('textfield')[2].setValue("");
				}
			}else if(src.name=="dm_Ip2"){
				me.query('textfield')[4].selectText();
				if(me.query('textfield')[4].getValue()==""){
					me.query('textfield')[4].setValue("");
				}
			}else if(src.name=="dm_Ip3"){
				me.query('textfield')[6].selectText();
				if(me.query('textfield')[6].getValue()==""){
					me.query('textfield')[6].setValue("");
				}
			}
		}
	},
	getValues : function(){
		var me = this;
		var ips;
		var ip1 = me.query('textfield')[0].getValue();
		var ip2 = me.query('textfield')[2].getValue();
		var ip3 = me.query('textfield')[4].getValue();
		var ip4 = me.query('textfield')[6].getValue();
		if(ip1>=0&&ip1<256&&ip1!=""){
			ips = ip1+".";
		}else{
			ips = "0.";
		}
		if(ip2>=0&&ip2<256&&ip2!=""){
			ips += ip2+".";
		}else{
			ips += "0.";
		}
		if(ip3>=0&&ip3<256&&ip3!=""){
			ips += ip3+".";
		}else{
			ips += "0.";
		}
		if(ip4>=0&&ip4<256&&ip4!=""){
			ips += ip4;
		}else{
			ips += "0";
		}
		return ips;
	},
	setValues : function(ips){
		var me = this;
		var ipArr = ips.split(".");
		if(ipArr.length==4){
			me.query('textfield')[0].setValue(ipArr[0])
			me.query('textfield')[2].setValue(ipArr[1])
			me.query('textfield')[4].setValue(ipArr[2])
			me.query('textfield')[6].setValue(ipArr[3])
		}
	},
	isNull : function(){
		var me = this;
		var ips;
		var ip1 = me.query('textfield')[0].getValue();
		var ip2 = me.query('textfield')[2].getValue();
		var ip3 = me.query('textfield')[4].getValue();
		var ip4 = me.query('textfield')[6].getValue();
		if(ip1>0&&ip1<256&&ip1!=""){
			return false;
		}else if(ip2>0&&ip2<256&&ip2!=""){
			return false;
		}else if(ip3>0&&ip3<256&&ip3!=""){
			return false;
		}else if(ip4>0&&ip4<256&&ip4!=""){
			return false;
		}else{
			return true;
		}
	}
});
