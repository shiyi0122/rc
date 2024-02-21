layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#scenicSpotGpsCoordinateList',
        url : '/system/scenicSpot/getScenicSpotGpsCoordinateList',
        cellMinWidth : 150,
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
        id : "scenicSpotGpsCoordinateListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'coordinateOuterring', title: 'WGS84坐标系', minWidth:100, align:"center"},
            {field: 'coordinateOuterringBaiDu', title: '百度坐标系', minWidth:100, align:"center"},
            {field: 'warningLoopCoordinateGroup', title: '外侧缓冲区', minWidth:100, align:"center"},
            {field: 'insideWarning', title: '内圈缓冲区', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#scenicSpotGpsCoordinateListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 根据景区名称模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("scenicSpotGpsCoordinateListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                scenicSpotName: $(".scenicSpotNameVal").val()
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
    table.on('tool(scenicSpotGpsCoordinateList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === "edit"){ //编辑电子围栏
            window.resources("SYSTEM_SCENIC_SPOT_UPDATEGPSCOORDINATE", function (e) {
                if (e.state == "200") {
                    updateScenicSpotGpsCoordinate(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

    //点击添加景区围栏按钮
    $('#btnAddScenicSpotGpsCoordinate').click(function(){
        window.resources("SYSTEM_SCENIC_SPOT_INSERTGPSCOORDINATE", function (e) {
            if (e.state == "200") {
                openAddScenicSpotGpsCoordinate();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })


    /**
     * 添加景区围栏
     */
    function openAddScenicSpotGpsCoordinate(){
        layer.open({
            type : 2,
            title: '添加景区围栏',
            offset: '10%',
            area: ['60%', '80%'],
            content: '/page/system/scenicSpot/html/scenicSpotGpsCoordinateAdd.html',
            tableId: '#scenicSpotList'
        });
    }

    /**
     * 修改景区围栏
     */
    function updateScenicSpotGpsCoordinate(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        layer.open({
            type : 2,
            title: '修改景区围栏',
            offset: '10%',
            area: ['60%', '75%'],
            content: '/page/system/scenicSpot/html/scenicSpotGpsCoordinateEdit.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    $.ajax({
                        type : 'POST',
                        url : '/system/scenicSpot/LoadEditScenicSpot',
                        data : {scenicSpotId : edit.coordinateScenicSpotId},
                        dataType : 'json',
                        success:function (data) {
                            if (data.state == "200"){
                                top.layer.close(dex);
                                body.find(".coordinateId").val(data.data.coordinateId);
                                body.find(".coordinateScenicSpotId").val(edit.coordinateScenicSpotId);
                                body.find(".coordinateOuterring").val(data.data.coordinateOuterring);
                                body.find(".coordinateOuterringBaiDu").val(data.data.coordinateOuterringBaiDu);
                                body.find(".warningLoopCoordinateGroup").val(data.data.warningLoopCoordinateGroup);
                                body.find(".insideWarning").val(data.data.insideWarning);
                                form.render();
                            }else if(data.state == "400"){
                                top.layer.close(dex);
                                layer.msg(data.msg);
                            }
                        }
                    })
                }
            }
        });
    }

})
