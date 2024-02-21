// 运营时长同比图
// 年运营时长同比图Echart实例
var opeBugChart = echarts.init(document.getElementById("opeBugEcharts"));

var opeBugChartOption = {
  tooltip: {
    trigger: "axis",
    axisPointer: {
      type: "cross",
      crossStyle: {
        color: "#999",
      },
    },
  },
  toolbox: {
    // feature: {
    //   dataView: { show: true, readOnly: false },
    //   magicType: { show: true, type: ["line", "bar"] },
    //   restore: { show: true },
    //   saveAsImage: { show: true },
    // },
  },
  legend: {
    data: ["平均运营时长", "同比"],
    bottom: 0, // 把legend放到柱状图下面
  },
  xAxis: [
    {
      type: "category",
      data: ["2019年", "2020年", "2021年"],
      axisPointer: {
        type: "shadow",
      },
    },
  ],
  yAxis: [
    {
      type: "value",
      name: "小时",
      //   min: 0,
      //   max: 70000,
      //   interval: 10000,
    },
    {
      type: "value",
      name: "比率",
      //   min: 0,
      //   max: 140,
      //   interval: 20,
      axisLabel: {
        formatter: "{value} %",
      },
    },
  ],
  series: [
    {
      name: "上报次数",
      type: "bar",
      data: [250, 290, 600],
    },
    {
      name: "上报次数/订单总数",
      type: "line",
      yAxisIndex: 1,
      data: [100, 80, 20, 30],
    },
  ],
};

opeBugChart.setOption(opeBugChartOption);

// 图表自适应
window.addEventListener("resize", function () {
  opeBugChart.resize();
});
