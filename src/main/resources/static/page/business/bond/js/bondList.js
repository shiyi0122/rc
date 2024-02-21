layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#bondList',
        url : '/business/order/getOrderList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        where:{
            orderType : 2
        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "bondListTable",
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
            {title: '操作', minWidth:175, templet:'#bondListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        if($(".userNameVal").val() != ''){
            table.reload("bondListTable",{
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
    table.on('tool(bondList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //退款
            window.resources("BUSINESS_BOND_ORDER_REFUND", function (e) {
                if (e.state == "200") {
                    openEditRobot(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("BUSINESS_BOND_ORDER_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/business/order/delOrder", {id : data.id}, function (data) {}, false,"GET","bondListTable");
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
    function openEditRobot(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改机器人',
                offset: '10%',
                area: ['800px', '460px'], //宽高
                content: '/page/system/robot/html/robotEdit.html',
                tableId: '#robotList',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".robotId").val(edit.robotId);
                        body.find(".robotCode").val(edit.robotCode);
                        body.find(".robotCodeSim").val(edit.robotCodeSim);
                        body.find(".robotVersionNumber").val(edit.robotVersionNumber);
                        body.find(".scenicspotNames").val(edit.scenicSpotId);
                        form.render();
                    }
                }
            });
        },500);
    }

})