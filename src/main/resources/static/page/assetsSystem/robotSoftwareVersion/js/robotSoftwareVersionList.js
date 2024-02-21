layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    table.render({
        elem: '#robotSoftwareVersionList',
        url : '/system/robotSoftwareVersion/getRobotSoftwareVersionList',
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
        id : "robotSoftwareVersionListTable",
        cols : [[
            {field: 'robotCode', title: '机器人编号', minWidth:100, align:"center"},
            {field: 'robotModel', title: '机器人型号', minWidth:100, align:"center"},
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'upgradeModule', title: '升级模块', minWidth:100, align:"center",templet:function(d){
                    if(d.upgradeModule == "1"){
                        return "APP端";
                    }else if(d.upgradeModule == "2"){
                        return "主控";
                    }else if(d.upgradeModule == "3"){
                        return "超声";
                    }
                }},
            {field: 'state', title: '升级状态', minWidth:100, align:"center",templet:function(d){
                    if(d.state == "1"){
                        return "升级成功";
                    }else if(d.state == "2"){
                        return "升级中";
                    }else if(d.state == "3"){
                        return "升级失败";
                    }else if(d.state == null || d.state ==""){
                        return "升级成功";
                    }
                }},
            {field: 'preUpgradeVersion', title: '升级前版本', minWidth:100, align:"center"},
            {field: 'upgradedVersion', title: '升级后版本', minWidth:100, align:"center"},
            {field: 'createDate', title: '升级时间', minWidth:100, align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("robotSoftwareVersionListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                robotCode: $(".robotCode").val(),
                scenicSpotId: $(".scenicSpotId").val(),
                upgradeModule: $(".upgradeModule").val(),
                startTime: $(".startTime").val(),
                endTime: $(".endTime").val(),
                state: $(".softWareState").val()
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
        window.resources("SYS_ROBOT_BELARC_ADVISOR_DOWNLOADEXCEL", function (e) {
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
        var robotCode = $(".robotCode").val();
        var scenicSpotId = $(".scenicSpotId").val();
        var upgradeModule = $(".upgradeModule").val();
        var startTime = $(".startTime").val();
        var endTime = $(".endTime").val()
        window.location.href = "/system/robotSoftwareVersion/uploadExcelRobotSoftwareVersion?robotCode=" + robotCode +"&scenicSpotId=" + scenicSpotId +"&upgradeModule=" + upgradeModule +"&startTime=" + startTime + "&endTime=" + endTime;
    }

})
