layui.use(['form','layer','jquery'],function(){
    var form = layui.form;
    //监听提交
    form.on('submit(btnSubmitRobotAccessoriesApplication)', function(data){
        window.resource("/system/robotAccessoriesApplication/editRobotAccessoriesApplication", data.field, function (data) {}, false,"POST",'robotAccessoriesApplicationListTable');
        return false;
    });

})