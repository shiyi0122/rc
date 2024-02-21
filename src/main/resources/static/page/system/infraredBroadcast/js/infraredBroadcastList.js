layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#infraredBroadcastList',
        url : '/system/infraredBroadcast/getInfraredBroadcastList',
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
        id : "infraredBroadcastListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'infraredTitle', title: '标题', minWidth:100, align:"center"},
            {field: 'infraredContent', title: '内容', minWidth:100, align:"center"},
            {field: 'infraredEnableType', title: '状态', minWidth:100, align:"center",templet:function(d){
                    if(d.infraredEnableType == "1"){
                        return "启用";
                    }else if(d.infraredEnableType == "0"){
                        return "禁用";
                    }
                }},
            {field: 'type', title: '类型', minWidth:100, align:"center",templet:function(d){
                    if(d.type == "1"){
                        return "文字";
                    }else if(d.type == "2"){
                        return "音频";
                    }
                }},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#infraredBroadcastListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(infraredBroadcastList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYS_SCENIC_SPOT_INFRARED_BROADCAST_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditInfraredBroadcast(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYS_SCENIC_SPOT_INFRARED_BROADCAST_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/infraredBroadcast/delInfraredBroadcast", {infraredId : data.infraredId}, function (data) {}, false,"GET","infraredBroadcastListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddInfraredBroadcast').click(function(){
        window.resources("SYS_SCENIC_SPOT_INFRARED_BROADCAST_INSERT", function (e) {
            if (e.state == "200") {
                openAddInfraredBroadcast();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddInfraredBroadcast() {
        layer.open({
            type : 2,
            title: '添加红外播报',
            offset: '10%',
            area: ['800px', '380px'], //宽高
            content: '/page/system/infraredBroadcast/html/infraredBroadcastAdd.html'
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditInfraredBroadcast(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改红外播报',
                offset: '10%',
                area: ['800px', '380px'], //宽高
                content: '/page/system/infraredBroadcast/html/infraredBroadcastEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".infraredId").val(edit.infraredId);
                        body.find(".infraredTitle").val(edit.infraredTitle);
                        body.find(".infraredContent").val(edit.infraredContent);
                        body.find(".type").val(edit.type);
                        form.render();
                    }
                }
            });
        },500);
    }

})
