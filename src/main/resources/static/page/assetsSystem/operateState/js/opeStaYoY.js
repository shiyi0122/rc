// 运营时长同比图
// 年运营时长同比图Echart实例
var opeTYoYChart = echarts.init(document.getElementById("opeStaYoYEcharts"));

var opeTYoYChartOption = {
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
    data: ["数量", "占比"],
    bottom: 0, // 把legend放到柱状图下面
  },
  xAxis: [
    {
      type: "category",
      data: ["有营收", "无营收", "故障"],
      axisPointer: {
        type: "shadow",
      },
    },
  ],
  yAxis: [
    {
      type: "value",
      name: "",
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
      name: "数量",
      type: "bar",
      barWidth: 300 / 3,
      data: [],
    },
    {
      name: "占比",
      type: "line",
      yAxisIndex: 1,
      data: [],
    },
  ],
};

opeTYoYChart.setOption(opeTYoYChartOption);

// 图表自适应
window.addEventListener("resize", function () {
  opeTYoYChart.resize();
});
