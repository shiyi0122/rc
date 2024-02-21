layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#robotPadList',
        url : '/system/robotPad/getRobotPadList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code6+
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "robotPadListTable",
        cols : [[
            {field: 'padUrl', title: 'PAD路径', minWidth:100, align:"center"},
            {field: 'padNumber', title: '版本号', minWidth:100, align:"center"},
            {field: 'padDescription', title: '备注', minWidth:100, align:"center"},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#robotPadListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(robotPadList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYS_ROBOT_PAD_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditRobotPad(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYS_ROBOT_PAD_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/robotPad/delRobotPad", {padId : data.padId}, function (data) {}, false,"GET","robotPadListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "download"){ //下载
            window.resources("SYS_ROBOT_PAD_DOWNLOAD", function (e) {
                if (e.state == "200") {
                    window.location.href = "/system/robotPad/download?fileName=" + data.padUrl
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddRobotPad').click(function(){
        window.resources("SYS_ROBOT_PAD_INSERT", function (e) {
            if (e.state == "200") {
                openAddRobotPad();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddRobotPad() {
        layer.open({
            type : 2,
            title: '上传PAD客户端',
            offset: '10%',
            area: ['800px', '400px'], //宽高
            content: '/page/system/robotPad/html/robotPadAdd.html'
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditRobotPad(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改PAD信息',
                offset: '10%',
                area: ['800px', '280px'], //宽高
                content: '/page/system/robotPad/html/robotPadEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".padId").val(edit.padId);
                        body.find(".padDescription").val(edit.padDescription);
                        form.render();
                    }
                }
            });
        },500);
    }

})
