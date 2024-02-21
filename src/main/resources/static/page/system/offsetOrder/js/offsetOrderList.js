layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#offsetOrderList',
        url : '/system/order/getOrderList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        where: {
            paymentMethod : "1",
            subMethod : "2"
        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "offsetOrderListTable",
        cols : [[
            {field: 'orderNumber', title: '订单编号', minWidth:100, align:"center"},
            {field: 'currentUserPhone', title: '用户手机号', minWidth:100, align:"center"},
            {field: 'orderRobotCode', title: '机器人编号', minWidth:100, align:"center"},
            {field: 'orderScenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'orderStartTime', title: '开始时间', minWidth:100, align:"center"},
            {field: 'orderEndTime', title: '结束时间', minWidth:100, align:"center"},
            {field: 'totalTime', title: '使用分钟', minWidth:100, align:"center"},
            {field: 'actualAmount', title: '实际计费金额', minWidth:100, align:"center"},
            {field: 'orderDiscount', title: '折扣', minWidth:100, align:"center"},
            {field: 'amountAfterDiscount', title: '折扣后', minWidth:100, align:"center"},
			{field: 'dispatchingFee', title: '调度费', minWidth:100, align:"center"},
            {field: 'orderAmount', title: '实际结算', minWidth:100, align:"center"},
            {field: 'deductibleAmount', title: '账户抵扣', minWidth:100, align:"center"},
            {field: 'orderRefundAmount', title: '微信退款', minWidth:100, align:"center"},
            {field: 'deductibleRefundAmount', title: '抵扣退款', minWidth:100, align:"center"},
            {field: 'realIncome', title: '实际收入', minWidth:100, align:"center"},
            {field: 'orderStatus', title: '支付状态', align:'center',templet:function(d){
                    if(d.orderStatus == "10"){
                        return "进行中";
                    }else if(d.orderStatus == "20"){
                        return "未付款";
                    }else if(d.orderStatus == "30"){
                        return "已付款";
                    }else if(d.orderStatus == "40"){
                        return "交易关闭";
                    }else if(d.orderStatus == "50"){
                        return "免单";
                    }else if(d.orderStatus == "60"){
                        return "全额退款";
                    }
                }},
            {title: '操作', minWidth:175, templet:'#offsetOrderListBar',fixed:"right",align:"center"}
        ]],done: function (res, curr, count) {
            $(".realIncome").val(res.realIncome);
        }
    });

    /**
     * 根据用户手机号模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("offsetOrderListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                currentUserPhone: $(".currentUserPhoneVal").val(),
                startTime: $(".startTime").val(),
                endTime: $(".endTime").val(),
                orderScenicSpotId: $(".scenicSpotId").val(),
                orderStatus: $(".orderStatus").val(),
                orderRobotCode: $(".orderRobotCodeVal").val(),
                orderParkingId: $(".orderParkingId").val(),
                parkingType: $(".typee").val()
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
    table.on('tool(offsetOrderList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === "transactionClosure"){//查看数据
            window.resources("SYS_OFFSET_ORDER_VIEW", function (e) {
                if (e.state == "200") {
                    viewOrder(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === "weChatRefund"){//微信订单退款
            window.resources("SYSTEM_ORDER_REFUND", function (e) {
                if (e.state == "200") {
                    weChatRefund(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === "deductionAndRefund"){//抵扣订单退款
            window.resources("SYSTEM_ORDER_REFUND", function (e) {
                if (e.state == "200") {
                    deductionAndRefund(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");deductionAndRefund
        }
    });

    //点击导出EXCEL表
    $('#downloadExcel').click(function(){
        window.resources("SYS_OFFSET_ORDER_DOWNLOAD", function (e) {
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
        var currentUserPhone = $(".currentUserPhoneVal").val();//客户手机号
        var startTime = $(".startTime").val();//开始时间
        var endTime = $(".endTime").val();//结束时间
        var orderScenicSpotId = $(".scenicSpotId").val();
        var orderStatus = $(".orderStatus").val();
        var orderRobotCode = $(".orderRobotCodeVal").val();
        var orderParkingId = $(".orderParkingId").val();
        var parkingType = $(".typee").val();
        var subMethod = 2;
        var paymentMethod = 1;
        window.location.href = "/system/order/uploadExcelOrder?currentUserPhone="+currentUserPhone+"&startTime="+startTime+"&endTime="+endTime+"&orderScenicSpotId="+orderScenicSpotId+"&orderStatus="+orderStatus+"&orderRobotCode="+orderRobotCode+"&subMethod="+subMethod+"&paymentMethod="+paymentMethod+"&orderParkingId="+orderParkingId+"&parkingType="+parkingType;
    }

    /**
     * 查看数据
     * @param edit
     */
    function viewOrder(edit){
        layer.open({
            type: 2,
            title: '查看数据',
            area: ['800px', '80%'],
            content: '/page/system/order/html/viewOrder.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".orderId").val(edit.orderId);
                    body.find(".orderNumber").val(edit.orderNumber);
                    body.find(".currentUserPhone").val(edit.currentUserPhone);
                    body.find(".orderRobotCode").val(edit.orderRobotCode);
                    body.find(".orderScenicSpotName").val(edit.orderScenicSpotName);
                    body.find(".orderStartTime").val(edit.orderStartTime);
                    body.find(".totalTime").val(edit.totalTime);
                    body.find(".orderAmount").val(edit.orderAmount);
                    body.find(".orderRefundAmount").val(edit.orderRefundAmount);
                    body.find(".reasonsRefunds").val(edit.reasonsRefunds);
                    form.render();
                }
            }
        });
    }

    /**
     * 微信订单退款
     * @param edit
     */
    function weChatRefund(edit){
        layer.open({
            type: 2,
            title: '微信订单退款',
            area: ['800px', '80%'],
            content: '/page/system/offsetOrder/html/weChatRefund.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".orderId").val(edit.orderId);
                    body.find(".orderNumber").val(edit.orderNumber);
                    body.find(".currentUserPhone").val(edit.currentUserPhone);
                    body.find(".orderRobotCode").val(edit.orderRobotCode);
                    body.find(".orderScenicSpotName").val(edit.orderScenicSpotName);
                    body.find(".orderStartTime").val(edit.orderStartTime);
                    body.find(".orderGpsCoordinate").val(edit.orderGpsCoordinate);
                    body.find(".totalTime").val(edit.totalTime);
                    body.find(".amountAfterDiscount").val(edit.amountAfterDiscount);
                    body.find(".orderAmount").val(edit.orderAmount);
                    body.find(".orderRefundAmount").val(edit.orderRefundAmount);
                    body.find(".payMoney").val((parseFloat(edit.orderAmount) - parseFloat(edit.orderRefundAmount)).toFixed(2));
                    body.find(".reasonsRefunds").val(edit.reasonsRefunds);
                    form.render();
                }
            }
        });
    }

    /**
     * 抵扣订单退款
     * @param edit
     */
    function deductionAndRefund(edit){
        layer.open({
            type: 2,
            title: '抵扣订单退款',
            area: ['800px', '80%'],
            content: '/page/system/offsetOrder/html/deductionAndRefund.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".orderId").val(edit.orderId);
                    body.find(".orderNumber").val(edit.orderNumber);
                    body.find(".currentUserPhone").val(edit.currentUserPhone);
                    body.find(".orderRobotCode").val(edit.orderRobotCode);
                    body.find(".orderScenicSpotName").val(edit.orderScenicSpotName);
                    body.find(".orderStartTime").val(edit.orderStartTime);
                    body.find(".orderGpsCoordinate").val(edit.orderGpsCoordinate);
                    body.find(".totalTime").val(edit.totalTime);
                    body.find(".amountAfterDiscount").val(edit.amountAfterDiscount);
                    body.find(".deductibleAmount").val(edit.deductibleAmount);
                    body.find(".deductibleRefundAmount").val(edit.deductibleRefundAmount);
                    body.find(".payMoney").val((parseFloat(edit.deductibleAmount) - parseFloat(edit.deductibleRefundAmount)).toFixed(2));
                    body.find(".reasonsRefunds").val(edit.reasonsRefunds);
                    form.render();
                }
            }
        });
    }

})
