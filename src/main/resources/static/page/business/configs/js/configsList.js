layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#configsList',
        url : '/business/businessConfigs/getConfigsList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        where: {
            type : '0'
        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "configsListTable",
        cols : [[
            // {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            // {field: 'coverId', title: '文章封面ID', minWidth:100, align:"center"},
            {field: 'title', title: '类型标题', minWidth:100, align:"center"},
            // {field: 'briefDesc', title: '简短描述', minWidth:100, align:"center"},
            {field: 'state', title: '状态', minWidth:100, align:"center",templet:function(d){
                    if(d.state == "0"){
                        return "正常";
                    }else if(d.state == "-1"){
                        return "不显示";
                    }
                }},
            {field: 'createTime', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#configsListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(configsList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("BUSINESS_CONFIGS_ABOUT_US", function (e) {
                if (e.state == "200") {
                    openEditTravelArticles(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
        // else if(layEvent === 'del'){ //删除
        //     window.resources("BUSINESS_TRAVEL_ARTICLES_DELETE", function (e) {
        //         if (e.state == "200"){
        //             window.resourcedel("/business/article/delArticle", {id : data.id}, function (data) {}, false,"GET","configsListTable");
        //         }else if (e.state == "400"){
        //             layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
        //         }
        //     }, false,"GET");
        // }
    });

    // //点击添加按钮
    // $('#btnAddTravelArticles').click(function(){
    //     window.resources("BUSINESS_TRAVEL_ARTICLES_INSERT", function (e) {
    //         if (e.state == "200") {
    //             openAddTravelArticles();
    //         } else {
    //             layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
    //         }
    //     }, false,"GET");
    // })

    // /**
    //  * 弹出添加框
    //  */
    // function openAddTravelArticles() {
    //     layer.open({
    //         type : 2,
    //         title: '添加旅游文章',
    //         offset: '10%',
    //         area: ['800px', '80%'],
    //         content: '/page/business/article/html/travelArticlesAdd.html'
    //     });
    // };

    /**
     * 弹出编辑框
     * @param edit
     */
    function openEditTravelArticles(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改关于我们',
                offset: '10%',
                area: ['800px', '80%'],
                content: '/page/business/configs/html/configsEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".id").val(edit.id);
                        body.find(".title").val(edit.title);
                        body.find(".state select").val(edit.state);
                        console.log(edit.state);
                        console.log(edit.values);
                        body.find("#details").html(edit.values);
                        form.render();
                    }
                }
            });
        },500);
    }

})