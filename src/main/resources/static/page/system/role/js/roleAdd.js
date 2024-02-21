layui.use(['form','layer'],function(){
    var form = layui.form;

    //监听提交
    form.on('submit(btnSubmitRole)', function(data){
        window.resource("/system/role/addRole", data.field, function (data) {}, false,"POST",'roleListTable');
        return false;
    });

})