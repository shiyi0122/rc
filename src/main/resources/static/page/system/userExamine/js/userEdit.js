layui.use(['form','layer'],function(){
    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,

        //监听提交
        form.on('submit(btnSubmitUser)', function(data){
            window.resource("/system/user/editUser", data.field, function (data) {}, false,"POST",'userListTable');
            return false;
        });

})