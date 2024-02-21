layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    table.render({
        elem: '#accessoriesErrorRecordsList',
        url : '/system/robotErrorRecords/accessoriesDetails',
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
        id : "AccessoriesErrorRecordsListTable",
        cols : [[
            {field: 'accessoryName', title: '配件名称', minWidth:100, align:"center",fixed:"left"},
            {field: 'accessoriesCode', title: '配件编码', minWidth:100, align:"center"},
            {field: 'accessoryModel', title: '配件型号', minWidth:100, align:"center"},
            {field: 'accessoryPriceOut', title: '价格/元', minWidth:100, align:"center"},
        ]],
        where : {
            errorRecordsId: $(".errorRecordsId").val(),
        }
    });
    table.reload("AccessoriesErrorRecordsListTable",{
        page: {
            curr: 1 //重新从第 1 页开始
        },
        where : {
            errorRecordsId: $(".errorRecordsId").val(),
        }
    });



})
