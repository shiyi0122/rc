layui.use(['form','layer','table','laytpl',"upload"],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#unusualTimeList',
        url : '/system/sysRobotUnusualTime/getSysRobotUnusualTimeList',
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
        id : "unusualTimeListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'forbiddenTime', title: '禁区异常监控时长(分钟)', minWidth:100, align:"center"},
            {field: 'temporaryLacking', title: '临时锁定监控时长(分钟)', minWidth:100, align:"center"},
            {field: 'receivingOrders', title: '长时间未接单监控时长(分钟)', minWidth:100, align:"center"},
            {field: 'padAppUsee', title: 'PadApp未启动异常(分钟)', minWidth:100, align:"center"},
            {field: 'orderAbnormalTime', title: '订单超长时间警告(分钟)', minWidth:100, align:"center"},
            {field: 'switchOnOff', title: '机器人开关机监控时长(分钟)', minWidth:100, align:"center"},
            {field: 'chargeStatus', title: '充电状态监控时长(分钟)', minWidth:100, align:"center"},
            {field: 'saturationLow', title: '饱和度范围(低)', minWidth:100, align:"center"},
            {field: 'saturationHigh', title: '饱和度范围(高)', minWidth:100, align:"center"},
            {field: 'saturationTime', title: '饱和度持续时间(分钟)', minWidth:100, align:"center"},
            {field: 'saturationTime', title: '饱和度持续时间(分钟)', minWidth:100, align:"center"},
            {field: 'batteryReminder', title: '最低电量界限', minWidth:100, align:"center"},
            {field: 'onOffStatus', title: '机器人开关机判断时间', minWidth:100, align:"center"},
            {field: 'orderState', title: '超长订单轮询状态', minWidth:100, align:"center",templet:function(d){
                            if(d.orderState == "1"){
                                return "启用";
                            }else if(d.orderState == "2"){
                                return "禁用";
                            }
                        }},

            // {field: 'parkingType', title: '状态', minWidth:100, align:"center",templet:function(d){
            //         if(d.parkingType == "1"){
            //             return "启用";
            //         }else if(d.parkingType == "0"){
            //             return "禁用";
            //         }
            //     }},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {field: 'updateDate', title: '修改时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#unusualTimeListBar',fixed:"right",align:"center"}
        ]]
    });


    /**
     * 根据用户手机号模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("unusualTimeListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                sysScenicSpotId: $(".scenicSpotId").val(),
            }
        })
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })





    //列表操作
    table.on('tool(unusualTimeList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYS_ROBOT_UNUSUAL_TIME", function (e) {
                if (e.state == "200") {
                    openEditUnusualTime(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYS_ROBOT_UNUSUAL_TIME", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/sysRobotUnusualTime/delSysRobotUnusualTime", {id : data.id}, function (data) {}, false,"GET","unusualTimeListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddInnercircle').click(function(){
        window.resources("SYS_ROBOT_UNUSUAL_TIME", function (e) {
            if (e.state == "200") {
                openAddunusualTime();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddunusualTime() {
        layer.open({
            type : 2,
            title: '添加异常监控时间',
            offset: '10%',
            area: ['800px', '500px'], //宽高
            content: '/page/system/sysRobotUnusualTime/html/unusualTimeAdd.html',
            tableId: '#unusualTimeList',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                $.ajax({
                    type : 'POST',
                    url : '/system/scenicSpot/getCurrentScenicSpot',
                    dataType : 'json',
                    success:function (data) {
                        if (data.state == "200"){
                            // body.find(".sysScenicSpotId").val(data.data.scenicSpotId);//景区ID
                            // body.find(".scenicSpotName").val(data.data.scenicSpotName);//景区名称
                            form.render();
                        }else if(data.state == "400"){
                            layer.msg(data.msg);
                        }
                    }
                })
            }
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditUnusualTime(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改异常监控时间',
                offset: '10%',
                area: ['800px', '500px'], //宽高
                content: '/page/system/sysRobotUnusualTime/html/unusualTimeEdit.html',
                tableId: '#unusualTimeList',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                                if(edit){
                                    top.layer.close(dex);
                                    body.find(".sysScenicSpotId").val(edit.sysScenicSpotId);//景区ID
                                    body.find(".scenicSpotName").val(edit.scenicSpotName);//景区名称
                                    body.find(".id").val(edit.id);
                                    body.find(".forbiddenTime").val(edit.forbiddenTime);
                                    body.find(".temporaryLacking").val(edit.temporaryLacking);
                                    body.find(".receivingOrders").val(edit.receivingOrders);
                                    body.find(".orderAbnormalTime").val(edit.orderAbnormalTime);
                                    body.find(".padAppUsee").val(edit.padAppUsee);
                                    body.find(".switchOnOff").val(edit.switchOnOff);
                                    body.find(".chargeStatus").val(edit.chargeStatus);
                                    body.find(".saturationLow").val(edit.saturationLow);
                                    body.find(".saturationHigh").val(edit.saturationHigh);
                                    body.find(".saturationTime").val(edit.saturationTime);
                                    // body.find(".orderState").val(edit.orderState);
                                    body.find(".batteryReminder").val(edit.batteryReminder);
                                    body.find(".onOffStatus").val(edit.onOffStatus);
                                    form.render();
                                }
                }
            });
        },500);
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
