layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#currentUserAgreementLogList',
        url : '/system/currentUserAgreementLog/getCurrentUserAgreementLogList',
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
        id : "currentUserAgreementLogListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'userPhone', title: '用户手机号', minWidth:100, align:"center"},
            {field: 'agreementType', title: '是否同意协议', minWidth:100, align:"center",templet:function(d){
                    if(d.agreementType == "0"){
                        return "不同意协议";
                    }else if(d.agreementType == "1"){
                        return "用户勾选当前协议并同意";
                    }
                }},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("currentUserAgreementLogListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userPhone: $(".userPhoneVal").val(),
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

})
