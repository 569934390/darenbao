Ext.define('KitchenSink.view.grid.GroupedHeaderGrid', {
    extend: 'Ext.grid.Panel',
    xtype: 'grouped-header-grid',
    store: 'Companies',
    columnLines: true,
    height: 350,
    title: 'Grouped Header Grid',
    viewConfig: {
        stripeRows: true
    },

    initComponent: function () {
        this.width = 675;
        this.columns = [{
                text     : 'test\\Company',
                flex     : 1,
                sortable : false,
                dataIndex: 'company'
            }, {
                text: 'Stock Price',
                columns: [{
                    text     : 'Price',
                    width    : 75,
                    sortable : true,
                    renderer : 'usMoney',
                    dataIndex: 'price'
                }, {
                    text     : 'Change',
                    width    : 80,
                    sortable : true,
                    renderer :  function(val) {
                        if (val > 0) {
                            return '<span style="color:green;">' + val + '</span>';
                        } else if (val < 0) {
                            return '<span style="color:red;">' + val + '</span>';
                        }
                        return val;
                    },
                    dataIndex: 'change'
                }, {
                    text     : '% Change',
                    width    : 100,
                    sortable : true,
                    renderer : function(val) {
                        if (val > 0) {
                            return '<span style="color:green;">' + val + '</span>';
                        } else if (val < 0) {
                            return '<span style="color:red;">' + val + '</span>';
                        }
                        return val;
                    },
                    dataIndex: 'pctChange'
                }]
            }, {
                text     : 'Last Updated',
                width    : 115,
                sortable : true,
                renderer : Ext.util.Format.dateRenderer('m/d/Y'),
                dataIndex: 'lastChange'
            }];

        this.callParent();
    }
});
Ext.onReady(function(){
	Ext.create('KitchenSink.view.grid.GroupedHeaderGrid',{renderTo:Ext.getBody(),store:store5})
})