layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#customTypeList',
        url : '/system/customType/getCustomTypeList',
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
        id : "customTypeListTable",
        cols : [[
            {field: 'typeName', title: '类型名称', minWidth:100, align:"center"},
            {field: 'typeNameValue', title: '类型编号', minWidth:100, align:"center"},
            {field: 'typeResId', title: '菜单名称', minWidth:100, align:"center",templet:function(d){
                    if(d.typeResId == "1"){
                        return "语义交互管理";
                    }else if(d.typeResId == "2"){
                        return "服务项";
                    }
                }},
            {field: 'createDate', title: '创建时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#customTypeListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(customTypeList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYSTEN_CUSTOM_TYPE_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditCustomType(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYSTEN_CUSTOM_TYPE_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/customType/delCustomType", {typeId : data.typeId}, function (data) {}, false,"GET","customTypeListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddBroadcast').click(function(){
        window.resources("SYSTEN_CUSTOM_TYPE_INSERT", function (e) {
            if (e.state == "200") {
                openAddCustomType();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddCustomType() {
        layer.open({
            type : 2,
            title: '添加自定义类型',
            offset: '10%',
            area: ['800px', '390px'],
            content: '/page/system/customType/html/customTypeAdd.html'
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditCustomType(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改自定义类型',
                offset: '10%',
                area: ['800px', '270px'],
                content: '/page/system/customType/html/customTypeEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".typeId").val(edit.typeId);
                        body.find(".typeName").val(edit.typeName);
                        form.render();
                    }
                }
            });
        },500);
    }

})