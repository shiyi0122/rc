layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#scenicSpotCapPriceList',
        url : '/system/sysRobotDownloadLog/getSysRobotDownloadLogList',
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
        id : "scenicSpotCapPriceListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'robotCode', title: '机器人编号', minWidth:100, align:"center"},
            {field: 'downloadUrl', title: '下载链接', minWidth:100, align:"center"},
            {field: 'size', title: '文件大小', minWidth:100, align:"center"},
            {field: 'remarks', title: '链接用途', minWidth:100, align:"center"},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {field: 'updateDate', title: '修改时间', minWidth:100, align:"center"},

        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("scenicSpotCapPriceListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                scenicSpotId: $(".scenicSpotId").val(),
                robotCode: $(".robotCode").val()
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