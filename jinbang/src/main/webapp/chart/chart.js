// 基于准备好的dom，初始化echarts图表
var myChart1 = echarts.init(document.getElementById('chart1')); 
var myChart2 = echarts.init(document.getElementById('chart2')); 
var myChart3 = echarts.init(document.getElementById('chart3')); 
var myChart4 = echarts.init(document.getElementById('chart4')); 
option = {
    title : {
        text: '某地区蒸发量和降水量',
        subtext: '纯属虚构'
    },
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['蒸发量','降水量']
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            magicType : {show: true, type: ['line', 'bar']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    xAxis : [
        {
            type : 'category',
            data : ['1月','2月','3月','4月','5月','6月','7月','8月','9月','10月','11月','12月']
        }
    ],
    yAxis : [
        {
            type : 'value',
            splitArea : {show : true}
        }
    ],
    series : [
        {
            name:'蒸发量',
            type:'bar',
            data:[2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3],
            markPoint : {
                data : [
                    {type : 'max', name: '最大值'},
                    {type : 'min', name: '最小值'}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name: '平均值'}
                ]
            }
        },
        {
            name:'降水量',
            type:'bar',
            data:[2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3],
            markPoint : {
                data : [
                    {name : '年最高', value : 182.2, xAxis: 7, yAxis: 183, symbolSize:18},
                    {name : '年最低', value : 2.3, xAxis: 11, yAxis: 3}
                ]
            },
            markLine : {
                data : [
                    {type : 'average', name : '平均值'}
                ]
            }
        }
    ]
};
var labelTop = {
    normal : {
        label : {
            show : true,
            position : 'center',
            textStyle: {
                baseline : 'bottom'
            }
        },
        labelLine : {
            show : false
        }
    }
};
var labelBottom = {
    normal : {
        color: '#ccc',
        label : {
            show : true,
            position : 'center',
            formatter : function(a,b,c){return 100 - c + '%'},
            textStyle: {
                baseline : 'top'
            }
        },
        labelLine : {
            show : false
        }
    },
    emphasis: {
        color: 'rgba(0,0,0,0)'
    }
};
var radius = [40, 55];
var option1 = {
    legend: {
        x : 'center',
        y : 'center',
        data:[
            'CPU使用率','物理内存','磁盘读写'
        ]
    },
    title : {
        text: '系统监控',
        x: 'center'
    },
    toolbox: {
        show : true,
        feature : {
            dataView : {show: true, readOnly: false},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    series : [
        {
            type : 'pie',
            center : ['20%', '50%'],
            radius : radius,
            data : [
                {name:'other', value:46, itemStyle : labelBottom},
                {name:'GoogleMaps', value:54,itemStyle : labelTop}
            ]
        },
        {
            type : 'pie',
            center : ['50%', '50%'],
            radius : radius,
            data : [
                {name:'other', value:56, itemStyle : labelBottom},
                {name:'Facebook', value:44,itemStyle : labelTop}
            ]
        },
        {
            type : 'pie',
            center : ['80%', '50%'],
            radius : radius,
            data : [
                {name:'other', value:65, itemStyle : labelBottom},
                {name:'Youtube', value:35,itemStyle : labelTop}
            ]
        }
    ]
};
// 为echarts对象加载数据 
myChart1.setOption(option1); 
var option2 = {
    tooltip : {
        trigger: 'item',
        formatter: "{a} <br/>{b} : {c} ({d}%)"
    },
    legend: {
        orient : 'vertical',
        x : 'left',
        data:['直达','营销广告','搜索引擎','邮件营销','联盟广告','视频广告','百度','谷歌','必应','其他']
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : false,
    series : [
        {
            name:'访问来源',
            type:'pie',
            selectedMode: 'single',
            radius : [0, 40],
            itemStyle : {
                normal : {
                    label : {
                        position : 'inner'
                    },
                    labelLine : {
                        show : false
                    }
                }
            },
            data:[
                {value:335, name:'直达'},
                {value:679, name:'营销广告'},
                {value:1548, name:'搜索引擎', selected:true}
            ]
        },
        {
            name:'访问来源',
            type:'pie',
            radius : [60, 100],
            data:[
                {value:335, name:'直达'},
                {value:310, name:'邮件营销'},
                {value:234, name:'联盟广告'},
                {value:135, name:'视频广告'},
                {value:1048, name:'百度'},
                {value:251, name:'谷歌'},
                {value:147, name:'必应'},
                {value:102, name:'其他'}
            ]
        }
    ]
};
//myChart2.on(ecConfig.EVENT.PIE_SELECTED, function(param){
//    var selected = param.selected;
//    var serie;
//    var str = '当前选择： ';
//    for (var idx in selected) {
//        serie = option.series[idx];
//        for (var i = 0, l = serie.data.length; i < l; i++) {
//            if (selected[idx][i]) {
//                str += '【系列' + idx + '】' + serie.name + ' : ' + 
//                       '【数据' + i + '】' + serie.data[i].name + ' ';
//            }
//        }
//    }
//    document.getElementById('wrong-message').innerHTML = str;
//})
myChart2.setOption(option2); 
var option3 = {
    tooltip : {
        trigger: 'axis'
    },
    legend: {
        data:['邮件营销','联盟广告','视频广告','直接访问','搜索引擎']
    },
    toolbox: {
        show : true,
        feature : {
            mark : {show: true},
            dataView : {show: true, readOnly: false},
            magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
            restore : {show: true},
            saveAsImage : {show: true}
        }
    },
    calculable : true,
    xAxis : [
        {
            type : 'category',
            boundaryGap : false,
            data : ['周一','周二','周三','周四','周五','周六','周日']
        }
    ],
    yAxis : [
        {
            type : 'value'
        }
    ],
    series : [
        {
            name:'邮件营销',
            type:'line',
            stack: '总量',
            itemStyle: {normal: {areaStyle: {
                color: 'rgba(255, 69, 0, 0.7)'
            }}},
            data:[120, 132, 101, 134, 90, 230, 210]
        },
        {
            name:'联盟广告',
            type:'line',
            stack: '总量',
            itemStyle: {normal: {areaStyle: {
                color: 'rgba(30, 144, 255, 0.6)'
            }}},
            data:[220, 182, 191, 234, 290, 330, 310]
        },
        {
            name:'视频广告',
            type:'line',
            stack: '总量',
            itemStyle: {normal: {areaStyle: {
                color: 'rgba(138, 43, 226, 0.5)'
            }}},
            data:[150, 232, 201, 154, 190, 330, 410]
        },
        {
            name:'直接访问',
            type:'line',
            stack: '总量',
            itemStyle: {normal: {areaStyle: {
                color: 'rgba(34, 139, 34, 0.4)'
            }}},
            data:[320, 332, 301, 334, 390, 330, 320]
        },
        {
            name:'搜索引擎',
            type:'line',
            stack: '总量',
            itemStyle: {normal: {areaStyle: {
                color: 'rgba(220, 20, 60, 0.3)'
            }}},
            data:[820, 932, 901, 934, 1290, 1330, 1320]
        }
    ]
};
myChart3.setOption(option3); 
myChart4.setOption(option); 