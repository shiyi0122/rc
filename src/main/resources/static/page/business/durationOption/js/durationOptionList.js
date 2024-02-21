layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#journalismArticleList',
        url : '/business/durationOption/getDurationOptionList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        where: {

        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "journalismArticleListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center",templet:function(d){
                    if(d.scenicSpotName == null){
                        return "默认";
                    }else {
                        return d.scenicSpotName;
                    }
                }},
            {field: 'amountOfMoney', title: '支付金额', minWidth:100, align:"center"},
            {field: 'viewingDuration', title: '观看时长(秒)', minWidth:100, align:"center"},
            {field: 'type', title: '时长类型', minWidth:100, align:"center",templet:function (d) {
                    if(d.type == 1){
                        return "购买时长";
                    }else if (d.type ==2) {
                        return "体验时长";
                    }
                }},
            {field: 'createTime', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#journalismArticleListBar',fixed:"right",align:"center"}
        ]]
    });


    $("#btnSearch").on("click",function(){
        table.reload("journalismArticleListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                scenicSpotId: $(".scenicSpotIds").val(),
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
    table.on('tool(journalismArticleList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;
        if(layEvent === 'edit'){ //编辑
            window.resources("BUSINESS_DURATION_OPTION", function (e) {
                if (e.state == "200") {
                    openEditJournalismArticle(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("BUSINESS_DURATION_OPTION", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/business/durationOption/delDurationOption", {id : data.id}, function (data) {}, false,"GET","journalismArticleListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

    //点击添加按钮
    $('#btnAddJournalismArticle').click(function(){
        window.resources("BUSINESS_DURATION_OPTION", function (e) {
            if (e.state == "200") {
                openAddJournalismArticle();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddJournalismArticle() {
        layer.open({
            type : 2,
            title: '添加直播金额选项',
            offset: '10%',
            area: ['800px', '80%'],
            content: '/page/business/durationOption/html/durationOptionAdd.html'
        });
    };

    /**
     * 弹出编辑框
     * @param edit
     */
    function openEditJournalismArticle(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改直播金额',
                offset: '10%',
                area: ['800px', '80%'],
                content: '/page/business/durationOption/html/durationOptionEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".id").val(edit.id);
                        body.find(".scenicSpotIds").val(edit.scenicSpotId);
                        body.find(".amountOfMoney").val(edit.amountOfMoney);
                        body.find(".viewingDuration").val(edit.viewingDuration);
                        body.find(".type").val(edit.type);
                        form.render();
                    }
                }
            });
        },500);
    }

})