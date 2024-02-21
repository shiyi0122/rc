layui.use(['form','layer','jquery','upload'],function(){
    var form = layui.form,
        $ = layui.jquery,
        upload = layui.upload;
    //监听提交
    form.on('submit(btnSubmitCustomType)', function(data){
        window.resource("/system/customType/editCustomType", data.field, function (data) {}, false,"POST",'customTypeListTable');
        return false;
    });

})