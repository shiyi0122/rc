layui.use(['form','layer','jquery'],function(){
    var form = layui.form;
    //监听提交
    form.on('submit(btnSubmitRobot)', function(data){
        window.resource("/system/sysRobotSimSupplier/editSysRobotSimSupplier", data.field, function (data) {}, false,"POST",'sysRobotSimSupplierListTable');
        return false;
    });

})