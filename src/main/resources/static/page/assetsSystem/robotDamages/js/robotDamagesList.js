layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    table.render({
        elem: '#robotDamagesList',
        url : '/system/robotDamages/getRobotDamagesList',
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
        id : "robotDamagesListTable",
        cols : [[
            {field: 'robotCode', title: '机器人编号', minWidth:100, align:"center"},
            {field: 'phone', title: '用户手机号', minWidth:100, align:"center"},
            {field: 'orderNumber', title: '订单编号', minWidth:100, align:"center"},
            {field: 'damagesType', title: '状态', minWidth:100, align:"center",templet:function(d){
                    if(d.damagesType == "1"){
                        return "未支付";
                    }else if(d.damagesType == "2"){
                        return "已支付";
                    }else if(d.damagesType == "3"){
                        return "已关闭";
                    }else{
                        return "未知"
                    }
                }},
            {field: 'robotOrderNumber', title: '设备使用订单', minWidth:100, align:"center"},
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'lossRater', title: '定损人', minWidth:100, align:"center"},
            {field: 'fixedLossAmount', title: '定损金额', minWidth:100, align:"center",edit: 'text'},
            {field: 'lossTime', title: '定损时间', minWidth:100, align:"center"},
            {field: 'errorRecordsName', title: '故障名称', minWidth:100, align:"center"},
            {field: 'errorRecordsDescription', title: '故障描述', minWidth:100, align:"center"},
            {field: 'paymentPlatform', title: '支付平台', minWidth:100, align:"center",templet:function(d){
                    if(d.paymentPlatform == "1"){
                        return "微信";
                    }else if(d.paymentPlatform == "2"){
                        return "余额";
                    }else if(d.paymentPlatform == "3"){
                        return "管理员代收";
                    }else if(d.paymentPlatform == "4"){
                        return "支付宝";
                    }else{
                        return "未知"
                    }
                }},
            {field: 'actualAmountPaid', title: '实际支付金额', minWidth:100, align:"center"},
            {field: 'actualIncomeAmount', title: '实际收入金额', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#robotDamagesListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("robotDamagesListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                scenicSpotId: $(".scenicSpotId").val(),  //搜索的关键字
                robotCode: $(".robotCode").val(),
                paymentPlatform: $(".paymentPlatform").val(),
                damagesType: $(".damagesType").val(),
                startTime: $(".startTime").val(),
                endTime: $(".endTime").val(),
            }
        })
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })

    //监听单元格编辑
    table.on('edit(robotDamagesList)', function(obj){
        var value = obj.value //得到修改后的值
            ,data = obj.data //得到所在行所有键值
            ,field = obj.field; //得到字段
        window.resources("FINANCE_ORDER_UPDATEORDERSTATE", function (e) {
            if (e.state == "200") {
                window.resource("/system/robotDamages/editfixedLossAmount", data, function (data) {}, false,"POST",'robotDamagesListTable');
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    });

    //列表操作
    table.on('tool(robotDamagesList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === "close") { //修改为关闭
            window.resources("RESOURCES_PARKING_EDITVALID", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定关闭吗？', {icon: 3, title: '提示信息'}, function (index) {
                        var dex = top.layer.msg('数据提交中，请稍候', {icon: 16, time: false, shade: 0.8});
                        $.ajax({
                            url: "/system/robotDamages/closeRobotDamages",
                            data: {
                                damagesId: data.damagesId,
                                damagesType: 3
                            },
                            type: "POST",
                            cache: false,
                            success: function (data) {
                                if (data.state == "200") {
                                    setTimeout(function () {
                                        top.layer.msg(data.msg, {icon: 6});
                                        top.layer.close(dex);
                                        layer.close(index);
                                        layui.table.reload("robotDamagesListTable");
                                    }, 500);
                                } else if (data.state == "400") {
                                    setTimeout(function () {
                                        top.layer.close(dex);
                                        layer.close(index);
                                        top.layer.msg(data.msg, {icon: 5, time: 1000, shift: 6});
                                    }, 500);
                                }
                            }
                        });
                    });
                } else {
                    layer.msg(e.msg, {icon: 5, time: 1000, shift: 6});
                }
            }, false, "GET");
        }
    });
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
        var scenicSpotId = $(".scenicSpotId").val();  //搜索的关键字
        var robotCode = $(".robotCode").val();
        var paymentPlatform = $(".paymentPlatform").val();
        var damagesType = $(".damagesType").val();
        var startTime = $(".startTime").val();
        var endTime = $(".endTime").val();
        window.location.href = "/system/robotDamages/uploadExcelRobotDamages?scenicSpotId=" + scenicSpotId +"&robotCode=" + robotCode +"&paymentPlatform=" + paymentPlatform +"&damagesType=" + damagesType +"&startTime=" + startTime +"&endTime=" + endTime;
    }

    /**
     * 弹出编辑框
     */
    function openEditOrderExceptionManagement(edit) {
        layer.open({
            type : 2,
            title: '编辑异常订单信息',
            offset: '10%',
            area: ['800px', '460px'], //宽高
            content: '/page/assetsSystem/orderExceptionManagement/html/orderExceptionManagementEdit.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if (edit){
                    body.find(".orderExceptionManagementId").val(edit.orderExceptionManagementId);
                    body.find(".causes select").val(edit.causes);
                    body.find(".reason").val(edit.reason);
                    body.find(".remarks").val(edit.remarks);
                    form.render();
                }
            }
        });
    };

    //导入Excel表
    upload.render({
        elem: '#importExcel'
        ,url: '/system/robotBelarcAdvisor/upload'
        ,accept: 'file' //普通文件
        ,exts: 'xls|xlsx' //只允许上传Excel文件
        ,before:function(obj){
            layer.load(); //上传loading
        }
        ,done: function(res){
            if (res.state == 200){
                setTimeout(function(){
                    layer.alert(res.msg,function(){
                        layer.closeAll();//关闭所有弹框
                    });
                    layui.table.reload("robotArchivesListTable");
                },500);
            }else{
                setTimeout(function(){
                    top.layer.msg(res.msg, {icon: 5,time: 1000,shift: 6});
                    layer.closeAll();
                },500);
            }
        }
    });

    //点击添加按钮
    $('#btnAdd').click(function(){
        window.resources("SYSTEM_ROBOT_INSERT", function (e) {
            if (e.state == "200") {
                openAddOrderExceptionManagement();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    function openAddOrderExceptionManagement() {
        layer.open({
            type : 2,
            title: '添加异常订单信息',
            offset: '10%',
            area: ['800px', '460px'], //宽高
            content: '/page/assetsSystem/orderExceptionManagement/html/orderExceptionManagementAdd.html'
        });
    };


})
