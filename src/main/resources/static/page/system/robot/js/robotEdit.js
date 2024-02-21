layui.use(['form','layer','jquery'],function(){
    var form = layui.form;
    //监听提交
    form.on('submit(btnSubmitRobot)', function(data){
        window.resource("/system/robot/editRobot", data.field, function (data) {}, false,"POST",'robotArchivesListTable');
        return false;
    });

})