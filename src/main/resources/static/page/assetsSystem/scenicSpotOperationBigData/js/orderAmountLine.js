// 订单金额折线图Echart实例
var myOAChart = echarts.init(document.getElementById("OrderAmountEcharts"));

$.get('/system/operationBigData/getOrderAmountLine',(function (data) {
    var createDate = [];
    var orderAmount = [];
    for(var i =0; i < data.data.length ; i++){
        createDate.push(data.data[i].createDate)
        orderAmount.push(data.data[i].orderAmount)
    }
      myOAChart.setOption(
          {
            tooltip: {
              trigger: "axis",
            },
            xAxis: {
              type: "category",
              data: createDate,
            },
            yAxis: {
              type: "value",
              name: "单位: 元",
            },
            dataZoom: [
              {
                type: "inside",
                start: 0,
                end: 50,
              },
              {
                start: 0,
                end: 10,
              },
            ],
            series: [
              {
                name: "订单金额",
                data: orderAmount,
                type: "line",
              },
            ],
          }
      );
  })
)

// 图表自适应
window.addEventListener("resize", function () {
  myOAChart.resize();
});
