layui.use(['form','layer','jquery','upload'],function(){
    var form = layui.form,
        $ = layui.jquery,
        upload = layui.upload;
    //监听提交
    form.on('submit(btnSubmitParking)', function(data){
        window.resource("/system/sysRobotUnusualTime/addSysRobotUnusualTime", data.field, function (data) {}, false,"POST",'unusualTimeListTable');
        return false;
    });

})