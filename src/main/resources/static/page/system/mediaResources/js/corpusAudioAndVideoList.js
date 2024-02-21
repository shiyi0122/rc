layui.use(['table','form','layer','jquery'],function(){
    var form = layui.form,
        table = layui.table,
        layer = layui.layer,
        $ = layui.jquery

	var load = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
	setTimeout(function(){
		top.layer.close(load);
	    var tableIns = table.render({
	        elem: '#corpusAudioAndVideoList',
	        url : '/system/corpusAudioAndVideo/getCorpusAudioAndVideoList',
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
	        id : "corpusAudioAndVideoListTable",
	        cols : [[
	            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
	            {field: 'mediaName', title: '媒体名称', minWidth:100, align:"center"},
	            {field: 'mediaAuthor', title: '媒体作者', minWidth:100, align:"center"},
	            {field: 'mediaType', title: '媒体类型', minWidth:100, align:"center",templet:function(d){
	                if(d.mediaType == "1"){
	                    return "儿歌";
	                }else if(d.mediaType == "2"){
	                    return "现代";
	                }else if(d.mediaType == "3"){
	                    return "古典";
	                }else if(d.mediaType == "4"){
	                    return "儿童视频";
	                }else if(d.mediaType == "5"){
	                    return "现代视频";
	                }else if(d.mediaType == "6"){
	                    return "古典视频";
	                }
	            }},
	            {field: 'mediaResourcesType', title: '媒体资源类型', minWidth:100, align:"center",templet:function(d){
	                if(d.mediaResourcesType == "1"){
	                    return "音频类型";
	                }else if(d.mediaResourcesType == "2"){
	                    return "视频类型";
	                }
	            }},
	            {field: 'mediaUrl', title: '媒体链接', minWidth:100, align:"center"},
	            {field: 'createDate', title: '添加时间', minWidth:100, align:"center"},
	            {title: '操作', minWidth:175, templet:'#corpusAudioAndVideoListBar',fixed:"right",align:"center"}
	        ]]
	    });
	},500);

    //列表操作
    table.on('tool(corpusAudioAndVideoList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("RESOURCES_MEDIARESOURCES_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditCorpusAudioAndVideo(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("RESOURCES_MEDIARESOURCES_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/corpusAudioAndVideo/delCorpusAudioAndVideo", {mediaId : data.mediaId}, function (data) {}, false,"GET","corpusAudioAndVideoListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent == 'details'){
            viewDetails(data);
        }
    });

    //点击添加按钮
    $('#btnAddCorpusAudioAndVideo').click(function(){
        window.resources("RESOURCES_MEDIARESOURCES_INSERT", function (e) {
            if (e.state == "200") {
                openAddCorpusAudioAndVideo();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 跳转详情页面
     * @param edit
     */
    function viewDetails(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '资源详情',
                offset: '10%',
                area: ['1000px', '580px'], //宽高
                content: '/page/system/mediaResources/html/viewDetailsList.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".mediaId").val(edit.mediaId);//景区ID
                        form.render();
                    }
                }
            });
        },500);
    }

    function openAddCorpusAudioAndVideo() {
        layer.open({
            type : 2,
            title: '添加语音媒体资源',
            offset: '10%',
            area: ['800px', '560px'], //宽高
            content: '/page/system/mediaResources/html/mediaResourcesAdd.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index)
            }
        });
    };

    function openEditCorpusAudioAndVideo(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改语音媒体资源',
                offset: '10%',
                area: ['800px', '560px'],
                content: '/page/system/mediaResources/html/mediaResourcesEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".mediaId").val(edit.mediaId);
                        body.find(".scenicSpotId select").val(edit.mediaCurrencyType);
                        body.find(".mediaName").val(edit.mediaName);
                        body.find(".mediaAuthor").val(edit.mediaAuthor);
                        body.find(".mediaType select").val(edit.mediaType);
                        body.find(".mediaResourcesType").val(edit.mediaResourcesType);
                        form.render();
                    }
                }
            });
        },500);
    }

})