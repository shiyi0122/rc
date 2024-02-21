layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#invoiceRecordList',
        url : '/business/invoiceRecord/getInvoiceRecordList',
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
        id : "invoiceRecordListTable",
        cols : [[
            {field: 'userName', title: '用户名称', minWidth:100, align:"center"},
            {field: 'phone', title: '用户手机号', minWidth:100, align:"center"},
            {field: 'titleInvoice', title: '发票抬头', minWidth:100, align:"center"},
            {field: 'address', title: '发票邮寄地址', minWidth:100, align:"center"},
            {field: 'invoiceAmount', title: '开票金额', minWidth:100, align:"center"},
            {field: 'failReasons', title: '开票失败原因', minWidth:100, align:"center"},
            {field: 'state', title: '开票状态', minWidth:100, align:"center",templet:function(d){
                    if(d.state == "0"){
                        return "待处理";
                    }else if(d.state == "1"){
                        return "开具成功";
                    }if(d.state == "2"){
                        return "开具失败";
                    }if (d.state == "3"){
                        return "已取消";
                    }
                }},
            {field: 'createTime', title: '创建时间', minWidth:100, align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("invoiceRecordListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userName: $(".userNameVal").val()
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
