layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#policyArticlesList',
        url : '/business/filingSpot/getFilingSpotList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        where: {
            pageNum : 1,
            pageSize : 10,
        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "policyArticlesListTable",
        cols : [[
            {field: 'highQuality', title: '优质景区名称', minWidth:100, align:"center"},
            {field: 'createDate', title: '创建时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#policyArticlesListBar',fixed:"right",align:"center"}
        ]]
    });


    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("policyArticlesListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                highQuality: $(".highQualityVal").val(),  //搜索的关键字

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
                    window.resourcedel("/business/filingSpot/delFilingSpot", {id : data.id}, function (data) {}, false,"GET","policyArticlesListTable");
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
            title: '添加优质景区',
            offset: '10%',
            area: ['800px', '80%'],
            content: '/page/business/filingSpot/html/filingSpotAdd.html'
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
                title: '修改优质景区',
                offset: '10%',
                area: ['800px', '80%'],
                content: '/page/business/filingSpot/html/filingSpotEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".id").val(edit.id);
                        body.find(".highQuality").val(edit.highQuality);

                        form.render();
                    }
                }
            });
        },500);
    }

})