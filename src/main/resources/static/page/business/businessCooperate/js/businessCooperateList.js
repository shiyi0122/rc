layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#businessCooperateList',
        url : '/business/businessCooperate/getBusinessCooperateList',
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
        id : "businessCooperateListTable",
        cols : [[
            {field: 'name', title: '用户名称', minWidth:100, align:"center"},
            {field: 'phone', title: '用户手机号', minWidth:100, align:"center"},
            {field: 'content', title: '内容', minWidth:100, align:"center"},
            {field: 'address', title: '地址', minWidth:100, align:"center"},
            {field: 'mergerName', title: '地区', minWidth:100, align:"center"},
            {field: 'createTime', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#businessCooperateListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("businessCooperateListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                phone: $(".phoneVal").val()
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
    table.on('tool(businessCooperateList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'del'){ //删除
            window.resources("INVESTMENT_PLATFORM_SUPPLIER_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/business/businessCooperate/delCooperate", {id : data.id}, function (data) {}, false,"GET","businessCooperateListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

})