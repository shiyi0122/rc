layui.use(['form','layer','jquery'],function(){
    var form = layui.form;
    //监听提交
    form.on('submit(btnSubmitPartsManagement)', function(data){
        window.resource("/system/robotPartsManagement/addRobotPartsManagement", data.field, function (data) {}, false,"POST",'robotPartsManagementListTable');
        return false;
    });

})