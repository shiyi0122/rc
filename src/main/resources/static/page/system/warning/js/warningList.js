layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#warningList',
        url : '/system/warning/getWarningList',
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
        id : "warningListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'warningName', title: '警告名称', minWidth:100, align:"center"},
            {field: 'warningGps', title: 'WGS84坐标', minWidth:100, align:"center"},
            {field: 'warningGpsBaiDu', title: '百度坐标', minWidth:100, align:"center"},
            {field: 'warningContent', title: '警告内容', minWidth:100, align:"center"},
            {field: 'warningPic', title: '警告图片', minWidth:100, align:"center"},
            {field: 'warningRadius', title: '警告点半径', minWidth:100, align:"center"},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#warningListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(warningList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("RESOURCES_WARNING_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditWarning(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("RESOURCES_WARNING_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/warning/delWarning", {warningId : data.warningId}, function (data) {}, false,"GET","warningListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddWarning').click(function(){
        window.resources("RESOURCES_WARNING_INSERT", function (e) {
            if (e.state == "200") {
                openAddWarning();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddWarning() {
        layer.open({
            type : 2,
            title: '添加警告',
            offset: '10%',
            area: ['800px', '650px'], //宽高
            content: '/page/system/warning/html/warningAdd.html',
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
    function openEditWarning(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改警告',
                offset: '10%',
                area: ['800px', '650px'], //宽高
                content: '/page/system/warning/html/warningEdit.html',
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
                                    body.find(".scenicSpotId").val(data.data.scenicSpotId);//景区ID
                                    body.find(".scenicSpotName").val(data.data.scenicSpotName);//景区名称
                                    body.find(".warningId").val(edit.warningId);
                                    body.find(".warningName").val(edit.warningName);
                                    body.find(".warningGps").val(edit.warningGps);
                                    body.find(".warningGpsBaiDu").val(edit.warningGpsBaiDu);
                                    body.find(".warningContent").val(edit.warningContent);
                                    body.find(".warningRadius").val(edit.warningRadius);
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

    $('#downloadExcel').click(function(){
        window.resources("RESOURCES_WARNING_UPDATE", function (e) {
            if (e.state == "200") {
                downloadExcel();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    function downloadExcel(){
        window.location.href = "/system/warning/getWarningExcel";
    }
    upload.render({
        elem: '#importExcel'
        ,url: '/system/warning/upWarningExcel'
        ,accept: 'file' //普通文件
        ,exts: 'xls|xlsx' //只允许上传Excel文件
        ,done: function(res){
            if (res.state == 200){
                setTimeout(function(){
                    top.layer.msg(res.msg, {icon: 6});
                    layui.table.reload("parkingListTable");
                },500);
            }else{
                setTimeout(function(){
                    top.layer.msg(res.msg, {icon: 5,time: 1000,shift: 6});
                },500);
            }
        }
    });

})