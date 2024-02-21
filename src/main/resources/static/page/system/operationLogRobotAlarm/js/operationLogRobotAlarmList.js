layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#operationLogRobotAlarmList',
        url : '/system/operationLogRobotAlarm/getOperationLogRobotAlarmList',
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
        id : "operationLogRobotAlarmListTable",
        cols : [[
            {field: 'robotCode', title: '机器人ID', minWidth:100, align:"center"},
            {field: 'robotVersion', title: '机器人版本号', minWidth:100, align:"center"},
            {field: 'sourceType', title: '源包类型',minWidth: 50, align: "center", templet:function (d) {
                    if(d.sourceType == "53" || d.sourceType == null){
                        return "故障";
                    }else if(d.sourceType == "33"){
                        return "心跳";
                    }else if(d.sourceType == "8B"){
                        return "照明";
                    }else if(d.sourceType == "36"){
                        return "解除推行";
                    }else if(d.sourceType == "37"){
                        return "推行";
                    }else if(d.sourceType == "85"){
                        return "开关超声避障";
                    }else if(d.sourceType == "86"){
                        return "开关超声避障-写第二级开始减速阈值";
                    }else if(d.sourceType == "99"){
                        return "开关超声避障-设置第一级开始减速阀值";
                    }else if(d.sourceType == "9B"){
                        return "设置驱动器参数指令";
                    }else if(d.sourceType == "9D"){
                        return "设置始刹车阈值";
                    }else if (d.sourceType == 'F0'){
                        return "远程升级-新老主控和超声";
                    }
                }},
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'robotAlarmInfo', title: '机器人告警信息', minWidth:100, align:"center"},
            {field: 'robotFaultCode', title: '机器人故障码', minWidth:100, align:"center"},
            {field: 'createDate', title: '创建时间', minWidth:100, align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("operationLogRobotAlarmListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                robotCode: $(".robotCodeVal").val(),
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
        window.resources("OPERATION_LOG_ROBOT_ALARM_DOWNLOADEXCEL", function (e) {
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
        var robotCode = $(".robotCodeVal").val();
        var startTime = $(".startTime").val();
        var endTime = $(".endTime").val();
        window.location.href = "/system/operationLogRobotAlarm/uploadExcelRobotLog?robotCode=" + robotCode + "&startTime=" + startTime + "&endTime=" +endTime;
    }

})