layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#currentUserAccountList',
        url : '/system/currentUserAccount/getCurrentUserAccountList',
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
        id : "currentUserAccountListTable",
        cols : [[
            {field: 'currentUserPhone', title: '客户手机号', minWidth:100, align:"center"},
            {field: 'accountAmount', title: '账户余额', minWidth:100, align:"center"},
            {field: 'discount', title: '折扣', minWidth:100, align:"center"},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#currentUserAccountListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(currentUserAccountList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("FINANCE_CURRENT_USER_ACCOUNT_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditCurrentUserAccount(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "refund"){//储值余额退款

            window.resources("SYSTEM_ORDER_REFUND", function (e) {
                if (e.state == "200") {
                    payBackBtn(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");

        }
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("currentUserAccountListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                currentUserPhone: $(".currentUserPhoneVal").val(),
                startTime: $(".startTime").val(),
                endTime: $(".endTime").val()
            }
        })
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })

    /**
     * 点击导出EXCEL表
     */
    $('#downloadExcel').click(function(){
        window.resources("FINANCE_CURRENT_USER_ACCOUNT_DOWNLOADEXCEL", function (e) {
            if (e.state == "200") {
                downloadExcel();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditCurrentUserAccount(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改储值信息',
                offset: '10%',
                area: ['800px', '400px'],
                content: '/page/system/currentUserAccount/html/currentUserAccountEdit.html',
                tableId: '#currentUserAccountList',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        console.log(edit);
                        top.layer.close(dex);
                        body.find(".accountId").val(edit.accountId);
                        body.find(".accountAmount").val(edit.accountAmount);
                        body.find(".discount").val(edit.discount);
                        form.render();
                    }
                }
            });
        },500);
    }

    /**
     * 导出EXCEL表
     */
    function downloadExcel(){
        var currentUserPhone = $(".currentUserPhoneVal").val();
        var startTime = $(".startTime").val();
        var endTime = $(".endTime").val();
        window.location.href = "/system/currentUserAccount/uploadExcelCurrentUserAccount?currentUserPhone=" + currentUserPhone + "&startTime=" + startTime + "&endTime=" +endTime;
    }


    /**
     * 用户储值退款
     * @param edit
     */
    function payBackBtn(edit){
        layer.open({
            type: 2,
            title: '储值订单退款',
            area: ['800px', '80%'],
            content: '/page/system/currentUserAccount/html/currentUserAccountRefund.html',
            tableId: '#currentUserAccountList',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    console.log(edit);
                  //  top.layer.close(dex);
                    body.find(".accountId").val(edit.accountId);
                    body.find(".accountAmount").val(edit.accountAmount);
                    body.find(".discount").val(edit.discount);
                    body.find(".phone").val(edit.currentUserPhone);
                    body.find(".accountUserId").val(edit.accountUserId);
                    form.render();
                }
            }
        });
    }

})