layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#treasureList',
        url : '/system/scenicSpotDateHunt/getSpotDateHuntList',
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
        id : "treasureListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'randomTime', title: '随机时间', minWidth:100, align:"center"},
            {field: 'sort', title: '排序', minWidth:100, align:"center"},
            {field: 'createDate',title: '创建时间',minWidth: 100, align: "center"},
            // {field: 'prizeWeight', title: '权重', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#treasureListBar',fixed:"right",align:"center"}

        ]]
    });
    /**
     * 根据条件模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("treasureListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
               scenicSpotId: $(".scenicSpotId").val(),
                scenicSpotName: $(".scenicSpotName").val(),
                // treasureName: $(".treasureName").val(),
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
    table.on('tool(treasureList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;


        if(layEvent === 'edit'){ //编辑
            window.resources("SYS_SCENIC_SPOT_DATE", function (e) {
                if (e.state == "200") {
                    openEditTreasure(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYS_SCENIC_SPOT_DATE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/scenicSpotDateHunt/delSpotDateHunt", {dateTreasureId : data.dateTreasureId , scenicSpotId : data.spotId}, function (data) {}, false,"GET","treasureListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
        // else if(layEvent === "open"){//修改为关闭
        //    // console.log("1111111");
        //     window.resources("SYSTEM_TREASUREHUNT_UPDATE",function (e) {
        //         console.log(e);
        //         if (e.state == "200"){
        //             layer.confirm('确定修改为关闭吗？',{icon:3, title:'提示信息'},function(index){
        //                 var dex = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        //                 $.ajax({
        //                     url: "/system/treasureHunt/switchBroadcastHunt",
        //                     data: {
        //                         broadcastId : data.broadcastId,
        //                         switchs : 0
        //                     },
        //                     type: "POST",
        //                     cache:false,
        //                     success: function (data) {
        //                         if (data.state == "200"){
        //                             setTimeout(function(){
        //                                 top.layer.msg(data.msg, {icon: 6});
        //                                 top.layer.close(dex);
        //                                 layer.close(index);
        //                                 layui.table.reload("broadcastListTable");
        //                             },500);
        //                         }else if (data.state == "400"){
        //                             setTimeout(function(){
        //                                 top.layer.close(dex);
        //                                 layer.close(index);
        //                                 top.layer.msg(data.msg, {icon: 5,time: 1000,shift: 6});
        //                             },500);
        //                         }
        //                     }
        //                 });
        //             });
        //         }else {
        //         //    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
        //         }
        //     },false,"GET")
        // }else if (layEvent == "forbidden"){
        //     window.resources("SYSTEM_TREASUREHUNT_UPDATE",function (e){
        //         if (e.state == "200"){
        //             layer.confirm('确定修改为开启吗？',{icon:3, title:'提示信息'},function(index){
        //                 var dex = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        //                 $.ajax({
        //                     url: "/system/treasureHunt/switchBroadcastHunt",
        //                     data: {
        //                         broadcastId : data.broadcastId,
        //                         switchs : 1
        //                     },
        //                     type: "POST",
        //                     cache:false,
        //                     success: function (data) {
        //                         if (data.state == "200"){
        //                             setTimeout(function(){
        //                                 top.layer.msg(data.msg, {icon: 6});
        //                                 top.layer.close(dex);
        //                                 layer.close(index);
        //                                 layui.table.reload("broadcastListTable");
        //                             },500);
        //                         }else if (data.state == "400"){
        //                             setTimeout(function(){
        //                                 top.layer.close(dex);
        //                                 layer.close(index);
        //                                 top.layer.msg(data.msg, {icon: 5,time: 1000,shift: 6});
        //                             },500);
        //                         }
        //                     }
        //                 });
        //             });
        //         }else {
        //             layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
        //         }
        //     },false,"GET")
        // } else if(layEvent == 'details'){
        //     viewDetails(data);
        // }
    });


    //点击添加按钮
    $('#btnAddTreasure').click(function(){
        window.resources("SYS_SCENIC_SPOT_DATE", function (e) {
            if (e.state == "200") {
                openAddTreasure();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    //开启关闭活动
    $('#kqhd').click(function(data){

        // console.log(data)
        data =  $("#activeState option:selected").val();
        console.log("111")
        console.log(data)
        activityOn(data);
    })



    //寻宝开启关闭
    function activityOn(data) {

        if (data.indexOf("0")>=0){
            data = "2";
        }else {
            data = "0";
        }
        console.log("222")
        console.log(data)
        //  window.location.href = "/system/scenicSpot/updateScenicSpotSwitchs";
        $.ajax({
            url: "/system/scenicSpot/updateScenicSpotSwitchs",
            data: {
                switchs : data
            },
            type: "POST",
            cache:false,
            success: function (data) {
                if (data.state == "200"){
                    setTimeout(function(){
                        top.layer.msg(data.msg, {icon: 6});
                        // top.layer.close(dex);
                        //   layer.close(index);
                        //   layui.table.reload("broadcastListTable");
                        location.reload();
                    },500);
                }else if (data.state == "400"){
                    setTimeout(function(){

                        // top.layer.close(dex);
                        // layer.close(index);
                        top.layer.msg(data.msg, {icon: 5,time: 1000,shift: 6});
                    },500);
                }
            }
        });
    }

    //一键开启关闭活动
    $('#yjkqhd').click(function(data){

        // console.log(data)
        data =  $("#activeState option:selected").val();
        // console.log("111")
        // console.log(data)
        activityOneTouch(data);
    })

    //一键寻宝开启关闭
    function activityOneTouch(data) {

        if (data.indexOf("0")>=0){
            data = "2";
        }else {
            data = "0";
        }
        // console.log("222")
        // console.log(data)
        //  window.location.href = "/system/scenicSpot/updateScenicSpotSwitchs";
        $.ajax({
            url: "/system/scenicSpot/oneTouchUpdateScenicSpotSwitchs",
            data: {
                switchs : data
            },
            type: "POST",
            cache:false,
            success: function (data) {
                if (data.state == "200"){
                    setTimeout(function(){
                        top.layer.msg(data.msg, {icon: 6});
                        // top.layer.close(dex);
                        //   layer.close(index);
                        //   layui.table.reload("broadcastListTable");
                        location.reload();
                    },500);
                }else if (data.state == "400"){
                    setTimeout(function(){

                        // top.layer.close(dex);
                        // layer.close(index);
                        top.layer.msg(data.msg, {icon: 5,time: 1000,shift: 6});
                    },500);
                }
            }
        });

    }




    // /**
    //  * 导出EXCEL表
    //  */
    // function downloadExcel(){
    //     window.location.href = "/system/treasureHunt/uploadExcelBroadcastHunt";
    // }


    /**
     * 弹出添加内容框
     */
    function openAddTreasure() {
        layer.open({
            type : 2,
            title: '添加随机寻宝景点时间',
            offset: '10%',
            area: ['800px', '460px'],
            content: '/page/system/scenicSpotDateHunt/html/scenicSpotDateHuntAdd.html',
            tableId: '#treasureList',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                $.ajax({
                    type : 'POST',
                    url : '/system/scenicSpot/getCurrentScenicSpot',
                    dataType : 'json',
                    success:function (data) {
                        if (data.state == "200"){
                            body.find(".spotId").val(data.data.scenicSpotId);//景区ID
                            body.find(".scenicSpotName").val(data.data.scenicSpotName);//景区名称
                            layui.table.reload("treasureListTable")
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
    function openEditTreasure(edit){
        // var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改随机寻宝时间',
                offset: '10%',
                area: ['800px', '580px'],
                content: '/page/system/scenicSpotDateHunt/html/scenicSpotDateHuntEdit.html',
                // tableId: '#broadcastList',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                                if(edit){
                                    console.log(edit)
                                    //top.layer.close(dex);
                                    body.find(".dateTreasureId").val(edit.dateTreasureId);
                                    body.find(".scenicSpotIds").val(edit.spotId);//景区ID
                                    body.find(".scenicSpotName").val(edit.scenicSpotName);
                                    body.find(".randomTime").val(edit.randomTime);//
                                    body.find(".sort").val(edit.sort);
                                    form.render();
                                }

                }
            });
        },500);
    }

})
