/**
 * SAVE_UPDATE("1", "新增或者修改"), ADD("2", "新增"), MODIFY("3", "修改"), RUN("4",
 * "立即执行"), FREQ("5", "修改任务执行时间"), START("6", "生效"), STOP("7", "失效");
 */
Ext.define('component.ctrl.Cmd', {
	singleton : true,
	initToolBarBtn : function() {
		var comps = Ext.ComponentQuery.query('button[action~=CTRL_CMD]');
		for (var i = 0; i < comps.length; i++) {
			comps[i].on('click', Ext.Function.bind(
							component.ctrl.Cmd.ctrlCmdFn, this));
		}
	},
	initMenuItem : function() {
		var me = this;
		Ext.Array.each(Ext.ComponentQuery.query('menuitem[action~=CTRL_CMD]'),
				function(comp) {
					comp.on('click', Ext.Function.bind(
									component.ctrl.Cmd.ctrlCmdFn, me));
				});
	},
	ctrlCmdFn : function(btn, e) {
		var action = '', params = {}, taskIds = [], records = this.searchGrid
				.getSelectionModel().getSelection();
		if (btn.action.indexOf('effective') != -1) {
			action = '生效';
			params.type = 'START';
		} else if (btn.action.indexOf('invalid') != -1) {
			action = '失效';
			params.type = 'DEL';
		} else if (btn.action.indexOf('just_run') != -1) {
			action = '立即执行';
			params.type = 'RUN';
		} else
			return Ext.Msg.alert('提示', '无效控制命令！');
		for (var i = 0, l = records.length; i < l; i++) {
			var record = records[i];
			taskIds.push(record.raw.NODE_TASK_ID);
			if (params.type == 'RUN' && record.raw.STATE == '00X')
				return Ext.Msg.alert('提示', '无法执行已失效的任务:【'
								+ record.raw.NODE_TASK_NAME + '】');
		}
		if (taskIds.length == 0)
			return Ext.Msg.alert('提示', '请勾选要执行控制命令的记录');
		params.taskIds = taskIds;
		perf.Functions.post('doo.do', params, function(msg) {
					Ext.Msg.alert('提示', action + '成功!', function() {
								if (params.type != 'RUN')
									perf.Functions.searchGrid.getStore().load();
							});
				}, 'ctrlCmdController');
	}
});