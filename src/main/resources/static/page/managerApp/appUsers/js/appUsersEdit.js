layui.use(['form','layer'],function(){
    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,

        //监听提交
        form.on('submit(btnSubmitAppUser)', function(data){
            window.resource("/system/appUsers/editAppUsers", data.field, function (data) {}, false,"POST",'appUserListTable');
            return false;
        });

})