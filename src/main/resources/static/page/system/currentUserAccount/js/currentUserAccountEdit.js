layui.use(['form','layer','jquery','upload'],function(){
    var form = layui.form,
        $ = layui.jquery,
        upload = layui.upload;
    //监听提交
    form.on('submit(btnSubmitCurrentUserAccount)', function(data){
        window.resource("/system/currentUserAccount/editCurrentUserAccount", data.field, function (data) {}, false,"POST",'currentUserAccountListTable');
        return false;
    });

})