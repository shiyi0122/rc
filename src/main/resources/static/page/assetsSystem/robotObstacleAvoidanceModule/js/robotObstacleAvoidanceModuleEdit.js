layui.use(['form','layer','jquery'],function(){
    var form = layui.form;
    //监听提交
    form.on('submit(btnSubmitObstacleAvoidanceModule)', function(data){
        window.resource("/system/robotObstacleAvoidanceModule/editRobotObstacleAvoidanceModule", data.field, function (data) {}, false,"POST",'robotObstacleAvoidanceModuleListTable');
        return false;
    });

})