layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#memberLevelList',
        url : '/business/memberLevel/getMemberLevelList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        where:{
            orderType : 2
        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "memberLevelListTable",
        cols : [[
            {field: 'attachId', title: '等级图标ID', minWidth:100, align:"center"},
            {field: 'title', title: '等级标题', minWidth:100, align:"center"},
            {field: 'integral', title: '积分数量', minWidth:100, align:"center"},
            {field: 'state', title: '积分状态', align:'center',templet:function(d){
                    if(d.state == "0"){
                        return "启用";
                    }else if(d.state == "1"){
                        return "禁用";
                    }
                }},
            {field: 'createTime', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#memberLevelListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(memberLevelList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //修改
            window.resources("BUSINESS_MEMBER_LEVEL_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditMemberLevel(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("BUSINESS_MEMBER_LEVEL_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/business/memberLevel/delMemberLevel", {id : data.id}, function (data) {}, false,"GET","memberLevelListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

    //点击添加按钮
    $('#btnAddMemberLevel').click(function(){
        window.resources("BUSINESS_MEMBER_LEVEL_INSERT", function (e) {
            if (e.state == "200") {
                openAddMemberLevel();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditMemberLevel(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改积分规则',
                offset: '10%',
                area: ['800px', '455px'], //宽高
                content: '/page/business/memberLevel/html/memberLevelEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".id").val(edit.id);
                        body.find(".title").val(edit.title);
                        body.find(".integral").val(edit.integral);
                        body.find(".state select").val(edit.state);
                        body.find(".attachId").val(edit.attachId);
                        form.render();
                    }
                }
            });
        },500);
    }

    /**
     * 弹出添加框
     */
    function openAddMemberLevel() {
        layer.open({
            type : 2,
            title: '添加积分规则',
            offset: '10%',
            area: ['800px', '455px'],
            content: '/page/business/memberLevel/html/memberLevelAdd.html'
        });
    };

})