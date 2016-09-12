Ext.define("component.public.IcmpComponent", {
			layout : 'fit',
			title : 'ICMP ping',
			width : 400,
			height : 350,
			extend : 'Ext.window.Window',
			items : [{
						xtype : 'form',
						frame : true,
						border : false,
						items : [{
									layout : 'form',
									frame : true,
									border : false,
									layout : 'column',
									name : 'leftForm',
									items : [{
												xtype : 'label',
												width : 30,
												text : 'ping'
											}, {
												xtype : 'textfield',
												width : 300,
												hideLabel : true
											}, {
												xtype : 'button',
												style : 'margin-left:5px',
												width : 30,
												text : ">"
											}]
								}, {
									xtype : 'textarea',
									height : 200,
									style : 'margin-top:5px;',
									width : 378
								}]
					}],
			buttons : [{
						text : "取消"
					}]
		});