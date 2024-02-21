layui.use(['form','layer','laydate','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery;

    //添加验证规则
    form.verify({
        newPwd : function(value, item){
            if(value.length < 6){
                return "密码长度不能小于6位";
            }
        },
        confirmPwd : function(value, item){
            if(!new RegExp($("#oldPwd").val()).test(value)){
                return "两次输入密码不一致，请重新输入！";
            }
        }
    })

    //监听提交
    form.on('submit(changePwd)', function(data){
        console.log(data.field)
        var index = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            url: "/system/user/resetPassword",

            data: data.field,
            type: "POST",
            dataType: "json",
            success: function (e) {
                if (e.state == "200") {
                    setTimeout(function(){
                        layer.close(index);
                        layer.msg(e.msg, {icon: 6});
                    },500);
                } else {
                    setTimeout(function(){
                        layer.close(index);
                        layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                    },500);

                }
            },
        });
        return false;
    });

})