define([
	'text!modules/memberinfo/memberTree/templates/treeView.html',
    'Portal'
], function(treeViewTpl,Portal,regionData) {
	var options = {
        height: $(window).height()*0.9,
        modal: false,
        draggable: false,
        content: treeViewTpl,
        autoResizable: true
    };
    var popup;

	return {
		openAddWin:function(listView,editValue){
            popup = club.popup($.extend({}, options, {
                modal: true
            }));
            console.info(editValue);
            $.post(Portal.webRoot+'/client/subClientsPage.do',{
                clientId:editValue.bizId},function(result){
                var data = [];
                result.subClient.attributeMap = editValue;
                (function cacsh(node){
                    node.name = node.attributeMap.clientName+'('+(node.attributeMap.sex==1?'男':'女')+(node.attributeMap.age?(node.attributeMap.age):'')+')';
                    node.phone = node.attributeMap.phone;
                    node.symbolSize = [50, 50];
                    node.symbol = node.attributeMap.icon?('image://'+Portal.webRoot+'/upload.do?getthumb='+node.attributeMap.icon+'&size=50'):'./image/user5-128x128.jpg';
                    node.itemStyle = {
                        normal: {
                            label: {
                                position: 'bottom',
                                show: true
                            }
                        }
                    };
                    if (node.children&&_.isArray(node.children)) {
                        for (var i = node.children.length - 1; i >= 0; i--) {
                            cacsh(node.children[i]);
                        };
                    };
                })(result.subClient);
                var option = {
                    title : {
                        text: editValue.clientName+' 下级会员',
                        subtext: '（6层）'
                    },
                    tooltip : {
                        trigger: 'item',
                        formatter: "{b}: {c}"
                    },
                    calculable : false,
                    series : [
                        {
                            name:'树图',
                            type:'tree',
                            orient: 'vertical',  // vertical horizontal
                            rootLocation: {x: 'center', y: 30}, // 根节点位置  {x: 'center',y: 10}
                            nodePadding: 1000/editValue.perRange,
                            symbol: 'circle',
                            symbolSize: 40,
                            itemStyle: {
                                normal: {
                                    label: {
                                        show: false,
                                    },
                                    lineStyle: {
                                        color: '#48b',
                                        shadowColor: '#000',
                                        shadowBlur: 3,
                                        shadowOffsetX: 3,
                                        shadowOffsetY: 3,
                                        type: 'curve' // 'curve'|'broken'|'solid'|'dotted'|'dashed'

                                    }
                                },
                                emphasis: {
                                    label: {
                                        show: false
                                    }
                                }
                            },
                            data: [result.subClient]
                        }
                    ]
                };
                var myChart = echarts.init(document.getElementById('main'));
                console.info(option)
                myChart.setOption(option);
            });
		}
	};
});