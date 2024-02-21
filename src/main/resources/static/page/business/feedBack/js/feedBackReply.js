layui.use(['form','layer','jquery'],function(){
    var form = layui.form;
    //监听提交
    form.on('submit(btnSubmitReply)', function(data){
        window.resource("/business/feedBack/editReply", data.field, function (data) {}, false,"POST",'feedBackListTable');
        return false;
    });

})