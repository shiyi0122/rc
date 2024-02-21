layui.use(['table','form','layer','jquery'],function(){
    var form = layui.form,
        table = layui.table,
        layer = layui.layer,
        $ = layui.jquery
	var load = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
	setTimeout(function(){
		top.layer.close(load);
		table.render({
        elem: '#broadcastContentList',
        url : '/system/broadcast/getBroadcastContentList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        where: {
            broadcastId : $(".broadcastId").val()
        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "broadcastContentListTable",
        cols : [[
            {field: 'broadcastName', title: '景点名称', minWidth:100, align:"center"},
            {field: 'broadcastContent', title: '景点播报内容', minWidth:100, align:"center"},
            {field: 'mediaResourceUrl', title: '媒体资源地址', minWidth:100, align:"center"},
            {field: 'pictureUrl', title: '图片地址', minWidth:100, align:"center"},
            {field: 'mediaType', title: '资源类型', align:'center',templet:function(d){
                    if(d.mediaType == "1"){
                        return "文字类型";
                    }else if(d.mediaType == "2"){
                        return "视频类型";
                    }else if(d.mediaType == "3"){
                        return "音频类型";
                    }
                }},
            {field: 'createDate', title: '创建时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#broadcastContentListBar',fixed:"right",align:"center"}
        ]]
   	 });
	},500);

    //列表操作
    table.on('tool(broadcastContentList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYSTEM_BROADCAST_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditCorpusExtend(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYSTEM_BROADCAST_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/broadcast/delBroadcastContentExtend", {broadcastResId : data.broadcastResId}, function (data) {}, false,"GET","broadcastContentListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddCorpusMediaExtend').click(function(){
        window.resources("SYSTEM_BROADCAST_INSERT", function (e) {
            if (e.state == "200") {
                var broadcastId = $(".broadcastId").val();
                openAddBroadcastContentExtend(broadcastId);
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddBroadcastContentExtend(broadcastId) {
        layer.open({
            type : 2,
            title: '添加资源详情',
            offset: '10%',
            area: ['800px', '380px'],
            content: '/page/system/broadcast/html/broadcastContentAdd.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if (broadcastId){
                    body.find(".broadcastId").val(broadcastId);
                }
            }
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditCorpusExtend(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改资源详情',
                offset: '10%',
                area: ['800px', '380px'],
                content: '/page/system/broadcast/html/broadcastContentEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".broadcastResId").val(edit.broadcastResId);
                        body.find(".broadcastContent").val(edit.broadcastContent);
                        body.find(".mediaType").val(edit.mediaType);
                        form.render();
                    }
                }
            });
        },500);
    }


})