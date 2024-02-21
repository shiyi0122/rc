layui.use(['table','form','layer','jquery'],function(){
    var form = layui.form,
        table = layui.table,
        layer = layui.layer,
        $ = layui.jquery

	var load = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
	setTimeout(function(){
		top.layer.close(load);
	    var tableIns = table.render({
	        elem: '#detailsList',
	        url : '/system/semantics/getSemanticsDetailsList',
	        cellMinWidth : 100,
	        page : true,
	        height : "full-125",
	        request: {
	            pageName: 'pageNum', //页码的参数名称，默认：pageNum
	            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
	        },
	        where: {
	            corpusId : $(".corpusId").val()
	        },
	        response:{
	            statusName: 'code', //数据状态的字段名称，默认：code
	            statusCode: 200, //成功的状态码，默认：0
	            countName: 'totals', //数据总数的字段名称，默认：count
	            dataName: 'list' //数据列表的字段名称，默认：data
	        },
	        id : "detailsListTable",
	        cols : [[
	            {field: 'extendCorpusProblem', title: '扩展问题', minWidth:100, align:"center"},
	            {field: 'extendCorpusPinyin', title: '扩展问题拼音', minWidth:100, align:"center"},
	            {field: 'createDate', title: '创建时间', minWidth:100, align:"center"},
	            {title: '操作', minWidth:175, templet:'#detailsListBar',fixed:"right",align:"center"}
	        ]]
	    });
    },500);

    //列表操作
    table.on('tool(detailsList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("RESOURCES_SEMANTICS_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditCorpusExtend(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("RESOURCES_SEMANTICS_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/semantics/delSemanticsDetails", {extendId : data.extendId}, function (data) {}, false,"GET","detailsListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddCorpusMediaExtend').click(function(){
        window.resources("RESOURCES_SEMANTICS_INSERT", function (e) {
            if (e.state == "200") {
                var corpusId = $(".corpusId").val();
                openAddCorpusExtend(corpusId);
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddCorpusExtend(corpusId) {
        layer.open({
            type : 2,
            title: '添加资源详情',
            offset: '10%',
            area: ['800px', '280px'],
            content: '/page/system/semantics/html/detailsAdd.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if (corpusId){
                    body.find(".corpusId").val(corpusId);
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
                area: ['800px', '280px'],
                content: '/page/system/semantics/html/detailsEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".extendId").val(edit.extendId);
                        body.find(".extendCorpusProblem").val(edit.extendCorpusProblem);
                        form.render();
                    }
                }
            });
        },500);
    }


})