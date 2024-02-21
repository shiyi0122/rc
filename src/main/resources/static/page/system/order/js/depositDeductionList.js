layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    $ = layui.jquery;
    layuiTable = layui.table;
    var tabledata;

    var tableIns = table.render({
        elem: '#depositDeductionList',
        url : '/system/order/getOrderList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        where:{
            paymentMethod : "5",
            subMethod : "0"
        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "depositDeductionListTable",
        cols : [[
            {field: 'orderNumber', title: '订单编号', minWidth:100, align:"center"},
            {field: 'currentUserPhone', title: '用户手机号', minWidth:100, align:"center"},
            {field: 'orderRobotCode', title: '机器人编号', minWidth:100, align:"center"},
            {field: 'orderScenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'orderStartTime', title: '开始时间', minWidth:100, align:"center"},
            {field: 'orderEndTime', title: '结束时间', minWidth:100, align:"center"},
            {field: 'totalTime', title: '使用分钟', minWidth:100, align:"center"},
            {field: 'actualAmount', title: '实际计费金额', minWidth:100, align:"center"},
            {field: 'dispatchingFee', title: '调度费', minWidth:100, align:"center"},
            {field: 'orderAndDeductible', title: '实际支付金额', minWidth:100, align:"center"},
            {field: 'orderRefundAmount', title: '退款', minWidth:100, align:"center"},
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
            {title: '操作', minWidth:175, templet:'#depositDeductionListBar',fixed:"right",align:"center"}
        ]], done: function (res) {
            $(".realIncome").val(res.realIncome);
        }
    });

    /**
     * 根据用户手机号模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("depositDeductionListTable",{
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
    table.on('tool(depositDeductionList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === "depositRefund"){//订单退款
            window.resources("SYSTEM_ORDER_REFUND", function (e) {
                if (e.state == "200") {
                    depositRefund(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === "transactionClosure"){//查看数据
            window.resources("SYSTEM_ORDER_DEDUCTION", function (e) {
                if (e.state == "200") {
                    viewOrder(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

    //点击导出EXCEL表
    $('#downloadExcel').click(function(){
        window.resources("DEPOSIT_DEDUCTION_DOWNLOAD", function (e) {
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
        var orderRobotCode = $(".orderRobotCodeVal").val();
        var orderParkingId = $(".orderParkingId").val();
        var parkingType = $(".typee").val();
        var paymentMethod = 5;
        var subMethod = 0;
        window.location.href = "/system/order/uploadExcelOrder?currentUserPhone="+currentUserPhone+"&startTime="+startTime+"&endTime="+endTime+"&orderScenicSpotId="+orderScenicSpotId+"&orderRobotCode="+orderRobotCode+"&paymentMethod="+paymentMethod+"&subMethod="+subMethod+"&orderParkingId="+orderParkingId+"&parkingType="+parkingType;
    }

    /**
     * 押金扣款退款
     * @param edit
     */
    function depositRefund(edit){
        layer.open({
            type: 2,
            title: '押金扣款退款',
            area: ['800px', '80%'],
            content: '/page/system/order/html/depositRefund.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".orderId").val(edit.orderId);
                    body.find(".orderNumber").val(edit.orderNumber);
                    body.find(".outTradeNo").val(edit.outTradeNo);
                    body.find(".currentUserPhone").val(edit.currentUserPhone);
                    body.find(".orderRobotCode").val(edit.orderRobotCode);
                    body.find(".orderScenicSpotName").val(edit.orderScenicSpotName);
                    body.find(".orderStartTime").val(edit.orderStartTime);
                    body.find(".orderGpsCoordinate").val(edit.orderGpsCoordinate);
                    body.find(".totalTime").val(edit.totalTime);
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
					body.find(".orderGpsCoordinate").val(edit.orderGpsCoordinate);
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

})
