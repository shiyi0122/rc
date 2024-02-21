layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#innercircleList',
        url : '/system/innercircle/getInnercircleList',
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
        id : "innercircleListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'coordinateInnercircleName', title: '内圈名称', minWidth:100, align:"center"},
            {field: 'coordinateInnercircleContent', title: '内圈内容', minWidth:100, align:"center"},
            {field: 'coordinateInnercircle', title: '内圈WGS84坐标组', minWidth:100, align:"center"},
            {field: 'coordinateInnercircleBaiDu', title: '内圈百度坐标组', minWidth:100, align:"center"},
            {field: 'bufferRadius', title: '警告缓冲圈(单位:米)', minWidth:100, align:"center"},
            {field: 'innercircleType', title: '状态', minWidth:100, align:"center",templet:function(d){
                    if(d.innercircleType == "1"){
                        return "启用";
                    }else if(d.innercircleType == "0"){
                        return "禁用";
                    }
                }},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#innercircleListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        if($(".coordinateInnercircleNameVal").val() != ''){
            table.reload("innercircleListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    coordinateInnercircleName: $(".coordinateInnercircleNameVal").val()  //搜索的关键字
                }
            })
        }else{
            layer.msg("请输入搜索的禁区名称");
        }
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })

    //列表操作
    table.on('tool(innercircleList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("RESOURCES_INNERCIRCLE_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditInnercircle(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("RESOURCES_INNERCIRCLE_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/innercircle/delInnercircle", {coordinateInnercircleId : data.coordinateInnercircleId}, function (data) {}, false,"GET","innercircleListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "open"){ //修改为禁用
            window.resources("RESOURCES_INNERCIRCLE_EDITVALID", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定修改为禁用吗？',{icon:3, title:'提示信息'},function(index){
                        var dex = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
                        $.ajax({
                            url: "/system/innercircle/editValid",
                            data: {
                                coordinateInnercircleId : data.coordinateInnercircleId,
                                innercircleType : 0
                            },
                            type: "POST",
                            cache:false,
                            success: function (data) {
                                if (data.state == "200"){
                                    setTimeout(function(){
                                        top.layer.msg(data.msg, {icon: 6});
                                        top.layer.close(dex);
                                        layer.close(index);
                                        layui.table.reload("innercircleListTable");
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
        }else if (layEvent === "forbidden"){ //修改为启用
            window.resources("RESOURCES_INNERCIRCLE_EDITVALID", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定修改为启用吗？',{icon:3, title:'提示信息'},function(index){
                        var dex = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
                        $.ajax({
                            url: "/system/innercircle/editValid",
                            data: {
                                coordinateInnercircleId : data.coordinateInnercircleId,
                                innercircleType : 1
                            },
                            type: "POST",
                            cache:false,
                            success: function (data) {
                                if (data.state == "200"){
                                    setTimeout(function(){
                                        top.layer.msg(data.msg, {icon: 6});
                                        top.layer.close(dex);
                                        layer.close(index);
                                        layui.table.reload("innercircleListTable");
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
    $('#btnAddInnercircle').click(function(){
        window.resources("RESOURCES_INNERCIRCLE_INSERT", function (e) {
            if (e.state == "200") {
                openAddInnercircle();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddInnercircle() {
        layer.open({
            type : 2,
            title: '添加内圈禁区',
            offset: '10%',
            area: ['800px', '580px'], //宽高
            content: '/page/system/innercircle/html/innercircleAdd.html',
            tableId: '#innercircleList',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                $.ajax({
                    type : 'POST',
                    url : '/system/scenicSpot/getCurrentScenicSpot',
                    dataType : 'json',
                    success:function (data) {
                        if (data.state == "200"){
                            body.find(".coordinateId").val(data.data.scenicSpotId);//景区ID
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
    function openEditInnercircle(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改内圈禁区',
                offset: '10%',
                area: ['800px', '580px'], //宽高
                content: '/page/system/innercircle/html/innercircleEdit.html',
                tableId: '#innercircleList',
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
                                    body.find(".coordinateId").val(data.data.scenicSpotId);//景区ID
                                    body.find(".scenicSpotName").val(data.data.scenicSpotName);//景区名称
                                    body.find(".coordinateInnercircleId").val(edit.coordinateInnercircleId);
                                    body.find(".coordinateInnercircleName").val(edit.coordinateInnercircleName);
                                    body.find(".coordinateInnercircleContent").val(edit.coordinateInnercircleContent);
                                    body.find(".coordinateInnercircle").val(edit.coordinateInnercircle);
                                    body.find(".coordinateInnercircleBaiDu").val(edit.coordinateInnercircleBaiDu);
                                    body.find(".bufferRadius").val(edit.bufferRadius);
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
    //导出Excel
    $('#downloadExcel').click(function(){
        window.resources("RESOURCES_INNERCIRCLE_DELETE", function (e) {
            if (e.state == "200") {
                downloadExcel();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    function downloadExcel(){
        window.location.href = "/system/innercircle/getInnercricleExcel";
    }

    //导入
    upload.render({
        elem: '#importExcel'
        ,url: '/system/innercircle/upInnercricleExcel'
        ,accept: 'file' //普通文件
        ,exts: 'xls|xlsx' //只允许上传Excel文件
        ,done: function(res){
            if (res.state == 200){
                setTimeout(function(){
                    top.layer.msg(res.msg, {icon: 6});
                    layui.table.reload("innercircleListTable");
                },500);
            }else{
                setTimeout(function(){
                    top.layer.msg(res.msg, {icon: 5,time: 1000,shift: 6});
                },500);
            }
        }
    });

})
