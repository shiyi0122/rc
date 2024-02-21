layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#feedBackList',
        url : '/business/feedBack/getFeedBackList',
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
        id : "feedBackListTable",
        cols : [[
            {field: 'userName', title: '用户名称', minWidth:100, align:"center"},
            {field: 'contact', title: '联系方式', minWidth:100, align:"center"},
            {field: 'content', title: '反馈内容', minWidth:100, align:"center"},
            {field: 'replyContent', title: '回复内容', minWidth:100, align:"center"},
            {field: 'state', title: '反馈状态', minWidth:100, align:"center",templet:function(d){
                    if(d.state == "0"){
                        return "反馈中";
                    }else if(d.state == "1"){
                        return "已回复";
                    }if(d.state == "-1"){
                        return "删除";
                    }
                }},
            {field: 'state', title: '是否已读', minWidth:100, align:"center",templet:function(d){
                    if(d.state == "0"){
                        return "未读";
                    }else if(d.state == "1"){
                        return "已读";
                    }
                }},
            {field: 'createTime', title: '创建时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#feedBackListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("feedBackListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                contact: $(".contactVal").val()
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
    table.on('tool(feedBackList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === "forbidden"){ //回复
            window.resources("BUSINESS_FEED_BACK_REPLY", function (e) {
                if (e.state == "200") {
                    openEditReply(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("BUSINESS_FEED_BACK_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/business/feedBack/delFeedBack", {id : data.id}, function (data) {}, false,"GET","feedBackListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

    function openEditReply(edit) {
        layer.open({
            type : 2,
            title: '回复内容',
            offset: '10%',
            area: ['800px', '280px'], //宽高
            content: '/page/business/feedBack/html/feedBackReply.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if (edit){
                    body.find(".id").val(edit.id);
                    form.render();
                }
            }
        });
    };

})
