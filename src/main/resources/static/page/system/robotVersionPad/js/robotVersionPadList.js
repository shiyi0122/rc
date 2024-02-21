layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#robotVersionPadList',
        url : '/system/robotVersionPad/getRobotVersionPadList',
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
        id : "robotVersionPadListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'versionUrl', title: 'URL', minWidth:100, align:"center"},
            {field: 'versionNumber', title: '版本号', minWidth:100, align:"center"},
            {field: 'versionDescription', title: '版本描述', minWidth:100, align:"center"},
            {field: 'createDate', title: '创建时间', minWidth:100, align:"center"},
            {title: '操作', width:350, templet:'#robotVersionPadListBar',fixed:"right",align:"center"}
        ]]
    });


    //列表操作
    table.on('tool(robotVersionPadList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("ROBOT_VERSION_PAD_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditRobotVersionPad(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("ROBOT_VERSION_PAD_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/robotVersionPad/delRobotVersionPad", {versionId : data.versionId}, function (data) {}, false,"GET","robotVersionPadListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "download"){ //下载
            window.resources("ROBOT_VERSION_PAD_DOWNLOAD", function (e) {
                if (e.state == "200") {
                    window.location.href = "/system/robotVersionPad/download?fileName=" + data.versionUrl
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

    //点击添加按钮
    $('#btnAddRobotVersionPad').click(function(){
        window.resources("ROBOT_VERSION_PAD_INSERT", function (e) {
            if (e.state == "200") {
                addRobotVersionPad();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function addRobotVersionPad() {
        layer.open({
            type : 2,
            title: '上传PAD客户端',
            offset: '10%',
            area: ['800px', '460px'], //宽高
            content: '/page/system/robotVersionPad/html/robotPadAdd.html',
            tableId: '#robotVersionPadList',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                $.ajax({
                    type : 'POST',
                    url : '/system/scenicSpot/getCurrentScenicSpot',
                    dataType : 'json',
                    success:function (data) {
                        if (data.state == "200"){
                            body.find(".scenicSpotId").val(data.data.scenicSpotId);//景区ID
                            body.find(".scenicSpotName").val(data.data.scenicSpotName);//景区名称
                            form.render();
                        }else if(data.state == "400"){
                            layer.msg(data.msg);
                        }
                    }
                })
            }
        });
    };
    /**
     * 弹出修改框
     */
    function openEditRobotVersionPad(edit) {
        layer.open({
            type : 2,
            title: '上传PAD客户端',
            offset: '10%',
            area: ['800px', '330px'], //宽高
            content: '/page/system/robotVersionPad/html/robotVersionPadEdit.html',
            tableId: '#robotVersionPadList',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                $.ajax({
                    type : 'POST',
                    url : '/system/scenicSpot/getCurrentScenicSpot',
                    dataType : 'json',
                    success:function (data) {
                        if (data.state == "200"){
                            if (edit){
                                body.find(".scenicSpotId").val(data.data.scenicSpotId);//景区ID
                                body.find(".scenicSpotName").val(data.data.scenicSpotName);//景区名称
                                body.find(".versionId").val(edit.versionId);
                                body.find(".versionNumber").val(edit.versionNumber);
                                body.find(".versionDescription").val(edit.versionDescription);
                                form.render();
                            }
                        }else if(data.state == "400"){
                            layer.msg(data.msg);
                        }
                    }
                })
            }
        });
    };

})
