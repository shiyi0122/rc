layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#appRoleList',
        url : '/system/AppRole/getAppRoleList',
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
        id : "appRoleListTable",
        cols : [[
            {field: 'roleName', title: '角色名称', minWidth:100, align:"center"},
            {field: 'roleDesc', title: '角色描述', minWidth:100, align:"center"},
            {field: 'roleStatus', title: '角色状态', align:'center',templet:function(d){
                    if(d.roleStatus == "10"){
                        return "正常";
                    }else {
                        return "异常";
                    }
                }},
            {field: 'createDate', title: '创建时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#appRoleListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 根据角色名称模糊查询
     */
    $("#btnSearch").on("click",function(){
        if($(".roleNameVal").val() != ''){
            table.reload("appRoleListTable",{
                page: {
                    curr: 1 //重新从第 1 页开始
                },
                where: {
                    roleName: $(".roleNameVal").val()  //搜索的关键字
                }
            })
        }else{
            layer.msg("请输入搜索的角色名称");
        }
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        $(".roleNameVal").val("");
        location.reload();
    })

    //列表操作
    table.on('tool(appRoleList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYSTEM_ROLE_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditRole(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYSTEM_ROLE_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/role/delRole", {roleId : data.roleId}, function (data) {}, false,"GET","roleListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "permissionSettings"){ //权限设置
            window.resources("SYSTEM_ROLE_PERMISSION_SETTINGS", function (e) {
                if (e.state == "200") {
                    permissionSettings(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddAppRole').click(function(){
        window.resources("SYSTEM_ROLE_INSERT", function (e) {
            if (e.state == "200") {
                openAddAppRole();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加角色框
     */
    function openAddAppRole() {
        layer.open({
            type : 2,
            title: '添加APP角色',
            offset: '10%',
            area: ['800px', '330px'], //宽高
            content: '/page/managerApp/appRole/html/appRoleAdd.html'
        });
    };

    /**
     * 弹出修改角色框
     * @param edit
     */
    function openEditRole(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改角色',
                offset: '10%',
                area: ['800px', '330px'], //宽高
                content: '/page/system/role/html/roleEdit.html',
                tableId: '#roleList',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".roleId").val(edit.roleId);
                        body.find(".roleName").val(edit.roleName);
                        body.find(".roleDesc").val(edit.roleDesc);
                        form.render();
                    }
                }
            });
        },500);
    }

    function permissionSettings(edit){
        layer.open({
            type: 2,
            title: '权限设置',
            area: ['300px', '400px'],
            content: '/page/managerApp/appRole/html/appRoleTree.html',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".roleId").val(edit.roleId);
                    form.render();
                }
            }
        });
    }


})
