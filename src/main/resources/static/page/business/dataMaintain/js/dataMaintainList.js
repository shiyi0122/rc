layui.use(['form','layer','table'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#dataMaintainList',
        url : '/business/dataMaintain/getDataMaintainList',
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
        id : "dataMaintainListTable",
        cols : [[
            {field: 'userName', title: '用户名称', minWidth:100, align:"center"},
            {field: 'phone', title: '用户手机号', minWidth:100, align:"center"},
            {field: 'type', title: '反馈类型', minWidth:100, align:"center",templet:function(d){
                    if(d.type == "0"){
                        return "边界管理";
                    }else if(d.type == "1"){
                        return "归还点管理";
                    }if(d.type == "2"){
                        return "播报内容管理";
                    }if(d.type == "3"){
                        return "商户管理";
                    }if(d.type == "4"){
                        return "随机播报内容";
                    }
                }},
            {field: 'content', title: '反馈内容', minWidth:100, align:"center"},
            {field: 'state', title: '是否已读', minWidth:100, align:"center",templet:function(d){
                    if(d.state == "0"){
                        return "未读";
                    }else if(d.state == "1"){
                        return "已读";
                    }
                }},
            {field: 'createTime', title: '创建时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#dataMaintainListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("dataMaintainListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                phone: $(".phoneVal").val()
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
    table.on('tool(dataMaintainList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if (layEvent === "forbidden"){ //修改为已读
            window.resources("BUSINESS_DATA_MAINTAIN_EDITSTATE", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定修改为已读吗？',{icon:3, title:'提示信息'},function(index){
                        var dex = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
                        $.ajax({
                            url: "/business/dataMaintain/editState",
                            data: {
                                id : data.id,
                                state : 1
                            },
                            type: "POST",
                            cache:false,
                            success: function (data) {
                                if (data.state == "200"){
                                    setTimeout(function(){
                                        top.layer.msg(data.msg, {icon: 6});
                                        top.layer.close(dex);
                                        layer.close(index);
                                        location.reload();
                                    },500);
                                }else if (data.state == "400"){
                                    setTimeout(function(){
                                        top.layer.close(dex);
                                        layer.close(index);
                                        top.layer.msg(data.msg, {icon: 5,time: 1000,shift: 6});
                                    },500);
                                }
                            }
                        });
                    });
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("BUSINESS_DATA_MAINTAIN_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/business/dataMaintain/delDataMaintain", {id : data.id}, function (data) {}, false,"GET","dataMaintainListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

})
