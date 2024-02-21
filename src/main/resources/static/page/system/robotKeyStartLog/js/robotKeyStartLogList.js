layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#robotKeyStartLogList',
        url : '/system/keyStart/getSysKeyStartLogList',
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
        id : "robotKeyStartLogTable",
        cols : [[
            {field: 'robotCode', title: '机器人ID', minWidth:100, align:"center"},
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'robotKeyType', title: '启动类型',minWidth: 50, align: "center", templet:function (d) {
                    if(d.robotKeyType == "1" ){
                        return "钥匙启动";
                    }else if(d.robotKeyType == "2"){
                        return "自检启动";
                    }else if(d.robotKeyType == "3"){
                        return "管理者app启动";
                    }
                }},
            {field: 'robotKeyStart', title: '机器人启动时间', minWidth:100, align:"center"},
            {field: 'robotKeyStop', title: '机器人停止时间', minWidth:100, align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("robotKeyStartLogTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                robotCode: $(".robotCodeVal").val(),
                scenicSpotId: $(".scenicSpotId").val(),
                // startTime: $(".startTime").val(),
                // endTime: $(".endTime").val()
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
        window.resources("SYS_ROBOT_KEY_START_LOG", function (e) {
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
        var scenicSpotId = $(".scenicSpotId").val();
        // var startTime = $(".startTime").val();
        // var endTime = $(".endTime").val();
        window.location.href = "/system/keyStart/uploadExcelKeyStartLog?robotCode=" + robotCode + "&scenicSpotId=" + scenicSpotId;
    }

})