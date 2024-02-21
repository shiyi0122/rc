layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#roleList',
        url : '/business/role/getBusinessRoleList',
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
        id : "roleListTable",
        cols : [[
            {field: 'roleName', title: '角色名称', minWidth:100, align:"center"},
            {field: 'roleStatus', title: '角色状态', align:'center',templet:function(d){
                    if(d.roleStatus == "10"){
                        return "正常";
                    }else if(d.roleStatus == "50"){
                        return "禁用";
                    }else if(d.roleStatus == "90"){
                        return "删除";
                    }
                }},
            {field: 'roleDesc', title: '角色描述', minWidth:100, align:"center"},
            {field: 'createDate', title: '创建时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#roleListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(roleList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("BUSINESS_ROLE_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditRole(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("BUSINESS_ROLE_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/business/role/delRole", {roleId : data.roleId}, function (data) {}, false,"GET" ,"roleListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddRole').click(function(){
        window.resources("BUSINESS_ROLE_INSERT", function (e) {
            if (e.state == "200") {
                openAddRole();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddRole() {
        layer.open({
            type : 2,
            title: '添加角色',
            offset: '10%',
            area: ['800px', '460px'],
            content: '/page/system/advertising/html/advertisingAdd.html'
        });
    };

    /**
     * 弹出修改框
     * @param edit
     */
    function openEditRole(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改角色',
                offset: '10%',
                area: ['800px', '460px'],
                content: '/page/system/advertising/html/advertisingEdit.html',
                tableId: '#advertisingList',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    $.ajax({
                        type : 'POST',
                        url : '/system/scenicSpot/getCurrentScenicSpot',
                        dataType : 'json',
                        success:function (data) {
                            if (data.state == "200"){
                                if(edit){
                                    top.layer.close(dex);
                                    body.find(".advertisingScenicSpotId").val(data.data.scenicSpotId);//景区ID
                                    body.find(".scenicSpotName").val(data.data.scenicSpotName);//景区名称
                                    body.find(".advertisingName").val(edit.advertisingName);
                                    body.find(".advertisingOrder").val(edit.advertisingOrder);
                                    body.find(".advertisingId").val(edit.advertisingId);
                                    form.render();
                                }
                            }else if(data.state == "400"){
                                layer.msg(data.msg);
                            }
                        }
                    })
                }
            });
        },500);
    }

})
