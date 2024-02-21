layui.use(['form','layer'],function(){
    var form = layui.form;

    //监听提交
    form.on('submit(btnSubmitAppUser)', function(data){
        window.resource("/system/appUsers/addAppUser", data.field, function (data) {}, false,"POST",'appUserListTable');
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