// 订单金额折线图Echart实例
var myOAChart = echarts.init(document.getElementById("OrderAmountEcharts"));

var base = +new Date(2020, 0, 0);
var oneDay = 24 * 3600 * 1000;
var date = [];

var data = [Math.random() * 300];

for (var i = 1; i < 3500; i++) {
  var now = new Date((base += oneDay));
  date.push([now.getFullYear(), now.getMonth() + 1].join("/"));
  data.push(Math.round((Math.random() - 0.5) * 20 + data[i - 1]));
}

// 订单金额折线图option
optionOA = {
  tooltip: {
    trigger: "axis",
    position: function (pt) {
      return [pt[0], "10%"];
    },
  },
  title: {
    left: "center",
    text: "交易金额趋势",
    textStyle: {
      color: "#4180D0",
    },
  },
  toolbox: {
    feature: {
      dataZoom: {
        yAxisIndex: "none",
      },
      restore: {},
      saveAsImage: {},
    },
  },
  xAxis: {
    type: "category",
    boundaryGap: false,
    data: date,
  },
  yAxis: {
    name: "单位: 万元",
    type: "value",
    boundaryGap: [0, "100%"],
  },
  dataZoom: [
    {
      type: "inside",
      start: 0,
      end: 10,
    },
    // 隐藏下面的滚动条
    // {
    //   start: 0,
    //   end: 100,
    // },
  ],
  grid: {
    // height: 200
  },
  series: [
    {
      name: "模拟数据",
      type: "line",
      symbol: "none",
      sampling: "lttb",
      itemStyle: {
        color: "rgb(255, 70, 131)", // 折线的颜色
      },
      areaStyle: {
        // 渐变色填充，从上到下
        color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
          {
            offset: 0,
            color: "rgb(255, 158, 68)",
          },
          {
            offset: 1,
            color: "rgb(255, 70, 131)",
          },
        ]),
      },
      data: data,
    },
  ],
};

myOAChart.setOption(optionOA);

// 图表自适应
window.addEventListener("resize", function () {
  myOAChart.resize();
});
