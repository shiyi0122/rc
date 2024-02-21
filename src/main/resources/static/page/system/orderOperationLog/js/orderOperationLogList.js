layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#orderOperationLogList',
        url : '/system/orderOperationLog/getOrderOperationLogList',
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
        id : "orderOperationLogListTable",
        cols : [[
            {field: 'operationName', title: '操作人名称', minWidth:100, align:"center"},
            {field: 'operationNumber', title: '订单编号', minWidth:100, align:"center"},
            {field: 'operationPhone', title: '用户手机号', minWidth:100, align:"center"},
            {field: 'operationScenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'operationRobotCode', title: '机器人编号', minWidth:100, align:"center"},
            {field: 'operationTotalTime', title: '使用时长', minWidth:100, align:"center"},
            {field: 'operationFront', title: '修改前', minWidth:100, align:"center"},
            {field: 'operationAfter', title: '修改后', minWidth:100, align:"center"},
            {field: 'createDate', title: '创建时间', minWidth:100, align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("orderOperationLogListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                operationPhone: $(".operationPhoneVal").val(),
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


    //点击导出EXCEL表
    $('#downloadExcel').click(function(){
        window.resources("OPERATION_LOG_VIEW", function (e) {
            if (e.state == "200") {
                downloadExcel();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })


    /**
     * 导出EXCEL表
     */
    function downloadExcel(){

        var operationPhone = $(".operationPhoneVal").val();//客户手机号
        var startTime = $(".startTime").val();//开始时间
        var endTime = $(".endTime").val();//结束时间
        window.location.href = "/system/orderOperationLog/uploadExcelOrderOperationLog?operationPhone="+operationPhone+"&startTime="+startTime+"&endTime="+endTime;

    }

})