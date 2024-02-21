layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#operationLogRefundOrderList',
        url : '/system/orderLogStored/getOperationLogRefundOrderList',
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
        id : "operationLogRefundOrderListTable",
        cols : [[
            {field: 'orderLogNumber', title: '订单编号', minWidth:100, align:"center"},
            {field: 'orderLogLoginname', title: '操作人账号', minWidth:100, align:"center"},
            {field: 'orderLogUsername', title: '操作人名称', minWidth:100, align:"center"},
            {field: 'orderRobotCode', title: '机器人编号', minWidth:100, align:"center"},
            {field: 'orderLogPhone', title: '客户手机号', minWidth:100, align:"center"},
            {field: 'depositMoney', title: '退款金额', minWidth:100, align:"center"},
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'reasonsRefunds', title: '退款原因', minWidth:100, align:"center"},
            {field: 'orderLogReason', title: '退款状态', minWidth:100, align:"center"},
            {field: 'createDate', title: '创建时间', minWidth:100, align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("operationLogRefundOrderListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                orderLogPhone: $(".orderLogPhoneVal").val(),
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

    /**
     * 点击导出EXCEL表
     */
    $('#downloadExcel').click(function(){
        window.resources("OPERATION_LOG_REFUND_ORDER_DOWNLOADEXCEL", function (e) {
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
        var orderLogPhone = $(".orderLogPhoneVal").val();
        var startTime = $(".startTime").val();
        var endTime = $(".endTime").val();
        window.location.href = "/system/orderLogStored/uploadExcelOrderLog?orderLogPhone=" + orderLogPhone + "&startTime=" + startTime + "&endTime=" +endTime;
    }

})