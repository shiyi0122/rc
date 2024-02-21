layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#dispatchLogList',
        url : '/system/dispatchLog/getSysDepositLogList',
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
        id : "dispatchLogListTable",
        cols : [[
            {field: 'robotDispatchUsersName', title: '调度人名称', minWidth:100, align:"center"},
            {field: 'robotDispatchCode', title: '机器人编号', minWidth:100, align:"center"},
            {field: 'robotDispatchCallOutName', title: '调出景区', minWidth:100, align:"center"},
            {field: 'robotDispatchTransferInName', title: '调入景区', minWidth:100, align:"center"},
            {field: 'createDate', title: '创建时间', minWidth:100, align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("dispatchLogListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                robotDispatchCallOutName: $(".robotDispatchCallOutNameVal").val(),
                robotDispatchCode: $(".robotDispatchCodeVal").val(),
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
        window.resources("DISPATCH_LOG_DOWNLOADEXCEL", function (e) {
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
        var robotDispatchCodeVal = $(".robotDispatchCodeVal").val();
        var startTime = $(".startTime").val();
        var endTime = $(".endTime").val();
        window.location.href = "/system/dispatchLog/uploadExcelDispatchtLog?robotDispatchCode=" + robotDispatchCodeVal + "&startTime=" + startTime + "&endTime=" +endTime;
    }

})
