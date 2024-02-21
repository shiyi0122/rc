layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#managementList',
        url : '/system/management/getManagementList',
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
        id : "managementListTable",
        cols : [[
            {field: 'merchantName', title: '主体名称', minWidth:100, align:"center"},
            {field: 'certFileName', title: '证书文件名称', minWidth:100, align:"center"},
            {field: 'merchantSecret', title: '微信小程序唯一密钥', minWidth:100, align:"center"},
            {field: 'merchantNumber', title: '商户号', minWidth:100, align:"center"},
            {field: 'createDate', title: '上传时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#managementListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(managementList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'del'){ //删除
            window.resources("SYSTEM_MANAGEMENT_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/management/delManagement", {merchantId : data.merchantId}, function (data) {}, false,"GET","managementListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddManagement').click(function(){
        window.resources("SYSTEM_MANAGEMENT_INSERT", function (e) {
            if (e.state == "200") {
                openAddManagement();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddManagement() {
        layer.open({
            type : 2,
            title: '添加证书',
            offset: '10%',
            area: ['800px', '520px'],
            content: '/page/system/management/html/managementAdd.html'
        });
    };

})
