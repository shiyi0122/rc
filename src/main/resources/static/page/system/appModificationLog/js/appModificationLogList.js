layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#appModificationLogList',
        url : '/system/appModificationLog/getAppModificationLogList',
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
        id : "appModificationLogListTable",
        cols : [[
            {field: 'modificationLogLoginName', title: '操作人', minWidth:100, align:"center"},
            {field: 'modificationLogRobotCode', title: '机器人编号', minWidth:100, align:"center"},
            {field: 'modificationLogFront', title: '操作前', minWidth:100, align:"center"},
            {field: 'modificationLogAfter', title: '操作后', minWidth:100, align:"center"},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("appModificationLogListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                modificationLogRobotCode: $(".robotDispatchCodeVal").val(),
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
