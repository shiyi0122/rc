layui.use(['form','layer','table','laytpl',"upload"],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#parkingList',
        url : '/system/parking/getParkingList',
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
        id : "parkingListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'parkingName', title: '停放点名称', minWidth:100, align:"center"},
            {field: 'parkingContent', title: '停放点内容', minWidth:100, align:"center"},
            {field: 'parkingCoordinateGroup', title: 'WGS84坐标组', minWidth:100, align:"center"},
            {field: 'parkingCoordinateGroupBaidu', title: '百度坐标组', minWidth:100, align:"center"},
            {field: 'parkingType', title: '状态', minWidth:100, align:"center",templet:function(d){
                    if(d.parkingType == "1"){
                        return "启用";
                    }else if(d.parkingType == "0"){
                        return "禁用";
                    }
                }},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#parkingListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        if($(".coordinateInnercircleNameVal").val() != ''){
            table.reload("parkingListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    parkingName: $(".parkingNameVal").val()  //搜索的关键字
                }
            })
        }else{
            layer.msg("请输入搜索的停放点名称");
        }
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })

    //列表操作
    table.on('tool(parkingList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("RESOURCES_PARKING_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditParking(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("RESOURCES_PARKING_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/parking/delParking", {parkingId : data.parkingId}, function (data) {}, false,"GET","parkingListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "open"){ //修改为禁用
            window.resources("RESOURCES_PARKING_EDITVALID", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定修改为禁用吗？',{icon:3, title:'提示信息'},function(index){
                        var dex = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
                        $.ajax({
                            url: "/system/parking/editParking",
                            data: {
                                parkingId : data.parkingId,
                                parkingType : 0
                            },
                            type: "POST",
                            cache:false,
                            success: function (data) {
                                if (data.state == "200"){
                                    setTimeout(function(){
                                        top.layer.msg(data.msg, {icon: 6});
                                        top.layer.close(dex);
                                        layer.close(index);
                                        layui.table.reload("parkingListTable");
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
            window.resources("RESOURCES_PARKING_EDITVALID", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定修改为启用吗？',{icon:3, title:'提示信息'},function(index){
                        var dex = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
                        $.ajax({
                            url: "/system/parking/editParking",
                            data: {
                                parkingId : data.parkingId,
                                parkingType : 1
                            },
                            type: "POST",
                            cache:false,
                            success: function (data) {
                                if (data.state == "200"){
                                    setTimeout(function(){
                                        top.layer.msg(data.msg, {icon: 6});
                                        top.layer.close(dex);
                                        layer.close(index);
                                        layui.table.reload("parkingListTable");
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
        window.resources("RESOURCES_PARKING_INSERT", function (e) {
            if (e.state == "200") {
                openAddParking();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddParking() {
        layer.open({
            type : 2,
            title: '添加停放点',
            offset: '10%',
            area: ['800px', '520px'], //宽高
            content: '/page/system/Parking/html/parkingAdd.html',
            tableId: '#parkingList',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                $.ajax({
                    type : 'POST',
                    url : '/system/scenicSpot/getCurrentScenicSpot',
                    dataType : 'json',
                    success:function (data) {
                        if (data.state == "200"){
                            body.find(".parkingScenicSpotId").val(data.data.scenicSpotId);//景区ID
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
    function openEditParking(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改停放点',
                offset: '10%',
                area: ['800px', '520px'], //宽高
                content: '/page/system/Parking/html/parkingEdit.html',
                tableId: '#parkingList',
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
                                    body.find(".parkingScenicSpotId").val(edit.parkingScenicSpotId);//景区ID
                                    body.find(".scenicSpotName").val(data.data.scenicSpotName);//景区名称
                                    body.find(".parkingId").val(edit.parkingId);
                                    body.find(".parkingName").val(edit.parkingName);
                                    body.find(".parkingContent").val(edit.parkingContent);
                                    body.find(".parkingCoordinateGroup").val(edit.parkingCoordinateGroup);
                                    body.find(".parkingCoordinateGroupBaidu").val(edit.parkingCoordinateGroupBaidu);
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
        window.resources("RESOURCES_PARKING_UPDATE", function (e) {
            if (e.state == "200") {
                downloadExcel();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    function downloadExcel(){
        window.location.href = "/system/parking/getParkingExcel";
    }

    upload.render({
        elem: '#importExcel'
        ,url: '/system/parking/upParkingExcel'
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
