Ext.define('component.tools.AutoLayout',{
	doAutoLayout:function(items,flags,formWidth){
		var form= {width:formWidth},fields = [];
			w = formWidth-20,offseth=8,offsetw=8,step=WEBConstants.FIELD_DEFAULT_WIDTH,row_max_height=32,
			startWidth = 8,hidden_count=0;
		Ext.each(items,function(item,index){
			if(flags[index]&&flags[index]>0){
				for(var i=0;i<flags[index];i++){
					offsetw=startWidth;offseth+=row_max_height;
					row_max_height = 32;
				}
			}
			if(item.xtype!='hidden'&&item.xtype!='hide'&&item.autoLayout){
				var width = item.width,height = item.height;
				if(item.graphInfo){
					width = item.graphInfo.width;
					height = item.graphInfo.height;
				}
				if(!width)
					width = step;

				if(item.allowBlank==false){
					width = item.width - WEBConstants.FIELD_REQUIRED_WIDTH;
				}
				if(offsetw+width>w){
					offsetw=startWidth;offseth+=row_max_height;
					row_max_height = 32;
				}
				fields.push({x:offsetw,y:offseth});
				if(height&&height>row_max_height)row_max_height = (Math.round((height+12)/8))*8;
				offsetw+=(Math.round((width+8)/8))*8;
			}
			else{
				fields.push({x:formWidth+20,y:hidden_count++*22+8});
			}
		});
		form.height = offseth+row_max_height+16;
		form.fields = fields.reverse();
		return form;
	}
});