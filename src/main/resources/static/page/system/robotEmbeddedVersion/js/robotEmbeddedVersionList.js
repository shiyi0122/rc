layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#robotEmbeddedVersionList',
        url : '/system/robotEmbeddedVersion/getRobotEmbeddedVersionList',
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
        id : "robotEmbeddedVersionListTable",
        cols : [[
            {field: 'embeddedVersion', title: '嵌入式版本号', minWidth:100, align:"center"},
            {field: 'embeddedDescribe', title: '嵌入式描述', minWidth:100, align:"center"},
            {field: 'robotVersion', title: '机器人版本号', minWidth:100, align:"center"},
            {field: 'softwareVersion', title: '嵌入式软件版本号', minWidth:100, align:"center"},
            {field: 'embeddedUrl', title: '嵌入式地址连接', minWidth:100, align:"center"},
			{field: 'embeddedType', title: '文件类型', align:'center',templet:function(d){
                if(d.embeddedType == "1"){
                    return "主控";
                }else if(d.embeddedType == "2"){
                    return "超声";
                }else{
                    return "无";
                }
            }},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#robotEmbeddedVersionListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(robotEmbeddedVersionList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYS_ROBOT_EMBEDDED_VERSION_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditRobotEmbeddedVersion(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYS_ROBOT_EMBEDDED_VERSION_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/robotEmbeddedVersion/delRobotEmbeddedVersion", {embeddedId : data.embeddedId}, function (data) {}, false,"GET","robotEmbeddedVersionListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "download"){ //下载
            window.resources("SYS_ROBOT_EMBEDDED_VERSION_DOWNLOAD", function (e) {
                if (e.state == "200") {
                    window.location.href = "/system/robotEmbeddedVersion/download?fileName=" + data.embeddedUrl
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddRobotEmbeddedVersion').click(function(){
        window.resources("SYS_ROBOT_EMBEDDED_VERSION_INSERT", function (e) {
            if (e.state == "200") {
                openAddRobotEmbeddedVersion();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddRobotEmbeddedVersion() {
        layer.open({
            type : 2,
            title: '上传嵌入式软件',
            offset: '10%',
            area: ['800px', '390px'], //宽高
            content: '/page/system/robotEmbeddedVersion/html/robotEmbeddedVersionAdd.html'
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditRobotEmbeddedVersion(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '编辑嵌入式软件',
                offset: '10%',
                area: ['800px', '220px'], //宽高
                content: '/page/system/robotEmbeddedVersion/html/robotEmbeddedVersionEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if (edit){
                        top.layer.close(dex);
                        body.find(".embeddedId").val(edit.embeddedId);
                        body.find(".embeddedDescribe").val(edit.embeddedDescribe);
                        form.render();
                    }
                }
            });
        },500);
    }

    /**
     * 修改状态
     * @param edit
     */
    function updateRobotRunState(edit){
        layer.open({
            type: 2,
            title: '状态修改',
            area: ['300px', '400px'],
            content: '/page/system/robot/html/updateRobotRunState.html',
            tableId: '#robotList',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".robotId").val(edit.robotId);
                    form.render();
                }
            }
        });
    }

    /**
     * 导出EXCEL表
     */
    function downloadExcel(){
        var robotCodeVal = $(".robotCodeVal").val();
        window.location.href = "/system/robot/uploadExcelRobot?robotCode=" + robotCodeVal;
    }

    function onQrCode(edit){
        if (edit.robotCode != '') {
            layer.confirm('确定生成此二维码吗？',{icon:3, title:'提示信息'},function(index){
                var dex = top.layer.msg('二维码生成中，请稍候',{icon: 16,time:false,shade:0.8});
                $.ajax({
                    url: "/system/robot/onQrCode",
                    data: {
                        robotCode : edit.robotCode
                    },
                    type: "POST",
                    cache:false,
                    success: function (data) {
                        if (data.state == "200"){
                            setTimeout(function(){
                                top.layer.msg(data.msg, {icon: 6});
                                top.layer.close(dex);
                                layer.close(index);
                                location.reload();
                            },500);
                        }else if (data.state == "400"){
                            setTimeout(function(){
                                top.layer.close(dex);
                                layer.close(index);
                                top.layer.msg(data.msg, {icon: 5,time: 1000,shift: 6});
                            },500);
                        }
                    }
                });
            });
        } else {
            top.layer.msg("机器人编号为空", {icon: 5,time: 1000,shift: 6});
        }
    }

})
