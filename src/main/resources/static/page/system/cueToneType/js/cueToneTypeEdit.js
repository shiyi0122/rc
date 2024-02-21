layui.use(['form','layer','jquery','upload'],function(){
    var form = layui.form,
        $ = layui.jquery,
        upload = layui.upload;
    //监听提交
    form.on('submit(btnSubmitCueToneType)', function(data){
        window.resource("/system/cueToneType/editCueToneType", data.field, function (data) {}, false,"POST",'cueToneTypeListTable');
        return false;
    });

})