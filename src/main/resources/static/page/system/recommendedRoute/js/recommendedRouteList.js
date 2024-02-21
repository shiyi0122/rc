layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#recommendedRouteList',
        url : '/system/recommendedRoute/getRecommendedRouteList',
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
        id : "recommendedRouteListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'routeName', title: '经典路线名称', minWidth:100, align:"center"},
            {field: 'routeNamePinYin', title: '经典路线拼音', minWidth:100, align:"center"},
            {field: 'routeIntroduce', title: '经典路线介绍', minWidth:100, align:"center"},
            {field: 'routeGps', title: 'WGS84坐标组', minWidth:100, align:"center"},
            {field: 'routeGpsBaiDu', title: '百度坐标组', minWidth:100, align:"center"},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#recommendedRouteListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        if($(".coordinateInnercircleNameVal").val() != ''){
            table.reload("recommendedRouteListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    routeName: $(".routeNameVal").val()  //搜索的关键字
                }
            })
        }else{
            layer.msg("请输入搜索的经典路线名称");
        }
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })

    //列表操作
    table.on('tool(recommendedRouteList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("RESOURCES_RECOMMENDED_ROUTE_UODATE", function (e) {
                if (e.state == "200") {
                    openEditRecommendedRoute(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("RESOURCES_RECOMMENDED_ROUTE_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/recommendedRoute/delRecommendedRoute", {routeId : data.routeId}, function (data) {}, false,"GET","recommendedRouteListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddInnercircle').click(function(){
        window.resources("RESOURCES_RECOMMENDED_ROUTE_INSERT", function (e) {
            if (e.state == "200") {
                openAddRecommendedRoute();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddRecommendedRoute() {
        layer.open({
            type : 2,
            title: '添加经典路线',
            offset: '10%',
            area: ['800px', '520px'], //宽高
            content: '/page/system/recommendedRoute/html/recommendedRouteAdd.html',
            tableId: '#recommendedRouteList',
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
    function openEditRecommendedRoute(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改经典路线',
                offset: '10%',
                area: ['800px', '520px'], //宽高
                content: '/page/system/recommendedRoute/html/recommendedRouteEdit.html',
                tableId: '#recommendedRouteList',
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
                                    body.find(".routeId").val(edit.routeId);
                                    body.find(".routeName").val(edit.routeName);
                                    body.find(".routeIntroduce").val(edit.routeIntroduce);
                                    body.find(".routeGps").val(edit.routeGps);
                                    body.find(".routeGpsBaiDu").val(edit.routeGpsBaiDu);
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