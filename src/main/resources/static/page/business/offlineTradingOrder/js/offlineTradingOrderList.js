layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#offlineTradingOrderList',
        url : '/business/order/getOrderList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        where:{
            orderType : 1
        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "offlineTradingOrderListTable",
        cols : [[
            {field: 'userName', title: '用户名称', minWidth:100, align:"center"},
            {field: 'phone', title: '用户手机号', minWidth:100, align:"center"},
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'orderNumber', title: '订单编号', minWidth:100, align:"center"},
            {field: 'orderStatus', title: '订单状态', align:'center',templet:function(d){
                    if(d.orderStatus == "0"){
                        return "未完成";
                    }else if(d.orderStatus == "1"){
                        return "已完成";
                    }else if(d.orderStatus == "2"){
                        return "已退款";
                    }
                }},
            {field: 'orderAmount', title: '订单金额', minWidth:100, align:"center"},
            {field: 'createTime', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#offlineTradingOrderListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        if($(".userNameVal").val() != ''){
            table.reload("offlineTradingOrderListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    userName: $(".userNameVal").val()  //搜索的关键字
                }
            })
        }else{
            layer.msg("请输入搜索的客户名称");
        }
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })

    //列表操作
    table.on('tool(offlineTradingOrderList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("BUSINESS_OFFLINE_TRADING_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditOfflineTradingOrder(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("BUSINESS_OFFLINE_TRADING_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/business/order/delOrder", {id : data.id}, function (data) {}, false,"GET","offlineTradingOrderListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditOfflineTradingOrder(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改线下交易订单',
                offset: '10%',
                area: ['800px', '300px'], //宽高
                content: '/page/business/offlineTradingOrder/html/offlineTradingOrderEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".id").val(edit.id);
                        body.find(".orderAmount").val(edit.orderAmount);
                        body.find(".orderStatus select").val(edit.orderStatus);
                        form.render();
                    }
                }
            });
        },500);
    }

})