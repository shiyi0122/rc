layui.use(['form','layer','jquery'],function(){
    var form = layui.form;
    //监听提交
    form.on('submit(btnSubmitRobotBelarcAdvisor)', function(data){
        window.resource("/system/robotBelarcAdvisor/editRobotBelarcAdvisor", data.field, function (data) {}, false,"POST",'robotBelarcAdvisorListTable');
        return false;
    });

})