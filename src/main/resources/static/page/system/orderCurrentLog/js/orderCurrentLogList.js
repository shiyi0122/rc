layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#orderCurrentLogList',
        url : '/system/orderCurrentLog/getOperationLogRefundOrderList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
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
        id : "orderCurrentLogListTable",
        cols : [[
            {field: 'orderNumber', title: '订单编号', minWidth:100, align:"center"},
            {field: 'frontPhone', title: '修改前手机号', minWidth:100, align:"center"},
            {field: 'afterPhone', title: '修改后手机号', minWidth:100, align:"center"},
            {field: 'frontPrice', title: '修改前金额', minWidth:100, align:"center"},
            {field: 'afterPrice', title: '修改后金额', minWidth:100, align:"center"},
            {field: 'createTime', title: '创建时间', minWidth:100, align:"center"},
            {field: 'updateTime', title: '修改时间', minWidth:100, align:"center"},
            {field: 'userName', title: '操作人名称', minWidth:100, align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("orderCurrentLogListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                afterPhone: $(".afterPhoneVal").val(),
                orderNumber: $(".orderNumberVal").val(),
                startTime: $(".startTime").val(),
                endTime: $(".endTime").val()
            }
        })
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })
})