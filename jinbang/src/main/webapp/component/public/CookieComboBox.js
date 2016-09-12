Ext.define("component.public.CookieComboBox", {
			extend : 'component.public.AjaxComboBox',
			alias : 'widget.cookieComboBox',
			constructor : function(config) {
				config=config||{};
				Ext.apply(config,{
					queryMode:'local',
					editable:true
				});
				if(Ext.isEmpty(Ext.state.Manager.getProvider())){
					Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
				}
				var data=Ext.state.Manager.get(this.getCookieField(config.cookieField||config.name||config.id));
				if(!Ext.isEmpty(data)){
					var arr=data.toString().split(this.getCookieSpit());
					data=[];
					for(var i in arr){
						data[i]=[arr[i],arr[i]];
					}
					config.data=data;
				}
				this.addEvents('addCookie');
				this.callParent([config]);
				this.bindEvent();
			},
			bindEvent:function(){
				this.on('addCookie',Ext.bind(this.addCookie,this));
			},
			getCookieSpit:function(){
				return this.cookieSpilt||",";
			},
			getCookieSize:function(){
				return this.cookieSize||3;
			},
			getCookieField:function(cookieField){
				return cookieField||this.cookieField||this.name||this.id;
			},
			addCookie:function(value){
				var me=this;
				if(Ext.isEmpty(value))return;
				var value=value||this.getValue();
				var data=Ext.state.Manager.get(me.getCookieField());
//				data="";
//				Ext.state.Manager.set(me.getCookieField(),"");
				var flag=false;
				if(Ext.isEmpty(data)){
					data=value.toString();
					flag=true;
				}else if(data.toString().indexOf(value)==-1){
					data=value+","+data;
					flag=true;
				}else if(data.toString().indexOf(value)!=0){
					var cookieSplit=this.getCookieSpit();
					var arr=data.split(cookieSplit);
					for (var index = 0; index <arr.length; index++) {
						if(arr[index]==value){
							arr[index]=arr[0];
							arr[0]=value;
							break;
						}
					}
					data=arr.join(cookieSplit);
					flag=true;
				}
				if(flag){
					Ext.state.Manager.set(me.getCookieField(),data.toString());
					var cookieSplit=this.getCookieSpit();
					var arr=data.split(cookieSplit);
					var storeArr=[];
					for (var index = 0; index <arr.length; index++) {
						if(index>=this.getCookieSize()){
							break
						}else{
							storeArr.push([arr[index],arr[index]]);
						}
					}
					this.getStore().loadData(storeArr);
				}
			}
		});
