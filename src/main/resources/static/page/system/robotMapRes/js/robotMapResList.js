layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#robotMapResList',
        url : '/system/robotMapRes/getRobotMapResList',
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
        id : "robotMapResListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'resUrl', title: '资源地址', minWidth:100, align:"center"},
            {field: 'resSize', title: '资源大小', minWidth:100, align:"center"},
            {field: 'resVersion', title: '地图版本号', minWidth:100, align:"center"},
            {field: 'resType', title: '是否启用', align:'center',templet:function(d){
                    if(d.resType == "1"){
                        return "启用";
                    }else if(d.resType == "0"){
                        return "禁用";
                    }
                }},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#robotMapResListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(robotMapResList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'del'){ //删除
            window.resources("SYS_ROBOT_MAP_RES_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/robotMapRes/delRobotMapRes", {resId : data.resId}, function (data) {}, false,"GET","robotMapResListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "download"){ //下载
            window.resources("SYS_ROBOT_MAP_RES_DOWNLOAD", function (e) {
                if (e.state == "200") {

                    window.location.href = "/system/robotMapRes/download?fileName=" + data.resUrl
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "forbidden"){ //修改为启用
            window.resources("SYS_ROBOT_MAP_RES_UPDATE", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定修改为启用吗？',{icon:3, title:'提示信息'},function(index){
                        var dex = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
                        $.ajax({
                            url: "/system/robotMapRes/editResType",
                            data: {
                                resId : data.resId,
                                resType : 1
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
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "open"){ //修改为禁用
            window.resources("SYS_ROBOT_MAP_RES_UPDATE", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定修改为禁用吗？',{icon:3, title:'提示信息'},function(index){
                        var dex = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
                        $.ajax({
                            url: "/system/robotMapRes/editResType",
                            data: {
                                resId : data.resId,
                                resType : 0
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
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddRobotMapRes').click(function(){
        window.resources("SYS_ROBOT_MAP_RES_INSERT", function (e) {
            if (e.state == "200") {
                openAddRobotMapRes();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddRobotMapRes(edit) {
        layer.open({
            type : 2,
            title: '上传地图资源',
            offset: '10%',
            area: ['800px', '330px'], //宽高
            content: '/page/system/robotMapRes/html/robotMapResAdd.html'
        });
    };

})
