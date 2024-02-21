layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    $ = layui.jquery;

    table.render({
        elem: '#vipOrderList',
        url : '/system/order/getOrderList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        where: {
            paymentMethod : "2"
        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "vipOrderListTable",
        cols : [[
            {field: 'currentUserPhone', title: '用户手机号', minWidth:100, align:"center"},
            {field: 'orderRobotCode', title: '机器人编号', minWidth:100, align:"center"},
            {field: 'orderScenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'orderStartTime', title: '开始时间', minWidth:100, align:"center"},
            {field: 'orderEndTime', title: '结束时间', minWidth:100, align:"center"},
            {field: 'totalTime', title: '使用时长', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#orderListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 根据用户手机号模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("vipOrderListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                startTime: $(".startTime").val(),
                endTime: $(".endTime").val(),
                orderScenicSpotId: $(".scenicSpotId").val(),
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
    table.on('tool(vipOrderList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === "settlement"){//结算订单
            window.resources("SYSTEM_ORDER_SETTLEMENT", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定结算此订单吗？',{icon:3, title:'提示信息'},function(index){
                        var dex = top.layer.msg('订单结算中，请稍候',{icon: 16,time:false,shade:0.8});
                        $.ajax({
                            url: "/system/order/settlementVipOrder",
                            data: {
                                orderId : data.orderId
                            },
                            type: "POST",
                            cache:false,
                            success: function (data) {
                                if (data.state == "200"){
                                    setTimeout(function(){
                                        top.layer.msg(data.msg, {icon: 6});
                                        top.layer.close(dex);
                                        layer.close(index);
                                        layui.table.reload("vipOrderListTable");
                                    },500);
                                }else if (data.state == "400"){
                                    setTimeout(function(){
                                        top.layer.close(dex);
                                        layer.close(index);
                                        top.layer.msg(data.msg, {icon: 5,time: 1000,shift: 6});
                                    },500);
                                }
                            }
                        });
                    });
                }else{
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            },false,"GET");
        }
    });

})
