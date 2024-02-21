layui.use(['form','layer','table','laytpl',"upload"],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#unusualLogList',
        url : '/system/sysRobotUnusualLog/getSysRobotUnusualLogList',
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
            dataName: 'data' //数据列表的字段名称，默认：data
        },
        id : "unusualLogListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'robotCode', title: '机器人编号', minWidth:100, align:"center"},
            {field: 'appProcessorName', title: '管理者app处理人姓名', minWidth:100, align:"center"},
            {field: 'backstageProcessorName', title: '后台处理人姓名', minWidth:100, align:"center"},
            {field: 'unusualTime', title: '异常时间(分钟)', minWidth:100, align:"center"},
            {field: 'unusualType', title: '异常类型', minWidth:100, align:"center",templet:function(d){
                    if(d.unusualType == "1"){
                        return "禁区异常";
                    }else if(d.unusualType == "2"){
                        return "临时锁定异常";
                    }else if (d.unusualType == "3"){
                        return "机器人接单异常"
                    }else if (d.unusualType == "4"){
                        return "PadApp未启动异常"
                    }else if (d.unusualType == "5"){
                        return "开关机状态异常"
                    }else if (d.unusualType == "6"){
                        return "机器人充电异常"
                    }else if (d.unusualType == "7"){
                        return "景区饱和度异常"
                    }else if (d.unusualType == "8"){
                        return "超长订单警告"
                    }
                }},
            {field: 'status', title: '处理状态', minWidth:100, align:"center",templet:function(d){
                    if(d.status == "1"){
                        return "未处理";
                    }else if(d.status == "2"){
                        return "已查看";
                    }else if (d.status == "3"){
                        return "已处理"
                    }
                }},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {field: 'updateDate', title: '修改时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#unusualLogListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("unusualLogListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                scenicSpotId: $(".scenicSpotId").val(),
                robotCode: $(".robotCode").val(),
                unusualType: $(".unusualType").val(),
                startTime: $(".startTime").val(),
                endTime: $(".endTime").val()
            }
        })
    });

    //列表操作
    table.on('tool(unusualLogList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYS_ROBOT_UNUSUAL_LOG", function (e) {
                if (e.state == "200") {
                    openEditUnusualTime(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })

    $('#editType').click(function(){
        window.resources("SYS_ROBOT_UNUSUAL_LOG", function (e) {
            if (e.state == "200") {
                oneClickProcessing();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })




    /**
     * 弹出修改框
     * @param edit
     */
    function openEditUnusualTime(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改异常监控状态',
                offset: '10%',
                area: ['800px', '280px'], //宽高
                content: '/page/system/sysRobotUnusualLog/html/unusualLogEdit.html',
                tableId: '#unusualLogList',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    $.ajax({
                        type : 'POST',
                        url : '/system/scenicSpot/getCurrentScenicSpot',
                        dataType : 'json',
                        success:function (data) {
                            if (data.state == "200"){
                                if(edit){
                                    top.layer.close(dex);
                                    body.find(".sysScenicSpotId").val(edit.sysScenicSpotId);//景区ID
                                    body.find(".id").val(edit.id);
                                    body.find(".status").val(edit.status);
                                    form.render();
                                }
                            }else if(data.state == "400"){
                                layer.msg(data.msg);
                            }
                        }
                    })
                }
            });
        },500);
    }

    /**
     * 一键处理
     */
    function oneClickProcessing(){
        // var robotWakeupWords = $(".robotWakeupWords").val();
        // window.location.href = "/system/sysRobotUnusualLog/oneClickProcessing?scenicSpotId="+scenicSpotId;
        $.ajax({
            type : 'GET',
            url : '/system/sysRobotUnusualLog/oneClickProcessing',
            dataType : 'json',
            data :{
                scenicSpotId : $(".scenicSpotId").val(),
                startTime : $(".startTime").val(),
                endTime : $(".endTime").val()
            },

            success:function (data) {

                if (data.state == "200"){

                    table.reload("unusualLogListTable",{
                        page: {
                            curr: 1 //重新从第 1 页开始
                        },
                        where: {
                            scenicSpotId: $(".scenicSpotId").val(),
                            robotCode: $(".robotCode").val()
                        }
                    })
                    layer.msg("操作成功", {icon: 1,time: 1000,shift: 6});
                }else if(data.state == "400"){
                    layer.msg(data.msg, {icon: 5,time: 1000,shift: 6});
                }
            }
        })

    }



    // $('#downloadExcel').click(function(){
    //     window.resources("SYS_ROBOT_UNUSUAL_TIME", function (e) {
    //         if (e.state == "200") {
    //             downloadExcel();
    //         } else {
    //             layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
    //         }
    //     }, false,"GET");
    // })

    // function downloadExcel(){
    //     window.location.href = "/system/parking/getParkingExcel";
    // }

    // upload.render({
    //     elem: '#importExcel'
    //     ,url: '/system/parking/upParkingExcel'
    //     ,accept: 'file' //普通文件
    //     ,exts: 'xls|xlsx' //只允许上传Excel文件
    //     ,done: function(res){
    //         if (res.state == 200){
    //             setTimeout(function(){
    //                 top.layer.msg(res.msg, {icon: 6});
    //                 layui.table.reload("parkingListTable");
    //             },500);
    //         }else{
    //             setTimeout(function(){
    //                 top.layer.msg(res.msg, {icon: 5,time: 1000,shift: 6});
    //             },500);
    //         }
    //     }
    // });



})
