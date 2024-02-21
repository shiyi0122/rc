layui.use(['form','layer','jquery','upload'],function(){
    var form = layui.form,
        $ = layui.jquery,
        upload = layui.upload;
    //监听提交
    form.on('submit(btnSubmitAscriptionCompany)', function(data){
        window.resource("/system/ascriptionCompany/editAscriptionCompany", data.field, function (data) {}, false,"POST",'ascriptionCompanyListTable');
        return false;
    });

})