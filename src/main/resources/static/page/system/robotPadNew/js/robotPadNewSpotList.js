layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    table.render({
        elem: '#spotsList',
        url : '/system/robotPadNew/getSpotIdByRobotPad',
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
        id : "spotsListTable",
        cols : [[
            {field: 'padNumber', title: 'pad版本', minWidth:100, align:"center",fixed:"left"},
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'createDate', title: '创建时间', minWidth:100, align:"center"},
            {field: 'updateDate', title: '修改时间', minWidth:100, align:"center"},

        ]],
        where : {
            padId: $(".padId").val(),

        }
    });

    // table.reload("accessoriesRecordsListTable",{
    //     page: {
    //         curr: 1 //重新从第 1 页开始
    //     },
    //     where : {
    //         accessoriesName: $(".accessoriesName").val(),
    //     }
    // });



})
