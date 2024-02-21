layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#robotSoundsList',
        url : '/system/robotSounds/getRobotSoundsList',
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
        id : "robotSoundsListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'soundsTitle', title: '标题', minWidth:100, align:"center"},
            {field: 'soundsContent', title: '内容', minWidth:100, align:"center"},
            {field: 'soundsMediaType', title: '资源类型', minWidth:100, align:"center",templet:function(d){
                    if(d.soundsMediaType == "1"){
                        return "文字类型";
                    }else if(d.soundsMediaType == "2"){
                        return "音频类型";
                    }else if(d.soundsMediaType == "3"){
                        return "视频类型";
                    }
                }},
            {field: 'soundsTypeName', title: '提示音类型', minWidth:100, align:"center"},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#robotSoundsListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(robotSoundsList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYS_ROBOT_SOUNDS_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditRobotSounds(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYS_ROBOT_SOUNDS_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/robotSounds/delRobotSounds", {soundsId : data.soundsId}, function (data) {}, false,"GET","robotSoundsListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddInnercircle').click(function(){
        window.resources("SYS_ROBOT_SOUNDS_INSERT", function (e) {
            if (e.state == "200") {
                openAddRobotSounds();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddRobotSounds() {
        layer.open({
            type : 2,
            title: '添加机器人提示音',
            offset: '10%',
            area: ['800px', '430px'], //宽高
            content: '/page/system/robotSounds/html/robotSoundsAdd.html'
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditRobotSounds(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改机器人提示音',
                offset: '10%',
                area: ['800px', '430px'], //宽高
                content: '/page/system/robotSounds/html/robotSoundsEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".soundsMediaType").val(edit.soundsMediaType);
                        body.find(".soundsId").val(edit.soundsId);
                        body.find(".soundsTypes").val(edit.soundsType);
                        body.find(".soundsTitle").val(edit.soundsTitle);
                        body.find(".soundsContent").val(edit.soundsContent);
                        form.render();
                    }
                }
            });
        },500);
    }

})
