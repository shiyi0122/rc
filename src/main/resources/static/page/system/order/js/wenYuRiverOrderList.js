layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    $ = layui.jquery;
    layuiTable = layui.table;
    var tabledata;

    table.render({
        elem: '#wenYuRiverOrderList',
        url : '/system/wenYuRiverInterface/getWenYuRiverOrderList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        where:{
            paymentMethod : "1,4",
            subMethod : "0",
            paymentPort : "0,1,2"
        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "wenYuRiverOrderListTable",
        cols : [[
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
                }}
        ]]
    });

    /**
     * 根据用户手机号模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("wenYuRiverOrderListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                startTime: $(".startTime").val(),
                endTime: $(".endTime").val(),
                orderRobotCode: $(".orderRobotCodeVal").val()
            }
        })
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })

})
