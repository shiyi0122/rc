layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#userList',
        url : '/system/user/getUserList',
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
        id : "userListTable",
        cols : [[
            {field: 'loginName', title: '用户账号', minWidth:100, align:"center"},
            {field: 'userName', title: '用户名称', minWidth:100, align:"center"},
            {field: 'userSex', title: '用户性别', align:'center',templet:function(d){
                    if(d.userSex == "1"){
                        return "男";
                    }else if(d.userSex == "2"){
                        return "女";
                    }
                }},
            {field: 'userPhone', title: '用户手机号', minWidth:100, align:"center"},
            {field: 'userUnitAddress', title: '公司地址', minWidth:100, align:"center"},
            {field: 'userEmail', title: '公司邮箱', minWidth:100, align:"center"},
            {field: 'userRoleState', title: '用户分类', align:'center',templet:function(d){
                if(d.userRoleState == "10"){
                    return "内部员工";
                }else if(d.userRoleState == "20"){
                    return "外部员工";
                }else if (d.userRoleState == "30"){
                    return "合伙人";
                }else if (d.userRoleState == "40"){
                    return "景区方";
                }
            }},
            {field: 'userState', title: '用户状态', align:'center',templet:function(d){
                if(d.userState == "10"){
                    return "正常";
                }else if(d.userState == "50"){
                    return "禁用";
                }else if (d.userState == "90"){
                    return "删除";
                }
            }},
            {field: 'createDate', title: '创建时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#userListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 根据用户名称模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("userListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userName: $(".userNameVal").val(),  //搜索的关键字
                loginName: $(".loginNameVal").val()  //搜索的关键字
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
    table.on('tool(userList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYSTEM_USER_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditUser(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYSTEM_USER_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/user/delUser", {userId : data.userId}, function (data) {}, false,"GET","userListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "permissionSettings"){ //权限设置
            window.resources("SYSTEM_USER_PERMISSION_SETTINGS", function (e) {
                if (e.state == "200") {
                    json = JSON.stringify(data);
                    permissionSettings(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent == 'details'){
            viewDetails(data);
        }else if (layEvent === "forbidden"){ //修改为启用
            window.resources("SYSTEM_USER_ENABLE", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定修改为启用吗？',{icon:3, title:'提示信息'},function(index){
                        var dex = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
                        $.ajax({
                            url: "/system/user/editUserState",
                            data: {
                                userId : data.userId,
                                userState : 10
                            },
                            type: "POST",
                            cache:false,
                            success: function (data) {
                                if (data.state == "200"){
                                    setTimeout(function(){
                                        top.layer.msg(data.msg, {icon: 6});
                                        top.layer.close(dex);
                                        layer.close(index);
                                        layui.table.reload("userListTable");
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
        }else if (layEvent === "open"){ //修改为禁用
            window.resources("SYSTEM_USER_DISABLE", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定修改为禁用吗？',{icon:3, title:'提示信息'},function(index){
                        var dex = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
                        $.ajax({
                            url: "/system/user/editUserState",
                            data: {
                                userId : data.userId,
                                userState : 50
                            },
                            type: "POST",
                            cache:false,
                            success: function (data) {
                                if (data.state == "200"){
                                    setTimeout(function(){
                                        top.layer.msg(data.msg, {icon: 6});
                                        top.layer.close(dex);
                                        layer.close(index);
                                        layui.table.reload("userListTable");
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
        }else if (layEvent === "changePwd"){

            window.resources("SYSTEM_USER_UPDATE", function (e) {
                if (e.state == "200") {
                    changePwdUser(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if (layEvent === "userRole"){
            window.resources("SYSTEM_USER_ROLE", function (e) {
                if (e.state == "200") {
                    roleDetails(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET")

        }

    });
    //点击添加按钮
    $('#btnAddUser').click(function(){
        window.resources("SYSTEM_USER_INSERT", function (e) {
            if (e.state == "200") {
                openAddUser();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加用户框
     */
    function openAddUser() {
        layer.open({
            type : 2,
            title: '添加用户',
            offset: '10%',
            area: ['800px', '520px'], //宽高
            content: '/page/system/user/html/userAdd.html',
            tableId: '#userList'
        });
    };

    /**
     * 弹出修改用户框
     * @param edit
     */
    function openEditUser(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改用户',
                offset: '10%',
                area: ['60%', '50%'],
                content: '/page/system/user/html/userEdit.html',
                tableId: '#userList',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".userId").val(edit.userId);
                        body.find(".userName").val(edit.userName);
                        body.find(".loginName").val(edit.loginName);
                        body.find(".userPhone").val(edit.userPhone);
                        body.find(".userRoleState select").val(edit.userRoleState);
                        form.render();
                    }
                }
            });
        },500);
    }

    /**
     * 权限设置
     * @param edit
     */
    function permissionSettings(edit){
        console.log("+++++++++++++++++++++++")
        layer.open({
            type: 2,
            title: '权限设置',
            area: ['300px', '400px'],
            content: '/page/system/user/html/scenicSpotTree.html',
            tableId: '#userList',
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".userId").val(edit.userId);
                    form.render();
                }
            }
        });
    }


    /**
     * 重置密码
     */

    function changePwdUser(edit) {
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '重置密码',
                offset: '10%',
                area: ['60%', '50%'],
                content: '/page/system/user/html/changePwd.html',
                tableId: '#userList',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".userId").val(edit.userId);
                        body.find(".userName").val(edit.userName);
                        body.find(".loginName").val(edit.loginName);
                        body.find(".userPhone").val(edit.userPhone);
                        body.find(".userRoleState select").val(edit.userRoleState);
                        form.render();
                    }
                }
            });
        },500);

    }


    /**
     * 跳转详情页面
     * @param edit
     */
    function viewDetails(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '权限详情',
                offset: '10%',
                area: ['1000px', '580px'], //宽高
                content: '/page/system/user/html/authorityDetailsList.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".userId").val(edit.userId);//用户ID
                        form.render();
                    }
                }
            });
        },500);
    }

    /**
     * 跳转用户角色详情页面
     * @param edit
     */
    function roleDetails(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '用户角色',
                offset: '10%',
                area: ['1000px', '580px'], //宽高
                content: '/page/system/user/html/userRoleDetailsList.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".userId").val(edit.userId);//用户ID
                        form.render();
                    }
                }
            });
        },500);
    }

    //点击导出EXCEL表
    $('#downloadExcel').click(function(){
        window.resources("SYSTEM_USER_DELETE", function (e) {
            if (e.state == "200") {
                downloadExcel();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 导出EXCEL表
     */
    function downloadExcel(){
        var userName = $(".userNameVal").val();
        var loginName = $(".loginNameVal").val();
        window.location.href = "/system/user/uploadUserExcel?userName="+userName+"&loginName="+loginName;
    }

    //导入Excel表
    upload.render({
        elem: '#importExcel'
        ,url: '/system/user/upload'
        ,accept: 'file' //普通文件
        ,exts: 'xls|xlsx' //只允许上传Excel文件
        ,done: function(res){
            if (res.state == 200){
                setTimeout(function(){
                    top.layer.msg(res.msg, {icon: 6});
                    layui.table.reload("userListTable");
                },500);
            }else{
                setTimeout(function(){
                    top.layer.msg(res.msg, {icon: 5,time: 1000,shift: 6});
                },500);
            }
        }
    });

})
