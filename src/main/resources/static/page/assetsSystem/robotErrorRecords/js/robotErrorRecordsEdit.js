layui.use(['form','layer','jquery'],function(){
    var form = layui.form;
    //监听提交
    form.on('submit(btnSubmit)', function(data){
        window.resource("/system/robotErrorRecords/editRobotErrorRecords", data.field, function (data) {}, false,"POST",'robotErrorRecordsListTable');
        return false;
    });

})