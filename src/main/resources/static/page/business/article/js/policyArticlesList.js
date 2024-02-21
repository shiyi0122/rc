layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#policyArticlesList',
        url : '/business/article/getArticleList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        where: {
            typesOf: '1',
            type : '2'
        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "policyArticlesListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'coverId', title: '文章封面ID', minWidth:100, align:"center"},
            {field: 'title', title: '文章标题', minWidth:100, align:"center"},
            {field: 'briefDesc', title: '简短描述', minWidth:100, align:"center"},
            {field: 'state', title: '状态', minWidth:100, align:"center",templet:function(d){
                    if(d.state == "0"){
                        return "正常";
                    }else if(d.state == "-1"){
                        return "启用";
                    }else if(d.state == "-2"){
                        return "删除";
                    }
                }},
            {field: 'createTime', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#policyArticlesListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(policyArticlesList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("BUSINESS_POLICY_ARTICLES_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditPolicyArticles(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("BUSINESS_POLICY_ARTICLES_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/business/article/delArticle", {id : data.id}, function (data) {}, false,"GET","policyArticlesListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

    //点击添加按钮
    $('#btnAddPolicyArticles').click(function(){
        window.resources("BUSINESS_POLICY_ARTICLES_INSERT", function (e) {
            if (e.state == "200") {
                openAddPolicyArticles();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddPolicyArticles() {
        layer.open({
            type : 2,
            title: '添加招商政策',
            offset: '10%',
            area: ['800px', '80%'],
            content: '/page/business/article/html/policyArticlesAdd.html'
        });
    };

    /**
     * 弹出编辑框
     * @param edit
     */
    function openEditPolicyArticles(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改招商政策',
                // offset: '10%',
                area: ['100%', '100%'],
                content: '/page/business/article/html/policyArticlesEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        console.log(edit.details)
                        top.layer.close(dex);
                        body.find(".id").val(edit.id);
                        body.find(".scenicSpotIds").val(edit.scenicSpotId);
                        body.find(".coverId").val(edit.coverId);
                        body.find(".title").val(edit.title);
                        body.find(".briefDesc").val(edit.briefDesc);
                        body.find(".recomSort").val(edit.recomSort);
                        body.find(".state select").val(edit.state);
                        body.find(".details").val(edit.details);
                        form.render();
                    }
                }
            });
        },500);
    }

})