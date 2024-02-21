layui.use(['form','layer','jquery','upload'],function(){
    var form = layui.form,
        $ = layui.jquery,
        upload = layui.upload;
    //监听提交
    form.on('submit(btnSubmitUnusualLog)', function(data){
        window.resource("/system/sysRobotUnusualLog/editSysRobotUnusualLog", data.field, function (data) {}, false,"POST",'unusualLogListTable');
        return false;
    });

})