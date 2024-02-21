// 基于准备好的dom，初始化echarts实例
var myTradeChart = echarts.init(document.getElementById('tradeEcharts'));
var loading = null;

// 交易统计的option
layui.use("layer", function () {
    layer = layui.layer
    // 加载loading
    loading = layer.load(0, {
        shade: false, // 是否有遮罩
        time: 10 * 1000, // 最大等待时间
    });
})
$.get('/system/operationBigData/getTradeEcharts',(function (data) {
        $(".right_middle_trade_stats").css({
            visibility: "visible"
        });
        $('#today').html(data.data.tradeEcharts.today);
        $('#yesterday').html(data.data.tradeEcharts.yesterday);
        $('#sevenDays').html(data.data.tradeEcharts.sevenDays);
        $('#thisMonth').html(data.data.tradeEcharts.thisMonth);
        myTradeChart.setOption(
            {
                legend: {
                    show: true,
                    bottom: 0, // 把legend放到柱状图下面
                },
                tooltip: {},
                title: {
                    left: 'left',
                    text: '交易统计',
                    textStyle: {
                        color: "#4180D0"
                    }
                },
                dataset: {
                    dimensions: ['product', '已结算订单数', '已结算累计收入'],
                    source: [
                        {product: data.data.list[0].time, '已结算订单数': data.data.list[0].orderTotal, '已结算累计收入': data.data.list[0].orderAmount},
                        {product: data.data.list[1].time, '已结算订单数': data.data.list[1].orderTotal, '已结算累计收入': data.data.list[1].orderAmount},
                        {product: data.data.list[2].time, '已结算订单数': data.data.list[2].orderTotal, '已结算累计收入': data.data.list[2].orderAmount},
                        {product: data.data.list[3].time, '已结算订单数': data.data.list[3].orderTotal, '已结算累计收入': data.data.list[3].orderAmount},
                        {product: data.data.list[4].time, '已结算订单数': data.data.list[4].orderTotal, '已结算累计收入': data.data.list[4].orderAmount},
                        {product: data.data.list[5].time, '已结算订单数': data.data.list[5].orderTotal, '已结算累计收入': data.data.list[5].orderAmount},
                        {product: data.data.list[6].time, '已结算订单数': data.data.list[6].orderTotal, '已结算累计收入': data.data.list[6].orderAmount},
                    ]
                },
                xAxis: {
                    name: "周",
                    type: 'category'
                },
                yAxis: {
                    name: "元"
                },
                grid: {
                    // height: 200
                },
                series: [{
                    type: 'bar',
                    // echarts 柱状图 柱顶部显示数字
                    itemStyle: {
                        normal: {
                            label: {
                                show: true, // 开启显示
                                position: 'top', //在上方显示
                            }
                        }
                    }
                },
                    {
                        type: 'bar',
                        // echarts 柱状图 柱顶部显示数字
                        itemStyle: {
                            normal: {
                                label: {
                                    show: true, // 开启显示
                                    position: 'top', //在上方显示
                                }
                            }
                        }
                    }
                ]
            }
        );

        layer.close(loading);
    })
)

// 图表自适应
window.addEventListener('resize', function () {
    myTradeChart.resize()
})