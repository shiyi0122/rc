// layui.use(['form','layer','table','laytpl','upload'],function(){
//   var form = layui.form;
  // 地图Echarts实例
  var myMapChart = echarts4.init(document.getElementById("myEchartsContent"));

  var areaColor = "#C7FFFD"; // 选中后的地图颜色
  var position = [10, 10]; // tooltip初始显示位置
  var isClickMap = false; // 是否点击了地图

  var tab = null;

  var loading = null; // loading 加载

  // 机器人运营大数据的option
  //使用制定的配置项和数据显示图表
  var mydata = []
  // 处理假数据(根据哪个字段分颜色，哪个字段就改成value)
  var newMyData = []
  var mapOption = {
    backgroundColor: "#FFFFFF",
    title: {
      text: "机器人运营大数据",
      subtext: "",
      x: "center",
      textStyle: {
        color: "#4180D0",
      },
    },
    tooltip: {
      trigger: "item",
      enterable: true, // 是否可以移到提示框上
      hideDelay: 100, // tooltip隐藏的延迟（alwaysShowContent: true 时无效）
      triggerOn: "click", // 点击地图后显示tooltip

      // 鼠标悬停在地图上时的省机器人信息
      formatter: function (params) {
        //自行定义formatter格式
        if (!!params.data) {
          return `
              <div class="province_robot_info">
              <div class="pri_head">
                  <span>${params.name}</span>
                  <span id="btn_xiangqing" class="xiangqing site-demo-active" data-type="tabAdd" data-id="${params.data.id}" onclick="onClickXQ(this)">详情<i class="iconfont icon-xiangqing"></i></span>
              </div>
              <span><i class="data_dot" style="background: ${params.color};"></i> 运营时长: ${params.data.value}小时</span>
              <span><i class="data_dot" style="background: ${params.color};"></i> 订单金额:
                  ${params.data.orderAmount}元</span>
              </div>
          `;
        } else {
          return;
        }
      },

      backgroundColor: "#ffffff", //提示标签背景颜色
      textStyle: {
        color: "#666666",
      }, //提示标签字体颜色
    },

    //左侧小导航图标
    visualMap: {
      show: true,
      x: "left",
      y: "center",
      splitList: [
        {
          start: 300,
        },
        {
          start: 200,
          end: 300,
        },
        {
          start: 100,
          end: 200,
        },
        {
          start: 1,
          end: 100,
        },
        {
          start: 0,
          end: 0,
        },
      ],
      color: ["#c0392b", "#FF9985", "#eccc68", "#FFE5DB", "#fcf7fc"],
    },
    // 写上了没效果，所以注释
    // geo: {
    //   map: 'some_svg'
    // },
    //配置属性
    series: [
      {
        name: "数据",
        type: "map",
        mapType: "china",
        symbolSize: 14,
        roam: true,
        selectedMode: "single", // 选中效果固化,字符串取值可选'single'，'multiple'，'single'单选，'multiple'多选
        label: {
          normal: {
            // formatter: '{b}',
            show: true, //省份名称
          },
          emphasis: {
            show: false,
          },
        },
        itemStyle: {
          emphasis: {
            // 鼠标悬停选中样式
            borderWidth: 0,
            // borderColor: '#fff',
            areaColor, // 选中后的地图颜色
            label: {
              show: true,
              textStyle: {
                // color: '#fff'
              },
            },
          },
        },
        data: newMyData, //数据
      },
    ],
  }

  // $.get('/system/operationBigData/getChinaMap',function (data) {
  //   var newMyData = data.data.map((item) => {
  //     return {
  //       name: item.name,
  //       id: item.id,
  //       value: item.operateTime,
  //       orderAmount: item.orderAmount,
  //     };
  //   });
  //   mapOption.series[0].data = newMyData
  //
  //   myMapChart.setOption(mapOption)
  // })

layui.use("layer", function () {
  layer = layui.layer
  // 加载loading
  loading = layer.load(0, {
    shade: false, // 是否有遮罩
    time: 10 * 1000, // 最大等待时间
  });
})

  async function getMapData() {
    const {data:res} = await axios.get("/system/operationBigData/getChinaMap")

    mydata = res.data;

    // 处理假数据(根据哪个字段分颜色，哪个字段就改成value)
    newMyData = mydata.map((item) => {
      return {
        id: item.id,
        name: item.name,
        value: item.operateTime,
        orderAmount: item.orderAmount,
        otherField: item.otherField,
      };
    });

    mapOption.series[0].data = newMyData;
    myMapChart.setOption(mapOption);

    layer.close(loading);
  }
  getMapData();

  myMapChart.setOption(mapOption);

  // 地图点击事件
  function onClickXQ(xqele) {
    var id = xqele.dataset.id
    layui.use(['form','layer','table','laytpl','upload'],function() {
      var form = layui.form;

      var layer = layui.layer;
      layer.open({
        type : 2,
        title: '景区排名',
        offset: '10%',
        area: ['1000px', '580px'], //宽高
        content: '/page/assetsSystem/scenicSpotOperationBigData/html/scenicSpotRankingList.html',
        success : function(layero, index){
          var body = layui.layer.getChildFrame('body', index);
          body.find(".id").val(id);//用户ID
          form.render();
        }
      });
    })

  }
  // myMapChart.on("click", function (params) {
  //   if (!!params.data) {
  //     isClickMap = true;
  //     // tooltip 详情点击事件
  //     var btnXQEve = document.getElementById("btn_xiangqing");
  //
  //     // 此详情点击事件只会触发一次，当鼠标离开此地图后下次才能触发（但是一次就够了，因为点击详情要跳转页面）
  //     btnXQEve.addEventListener("click", function () {
  //         var layer = layui.layer;
  //         layer.open({
  //           type : 2,
  //           title: '景区排名',
  //           offset: '10%',
  //           area: ['1000px', '580px'], //宽高
  //           content: '/page/assetsSystem/scenicSpotOperationBigData/html/scenicSpotRankingList.html',
  //           success : function(layero, index){
  //             var body = layui.layer.getChildFrame('body', index);
  //             body.find(".id").val(params.data.id);//用户ID
  //             form.render();
  //           }
  //         });
  //
  //     });
  //
  //
  //   } else {
  //     return;
  //   }
  // });

  // 图表自适应
  window.addEventListener("resize", function () {
    myMapChart.resize();
  });
// })