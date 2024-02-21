layui.use(['form','layer','jquery','upload'],function(){
    var form = layui.form,
        $ = layui.jquery,
        upload = layui.upload;
    //监听提交
    form.on('submit(btnSubmitInnercircle)', function(data){
        window.resource("/system/innercircle/editInnercircle", data.field, function (data) {}, false,"POST",'innercircleListTable');
        return false;
    });

})