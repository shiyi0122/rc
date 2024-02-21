layui.use(['table','form','layer','jquery'],function(){
    var form = layui.form,
        table = layui.table,
        layer = layui.layer,
        $ = layui.jquery

    var tableIns = table.render({
        elem: '#viewDetailsList',
        url : '/system/corpusAudioAndVideo/getViewDetailsList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        where: {
            mediaId : $(".mediaId").val()
        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "viewDetailsListTable",
        cols : [[
            {field: 'mediaExtendName', title: '媒体扩展名称', minWidth:100, align:"center"},
            {field: 'mediaExtendNamePingYin', title: '媒体拼音名称', minWidth:100, align:"center"},
            {field: 'mediaExtendAuthor', title: '媒体扩展作者', minWidth:100, align:"center"},
            {field: 'mediaExtendAuthorPingYin', title: '媒体拼音作者', minWidth:100, align:"center"},
            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#viewDetailsListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(viewDetailsList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("RESOURCES_INNERCIRCLE_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditCorpusMediaExtend(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("RESOURCES_INNERCIRCLE_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/corpusAudioAndVideo/delCorpusMediaExtend", {mediaExtendId : data.mediaExtendId}, function (data) {}, false,"GET","viewDetailsListTable");
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
                var mediaId = $(".mediaId").val();
                openAddCorpusMediaExtend(mediaId);
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddCorpusMediaExtend(mediaId) {
        layer.open({
            type : 2,
            title: '添加资源详情',
            offset: '10%',
            area: ['800px', '330px'],
            content: '/page/system/mediaResources/html/viewDetailsAdd.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if (mediaId){
                    body.find(".mediaId").val(mediaId);
                }
            }
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditCorpusMediaExtend(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改资源详情',
                offset: '10%',
                area: ['800px', '330px'],
                content: '/page/system/mediaResources/html/viewDetailsEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".mediaExtendId").val(edit.mediaExtendId);
                        body.find(".mediaExtendName").val(edit.mediaExtendName);
                        body.find(".mediaExtendAuthor").val(edit.mediaExtendAuthor);
                        form.render();
                    }
                }
            });
        },500);
    }


})