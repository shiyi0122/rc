var operateName = null;
var statsWay = null;

// 展示运营时长字段名称的函数
function operateTimeName(way, data) {
  if (way == "avg_operate_time") {
    if (!!data && !!data.arr[0]) {
      operateName = data.arr[0].name;
    } else {
      operateName = "平均单台运营时长";
    }
    return operateName;
  } else {
    return operateName;
  }
}

// 渲染表格的函数
/**
 *
 * @param {*} data 表格数据
 */
function renderTable(way = "avg_operate_time") {
  return function (data) {
    layui.use("table", function () {
      var table = layui.table;
      var dropdown = layui.dropdown;

      // 如果方式为stats_way，并且还不是按景区统计，则执行以下逻辑
      if (
        way == "stats_way" &&
        data &&
        data.arr[0] &&
        data.arr[0].name !== "按景区统计"
      ) {
        // 把按景区统计的标识记为null
        statsWay = null;
      }

      if (
        (data && data.arr[0] && data.arr[0].name === "按景区统计") ||
        (way == "avg_operate_time" && statsWay == "scenic_stats")
      ) {
        // 显示景区名称字段
        statsWay = "scenic_stats";
        // 按景区统计
        // 初始表格
        var operateTable = table.render({
          elem: "#operate_table",
          // url: 'https://www.layui.com/demo/table/user/?page=1&limit=30',
          data: [
            {
              date: "2021-01",
              scenicName: "玉渊潭", // 景区名称
              avgOperateTime: 2450, // 平均运营时间
              YoY: "2.08%", // 同比
              QoQ: "2.08%", // 环比
              robotUseRatio: "80%", // 机器利用率
            },
            {
              date: "2021-01",
              scenicName: "后海",
              avgOperateTime: 2450,
              YoY: "3.08%",
              QoQ: "3.08%",
              robotUseRatio: "83%",
            },
          ],
          // cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
          cols: [
            [
              {
                field: "date", // 日期
                // width: 80,
                title: "时间",
              },
              {
                field: "scenicName", // 景区名称
                // width: 80,
                title: "景区名称",
              },
              {
                field: "avgOperateTime",
                // width: 80,
                title: operateTimeName(way, data),
              },
              {
                field: "YoY", // 同比，即同期相比，表示某个特定统计段今年与去年之间的比较。比如今年的某天和去年的某天进行比较
                // width: 80,
                title: "同比",
                sort: true,
              },
              {
                field: "QoQ", // 环比表示本次统计段与相连的上次统计段之间的比较。比如今天和昨天比较
                // width: 80,
                title: "环比",
                sort: true,
              },
              {
                field: "robotUseRatio",
                // width: 80,
                title: "机器利用率",
                sort: true,
                // minWidth: 100
              },
            ],
          ],
          // id: "scenicReload",
          page: true, // 开启分页
          // height: 300,
          // limits: [3, 5, 10], // 自定义每页显示条数
          // limit: 5 // 默认显示的条数
        });
      } else {
        // 不显示景区名称字段
        // 初始表格
        var operateTable = table.render({
          elem: "#operate_table",
          // url: 'https://www.layui.com/demo/table/user/?page=1&limit=30',
          data: [
            {
              date: "2021-01",
              avgOperateTime: 2450, // 平均运营时间
              YoY: "3.08%", // 同比
              QoQ: "3.08%", // 环比
              robotUseRatio: "83%", // 机器利用率
            },
            {
              date: "2021-01",
              avgOperateTime: 2450,
              YoY: "2.08%",
              QoQ: "2.08%",
              robotUseRatio: "80%",
            },
          ],
          // cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
          cols: [
            [
              {
                field: "date", // 日期
                // width: 80,
                title: "时间",
              },
              {
                field: "avgOperateTime",
                // width: 80,
                title: operateTimeName(way, data),
              },
              {
                field: "YoY", // 同比，即同期相比，表示某个特定统计段今年与去年之间的比较。比如今年的某天和去年的某天进行比较
                // width: 80,
                title: "同比",
                sort: true,
              },
              {
                field: "QoQ", // 环比表示本次统计段与相连的上次统计段之间的比较。比如今天和昨天比较
                // width: 80,
                title: "环比",
                sort: true,
              },
              {
                field: "robotUseRatio",
                // width: 80,
                title: "机器利用率",
                sort: true,
                // minWidth: 100
              },
            ],
          ],
          // id: "avtReload",
          page: true, // 开启分页
          // height: 300,
          // limits: [3, 5, 10], // 自定义每页显示条数
          // limit: 5 // 默认显示的条数
        });
      }
    });
  };
}
renderTable()(); // 执行函数内的函数

// 景区搜索
xmSelect.render({
  el: "#scenic_name",
  filterable: true,
  data: [
    { name: "玉渊潭", value: 1 },
    { name: "后海", value: 2 },
    { name: "石家庄", value: 3 },
    { name: "北京", value: 4 },
  ],
});

// 更多 下拉树
xmSelect.render({
  el: "#more_btn",
  tips: "更多",
  style: {
    borderRadius: "50px",
    // height: "50px",
  },
  data: [
    {
      name: "全部",
      value: -1,
      children: [
        { name: "自营", value: 100 },
        {
          name: "运营模式",
          value: 2,
          children: [
            {
              name: "北京九星智元",
              value: 101,
              children: [
                {
                  name: "景区",
                  value: 111,
                  children: [{ name: "机器人编号", value: 222 }],
                },
              ],
            },
            {
              name: "平遥九星智元",
              value: 102,
              children: [
                {
                  name: "景区",
                  value: 111,
                  children: [{ name: "机器人编号", value: 222 }],
                },
              ],
            },
          ],
        },
      ],
    },
  ],
  //选中关闭
  // clickClose: true,
  tree: {
    //是否显示树状结构
    show: true,
    //默认展开节点
    // expandedKeys: [-1],
  },
});

// 请选择统计方式
xmSelect.render({
  el: "#stats_way",
  tips: "请选择统计方式",
  radio: true,
  clickClose: true,
  model: {
    label: {
      type: "text",
      text: {
        //左边拼接的字符
        left: "",
        //右边拼接的字符
        right: "",
        //中间的分隔符
        separator: ", ",
      },
    },
  },
  data: [
    { name: "按景区统计", value: 1 },
    { name: "按**统计", value: 2 },
    { name: "按**统计", value: 3 },
  ],
  on: renderTable("stats_way"),
});

// 时间统计方式
xmSelect.render({
  el: "#date_way",
  radio: true,
  clickClose: true,
  model: {
    label: {
      type: "text",
      text: {
        //左边拼接的字符
        left: "",
        //右边拼接的字符
        right: "",
        //中间的分隔符
        separator: ", ",
      },
    },
  },
  data: [
    { name: "日份", value: 1 },
    { name: "月份", value: 2, selected: true },
    { name: "年份", value: 3 },
  ],
});

var startToEndTimes = []; // 存储起始时间和结束时间的列表
// 删除数组指定元素的方法
Array.prototype.remove = function (val) {
  let index = this.indexOf(val);
  if (index > -1) {
    this.splice(index, 1);
  }
};

// 下拉选择日期功能
var startTime = xmSelect.render({
  el: "#start_end_time",
  tips: "起止时间",
  content: '<div id="laydate" />',
  height: "auto",
  autoRow: true,
  // 删除日期时触发
  on: function (data) {
    startToEndTimes.remove(data.change[0].value);
    if (!data.isAdd) {
      dateSelect(startTime.getValue("value"));
    }
  },
});
layui.laydate.render({
  elem: "#laydate",
  position: "static",
  showBottom: false,
  format: "yyyy-M-dd",
  change: function () {
    dateSelect(startTime.getValue("value"));
  },
  // 选择日期时触发
  done: function (value) {

    var values = startTime.getValue("value");
    if (values.length >= 2) return;

    if (+new Date(startToEndTimes[0]) > +new Date(value)) {
      layer.msg("结束时间不能比开始时间晚哦~");
      return;
    }

    startToEndTimes.push(value);

    var newstet = startToEndTimes;
    if (startToEndTimes.length > 2) {
      newstet = startToEndTimes.slice(-2);
    }
    if (+new Date(newstet[1]) < +new Date(newstet[0])) {
    }
    var index = values.findIndex(function (val) {
      return val === value;
    });

    if (index != -1) {
      values.splice(index, 1);
    } else {
      values.push(value);
    }

    dateSelect(values);

    startTime.update({
      data: values.map(function (val) {
        return {
          name: val,
          value: val,
          selected: true,
        };
      }),
    });
  },
  ready: removeAll,
});
function removeAll() {
  document
    .querySelectorAll("#laydate td[lay-ymd].layui-this")
    .forEach(function (dom) {
      dom.classList.remove("layui-this");
    });
}
function dateSelect(values) {
  removeAll();
  values.forEach(function (val) {
    var dom = document.querySelector(
      '#laydate td[lay-ymd="' + val.replace(/-0([1-9])/g, "-$1") + '"]'
    );
    dom && dom.classList.add("layui-this");
  });
}

// 平均单台运营时长
xmSelect.render({
  el: "#avg_operate_time",
  tips: "请选择运营时长",
  radio: true,
  clickClose: true,
  model: {
    label: {
      type: "text",
      text: {
        //左边拼接的字符
        left: "",
        //右边拼接的字符
        right: "",
        //中间的分隔符
        separator: ", ",
      },
    },
  },
  data: [
    { name: "平均单台运营时长", value: 1, selected: true },
    { name: "运营时长", value: 2 },
  ],
  on: renderTable("avg_operate_time"),
});
