layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    $ = layui.jquery;
    layuiTable = layui.table;
    var tabledata;

    var tableIns = table.render({
        elem: '#currentUserAccountOrderList',
        url : '/system/currentUserAccountOrder/getCurrentUserAccountOrderList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        where:{
            paymentMethod : "1"
        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "currentUserAccountOrderListTable",
        cols : [[
            {field: 'accountOrderNumber', title: '订单编号', minWidth:100, align:"center"},
            {field: 'userPhone', title: '用户手机号', minWidth:100, align:"center"},
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'rechargeAmount', title: '充值金额', minWidth:100, align:"center"},
            {field: 'donationAmount', title: '折扣', minWidth:100, align:"center"},
            {field: 'refundAmount', title: '退款金额', minWidth:100, align:"center"},
            {field: 'revenueAmount', title: '收入金额', minWidth:100, align:"center"},
            {field: 'createDate', title: '创建时间', minWidth:100, align:"center"}
        ]]
    });

    /**
     * 根据用户手机号模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("currentUserAccountOrderListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userPhone: $(".userPhoneVal").val(),
                startTime: $(".startTime").val(),
                endTime: $(".endTime").val(),
                scenicSpotId: $(".scenicSpotId").val()
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
        window.resources("SYS_CURRENT_USER_ACCOUNT_ORDER_EXCEL", function (e) {
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
        var userPhone = $(".userPhoneVal").val();//客户手机号
        var startTime = $(".startTime").val();//开始时间
        var endTime = $(".endTime").val();//结束时间
        var scenicSpotId = $(".scenicSpotId").val();
        window.location.href = "/system/currentUserAccountOrder/uploadExcelCurrentUserAccountOrder?userPhone="+userPhone+"&startTime="+startTime+"&endTime="+endTime+"&scenicSpotId="+scenicSpotId;
    }


})
