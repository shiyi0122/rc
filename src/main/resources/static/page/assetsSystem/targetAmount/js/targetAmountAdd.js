layui.use(['form','layer','jquery','upload'],function(){
    var form = layui.form,
        $ = layui.jquery,
        upload = layui.upload;
    //监听提交
    form.on('submit(btnSubmitTargetAmount)', function(data){
        window.resource("/system/targetAmount/addTargetAmount", data.field, function (data) {}, false,"POST",'targetAmountListTable');
        return false;
    });

})