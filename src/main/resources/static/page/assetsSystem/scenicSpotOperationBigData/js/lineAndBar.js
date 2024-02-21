// 折线图、柱状图混合Echarts实例
var myLineAndBarChart = echarts.init(document.getElementById('myLineAndBarEcharts'));

// 折线图、柱状图混合option
var optionLineAndBar = {
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            type: 'cross',
            crossStyle: {
                color: '#999'
            }
        },
        // backgroundColor: "#ffffff", //提示标签背景颜色
        // textStyle: {
        //     color: "#6E7079"
        // } //提示标签字体颜色
    },
    toolbox: {
        feature: {
            dataView: {
                show: true,
                readOnly: false
            },
            magicType: {
                show: true,
                type: ['line', 'bar']
            },
            restore: {
                show: true
            },
            saveAsImage: {
                show: true
            }
        }
    },
    legend: {
        data: ['周期平均运营时长', '本期平均运营时长', '同比', '环比'],
        bottom: 0
    },
    xAxis: [{
        type: 'category',
        data: ['2021年1月', '2021年2月', '2021年3月', '2021年4月', '2021年5月', '2021年6月', '2021年7月',
            '2021年8月', '2021年9月',
            '2021年10月', '2021年11月', '2021年12月'
        ],
        axisPointer: {
            type: 'shadow'
        },
        shadowStyle: {
            // color: 
        },
        axisLabel: {
            // interval:0,
            // rotate: 40
        }
    }],
    yAxis: [{
            type: 'value',
            name: '时长',
            min: 0,
            max: 4500,
            interval: 500,
            axisLabel: {
                formatter: '{value}'
            }
        },
        {
            type: 'value',
            name: '比率',
            min: -60,
            max: 75,
            interval: 15,
            // min: -90, // 最小能到-90%
            // max: 90, // 最大能到90%
            // interval: 20,
            axisLabel: {
                formatter: '{value}%'
            }
        }
    ],
    series: [{
            name: '周期平均运营时长',
            type: 'bar',
            data: [2300, 2300, 2300, 2300, 4000, 2300, 2800, 3200, 2600, 2400, 2100, 1700]
        },
        {
            name: '本期平均运营时长',
            type: 'bar',
            data: [2400, 2400, 2400, 2400, 4200, 2400, 3000, 3500, 2800, 2500, 2100, 1800]
        },
        {
            name: '同比',
            type: 'line',
            yAxisIndex: 1,
            data: [2.0, 3.2, 5.3, 8.5, 7.3, 6.2, 7.3, 8.4, 7.0, 6.5, 5.0, 6.2]
        },
        {
            name: '环比',
            type: 'line',
            yAxisIndex: 1,
            data: [2.0, 2.2, 2.3, 2.5, 60.3, -30.2, 23.3, 20.4, -25.0, 16.5, -26.0, -18.2]
        }
    ]
};

//使用制定的配置项和数据显示图表
myLineAndBarChart.setOption(optionLineAndBar);