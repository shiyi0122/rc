layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#storedValueAmountLogList',
        url : '/system/storedValueAmountLog/getStoredValueAmountLogList',
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
        where:{
            logType : "1"
        },
        id : "storedValueAmountLogListTable",
        cols : [[
            {field: 'userName', title: '操作人名称', minWidth:100, align:"center"},
            {field: 'logUserPhone', title: '用户手机号', minWidth:100, align:"center"},
            {field: 'logAmount', title: '操作金额', minWidth:100, align:"center"},
            {field: 'logDiscount', title: '操作折扣', minWidth:100, align:"center"},
            {field: 'logReasonsRefunds', title: '操作原因', minWidth:100, align:"center"},
            {field: 'createDate', title: '操作时间', minWidth:100, align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("storedValueAmountLogListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                logUserPhone: $(".logUserPhoneVal").val()
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