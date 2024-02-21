layui.use("table", function () {
  var table = layui.table;

  table.render({
    elem: '#test',
    url : '/system/operationBigData/getMonthOperateData',
    cellMinWidth : 100,
    page : true,
    autoSort: false, //禁用前端自动排序
    height: 250,
    request: {
      pageName: 'pageNum', //页码的参数名称，默认：pageNum
      limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
    },
    response:{
      statusName: 'code', //数据状态的字段名称，默认：code
      statusCode: 200, //成功的状态码，默认：0
      countName: 'totals', //数据总数的字段名称，默认：count
      dataName: 'list' //数据列表的字段名称，默认：data
    },
    id : "monthOperateDataListTable",
    cols : [[
      {field: 'province', title: '省份', minWidth:100, align:"center"},
      {field: 'operationTime', title: '运营时长/分钟', minWidth:100, align:"center",sort: true},
      {field: 'orderAmount', title: '订单金额/元', minWidth:100, align:"center",sort: true},
      {field: 'completionRatio', title: '目标完成比例', minWidth:100, align:"center",sort: true},
      {field: 'robotUtilization', title: '机器人利用率/%', minWidth:100, align:"center",sort: true},
      {field: 'failureRate', title: '订单故障率', minWidth:100, align:"center",sort: true},
      {field: 'unitPrice', title: '客单价/元', minWidth:100, align:"center",sort: true},
      {field: 'robotOutputValue', title: '单机器人产值/元', minWidth:100, align:"center",sort: true}
    ]]
  });

  /**
   * 排序操作
   */
  //注：sort 是工具条事件名，operate_table 是 table 原始容器的属性 lay-filter="对应的值"
  table.on("sort(test)", function (obj) {
    table.reload('monthOperateDataListTable', {
      initSort: obj,
      where: { //请求参数（注意：这里面的参数可任意定义，并非下面固定的格式）
        field: obj.field,
        type: obj.type //排序方式
      }
    })
  });
});
