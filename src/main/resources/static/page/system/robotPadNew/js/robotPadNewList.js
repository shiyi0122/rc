layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#robotPadList',
        url : '/system/robotPadNew/getRobotPadNewList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code6+
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "robotPadListTable",
        cols : [[
            {field: 'padUrl', title: 'PAD路径', minWidth:100, align:"center"},
            {field: 'padNumber', title: '版本号', minWidth:100, align:"center"},
            {field: 'padDescription', title: '版本描述', minWidth:100, align:"center"},
            {field: 'updateScope', title: '更新范围', minWidth:100, align:"center",templet:function(d){
                    if(d.updateScope == "1"){
                        return "全国";
                    }else if(d.updateScope == "2"){
                        return "景区";
                    }else if(d.updateScope == "3"){
                        return "待定";
                    }
                }},
            {field: 'updateType', title: '更新类型', minWidth:100, align:"center",templet:function(d){
                    if(d.updateType == "1"){
                        return "强制";
                    }else if(d.updateType == "2"){
                        return "提示更新";
                    }else if(d.updateType == "3"){
                        return "不提示更新";
                    }
                }},
            {field: 'packageType', title: '包类型', minWidth:100, align:"center",templet:function(d){
                    if(d.packageType == "1"){
                        return "完整包";
                    }else if(d.packageType == "2"){
                        return "资源包";
                    }
                }},
            {field: 'startTime', title: '更新开始时间', minWidth:100, align:"center"},
            {field: 'endTime', title: '更新结束时间', minWidth:100, align:"center"},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#robotPadListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 根据景区名称模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("robotPadListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                packageType: $(".packageTypeVal").val(),
                // scenicSpotName: $(".scenicSpotNameVal").val() //搜索的关键字
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
    table.on('tool(robotPadList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYS_ROBOT_PAD_NEW", function (e) {
                if (e.state == "200") {
                    openEditRobotPad(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYS_ROBOT_PAD_NEW", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/robotPadNew/delRobotPadNew", {padId : data.padId}, function (data) {}, false,"GET","robotPadListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "download"){ //下载
            window.resources("SYS_ROBOT_PAD_NEW", function (e) {
                if (e.state == "200") {
                    window.location.href = "/system/robotPad/downloadNew?fileName=" + data.padUrl
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "spotList") {//景区列表
            window.resources("SYS_ROBOT_PAD_NEW", function (e) {
                if (e.state == "200") {
                    console.log(11111)
                    openRobotPadList(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");

        }
    });
    //点击添加按钮
    $('#btnAddRobotPad').click(function(){
        window.resources("SYS_ROBOT_PAD_NEW", function (e) {
            if (e.state == "200") {
                openAddRobotPad();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddRobotPad() {
        layer.open({
            type : 2,
            title: '上传PAD客户端',
            offset: '10%',
            area: ['800px', '400px'], //宽高
            content: '/page/system/robotPadNew/html/robotPadNewAdd.html'
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditRobotPad(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改PAD信息',
                offset: '10%',
                area: ['800px', '480px'], //宽高
                content: '/page/system/robotPadNew/html/robotPadNewEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".padId").val(edit.padId);
                        body.find(".padDescription").val(edit.padDescription);
                        body.find(".padNumber").val(edit.padNumber);
                        body.find(".updateScope").val(edit.updateScope);
                        body.find(".updateType").val(edit.updateType);
                        body.find(".packageType").val(edit.packageType);
                        body.find(".startTime").val(edit.startTime);
                        body.find(".endTime").val(edit.endTime);
                        form.render();
                    }
                }
            });
        },500);
    }


    /**
     * 弹出景区列表框
     * @param
     */
    function openRobotPadList(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '景区列表',
                offset: '10%',
                area: ['800px', '380px'], //宽高
                content: '/page/system/robotPadNew/html/robotPadNewSpotList.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".padId").val(edit.padId);
                        body.find(".padDescription").val(edit.padDescription);
                        // body.find(".updateScope").val(edit.updateScope);
                        // body.find(".updateType").val(edit.updateType);
                        // body.find(".packageType").val(edit.packageType);
                        form.render();
                    }
                }
            });
        },500);
    }

})
