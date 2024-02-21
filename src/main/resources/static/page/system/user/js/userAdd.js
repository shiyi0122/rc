layui.use(['form','layer'],function(){
    var form = layui.form;

    //监听提交
    form.on('submit(btnSubmitUser)', function(data){
        window.resource("/system/user/addUser", data.field, function (data) {}, false,"POST",'userListTable');
        return false;
    });

    // 校验两次密码是否一致
    form.verify({
        confirmPass:function(value){
            if($('input[name=password]').val() !== value)
                return '两次密码输入不一致！';
        }
    });

})