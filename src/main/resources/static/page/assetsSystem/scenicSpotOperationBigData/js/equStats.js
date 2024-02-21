// 柱状图Echarts实例
var myBarEquChart = echarts.init(document.getElementById("equBarEcharts"));
// 饼图Echarts实例
var myPieEquChart = echarts.init(document.getElementById("equPieEcharts"));

// 设备统计的柱状图的option
optionBarEqu = {
  legend: {
    show: false, // 是否显示legend
    bottom: 0, // 把legend放到柱状图下面
  },
  tooltip: {},
  title: {
    left: "left",
    text: "设备使用情况(实时)",
    textStyle: {
      color: "#4180D0",
    },
  },
  dataset: {
    source: [
      ["product", "设备数量"],
      ["使用中", 680], // 设备数量
      ["闲置", 300],
      ["故障", 20],
    ],
  },
  xAxis: {
    type: "category",
  },
  yAxis: {},
  grid: {
    // height: 200,
  },
  series: [
    {
      type: "bar",
      barWidth: 50, // 柱状图宽度设置
      // echarts 柱状图 柱顶部显示数字
      itemStyle: {
        normal: {
          color: "#4F81BD", // 可以指定柱状图的颜色
          label: {
            show: true, // 开启显示
            position: "top", //在上方显示
          },
        },
      },
    },
  ],
};

// 设备统计的饼图的option
optionPieEqu = {
  legend: {
    show: false, // 是否显示legend
    bottom: 0, // 把legend放到柱状图下面
  },
  tooltip: {
    // trigger: "axis",
    showContent: true,
    // formatter: "{b}: {d}%",
    formatter: function (params) {
      //自行定义formatter格式
      if (!!params.data) {
        var sum = 0; // 机器总数
        optionPieEqu.dataset.source.slice(1).forEach((item) => {
          sum += item[1];
        });
        return `
            <div>
                <span><i class="data_dot" style="background: ${
                  params.color
                };"></i></span>
                <span>${params.data[0]}&nbsp;&nbsp;&nbsp;&nbsp;</span>
                <span class="font_weight">${
                  (params.data[1] / sum) * 100
                }%</span>
            </div>
        `;
      } else {
        return;
      }
    },
  },
  dataset: {
    source: [
      ["product", "设备数量"],
      ["使用中", 680], // 设备数量
      ["闲置", 300],
      ["故障", 20],
    ],
  },
  color: ["#2ecc71", "#95a5a6", "#e74c3c"], // 在这里可以修改饼图的色块
  series: [
    {
      type: "pie",
      id: "pie",
      radius: "50%",
      // center: ['50%', '25%'],
      emphasis: {
        focus: "data",
      },
      encode: {
        itemName: "product",
        value: "设备数量",
        tooltip: "设备数量",
      },

      labelLine: {
        normal: {
          length: 5, // 改变标示线的长度
          lineStyle: {
            // color: "red", // 改变标示线的颜色
          },
        },
      },

      label: {
        normal: {
          show: true, // 开启显示
          formatter: "{b}: {d}%",
        },
      },

    },
  ],
};

myBarEquChart.setOption(optionBarEqu);
myPieEquChart.setOption(optionPieEqu);

$.get('/system/operationBigData/getRobotState',function (data) {
  // {
  //   使用中: 55
  //   故障: 1
  //   闲置: 1833
  // }

  // [
  //     ["product", "设备数量"],
  //     ["使用中", 680], // 设备数量
  //     ["闲置", 300],
  //     ["故障", 20],
  // ]
  var newData = []
  newData.push(["product", "设备数量"])
  for(var i in data.data) {
    // var arr = []
    // arr.push(i, data[i])
    // newData.push(arr)


    newData.push([i, data.data[i]])
  }

  optionBarEqu.dataset.source = newData
  optionPieEqu.dataset.source = newData

  myBarEquChart.setOption(optionBarEqu);
  myPieEquChart.setOption(optionPieEqu);
})

// 图表自适应
window.addEventListener("resize", function () {
  myBarEquChart.resize();
  myPieEquChart.resize();
});
