layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#appUserList',
        url : '/system/appUsers/getAppUsersList',
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
        id : "appUserListTable",
        cols : [[
            {field: 'userName', title: '用户名称', minWidth:100, align:"center"},
            {field: 'loginName', title: '用户账号', minWidth:100, align:"center"},
            {field: 'userState', title: '用户状态', align:'center',templet:function(d){
                    if(d.userState == "10"){
                        return "正常";
                    }else if(d.userState == "50"){
                        return "禁用";
                    }
                }},
            {field: 'userType', title: '岗位级别', align:'center',templet:function(d){
                    if(d.userType == "1"){
                        return "一线运营";
                    }else if(d.userType == "2"){
                        return "园长";
                    }else if (d.userType == "3"){
                        return "区域经理";
                    }else if (d.userType == "4"){
                        return "运营总监";
                    }else{

                    }
                }},
            {field: 'userApproval', title: '配件申请权', align:'center',templet:function(d){
                    if(d.userApproval == "1"){
                        return "有权限";
                    }else if(d.userApproval == "0"){
                        return "无权限";
                    }else{
                        return "暂无";
                    }
                }},
            {field: 'createDate', title: '创建时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#appUserListBar',fixed:"right",align:"center"}
        ]]
    });

    /**
     * 根据用户名称模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("appUserListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                userName: $(".userNameVal").val(), //搜索的关键字
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
    table.on('tool(appUserList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("SYS_APP_USERS_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditAppUser(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("SYS_APP_USERS_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/system/appUsers/delAppUsers", {userId : data.userId}, function (data) {}, false,"GET","appUserListTable");
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
        }else if (layEvent === "resetPassword"){ //重置密码
            window.resources("SYSTEM_USER_PERMISSION_SETTINGS", function (e) {
                if (e.state == "200") {
                    resetPassword(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });
    //点击添加按钮
    $('#btnAddUser').click(function(){
        window.resources("SYS_APP_USERS_INSERT", function (e) {
            if (e.state == "200") {
                openAddAppUser();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加用户框
     */
    function openAddAppUser() {
        layer.open({
            type : 2,
            title: '添加APP用户',
            offset: '10%',
            area: ['800px', '520px'], //宽高
            content: '/page/managerApp/appUsers/html/appUsersAdd.html'
        });
    };

    /**
     * 弹出修改用户框
     * @param edit
     */
    function openEditAppUser(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改APP用户',
                offset: '10%',
                area: ['800px', '330px'],
                content: '/page/managerApp/appUsers/html/appUsersEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".userId").val(edit.userId);
                        body.find(".userName").val(edit.userName);
                        body.find(".loginName").val(edit.loginName);
                        body.find(".userApproval select").val(edit.userApproval);
                        body.find(".userType select").val(edit.userType);
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
        layer.open({
            type: 2,
            title: '权限设置',
            area: ['300px', '400px'],
            content: '/page/managerApp/appUsers/html/scenicSpotApp.html',
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
     * @param edit
     */
    function resetPassword(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '重置密码',
                offset: '10%',
                area: ['800px', '460px'],
                content: '/page/managerApp/appUsers/html/resetPassword.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".userId").val(edit.userId);
                        body.find(".userName").val(edit.userName);
                        body.find(".loginName").val(edit.loginName);
                        form.render();
                    }
                }
            });
        },500);
    }

	//点击导出EXCEL表
    $('#downloadExcel').click(function(){
        window.resources("FINANCE_ORDER_DOWNLOADEXCEL", function (e) {
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
        window.location.href = "/system/appUsers/uploadUserExcel?userName="+userName;
    }

})
