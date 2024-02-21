// renderTable的方式
var scenicSearchWay = "scenic_search";
var avgOpeTimeWay = "avg_operate_time";
var scenicStatWay = "scenic_stats";
var checkDateWay = "check_date";

// 统计方式
var scenicStat = "按景区统计";
var robotStat = "按机器人统计";

// 选择时间
var checkDay = "日份";
var checkMonth = "月份";
var checkYear = "年份";

// 运营时长
var avgOpeTime = "平均单台运营时长";
var opeTime = "运营时长";
var YoY = "同比";
var QoQ = "环比";

var syncOpeTime = "同期平均运营时长";
var currOpeTime = "本期平均运营时长";

var operateName = null;
// var statsWay = null; // 选择按景区统计的标识
var statsWay = scenicStatWay; // 选择按景区统计的标识
var robotIdWay = null;
var dateGapWay = checkMonth; // 选择时间的标识(年月日)
var operateTimeWay = avgOpeTime; // 选择运营时长的标识
var operateTable = null;
var $ = layui.$;

var count = 0; // 数据总数
// var diffDate = 0; // 相差时间(相对的 setTimeout 延迟时间)
var diffObj = {
  diffDate: 0, // 相差时间(相对的 setTimeout 延迟时间)
  diffCodeExec: 0,
};
var listenData = null; // proxy监听数据变化

var currPage = 1; // 当前页数

var listData1 = []; // 临时存放数据的列表

var tableData = []; // 表格数据
var tableCols = []; // 表格表头

var avgOpeTimes = []; // 平均运营时长列表
var opeTimes = []; // 运营时长列表

var legendData = ["上报次数", "上报次数/订单总数"]; // echarts中legend的data

var resultTableData = []; // 表格数据(比率没有%)
var resultData = []; // 后端接口返回的数据(比率有%)
var resultCols = []; // 后端接口返回的表头
var resultOpeTimes = []; // 后端接口返回的运营时长数据(同期平均运营时长、本期平均运营时长)

var startToEndTimes = []; // 存储起始时间和结束时间的列表

var mycurrpage = null; // 上一个页数
var myscenicsearch = null; // 上一个景区搜索关键字
var mystarttoendtime = null; // 上一个起止时间关键字
var clickQueryflag = false; // 点击查询标识

var scenicSearchHelp = ""; // 景区搜索关键字辅助
var resultOTTs = []; // 表格的运营时长日期列表

var YoYOrderBys = []; // 同比排序
var QoQOrderBys = []; // 环比排序
var robotUseRatioOrderBys = []; // 机器利用率排序
var downEnter = false; // 是否按下了回车键

// var prevScenicSearch = ""; // 上一个景区搜索关键字
var doneNum = 0; // 起止时间触发次数

var opeTimeInputValue = null; // 运营时长输入框文字的内容

var opetemdata = {
  opeTimePla: "平均单台运营时长",
};

var selectData = null;

var check_scenic_way = "所有景区"; // 请选择景区
var stats_option_way = "上报原因大类"; // 统计项
var prev_check_scenic_way = null; // 上一个选择的景区
var prev_stats_option_way = null; // 上一个选择的景区

var startTimeDay = formatTime(); // 起始时间(日)
var endTimeDay = formatTime(); // 终止时间(日)
var prev_startTimeDay = ""; // 上一个起始时间(日)
var prev_endTimeDay = ""; // 上一个终止时间(日)

/**
 * 展示运营时长字段名称的函数
 * @param {*} way 点击的是统计方式还是运营时长下拉框
 * @param {*} data 统计方式的data
 * @returns
 */
function operateTimeName(way, data) {
  // 如果点的是平均运营时长这个下拉框
  if (way == avgOpeTimeWay) {
    if (
        !!data &&
        !!data.arr[0] &&
        (data.arr[0].name == avgOpeTime || data.arr[0].name == opeTime)
    ) {
      operateName = data.arr[0].name;
    } else {
      operateName = avgOpeTime;
    }
    return operateName;
  } else {
    return operateName;
  }
}

/**
 * 初始表格的函数
 * @param {*} tableData 表格的数据
 * @param {*} tableCols 表格的表头
 */
function initTable(tableData, tableCols) {
  // 按景区统计
  // 初始表格
  var table = layui.table;
  var laypage = layui.laypage;
  operateTable = table.render({
    elem: "#operate_table",
    autoSort: false, //禁用前端自动排序
    // width: 2000,
    // url: 'https://www.layui.com/demo/table/user/?page=1&limit=30',
    data: tableData,
    // cellMinWidth: 80, //全局定义常规单元格的最小宽度，layui 2.2.1 新增
    cols: [tableCols],
    // id: "scenicReload",
    page: false, // 开启分页
    // height: 300,
    // limits: [3, 5, 10], // 自定义每页显示条数
    // limit: 5 // 默认显示的条数
  });

  /**
   * 排序操作
   */
  //注：sort 是工具条事件名，operate_table 是 table 原始容器的属性 lay-filter="对应的值"
  table.on("sort(operate_table)", function (obj) {
    if (obj.field == "YoY") {
      YoYOrderBys.length = 0;
      YoYOrderBys.push(obj.field, obj.type);
    }
    if (obj.field == "QoQ") {
      QoQOrderBys.length = 0;
      QoQOrderBys.push(obj.field, obj.type);
    }
    if (obj.field == "robotUseRatio") {
      robotUseRatioOrderBys.length = 0;
      robotUseRatioOrderBys.push(obj.field, obj.type);
    }
    // console.log(YoYOrderBys, QoQOrderBys, robotUseRatioOrderBys);

    renderTable()();

    // layer.msg("服务端排序。order by " + obj.field + " " + obj.type);
  });
}

/**
 * 时间格式化（天）
 */
function formatTime() {
  var date = new Date();
  var year = date.getFullYear();
  var month = (date.getMonth() + 1).toString().padStart(2, "0");
  var day = date.getDate().toString().padStart(2, "0"); // 不足2位自动补0
  return year + "-" + month + "-" + day;
}

$(window).keydown(function (e) {
  var key = window.event ? e.keyCode : e.which;
  if (key.toString() == "13") {
    downEnter = true;
  }
});

/**
 * 获取input输入框文字内容
 */

/**
 * 获取表格数据的函数
 * @param {*} data 下拉框所选择的数据
 * @param {*} currPage 当前页数
 * @param {*} way 渲染方式(点击的是哪个下拉框)
 * @returns
 */
var startDate = +new Date();
async function getResultTableData(data, currPage, way) {
  // console.log(way);
  if (!!data && !!data.arr[0] && way == "stats_way") {
    check_scenic_way = data.arr[0].name;
  }
  if (!!data && !!data.arr[0] && way == "check_date") {
    stats_option_way = data.arr[0].name;
  }

  // console.log(mycurrpage, currPage);
  var scenicSearch = ""; // 景区搜索关键字
  var startToEndTime = ""; // 起止时间
  if (!!data && !!data.arr[0] && way == scenicSearchWay) {
    scenicSearchHelp = data.arr[0].name;
  }

  if (check_scenic_way == "所有景区") {
    check_scenic_way = "1";
  } else if (check_scenic_way == "区分景区") {
    check_scenic_way = "2";
  }

  if (stats_option_way == "上报原因") {
    stats_option_way = "1";
  } else if (stats_option_way == "处理结果") {
    stats_option_way = "2";
  } else if (stats_option_way == "上报原因大类") {
    stats_option_way = "3";
  } else if (stats_option_way == "上报原因--按景区排名前五") {
    stats_option_way = "4";
  }

  // console.log(mycurrpage, currPage);
  if (clickQueryflag) {
    var startTETs = Array.from(new Set(startToEndTimes));
    // console.log(startTETs);
    if (startTETs.length == 2) {
      startToEndTime = startTETs.join(",");
    } else if (startTETs.length == 1) {
      var toCurrTimes = startTETs.concat(); // 如果没有选择第二个时间，则默认到当前时间
      var currTime = formatTime();
      toCurrTimes.push(currTime);
      // console.log(toCurrTimes);
      startToEndTime = toCurrTimes.join(",");
    }

    // clickQueryflag = false;
  }

  // console.log(startToEndTime);

  // 获取表头数据
  if (
      resultCols.length === 0 ||
      prev_check_scenic_way !== check_scenic_way ||
      prev_stats_option_way !== stats_option_way
  ) {
    // 获取表格表头数据
    var { data: rescol } = await axios.get(
        "https://www.fastmock.site/mock/4b7723086cfcc561616f87fb8e1e3561/test/bugStatsCols",
        {
          params: {
            check_scenic_way, // 选择景区方式
            stats_option_way, // 统计项方式
          },
        }
    );
  } else {
    var rescol = { data: resultCols };
  }
  // console.log(prev_check_scenic_way, check_scenic_way);

  // console.log(clickQueryflag);
  // 请选择景区 && 统计项
  if (
      resultTableData.length === 0 ||
      mycurrpage !== currPage ||
      prev_check_scenic_way !== check_scenic_way ||
      prev_stats_option_way !== stats_option_way ||
      (!!startTimeDay &&
          !!endTimeDay &&
          (startTimeDay !== prev_startTimeDay || endTimeDay !== prev_endTimeDay))
  ) {
    var { data: resdata } = await axios.get(
        "/system/bugStatus/getBugStatusList",
        {
          params: {
            currPage, // 当前页数
            // startToEndTime, // 起止时间
            startTime: startTimeDay, // 起始时间
            endTime: endTimeDay, // 终止时间
            checkScenicWay : check_scenic_way, // 选择景区方式
            statsOptionWay : stats_option_way, // 统计项方式
          },
        }
    );
    count = resdata.count;
  } else {
    var resdata = { data: resultTableData };
  }
  if (clickQueryflag) {
    myscenicsearch = scenicSearch;
    mystarttoendtime = startToEndTime;
    clickQueryflag = false;
  }

  prev_check_scenic_way = check_scenic_way;
  prev_stats_option_way = stats_option_way;

  mycurrpage = currPage;

  var endDate = +new Date();
  listenData.diffDate = endDate - startDate;

  prev_startTimeDay = startTimeDay; // 上一个开始时间值(日)
  prev_endTimeDay = endTimeDay; // 上一个结束时间值(日)

  layer.close(loading);

  return {
    resdata,
    rescol,
  };
}
// getResultTableData();

/**
 * 获取景区名称的函数
 * (景区名称列表数据全部返回)
 */
(async function () {
  var { data: res } = await axios.get(
      "https://www.fastmock.site/mock/4b7723086cfcc561616f87fb8e1e3561/test/scenicNames"
  );

  // 处理景区名称数据
  var scenicNames = res.data.map((item, index) => {
    var obj = {};
    obj.name = item;
    obj.value = index;
    return obj;
  });
  // console.log(scenicNames);
})();

/**
 * 渲染表格的函数
 * @param {*} data 表格数据
 */
function renderTable(way = avgOpeTimeWay) {
  return function (data) {
    if (data == undefined) {
      data = selectData;
    } else {
      selectData = data;
    }
    // console.log(currPage); // 当前页数

    /* 2021.08.19 新增 start
    *
    * 解决了二次选择选项后取消选择问题
    *  */
    /*
    * @params - opt 选项
    */
    function setValue(opt) {
      var name = ""
      var value = ""
      name = data.change[0].name
      value = data.change[0].value
      // 这里必须做一个延迟执行，因为当前选项为空时，需要时间判断一会，判断完成后再选择当前点击的选项
      setTimeout(() => {
        // 选中指定选项
        opt.setValue([
          {
            name,
            value
          }
        ])
      })
      return name
    }

    if(way == "stats_way" && !!data && data.arr.length == 0) {
      setValue(check_scenic)
    }

    if(way == "check_date" && !!data && data.arr.length == 0) {
      var name = setValue(stats_option)
    }

    /* 2021.08.19 新增 end */

    // 加载loading
    loading = layer.load(0, {
      shade: false, // 是否有遮罩
      time: 10 * 1000, // 最大等待时间
    });
    var res = getResultTableData(data, currPage, way);
    // console.log(res);
    res.then((resoved, rejected) => {
      // console.log(resoved);
      resultTableData = resoved.resdata.data || [{}]; // 表格数据
      resultCols = resoved.rescol.data; // 表格表头

      resultData = [];
      // 直接处理后端返回的数据(首先深复制一下-对象数组)
      resultTableData.forEach((item) => {
        var item2 = Object.assign({}, item);
        if (!!item2.YoY && String(item2.YoY).indexOf("%") === -1) {
          // item2.YoY += "%";
        }
        if (!!item.QoQ && String(item2.QoQ).indexOf("%") === -1) {
          // item2.QoQ += "%";
        }
        resultData.push(item2);
      });
      // console.log(resultTableData);

      layui.use("table", function () {
        // if (way == scenicSearchWay) {
        //   statsWay = scenicStatWay;
        // }
        if (way == "stats_way" && !!data && !data.arr[0]) {
        //   avg_operate_time.update({ disabled: false }); // 可以使用运营时长下拉框
        // }
        // if (!!data && !!data.arr[0] && data.arr[0].name == scenicStat) {
        //   // 如果选择了按景区统计
        //   robotIdWay = null;
        //   avg_operate_time.update({ disabled: false }); // 可以使用运营时长下拉框
        // }
        // if (
        //     (!!data && !!data.arr[0] && data.arr[0].name == robotStat) ||
        //     robotIdWay == robotStat
        // ) {
        //   // var newDates = date_way.options.data.filter((item) => {
        //   //   return item.name === checkMonth;
        //   // });
        //   // console.log(newDates);
        //   // 如果选择的是按机器人统计，那么只显示运营时长，隐藏统计报表、导出报表
        //
        //   avg_operate_time.update({
        //     data: [
        //       { name: avgOpeTime, value: 1 },
        //       { name: opeTime, value: 2, selected: true },
        //     ],
        //   }); // 更换为月份
        //
        //   avg_operate_time.update({ disabled: true }); // 禁止使用运营时长下拉框
          // 如果按机器人统计
          robotIdWay = robotStat;
          // 处理一下后端返回的数据(有scenicName字段)
          var sceResultData = resultData;

          /* 2021.08.19 删除 start */
          // // 禁用统计报表
          // $("#stats_table").addClass("layui-btn-disabled");
          // $("#stats_table").attr("disabled", true);
          //
          // // 禁用导出报表
          // $(".export_report").addClass("layui-btn-disabled");
          // // 删除a标签的点击事件
          // document
          //     .querySelector(".export_report")
          //     .removeEventListener("click", exportReport);
          // // 阻止a标签默认事件
          // document
          //     .querySelector(".export_report")
          //     .addEventListener("click", preventA);
          /* 2021.08.19 删除 end */

          // avgOpeTimes = []; // 平均运营时长列表
          // opeTimes = []; // 运营时长列表
          // resultData.forEach((item) => {
          //   avgOpeTimes.push(item.avgOperateTime);
          //   opeTimes.push(item.avgOperateTime);
          // });

          // console.log(avgOpeTimes);
          // console.log(opeTimes);

          // 处理一下后端返回的数据(没有scenicName字段)
          var noSceResultData = [];
          sceResultData.forEach((item) => {
            var item2 = Object.assign({}, item); // 把每个item对象都深拷贝一下
            noSceResultData.push(item2); // 然后push到新的数组里面去
          });
          noSceResultData.forEach((item) => {
            // delete item.scenicName;
          });
          // console.log(noSceResultData);

          // 处理一下后端返回的表头(有scenicName字段)
          var sceResultCols = [];

          resultCols.forEach((item) => {
            var obj = {
              field: item.field, // 字段
              title: item.title, // 标题
              sort: item.sort, // 是否排序
              align: "center", // 表格文字居中
            };
            sceResultCols.push(obj);
          });
          // console.log(sceResultCols);

          sceResultCols.forEach((item) => {
            // 让avgOperateTime自动修改
            if (item.field == "avgOperateTime") {
              // console.log(operateTimeName(way, data));
              item.title = opeTime;
            }
          });

          // 处理一下后端返回的表头
          var noSceResultCols = sceResultCols.concat();
          // noSceResultCols.splice(3, 1); // 不显示平均单台运营时长

          // console.log(sceResultData);
        } else {
          // 否则
          /* 2021.08.19 删除 start */
          // // 启用统计报表
          // $("#stats_table").removeClass("layui-btn-disabled");
          // $("#stats_table").attr("disabled", false);
          //
          // // 启用导出报表
          // $(".export_report").removeClass("layui-btn-disabled");
          // // 添加a标签的点击事件
          // document
          //     .querySelector(".export_report")
          //     .addEventListener("click", exportReport);
          // // 允许a标签默认事件
          // document
          //     .querySelector(".export_report")
          //     .removeEventListener("click", preventA);
          /* 2021.08.19 删除 end */

          // 处理一下后端返回的数据(有scenicName字段)
          var sceResultData = resultData;

          avgOpeTimes = []; // 平均运营时长列表
          opeTimes = []; // 运营时长列表
          resultData.forEach((item) => {
            avgOpeTimes.push(item.avgOperateTime);
            opeTimes.push(item.avgOperateTime);
          });
          // console.log(avgOpeTimes);
          // console.log(opeTimes);

          // 处理一下后端返回的数据(没有scenicName字段)
          var noSceResultData = [];
          sceResultData.forEach((item) => {
            var item2 = Object.assign({}, item); // 把每个item对象都深拷贝一下
            noSceResultData.push(item2); // 然后push到新的数组里面去
          });
          noSceResultData.forEach((item) => {
            // delete item.scenicName;
          });
          // console.log(noSceResultData);

          // 处理一下后端返回的表头(有scenicName字段)

          var newResultCols = resultCols;
          // console.log(newResultCols);

          var sceResultCols = [];
          // console.log(newResultCols);
          newResultCols.forEach((item) => {
            var obj = {
              field: item.field, // 字段
              title: item.title, // 标题
              sort: item.sort, // 是否排序
              align: "center", // 表格文字居中
            };
            sceResultCols.push(obj);
          });

          sceResultCols.forEach((item) => {
            // 让avgOperateTime自动修改
            if (item.field == "avgOperateTime") {
              // console.log(operateTimeName(way, data));
              item.title = operateTimeName(way, data);
            }
          });

          // 处理一下后端返回的表头(没有scenicName字段)
          var noSceResultCols = sceResultCols.concat();
          noSceResultCols.splice(0); // 使用splice(0)才能显示最后一列
        }

        // 如果方式为stats_way，并且还不是按景区统计，则执行以下逻辑
        if (
            way == "stats_way" &&
            data &&
            data.arr[0] &&
            data.arr[0].name !== scenicStat
        ) {
          // 把按景区统计的标识记为null
          // statsWay = null;
        }

        if (
            (!!data && !!data.arr[0] && data.arr[0].name === scenicStat) ||
            (way == avgOpeTimeWay && statsWay == scenicStatWay)
        ) {
          // 显示景区名称字段
          statsWay = scenicStatWay;

          tableData = sceResultData;
          tableCols = sceResultCols;
        } else {
          // // 不显示景区名称字段
          // tableData = noSceResultData;
          // tableCols = noSceResultCols;
          tableData = sceResultData;
          tableCols = sceResultCols;
        }
        /**2021-06-10 增加 start */
        // console.log(dateGapWay);
        // date_way.setValue([{ name: checkDay, value: 1 }]); // 赋值日份
        // date_way.update({
        //   data: [
        //     { name: checkDay, value: 1, selected: true },
        //     { name: checkMonth, value: 2 },
        //     { name: checkYear, value: 3 },
        //   ],
        // }); // 更换为月份
        /**2021-06-10 增加 end */

        // dateGapWay = checkMonth; // 默认即月份
        // way == checkDateWay && data && data.arr[0] // 2021-06-10修改
        if (
            (way == checkDateWay && data && data.arr[0]) ||
            (!!dateGapWay && data && data.arr[0])
        ) {
          // console.log(data.arr[0].name);
        }

        /** 2021-06-05 新增 start */
        if (!!data && !!data.arr[0] && data.arr[0].name == robotStat) {
          restoreTable2();
        } else {
          clickflag = false;
          showOpeTiem();
        }
        /** 2021-06-05 新增 end */

        /****************************echarts图表操作 start**************************** */
        // console.log(avgOpeTimes);
        // console.log(tableData);
        if (
            (check_scenic_way == "1" && stats_option_way == "3") ||
            (check_scenic_way == "1" && stats_option_way == "1")
        ) {
          // 上报原因大类 || 上报原因
          var reportReasonClasses = tableData.map((item) => {
            return item.reportReasonClass || item.reportReason;
          });
          // console.log(reportReasonClasses);
          legendData = ["上报次数", "上报次数/订单总数"];
          opeBugChartOption.xAxis[0].data = reportReasonClasses;
          opeBugChartOption.legend.data = legendData;

          var optionSeries = opeBugChartOption.series;

          // 上报次数
          var reportNums = tableData.map((item) => {
            // console.log(item);
            return item.reportNum;
          });

          // 上报次数/订单总数
          var reportTotals = tableData.map((item) => {
            return item.reportTotal;
          });
          for (var i = 0; i < optionSeries.length; i++) {
            optionSeries[i].name = legendData[i];
            if (optionSeries[i].name == "上报次数") {
              optionSeries[i].data = reportNums;
            }
            if (optionSeries[i].name == "上报次数/订单总数") {
              optionSeries[i].data = reportTotals;
            }
          }
        } else if (check_scenic_way == "1" && stats_option_way == "2") {
          // 处理结果
          var handleResults = tableData.map((item) => {
            return item.handleResult;
          });
          // console.log(reportReasonClasses);
          legendData = ["处理次数", "处理次数/订单总数"];
          opeBugChartOption.xAxis[0].data = handleResults;
          opeBugChartOption.legend.data = legendData;

          var optionSeries = opeBugChartOption.series;

          // 处理次数
          var handleNums = tableData.map((item) => {
            // console.log(item);
            return item.handleNum;
          });

          // 处理次数/订单总数
          var handleTotals = tableData.map((item) => {
            return item.handleTotal;
          });
          for (var i = 0; i < optionSeries.length; i++) {
            optionSeries[i].name = legendData[i];
            if (optionSeries[i].name == "处理次数") {
              optionSeries[i].data = handleNums;
            }
            if (optionSeries[i].name == "处理次数/订单总数") {
              optionSeries[i].data = handleTotals;
            }
          }
        } else if (
            (check_scenic_way == "2" && stats_option_way == "3") ||
            (check_scenic_way == "1" && stats_option_way == "1")
        ) {
          // 上报原因大类 || 上报原因
          var reportResultClasses = tableData.map((item) => {
            return item.reportResultClass;
          });
          // console.log(reportReasonClasses);
          legendData = ["上报次数", "该景区上报次数/平台交易总笔数"];
          opeBugChartOption.xAxis[0].data = reportResultClasses;
          opeBugChartOption.legend.data = legendData;

          var optionSeries = opeBugChartOption.series;

          // 上报次数
          var reportNums = tableData.map((item) => {
            // console.log(item);
            return item.reportNum;
          });

          // 该景区上报次数/平台交易总笔数
          var tradeTotals = tableData.map((item) => {
            return item.tradeTotal;
          });
          for (var i = 0; i < optionSeries.length; i++) {
            optionSeries[i].name = legendData[i];
            if (optionSeries[i].name == "上报次数") {
              optionSeries[i].data = reportNums;
            }
            if (optionSeries[i].name == "该景区上报次数/平台交易总笔数") {
              optionSeries[i].data = tradeTotals;
            }
          }
        } else if (check_scenic_way == "2" && stats_option_way == "2") {
          // 处理结果
          var handleResults = tableData.map((item) => {
            return item.handleResult;
          });
          // console.log(handleResults);
          legendData = ["处理结果", "处理次数/处理总次数"];
          opeBugChartOption.xAxis[0].data = handleResults;
          opeBugChartOption.legend.data = legendData;

          var optionSeries = opeBugChartOption.series;

          // 处理次数
          var handleNums = tableData.map((item) => {
            // console.log(item);
            return item.handleNum;
          });

          // 处理次数/处理总次数
          var handleTotals = tableData.map((item) => {
            return item.handleTotal;
          });
          // console.log(handleTotal);

          for (var i = 0; i < optionSeries.length; i++) {
            optionSeries[i].name = legendData[i];
            if (optionSeries[i].name == "处理次数") {
              optionSeries[i].data = handleNums;
            }
            if (optionSeries[i].name == "处理次数/处理总次数") {
              optionSeries[i].data = handleTotals;
            }
          }
        } else if (
            (check_scenic_way == "1" && stats_option_way == "4") ||
            (check_scenic_way == "2" && stats_option_way == "4")
        ) {
          // 故障排序前五
          function bugSortTop5() {
            var reportReasonObj = {}; // 上报原因对象(包含哪些故障原因)
            var reportReasonList = []; // 上报原因列表
            var reasonResultList = []; // 原因结果列表(已经排好序)
            tableData.forEach((item) => {
              reportReasonList.push(item);
              reportReasonObj[item.reportReason] = reportReasonList;
            });

            for (var i in reportReasonObj) {
              reportReasonObj[i].sort(function (a, b) {
                return b.reportNum - a.reportNum;
              });

              var list = reportReasonObj[i].filter((item, index) => {
                return index < 5;
              });
              reportReasonObj[i] = list;

              reportLists = reportReasonObj[i].concat();
            }
            tableData = reportLists;
          }
          // bugSortTop5(); // 因前端拿到的数据有限(一次只能拿到10条数据，所以排序还是得交给后端从数据库进行处理)

          // 上报原因
          var reportReasons = tableData.map((item) => {
            return item.reportReason;
          });
          console.log(reportReasons);
          legendData = ["处理结果", "处理次数/处理总次数"];
          opeBugChartOption.xAxis[0].data = reportReasons;
          opeBugChartOption.legend.data = legendData;

          var optionSeries = opeBugChartOption.series;

          // 该景区上报次数
          var reportNums = tableData.map((item) => {
            // console.log(item);
            return item.reportNum;
          });

          // 该景区上报次数/平台交易总笔数
          var tradeTotals = tableData.map((item) => {
            return item.tradeTotal;
          });
          // console.log(tradeTotals);

          for (var i = 0; i < optionSeries.length; i++) {
            optionSeries[i].name = legendData[i];
            if (optionSeries[i].name == "上报次数") {
              optionSeries[i].data = reportNums;
            }
            if (optionSeries[i].name == "该景区上报次数/平台交易总笔数") {
              optionSeries[i].data = tradeTotals;
            }
          }
        }
        // 重绘echarts图
        opeBugChart.setOption(opeBugChartOption);

        // console.log(tableCols)
        if(JSON.stringify(tableData[0]) === '{}') {
          tableData = []
        }
        initTable(tableData, tableCols);

        /****************************echarts图表操作 end**************************** */
      });
    });
  };
}
// renderTable()(); // 执行函数内的函数(初始表格) // 因为在下面有调用，所以这里暂且不用调用
/****************************分页操作 start**************************** */
layui.use("laypage", function () {
  var laypage = layui.laypage;

  listenData = new Proxy(diffObj, {
    // 代理diffObj
    set(obj, name, value) {
      obj[name] = value;

      //执行一个laypage实例
      laypage.render({
        elem: "page", //注意，这里的 test1 是 ID，不用加 # 号
        curr: currPage,
        limit: 10,
        count, //数据总数，从服务端得到
        layout: ["count", "prev", "page", "skip"],
        jump: function (obj, first) {
          //obj包含了当前分页的所有参数，比如：
          currPage = obj.curr;

          //首次不执行
          if (!first) {
            //do something
            renderTable()();
            // initTable(tableData, tableCols);
          }
        },
        // curr:
        //   location.hash.indexOf("#!") == -1
        //     ? ""
        //     : location.hash
        //         .substr(location.hash.indexOf("#!"))
        //         .replace("#!page=", ""), //获取hash值为page的当前页
        // hash: "page",
      });
    },
  });
});
/****************************分页操作 end**************************** */

/******************************************************************* */

// 请选择统计方式
var check_scenic = null;
function checkScenic() {
  check_scenic = xmSelect.render({
    el: "#check_scenic",
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
      { name: "所有景区", value: 1, selected: true },
      { name: "区分景区", value: 2 },
    ],
    on: renderTable("stats_way"),
  });
}
checkScenic();

/**
 * 时间统计方式
 */
var stats_option = null;
function statsOption() {
  stats_option = xmSelect.render({
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
      { name: "上报原因", value: 1 },
      { name: "上报原因大类", value: 3, selected: true },
      { name: "上报原因--按景区排名前五", value: 4 },
    ],
    // on: function (data) {
    //   // console.log(data);
    //   // console.log(tableData);
    //   // 调接口，重绘表格

    //   // 模拟后端接口返回的数据
    //   // 重新绘制表格
    //   tableData = [

    //   ]
    // },
    on: renderTable("check_date"),
  });
}
statsOption();

// 删除数组指定元素的方法
Array.prototype.remove = function (val) {
  let index = this.indexOf(val);
  if (index > -1) {
    this.splice(index, 1);
  }
};

/**
 * 显示起止时间日份，隐藏起止时间月份、年份
 */
function showStartEndDay() {
  $(".start_end_time_day").css({
    display: "flex",
  });
  $(".start_end_time_month").css({
    display: "none",
  });
  $(".start_end_time_year").css({
    display: "none",
  });
}

layui.config({
  base: "./../layuinotice/dist/",
});
// 下拉选择日期功能
layui.use(["laydate", "notice"], function () {
  var laydate = layui.laydate;
  var notice = layui.notice; // 允许别名 toastr

  // 初始化配置，同一样式只需要配置一次，非必须初始化，有默认配置
  notice.options = {
    closeButton: true, //显示关闭按钮
    debug: false, //启用debug
    positionClass: "toast-top-center", //弹出的位置,
    showDuration: "300", //显示的时间
    hideDuration: "1000", //消失的时间
    timeOut: "2000", //停留的时间
    extendedTimeOut: "1000", //控制时间
    showEasing: "swing", //显示时的动画缓冲方式
    hideEasing: "linear", //消失时的动画缓冲方式
    iconClass: "toast-info", // 自定义图标，有内置，如不需要则传空 支持layui内置图标/自定义iconfont类名
    onclick: null, // 点击关闭回调
  };

  startTimeDay = formatTime();
  endTimeDay = formatTime();

  // console.log(startTime, endTime);

  laydate.render({
    elem: "#start_time_day",
    max: startTimeDay, // 最大可选择的日期
    value: startTimeDay, // 初始赋值
    type: "date", // 选择日历类型（年、月、日）
    showBottom: false, // 不出现底部栏
    done: function (value, date) {
      // 日期选中后的回调
      var timestamp = new Date(value.replace(/-/g, "/")).getTime();
      var currtime = new Date().getTime();
      // console.log(timestamp, currtime);
      if (timestamp > currtime) {
        // 选择的时间比当前时间大
        notice.warning("选择的日期不能比当前日期大");
        $("#start_time_day").val(startTimeDay);
        return;
      }
      if (value > endTimeDay) {
        // 开始日期不能比结束日期晚
        notice.warning("开始日期不能比结束日期晚");
        $("#start_time_day").val(startTimeDay);
        return;
      }
      startTimeDay = value;
    },
  });

  laydate.render({
    elem: "#end_time_day",
    max: endTimeDay, // 最大选择的日期
    value: endTimeDay, // 初始赋值
    type: "date", // 选择日历类型（年、月、日）
    showBottom: false, // 不出现底部栏
    done: function (value, date) {
      // 日期选中后的回调
      var timestamp = new Date(value.replace(/-/g, "/")).getTime();
      var currtime = new Date().getTime();
      // console.log(timestamp, currtime);
      if (timestamp > currtime) {
        // 选择的时间比当前时间大
        notice.warning("选择的日期不能比当前日期大");
        $("#end_time_day").val(endTimeDay);
        return;
      }
      if (value < startTimeDay) {
        // 结束日期不能比开始日期早
        notice.warning("结束日期不能比开始日期早");
        $("#end_time_day").val(endTimeDay);
        return;
      }
      endTimeDay = value;
    },
  });
});

/** 2021-06-05 修改 start */
/**
 * 还原表格的方法
 */
function restoreTable() {
  $(".operate_time_content").width("200%");

  // 折线柱状图display变为block
  $("#opeBugEcharts").css({
    display: "none",
    position: "absolute",
    left: 0,
    top: 0,
  });
  $("#opeBugEcharts").css("left", "50vw");

  statsWay = scenicStatWay;
  renderTable()();
}

function restoreTable2() {
  $(".operate_time_content").width("200%");

  // 折线柱状图display变为block
  $("#opeBugEcharts").css({
    display: "none",
    position: "absolute",
    left: 0,
    top: 0,
  });

  $("#opeBugEcharts").css("left", "50vw");

  statsWay = scenicStatWay;
  initTable(tableData, tableCols);
}

/**
 * 恢复按钮的函数
 */
function restoreBtn() {
  // 启用统计报表
  $("#stats_table").removeClass("layui-btn-disabled");
  $("#stats_table").attr("disabled", false);

  // 启用导出报表
  $(".export_report").removeClass("layui-btn-disabled");
  // 添加a标签的点击事件
  document
      .querySelector(".export_report")
      .addEventListener("click", exportReport);
  // 允许a标签默认事件
  document
      .querySelector(".export_report")
      .removeEventListener("click", preventA);
}

/**
 * 重置表格的函数
 */
function resetTable() {
  check_scenic.setValue([]);
  checkScenic();
  stats_option.setValue([]);
  statsOption(); // 重置回月份
  startTime.setValue([]);
  // removeAll();
  statsWay = null;
  operateTable = null;

  if ($("#layui-laypage-1 a")[1].dataset.page == 1) {
    $("#layui-laypage-1 a")[1].click();
  }

  restoreTable();

  var timer1 = null;
  var timer2 = null;

  clearTimeout(timer1, timer2);
  timer1 = setTimeout(() => {
    robotIdWay = null;
    restoreBtn();
  });
  timer2 = setTimeout(() => {
    robotIdWay = null;
    restoreBtn();
  }, 200);
}

// 重置表格
$("#reset").on("click", function (e) {
  // resetTable();
  location.reload();
});

// 统计报表
var clickflag = false; // 统计报表是否可以点击
$("#stats_table").on("click", function (e) {
  if (!clickflag) {
    $(".operate_time_content").width("100%");
    $("#opeBugEcharts").css("left", "0");

    initTable(tableData, tableCols); // 重新加载表格，使其样式正常显示
    clickflag = true;
  }
});

/**
 * 显示柱状折线图
 */
function showLineAndBar() {
  // 运营同比图display变为none
  $("#opeBugEcharts").css({
    display: "none",
    position: "absolute",
    left: 0,
    top: 0,
  });
}

/**
 * 显示运营同比图
 */
function showOpeTiem() {
  // 折现柱状图display变为block
  $("#opeBugEcharts").css({
    display: "flex",
    position: "relative",
    left: 0,
    top: 0,
  });
}
/** 2021-06-05 修改 end */

// 异步修改样式才能生效
setTimeout(() => {
  restoreTable();
});

/**
 * 导出报表的函数
 * @param {*} e event对象
 */
function exportReport(e) {
  // e.preventDefault();

  var opeBugPicInfo = opeBugChart.getDataURL();
  $(".export_report").attr("href", opeBugPicInfo);
}
// 导出报表
document
    .querySelector(".export_report")
    .addEventListener("click", exportReport);

// 查询数据
$("#query_data").on("click", function () {
  if (
      $("#layui-laypage-1 a")[1] &&
      $("#layui-laypage-1 a")[1].dataset.page == 1
  ) {
    $("#layui-laypage-1 a")[1].click();
  } // 跳到第一页
  clickflag = false;
  clickQueryflag = true;
  restoreTable();
});

// 导出数据
$("#export_data").on("click", async function () {
  var exportData = tableData.concat(); // 数组深拷贝
  var obj = {};
  var objFields = [];

  exportData.forEach((item) => {
    delete item.LAY_TABLE_INDEX; // 删除数组中多余字段LAY_TABLE_INDEX
  });
  tableCols.forEach((item) => {
    objFields.push(item.field);
    obj[item.field] = item.title;
  });

  clickflag = false;
  clickQueryflag = true;

  var startTime = "";
  var endTime = "";
  var showQoQ = "";
  var chooseDate = "1"; // 1代表月份、2代表日份、3代表年份

  if (check_scenic_way == "所有景区") {
    check_scenic_way = "1";
  } else if (check_scenic_way == "区分景区") {
    check_scenic_way = "2";
  }

  if (stats_option_way == "上报原因") {
    stats_option_way = "1";
  } else if (stats_option_way == "处理结果") {
    stats_option_way = "2";
  } else if (stats_option_way == "上报原因大类") {
    stats_option_way = "3";
  } else if (stats_option_way == "上报原因--按景区排名前五") {
    stats_option_way = "4";
  }

  // console.log(chooseDate);
  const { data: resdata } = await axios.get(
      "/system/bugStatus/getBugStatusList",
      {
        params: {
          currPage: 1, // 当前页数
          // startToEndTime, // 起止时间
          startTime: startTimeDay, // 起始时间
          endTime: endTimeDay, // 终止时间
          checkScenicWay : check_scenic_way, // 选择景区方式
          statsOptionWay : stats_option_way, // 统计项方式
          currPage : 1,
          pageSize : 10000
        },
      }
  );
  // console.log(resdata.data);
  // console.log(obj);

  var resultDataList = resdata.data;
  var newResultDataList = [];

  // console.log(objFields);

  resultDataList.filter((item) => {
    // console.log(item);
    var obj = {};
    objFields.forEach((item2) => {
      obj[item2] = item[item2];
    });
    newResultDataList.push(obj);
  });

  // exportData.unshift(obj); // 在exportData前插入
  newResultDataList.unshift(obj); // 在newResultDataList前插入
  LAY_EXCEL.exportExcel(newResultDataList, "表格导出.xlsx", "xlsx");

  // exportData.unshift(obj); // 在exportData前插入
  // // console.log(exportData);
  // LAY_EXCEL.exportExcel(exportData, "表格导出.xlsx", "xlsx");
});

// 阻止a标签的函数
function preventA(e) {
  e.preventDefault();
}

// ************************年平均运营时长同比图**************************

// ************************年平均运营时长同比图**************************
