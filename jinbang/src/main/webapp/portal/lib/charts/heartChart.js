constans.heartChart=function(menu){
	option = {
		title : {
	        text: menu.title||'健康监控',
	        subtext: menu.subTitle||'实时',
	        x:'right',
	        y:'top'
	    },
	    tooltip : {
	        formatter: "{a} <br/>{c} {b}"
	    },
	    toolbox: {
	        show : false,
	        feature : {
	            mark : {show: true},
	            restore : {show: true},
	            saveAsImage : {show: true}
	        }
	    },
	    series : [
	        {
	            name:'内存MB',
	            type:'gauge',
	            min:0,
	            max:100,
	            splitNumber:10,
	            axisLine: {            // 坐标轴线
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    width: 10
	                }
	            },
	            axisTick: {            // 坐标轴小标记
	                length :15,        // 属性length控制线长
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    color: 'auto'
	                }
	            },
	            splitLine: {           // 分隔线
	                length :20,         // 属性length控制线长
	                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
	                    color: 'auto'
	                }
	            },
	            title : {
	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    fontWeight: 'bolder',
	                    fontSize: 20,
	                    fontStyle: 'italic'
	                }
	            },
	            detail : {
	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    fontWeight: 'bolder'
	                }
	            },
	            data:[{value: 30, name: '内存'}]
	        },
	        {
	            name:'CPU',
	            type:'gauge',
	            center : ['20%', '55%'],    // 默认全局居中
	            radius : '50%',
	            min:0,
	            max:100,
	            endAngle:45,
	            splitNumber:4,
	            axisLine: {            // 坐标轴线
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    width: 8
	                }
	            },
	            axisTick: {            // 坐标轴小标记
	                length :12,        // 属性length控制线长
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    color: 'auto'
	                }
	            },
	            splitLine: {           // 分隔线
	                length :20,         // 属性length控制线长
	                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
	                    color: 'auto'
	                }
	            },
	            pointer: {
	                width:5
	            },
	            title : {
	                offsetCenter: [0, '-30%']      // x, y，单位px
	            },
	            detail : {
	                textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
	                    fontWeight: 'bolder'
	                }
	            },
	            data:[{value: 33, name: 'CPU'}]
	        },
	        {
	            name:'磁盘读',
	            type:'gauge',
	            center : ['80%', '50%'],    // 默认全局居中
	            radius : '50%',
	            min:0,
	            max:2,
	            startAngle:135,
	            endAngle:45,
	            splitNumber:2,
	            axisLine: {            // 坐标轴线
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    color: [[0.2, '#ff4500'],[0.8, '#48b'],[1, '#228b22']], 
	                    width: 8
	                }
	            },
	            axisTick: {            // 坐标轴小标记
	                splitNumber:5,
	                length :10,        // 属性length控制线长
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    color: 'auto'
	                }
	            },
	            axisLabel: {
	                formatter:function(v){
	                    switch (v + '') {
	                        case '0' : return 'E';
	                        case '1' : return '读';
	                        case '2' : return 'F';
	                    }
	                }
	            },
	            splitLine: {           // 分隔线
	                length :15,         // 属性length控制线长
	                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
	                    color: 'auto'
	                }
	            },
	            pointer: {
	                width:2
	            },
	            title : {
	                show: false
	            },
	            detail : {
	                show: false
	            },
	            data:[{value: 0.5, name: '读'}]
	        },
	        {
	            name:'磁盘写',
	            type:'gauge',
	            center : ['80%', '50%'],    // 默认全局居中
	            radius : '50%',
	            min:0,
	            max:2,
	            startAngle:315,
	            endAngle:225,
	            splitNumber:2,
	            axisLine: {            // 坐标轴线
	                lineStyle: {       // 属性lineStyle控制线条样式
	                    color: [[0.2, '#ff4500'],[0.8, '#48b'],[1, '#228b22']], 
	                    width: 8
	                }
	            },
	            axisTick: {            // 坐标轴小标记
	                show: false
	            },
	            axisLabel: {
	                formatter:function(v){
	                    switch (v + '') {
	                        case '0' : return 'H';
	                        case '1' : return '写';
	                        case '2' : return 'C';
	                    }
	                }
	            },
	            splitLine: {           // 分隔线
	                length :15,         // 属性length控制线长
	                lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
	                    color: 'auto'
	                }
	            },
	            pointer: {
	                width:2
	            },
	            title : {
	                show: false
	            },
	            detail : {
	                show: false
	            },
	            data:[{value: 0.5, name: '写'}]
	        }
	    ]
	};
	
	return option;
};