layui.use(['form','layer'],function(){
    var form = layui.form;

    //监听提交
    form.on('submit(btnSubmitAppRole)', function(data){
        window.resource("/system/AppRole/addAppRole", data.field, function (data) {}, false,"POST",'appRoleListTable');
        return false;
    });

})