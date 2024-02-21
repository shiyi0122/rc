layui.use(['form','layer','table','laytpl','laydate'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var laydate = layui.laydate,

    $ = layui.jquery;

    $.get("/system/user/getUser", function (data) {
        if (data.state = "200"){
            $(".userId").val(data.data.userId); //用户ID
            $(".loginName").val(data.data.loginName); //用户名称
            $(".userName").val(data.data.userName); //用户真实名称
            $(".sex input[value='"+ data.data.userSex +"']").attr("checked","checked"); //性别
            $(".userPhone").val(data.data.userPhone); //用户手机号
            $(".userBirthday").val(data.data.userBirthday); //用户入职日期
            $(".userEmail").val(data.data.userEmail); //用户邮箱
            $(".userUnitAddress").val(data.data.userUnitAddress); //用户单位地址
            form.render();
        }
    })

    //选择出生日期
    laydate.render({
        elem: '.userBirthday',
        format: 'yyyy年MM月dd日',
        trigger: 'click',
        max : 0
    });



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




    //提交个人资料
    form.on("submit(changeUser)",function(data){
        var index = layer.msg('提交中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            type : 'POST',
            url : '/system/user/editUser',
            data : data.field,
            dataType : 'json',
            success:function (data) {
                if (data.state == "200"){
                    setTimeout(function(){
                        layer.close(index);
                        layer.msg(data.msg);
                    },2000);
                }else if(data.state == "400"){
                    layer.msg(data.msg);
                }
            }
        })
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })

    //提交个人资料
    form.on("submit(changeUserInfo)",function(data){
        var index = layer.msg('提交中，请稍候',{icon: 16,time:false,shade:0.8});
        $.ajax({
            type : 'POST',
            url : '/system/user/editUser',
            data : data.field,
            dataType : 'json',
            success:function (data) {
                if (data.state == "200"){
                    setTimeout(function(){
                        layer.close(index);
                        var ind = layer.msg('信息完善成功，请重新登陆！页面跳转中...',{icon: 16,time:false,shade:0.8});
                        setTimeout(function(){
                            layer.close(ind);
                            window.location.href="/logout";
                        },5000);
                    },2000);
                }else if(data.state == "400"){
                    layer.msg(data.msg);
                }
            }
        })
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    })

    //退出登录
    logout = function () {
        window.location.href="/logout";
    }

})
