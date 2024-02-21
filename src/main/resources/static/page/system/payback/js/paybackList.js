layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#currentUserList',
        url : '/system/payback/getCurrentUserList',
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
        id : "currentUserListTable",
        cols : [[
            {field: 'currentUserPhone', title: '客户手机号', minWidth:100, align:"center"},
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'outTradeNo', title: '订单编号', minWidth:100, align:"center"},
            {field: 'currentOpenId', title: '客户OPENID', minWidth:100, align:"center"},
            {field: 'paymentChannels', title: '支付渠道', align:'center',templet:function(d){
                if(d.paymentChannels == "1"){
                    return "微信";
                }else if(d.paymentChannels == "2"){
                    return "支付宝";
                }else if(d.paymentChannels == "0"){
                    return "未缴纳";
                }
            }},
			{field: 'depositPayState', title: '押金交纳状态', align:'center',templet:function(d){
                if(d.depositPayState == "10"){
                    return "已缴纳";
                }else if(d.depositPayState == "20"){
                    return "未缴纳";
                }
            }},
            {field: 'creditArrearsState', title: '信用欠费状态', align:'center',templet:function(d){
                if(d.creditArrearsState == "10"){
                    return "无欠款";
                }else if(d.creditArrearsState == "20"){
                    return "欠款";
                }
            }},
            {field: 'creditArrearsState', title: '黑名单状态', align:'center',templet:function(d){
                if(d.blackListType == "1"){
                    return "黑名单";
                }else if(d.blackListType == "0"){
                    return "白名单";
                }
            }},
            {field: 'createDate', title: '注册时间', minWidth:100, align:"center"},
            {field: 'depositPayAmount', title: '押金金额', minWidth:100, align:"center"},
            {field: 'depositPayTime', title: '交纳押金时间', minWidth:100, align:"center"},
            {field: 'returnDepositPayTime', title: '退还押金时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#currentUserListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 根据用户手机号模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("currentUserListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                currentUserPhone: $(".currentUserPhoneVal").val(),
                startTime: $(".startTime").val(),
                endTime: $(".endTime").val(),
                depositPayState: $(".depositPayState").val(),
                creditArrearsState: $(".creditArrearsState").val()
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
    table.on('tool(currentUserList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'updateDepositPayState'){ //修改状态
            window.resources("FINANCE_PAYBACK_UPDATE_DEPOSITPAYSTATE", function (e) {
                if (e.state == "200") {
                    updateDepositPayState(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === 'retreatDeposit'){
            window.resources("FINANCE_PAYBACK_RETREAT_DEPOSIT", function (e) {
                if (e.state == "200") {
                    retreatDeposit(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === "blacklist"){//黑名单
            window.resources("FINANCE_PAYBACK_BLACK_LIST", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定要将此用户设置成黑名单吗？',{icon:3, title:'提示信息'},function(index){
                        var dex = top.layer.msg('执行中，请稍候',{icon: 16,time:false,shade:0.8});
                        $.ajax({
                            url: "/system/order/setBlacklist",
                            data: {
                                userId : data.currentUserId
                            },
                            type: "POST",
                            cache:false,
                            success: function (data) {
                                if (data.state == "200"){
                                    setTimeout(function(){
                                        top.layer.msg(data.msg, {icon: 6});
                                        top.layer.close(dex);
                                        layer.close(index);
                                        layui.table.reload("currentUserListTable");
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
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === "whitelist"){//白名单
            window.resources("FINANCE_PAYBACK_BLACK_LIST", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定要将此用户设置成白名单吗？',{icon:3, title:'提示信息'},function(index){
                        var dex = top.layer.msg('执行中，请稍候',{icon: 16,time:false,shade:0.8});
                        $.ajax({
                            url: "/system/payback/setWhitelist",
                            data: {
                                userId : data.currentUserId
                            },
                            type: "POST",
                            cache:false,
                            success: function (data) {
                                if (data.state == "200"){
                                    setTimeout(function(){
                                        top.layer.msg(data.msg, {icon: 6});
                                        top.layer.close(dex);
                                        layer.close(index);
                                        layui.table.reload("currentUserListTable");
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
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

    /**
     * 修改状态
     * @param edit
     */
    function updateDepositPayState(edit){
        layer.open({
            type: 2,
            title: '修改押金缴纳状态',
            area: ['300px', '400px'],
            content: '/page/system/payback/html/updateDepositPayState.html',
            tableId: '#currentUserList',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".currentUserId").val(edit.currentUserId);
                    form.render();
                }
            }
        });
    }

    function retreatDeposit(edit) {
        layer.open({
            type: 2,
            title: '押金退款',
            area: ['800px', '400px'],
            content: '/page/system/payback/html/retreatDeposit.html',
            tableId: '#currentUserList',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".currentUserId").val(edit.currentUserId);
                    body.find(".currentUserPhone").val(edit.currentUserPhone);
                    body.find(".depositPayAmount").val(edit.depositPayAmount);
                    body.find(".depositPay").val(edit.depositPayAmount);
                    form.render();
                }
            }
        })
    }

    //点击导出EXCEL表
    $('#downloadExcel').click(function(){
        window.resources("FINANCE_PAYBACK_DOWNLOADEXCEL", function (e) {
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
        var currentUserPhone = $(".currentUserPhoneVal").val();
        var startTime = $(".startTime").val();
        var endTime = $(".endTime").val();
        var depositPayState = $(".depositPayState").val();
        var creditArrearsState = $(".creditArrearsState").val()
        window.location.href = "/system/payback/uploadExcelCurrentUser?currentUserPhone="+currentUserPhone+"&startTime="+startTime+"&endTime="+endTime+"&depositPayState="+depositPayState+"&creditArrearsState="+creditArrearsState;
    }

})
