layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    table.render({
        elem: '#robotObstacleAvoidanceModuleList',
        url : '/system/robotObstacleAvoidanceModule/getRobotObstacleAvoidanceModuleList',
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
        id : "robotObstacleAvoidanceModuleListTable",
        cols : [[
            {field: 'obstacleAvoidanceId', title: '避障模块ID', minWidth:100, align:"center"},
            {field: 'obstacleAvoidanceName', title: '避障模块名称', minWidth:100, align:"center"},
            {field: 'modularModel', title: '模块型号', minWidth:100, align:"center"},
            {field: 'modularManufactor', title: '模块厂家', minWidth:100, align:"center"},
            {field: 'modularId', title: '模块ID', minWidth:100, align:"center"},
            {field: 'remarks', title: '备注', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#robotObstacleAvoidanceModuleListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("robotObstacleAvoidanceModuleListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                obstacleAvoidanceId : $(".obstacleAvoidanceId").val()
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
    table.on('tool(robotObstacleAvoidanceModuleList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYSTEM_ROBOT_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditRobotObstacleAvoidanceModule(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYSTEM_ROBOT_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/robotObstacleAvoidanceModule/delRobotObstacleAvoidanceModule", {obstacleAvoidanceModularId : data.obstacleAvoidanceModularId}, function (data) {}, false,"GET","robotObstacleAvoidanceModuleListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击导出EXCEL表
    $('#downloadExcel').click(function(){
        window.resources("SYSTEM_ROBOT_DOWNLOADEXCEL", function (e) {
            if (e.state == "200") {
                downloadExcel();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 导出EXCEL表
     */
    function downloadExcel(){
        var obstacleAvoidanceId = $(".obstacleAvoidanceId").val();
        window.location.href = "/system/robotObstacleAvoidanceModule/uploadExcelRobotObstacleAvoidanceModule?obstacleAvoidanceId=" + obstacleAvoidanceId;
    }

    /**
     * 弹出编辑框
     */
    function openEditRobotObstacleAvoidanceModule(edit) {
        layer.open({
            type : 2,
            title: '编辑机器人避障模块信息',
            offset: '10%',
            area: ['800px', '550px'], //宽高
            content: '/page/assetsSystem/robotObstacleAvoidanceModule/html/robotSoftAssetInformationEdit.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if (edit){
                    body.find(".obstacleAvoidanceModularId").val(edit.obstacleAvoidanceModularId);
                    body.find(".obstacleAvoidanceId").val(edit.obstacleAvoidanceId);
                    body.find(".obstacleAvoidanceName").val(edit.obstacleAvoidanceName);
                    body.find(".modularModel").val(edit.modularModel);
                    body.find(".modularManufactor").val(edit.modularManufactor);
                    body.find(".modularId").val(edit.modularId);
                    body.find(".remarks").val(edit.remarks);
                    form.render();
                }
            }
        });
    };

    //导入Excel表
    upload.render({
        elem: '#importExcel'
        ,url: '/system/robotBelarcAdvisor/upload'
        ,accept: 'file' //普通文件
        ,exts: 'xls|xlsx' //只允许上传Excel文件
        ,before:function(obj){
            layer.load(); //上传loading
        }
        ,done: function(res){
            if (res.state == 200){
                setTimeout(function(){
                    layer.alert(res.msg,function(){
                        layer.closeAll();//关闭所有弹框
                    });
                    layui.table.reload("robotArchivesListTable");
                },500);
            }else{
                setTimeout(function(){
                    top.layer.msg(res.msg, {icon: 5,time: 1000,shift: 6});
                    layer.closeAll();
                },500);
            }
        }
    });

    //点击添加按钮
    $('#btnAdd').click(function(){
        window.resources("SYSTEM_ROBOT_INSERT", function (e) {
            if (e.state == "200") {
                openAddRobotObstacleAvoidanceModule();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddRobotObstacleAvoidanceModule() {
        layer.open({
            type : 2,
            title: '添加机器人避障模块信息',
            offset: '10%',
            area: ['800px', '550px'], //宽高
            content: '/page/assetsSystem/robotObstacleAvoidanceModule/html/robotObstacleAvoidanceModuleAdd.html'
        });
    };

})
