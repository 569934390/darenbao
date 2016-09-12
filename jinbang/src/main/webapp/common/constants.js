Ext.define('common.constant', {
	singleton:true,
	state:[['有效','00A'],['失效','00X']],
	privType:[['菜单权限',1],['按钮权限',2]],
	authStatus:[['未认证',0],['已认证',1]],
	testDcc:{
		sysType:[['OCS',3],['ABM',4]],
		kpiType : {
					'IN' : ' 语音',
					'ISMP' : '彩信(增值)',
					'P2PSMS' : '短信',
					'CCG' : 'CCG数据',
					'DSL' :'宽带',
					'CRBT':'宽带',
					'PAYMENT':'代收',
					'EXT':'代收',
					'PS' : '数据',
					'PGW' : '4G'
				},
		dataType:[['OctetString', 'OctetString'],
				['Integer32', 'Integer32'],
				['Integer64', 'Integer64'],
				['Unsigned32', 'Unsigned32'],
				['Unsigned64', 'Unsigned64'],
				['Float32', 'Float32'],
				['Float64', 'Float64'],
				['Grouped', 'Grouped'],
				['Address', 'Address'],
				['Time', 'Time'],
				['UTF8String', 'UTF8String'],
				['DiameterIdentity', 'DiameterIdentity'],
				['Enumerated', 'Enumerated'],
				['IPFilterRule', 'IPFilterRule']]
	},
	buttonOperate:{view:'view',save:'save',add:'add',update:'update',edit:'edit',remove:'remove',saveOrUpdate:'saveOrUpdate',cancel:'cancel'}
});