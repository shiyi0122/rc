layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#treasureList',
        url : '/system/broadcastHunt/getTreasureHuntList',
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
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center",templet:function(d){
                if (d.type==1){
                    return    '<div class="layui-table-cell laytable-cell-1-0-0" style="color: red;" >'+d.scenicSpotName+'</div>';
                } else{
                    return    '<div class="layui-table-cell laytable-cell-1-0-0" >'+d.scenicSpotName+'</div>';
                }}},
            {field: 'treasureName', title: '寻宝奖品名称', minWidth:100, align:"center"},
            {field: 'treasureType', title: '奖品类型', minWidth:100, align:"center",templet:function(d){
                    if(d.treasureType == "1"){
                        return "代金券";
                    }else if(d.treasureType == "2"){
                        return "免单";
                    }else if(d.treasureType == "3"){
                        return "实物";
                    }
                }},
            {field: 'couponAmount', title: '代金券金额', minWidth:100, align:"center",templet:function(d){
                    if(d.couponAmount == "0.00"){
                        return "0.00";
                    }else{
                        return d.couponAmount;
                    }
                }},
            {field: 'inventory', title: '库存数量', minWidth:100, align:"center"},
            {field: 'endValidity', title: '结束时间', minWidth:100, align:"center"},
            {field: 'startValidity', title: '开始时间', minWidth:100, align:"center"},
            {field: 'picUrl', title: '奖品图片', minWidth:100, align:"center"},
            {field: 'prizeWeight', title: '权重', minWidth:100, align:"center"},
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
               // scenicSpotId: $(".scenicSpotId").val(),
                scenicSpotName: $(".scenicSpotName").val(),
                treasureName: $(".treasureName").val(),
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
            window.resources("SYSTEM_TREASUREHUNT_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditTreasure(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYSTEM_TREASUREHUNT_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/broadcastHunt/delTreasureHunt", {treasureId : data.treasureId , scenicSpotId : data.scenicSpotId}, function (data) {}, false,"GET","treasureListTable");
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

    // /**
    //  * 跳转详情页面
    //  * @param edit
    //  */
    // function viewDetails(edit){
    //     var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
    //     setTimeout(function(){
    //         layer.open({
    //             type : 2,
    //             title: '资源详情',
    //             offset: '10%',
    //             area: ['1000px', '580px'], //宽高
    //             content: '/page/system/treasureHunt/html/broadcastContentList.html',
    //             success : function(layero, index){
    //                 var body = layui.layer.getChildFrame('body', index);
    //                 if(edit){
    //                     top.layer.close(dex);
    //                     body.find(".broadcastId").val(edit.broadcastId);
    //                     form.render();
    //                 }
    //             }
    //         });
    //     },500);
    // }

    //点击添加按钮
    $('#btnAddTreasure').click(function(){
        window.resources("SYSTEM_TREASUREHUNT_INSERT", function (e) {
            if (e.state == "200") {
                openAddTreasure();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    // //点击导出EXCEL按钮
    // $('#downloadExcel').click(function(){
    //     window.resources("SYSTEM_TREASUREHUNT_INSERT", function (e) {
    //         if (e.state == "200") {
    //             downloadExcel();
    //         } else {
    //             layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
    //         }
    //     }, false,"GET");
    // })

    // //导入Excel表
    // upload.render({
    //     elem: '#importExcel'
    //     ,url: '/system/treasureHunt/importExcel'
    //     ,accept: 'file' //普通文件
    //     ,exts: 'xls|xlsx' //只允许上传Excel文件
    //     ,before:function(obj){
    //         layer.load(); //上传loading
    //     }
    //     ,done: function(res){
    //         if (res.state == 200){
    //             setTimeout(function(){
    //                 layer.alert(res.msg,function(){
    //                     layer.closeAll();//关闭所有弹框
    //                 });
    //                 layui.table.reload("broadcastListTable");
    //             },500);
    //         }else{
    //             setTimeout(function(){
    //                 top.layer.msg(res.msg, {icon: 5,time: 1000,shift: 6});
    //                 layer.closeAll();
    //             },500);
    //         }
    //     }
    // });


    //开启关闭活动
    // $('#kqhd').click(function(data){
    //     activityOn(data);
    // })



    //寻宝开启关闭
    // function activityOn(data) {
    //
    //  //  window.location.href = "/system/scenicSpot/updateScenicSpotSwitchs";
    //     $.ajax({
    //         url: "/system/scenicSpot/updateScenicSpotSwitchs",
    //         data: {
    //             broadcastId : data.broadcastId,
    //             switchs : 0
    //         },
    //         type: "POST",
    //         cache:false,
    //         success: function (data) {
    //             if (data.state == "200"){
    //                 setTimeout(function(){
    //                     top.layer.msg(data.msg, {icon: 6});
    //                     top.layer.close(dex);
    //                  //   layer.close(index);
    //                  //   layui.table.reload("broadcastListTable");
    //                 },500);
    //             }else if (data.state == "400"){
    //                 setTimeout(function(){
    //
    //                     // top.layer.close(dex);
    //                     // layer.close(index);
    //                     top.layer.msg(data.msg, {icon: 5,time: 1000,shift: 6});
    //                 },500);
    //             }
    //         }
    //     });
    //
    // }



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
            title: '添加寻宝景点奖品',
            offset: '10%',
            area: ['800px', '460px'],
            content: '/page/system/broadcastHunt/html/treasureAdd.html',
            tableId: '#treasureList',
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
                title: '修改寻宝景点奖品',
                offset: '10%',
                area: ['800px', '580px'],
                content: '/page/system/broadcastHunt/html/treasureEdit.html',
                // tableId: '#broadcastList',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                                if(edit){
                                    console.log(edit)
                                    //top.layer.close(dex);
                                    body.find(".treasureId").val(edit.treasureId);
                                    body.find(".scenicSpotIds").val(edit.scenicSpotId);//景区ID
                                    body.find(".scenicSpotName").val(edit.scenicSpotName);
                                    body.find(".treasureName").val(edit.treasureName);//
                                    body.find(".treasureType").val(edit.treasureType);
                                    body.find(".couponAmount").val(edit.couponAmount);
                                    body.find(".inventory").val(edit.inventory);
                                    body.find(".endValidity").val(edit.endValidity);
                                    body.find(".startValidity").val(edit.startValidity);
                                    body.find(".picUrl").val(edit.picUrl);
                                    body.find(".prizeWeight").val(edit.prizeWeight);
                                    form.render();
                                }

                }
            });
        },500);
    }

})