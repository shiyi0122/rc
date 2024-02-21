layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#broadcastList',
        url : '/system/broadcast/getBroadcastList',
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
        id : "broadcastListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'broadcastName', title: '景点名称', minWidth:100, align:"center"},
            {field: 'pinYinName', title: '景点名称拼音格式', minWidth:100, align:"center"},
            {field: 'broadcastGps', title: 'WGS84坐标', minWidth:100, align:"center"},
            {field: 'broadcastGpsBaiDu', title: '百度坐标', minWidth:100, align:"center"},
            {field: 'scenicSpotRange', title: '坐标半径', minWidth:100, align:"center"},
            {field: 'createDate', title: '创建时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#broadcastListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(broadcastList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYSTEM_BROADCAST_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditBroadcast(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYSTEM_BROADCAST_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/broadcast/delBroadcast", {broadcastId : data.broadcastId , scenicSpotId : data.scenicSpotId}, function (data) {}, false,"GET","broadcastListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent == 'details'){
            viewDetails(data);
        }
    });

    /**
     * 跳转详情页面
     * @param edit
     */
    function viewDetails(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '资源详情',
                offset: '10%',
                area: ['1000px', '580px'], //宽高
                content: '/page/system/broadcast/html/broadcastContentList.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".broadcastId").val(edit.broadcastId);
                        form.render();
                    }
                }
            });
        },500);
    }

    //点击添加按钮
    $('#btnAddBroadcast').click(function(){
        window.resources("SYSTEM_BROADCAST_INSERT", function (e) {
            if (e.state == "200") {
                openAddBroadcast();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    //点击导出EXCEL按钮
    $('#downloadExcel').click(function(){
        window.resources("SYSTEM_BROADCAST_INSERT", function (e) {
            if (e.state == "200") {
                downloadExcel();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    //导入Excel表
    upload.render({
        elem: '#importExcel'
        ,url: '/system/broadcast/importExcel'
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
                    layui.table.reload("broadcastListTable");
                },500);
            }else{
                setTimeout(function(){
                    top.layer.msg(res.msg, {icon: 5,time: 1000,shift: 6});
                    layer.closeAll();
                },500);
            }
        }
    });

    /**
     * 导出EXCEL表
     */
    function downloadExcel(){
        window.location.href = "/system/broadcast/uploadExcelBroadcast";
    }

    /**
     * 弹出添加内容框
     */
    function openAddBroadcast() {
        layer.open({
            type : 2,
            title: '添加景点',
            offset: '10%',
            area: ['800px', '460px'],
            content: '/page/system/broadcast/html/broadcastAdd.html',
            tableId: '#broadcastList',
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
     * @param edit
     */
    function openEditBroadcast(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改景点',
                offset: '10%',
                area: ['800px', '580px'],
                content: '/page/system/broadcast/html/broadcastEdit.html',
                tableId: '#broadcastList',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    $.ajax({
                        type : 'POST',
                        url : '/system/scenicSpot/getCurrentScenicSpot',
                        dataType : 'json',
                        success:function (data) {
                            if (data.state == "200"){
                                if(edit){
                                    top.layer.close(dex);
                                    body.find(".scenicSpotId").val(edit.scenicSpotId);//景区ID
                                    body.find(".scenicSpotName").val(data.data.scenicSpotName);//景区名称
                                    body.find(".broadcastName").val(edit.broadcastName);
                                    body.find(".broadcastGps").val(edit.broadcastGps);
                                    body.find(".broadcastGpsBaiDu").val(edit.broadcastGpsBaiDu);
                                    body.find(".scenicSpotRange").val(edit.scenicSpotRange);
                                    body.find(".broadcastPriority").val(edit.broadcastPriority);
                                    body.find(".broadcastId").val(edit.broadcastId);
                                    body.find(".sortType").val(edit.sortType);
                                    form.render();
                                }
                            }else if(data.state == "400"){
                                layer.msg(data.msg);
                            }
                        }
                    })
                }
            });
        },500);
    }

})
