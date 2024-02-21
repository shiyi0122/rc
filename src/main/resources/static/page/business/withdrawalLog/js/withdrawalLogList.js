layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#withdrawalLogList',
        url : '/business/withdrawalLog/getWithdrawalLogList',
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
        id : "withdrawalLogListTable",
        cols : [[
            {field: 'userName', title: '用户名称', minWidth:100, align:"center"},
            {field: 'bankAccount', title: '银行卡号', minWidth:100, align:"center"},
            {field: 'bankInformation', title: '开户行信息', minWidth:100, align:"center"},
            {field: 'identityCard', title: '身份证号码', minWidth:100, align:"center"},
            {field: 'money', title: '提现金额', minWidth:100, align:"center"},
            {field: 'reason', title: '驳回原因', minWidth:100, align:"center"},
            {field: 'state', title: '提现状态', minWidth:100, align:"center",templet:function(d){
                    if(d.state == "0"){
                        return "申请中";
                    }else if(d.state == "1"){
                        return "同意";
                    }if(d.state == "2"){
                        return "驳回";
                    }
                }},
            {field: 'createTime', title: '创建时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#withdrawalLogListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(withdrawalLogList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'adopt'){ //审核通过
            window.resources("BUSINESS_BANK_CARD_TO_EXAMINE", function (e) {
                if (e.state == "200") {
                    openEditAdopt(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'reject'){ //审核驳回
            window.resources("BUSINESS_BANK_CARD_TO_EXAMINE", function (e) {
                if (e.state == "200") {
                    openEditReject(data);
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
        table.reload("withdrawalLogListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userName: $(".userNameVal").val(),
                state: $(".state").val()
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
     * 驳回页面
     * @param edit
     */
    function openEditReject(edit){
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '原因',
                offset: '10%',
                area: ['800px', '280px'],
                content: '/page/business/withdrawalLog/html/withdrawalLogAdopt.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        body.find(".id").val(edit.id);
                        body.find(".userId").val(edit.userId);
                        body.find(".money").val(edit.money);
                        body.find(".state").val("2");
                        form.render();
                    }
                }
            });
        },500);
    }
    /**
     * 同意页面
     * @param edit
     */
    function openEditAdopt(edit){
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '原因',
                offset: '10%',
                area: ['800px', '280px'],
                content: '/page/business/withdrawalLog/html/withdrawalLogAdopt.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        body.find(".id").val(edit.id);
                        body.find(".userId").val(edit.userId);
                        body.find(".state").val("1");
                        body.find(".money").val(edit.money);
                        form.render();
                    }
                }
            });
        },500);
    }

    //点击导出EXCEL表
    $('#downloadExcel').click(function(){
        window.resources("FINANCE_ORDER_DOWNLOADEXCEL", function (e) {
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
        var userName = $(".userNameVal").val();
        var state = $(".state").val();
        window.location.href = "/business/withdrawalLog/uploadExcelWithdrawalLog?userName="+userName+"&state="+state;
    }

})