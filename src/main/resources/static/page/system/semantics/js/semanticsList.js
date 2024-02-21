layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#semanticsList',
        url : '/system/semantics/getSemanticsList',
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
        id : "semanticsListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'corpusProblem', title: '语义问题', minWidth:100, align:"center"},
            {field: 'pinYinProblem', title: '语义问题拼音', minWidth:100, align:"center"},
            {field: 'corpusAnswer', title: '语义答案', minWidth:100, align:"center"},
            {field: 'createDate', title: '创建时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#semanticsListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 根据用户名称模糊查询
     */
    $("#btnSearch").on("click",function(){
        if($(".userNameVal").val() != ''){
            table.reload("semanticsListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    corpusProblem: $(".corpusProblemVal").val()  //搜索的关键字
                }
            })
        }else{
            layer.msg("请输入搜索的语义问题");
        }
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })

    //列表操作
    table.on('tool(semanticsList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("RESOURCES_SEMANTICS_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditSemantics(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("RESOURCES_SEMANTICS_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/semantics/delSemantics", {corpusId : data.corpusId}, function (data) {}, false,"GET","semanticsListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent == 'details'){
            viewDetails(data);
        }
    });

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
                content: '/page/system/semantics/html/details.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".corpusId").val(edit.corpusId);//景区ID
                        form.render();
                    }
                }
            });
        },500);
    }

    //点击添加按钮
    $('#btnAddSemantics').click(function(){
        window.resources("RESOURCES_SEMANTICS_INSERT", function (e) {
            if (e.state == "200") {
                openAddSemantics();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddSemantics() {
        layer.open({
            type : 2,
            title: '添加语义交互',
            offset: '10%',
            area: ['800px', '390px'], //宽高
            content: '/page/system/semantics/html/semanticsAdd.html'
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditSemantics(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改语义交互',
                offset: '10%',
                area: ['800px', '390px'],
                content: '/page/system/semantics/html/semanticsEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".corpusId").val(edit.corpusId);
                        body.find(".genericType select").val(edit.genericTypeTwo);
                        body.find(".corpusProblem").val(edit.corpusProblem);
                        body.find(".corpusAnswer").val(edit.corpusAnswer);
                        form.render();
                    }
                }
            });
        },500);
    }

})
