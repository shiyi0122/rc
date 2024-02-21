layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#scenicSpotList',
        url : '/system/scenicSpot/getScenicSpotList',
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
        id : "scenicSpotListTable",
        cols : [[
            {field: 'scenicSpotId', title: '景区ID', minWidth:100, align:"center"},
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'scenicSpotContact', title: '联系人', minWidth:100, align:"center"},
            {field: 'scenicSpotPhone', title: '联系人电话', minWidth:100, align:"center"},
            {field: 'scenicSpotDeposit', title: '景区押金', minWidth:100, align:"center"},
            // {field: 'treasureCooldownTime', title: '宝箱冷却时间', minWidth:100, align:"center"},
            {field: 'robotWakeupWords', title: '景区状态', minWidth:100, align:"center",templet:function(d){
                    if(d.robotWakeupWords == "1"){
                        return "已运营景区";
                    }else if(d.robotWakeupWords == "2"){
                        return "测试景区";
                    }else if(d.robotWakeupWords == "3"){
                        return "预运营景区";
                    }
                }},
            {title: '操作', minWidth:175, templet:'#scenicSpotListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 根据景区名称模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("scenicSpotListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                scenicSpotName: $(".scenicSpotNameVal").val(), //搜索的关键字
                robotWakeupWords: $(".robotWakeupWords").val() //搜索的关键字
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
    table.on('tool(scenicSpotList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYSTEM_SCENIC_SPOT_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditScenicSpot(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYSTEM_SCENIC_SPOT_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/scenicSpot/delScenicSpot", {scenicSpotId : data.scenicSpotId}, function (data) {}, false,"GET","scenicSpotListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "updateScenicSpotState"){ //修改状态
            window.resources("SYSTEM_SCENIC_SPOT_UPDATE_STATE", function (e) {
                if (e.state == "200") {
                    updateScenicSpotState(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "updateScenicSpotGpsCoordinate"){ //编辑电子围栏
            window.resources("SYSTEM_SCENIC_SPOT_UPDATEGPSCOORDINATE", function (e) {
                if (e.state == "200") {
                    updateScenicSpotGpsCoordinate(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "updateScenicSpotCapprice"){
            window.resources("SYSTEM_SCENIC_SPOT_UPDATE_CAPPRICE", function (e) {
                if (e.state == "200") {
                    updateScenicSpotCapprice(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "scenicSpotPicture"){
            window.resources("SYSTEM_SCENIC_SPOT_UPDATE_CAPPRICE", function (e) {
                if (e.state == "200") {
                    scenicSpotPicture(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddScenicSpot').click(function(){
        window.resources("SYSTEM_SCENIC_SPOT_INSERT", function (e) {
            if (e.state == "200") {
                openAddScenicSpot();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })
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
    //点击添加景区封顶价格
    $('#btnAddScenicSpotCapPrice').click(function(){
        window.resources("SYSTEM_SCENIC_SPOT_ADD_CAPPRICE", function (e) {
            if (e.state == "200") {
                openAddScenicSpotCapPrice();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddScenicSpot() {
        layer.open({
            type : 2,
            title: '添加景区',
            offset: '10%',
            area: ['800px', '80%'],
            content: '/page/system/scenicSpot/html/scenicSpotAdd.html',
            tableId: '#scenicSpotList'
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditScenicSpot(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改景区',
                offset: '10%',
                area: ['800px', '80%'],
                content: '/page/system/scenicSpot/html/scenicSpotEdit.html',
                tableId: '#scenicSpotList',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".scenicSpotId").val(edit.scenicSpotId);
                        body.find(".scenicSpotName").val(edit.scenicSpotName);
                        body.find(".scenicSpotContact").val(edit.scenicSpotContact);
                        body.find(".scenicSpotPhone").val(edit.scenicSpotPhone);
                        body.find(".scenicSpotEmail").val(edit.scenicSpotEmail);
                        body.find(".scenicSpotAddres").val(edit.scenicSpotAddres);
                        body.find(".scenicSpotPostalCode").val(edit.scenicSpotPostalCode);
                        body.find(".scenicSpotOpenword").val(edit.scenicSpotOpenword);
                        body.find(".scenicSpotCloseword").val(edit.scenicSpotCloseword);
                        body.find(".scenicSpotDeposit").val(edit.scenicSpotDeposit);
                        body.find(".scenicSpotWeekendPrice").val(edit.scenicSpotWeekendPrice);
                        body.find(".scenicSpotNormalPrice").val(edit.scenicSpotNormalPrice);
                        body.find(".scenicSpotWeekendRentPrice").val(edit.scenicSpotWeekendRentPrice);
                        body.find(".scenicSpotNormalRentPrice").val(edit.scenicSpotNormalRentPrice);
                        /*body.find(".weekendCappedPrice").val(edit.weekendCappedPrice);*/
                        /*body.find(".normalCappedPrice").val(edit.normalCappedPrice);*/
                        body.find(".scenicSpotWeekendTime").val(edit.scenicSpotWeekendTime);
                        body.find(".scenicSpotNormalTime").val(edit.scenicSpotNormalTime);
                        body.find(".scenicSpotRentTime").val(edit.scenicSpotRentTime);
                        body.find(".scenicSpotBeyondPrice").val(edit.scenicSpotBeyondPrice);
                        body.find(".spotFId").val(edit.scenicSpotFid);
                        body.find(".treasureCooldownTime").val(edit.treasureCooldownTime);
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
    function updateScenicSpotState(edit){
        layer.open({
            type: 2,
            title: '修改状态',
            area: ['300px', '400px'],
            content: '/page/system/scenicSpot/html/updateScenicSpotState.html',
            tableId: '#scenicSpotList',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".scenicSpotId").val(edit.scenicSpotId);
                    form.render();
                }
            }
        });
    }

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
     * 添加景区封顶价格
     */
    function openAddScenicSpotCapPrice(){
        layer.open({
            type : 2,
            title: '添加景区封顶价格',
            offset: '10%',
            area: ['60%', '80%'],
            content: '/page/system/scenicSpot/html/scenicSpotCapPriceAdd.html'
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
            tableId: '#scenicSpotList',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    $.ajax({
                        type : 'POST',
                        url : '/system/scenicSpot/LoadEditScenicSpot',
                        data : {scenicSpotId : edit.scenicSpotId},
                        dataType : 'json',
                        success:function (data) {
                            if (data.state == "200"){
                                top.layer.close(dex);
                                body.find(".coordinateId").val(data.data.coordinateId);
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
    function updateScenicSpotCapprice(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        layer.open({
            type : 2,
            title: '修改景区封顶价格',
            offset: '10%',
            area: ['60%', '75%'],
            content: '/page/system/scenicSpot/html/scenicSpotCapPriceEdit.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    $.ajax({
                        type : 'POST',
                        url : '/system/scenicSpot/loadEditScenicSpotCapPrice',
                        data : {scenicSpotId : edit.scenicSpotId},
                        dataType : 'json',
                        success:function (data) {
                            if (data.state == "200"){
                                top.layer.close(dex);
                                body.find(".capPriceId").val(data.data.capPriceId);
                                body.find(".scenicSpotId").val(data.data.scenicSpotId);
                                body.find(".scenicSpotName").val(data.data.scenicSpotName);
                                body.find(".normalCapOneTime").val(data.data.normalCapOneTime);
                                body.find(".normalCapOnePrice").val(data.data.normalCapOnePrice);
                                body.find(".normalCapOneUnitPrice").val(data.data.normalCapOneUnitPrice);
                                body.find(".normalCapTwoTime").val(data.data.normalCapTwoTime);
                                body.find(".normalCapTwoPrice").val(data.data.normalCapTwoPrice);
                                body.find(".normalCapTwoUnitPrice").val(data.data.normalCapTwoUnitPrice);
                                body.find(".weekendCapOneTime").val(data.data.weekendCapOneTime);
                                body.find(".weekendCapOnePrice").val(data.data.weekendCapOnePrice);
                                body.find(".weekendCapOneUnitPrice").val(data.data.weekendCapOneUnitPrice);
                                body.find(".weekendCapTwoTime").val(data.data.weekendCapTwoTime);
                                body.find(".weekendCapTwoPrice").val(data.data.weekendCapTwoPrice);
                                body.find(".weekendCapTwoUnitPrice").val(data.data.weekendCapTwoUnitPrice);
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

    /**
     * 上传景区照片
     * @param edit
     */
    function scenicSpotPicture(edit){
        layer.open({
            type: 2,
            title: '上传景区照片',
            area: ['800px', '330px'],
            content: '/page/system/scenicSpot/html/scenicSpotPicture.html',
            tableId: '#scenicSpotList',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".scenicSpotId").val(edit.scenicSpotId);
                    body.find(".scenicSpotName").val(edit.scenicSpotName);
                    form.render();
                }
            }
        });
    }

})
