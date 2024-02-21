layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    $ = layui.jquery;
    layuiTable = layui.table;

    var tableIns = table.render({
        elem: '#reconciliationOrderList',
        url : '/system/reconciliationOrder/getReconciliationOrderList',
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
        id : "reconciliationOrderListTable",
		where : {
			subMethod : "1"
		},
        cols : [[
            {field: 'currentUserPhone', title: '用户手机号', minWidth:100, align:"center"},
            {field: 'orderRobotCode', title: '机器人编号', minWidth:100, align:"center"},
            {field: 'orderScenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'realIncome', title: '实际收入', minWidth:100, align:"center"},
            {field: 'orderRefundAmount', title: '退款金额', minWidth:100, align:"center"},
            {field: 'tenCentCommission', title: '腾讯手续费费率', minWidth:100, align:"center"},
            {field: 'paymentTotalAccount', title: '最终入账金额', minWidth:100, align:"center"},
            {field: 'orderStatus', title: '运行状态', minWidth:100, align:"center",templet:function(d){
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
            {field: 'orderStartTime', title: '订单日期', minWidth:100, align:"center"}
        ]],
        done: function (res) {
            $(".realIncome").val(res.realIncome);
            $(".paymentTotalAccount").val(res.paymentTotalAccount);
        }
    });

    /**
     * 根据用户手机号模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("reconciliationOrderListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
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

    //点击导出EXCEL表
    $('#downloadExcel').click(function(){
        window.resources("SYS_RECONCILIATION_ORDER_DOWNLOADEXCEL", function (e) {
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
        var data = $('input[name="data"]:checked').val();
        var subMethod = "1";
        if (subMethod == ''){
            layer.msg("请选择订单类型");
        }else{
            window.location.href = "/system/reconciliationOrder/uploadExcelReconciliationOrder?data="+data+"&subMethod="+subMethod;
        }

    }

    $('.notice').hover(function () {
        // 鼠标移入时添加hover类
        $('.contents').show();
    }, function () {
        // 鼠标移出时移出hover类
        $('.contents').hide();
    });

})
