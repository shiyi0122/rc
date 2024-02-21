layui.use(['form','layer','jquery','upload'],function(){
    var form = layui.form,
        $ = layui.jquery,
        upload = layui.upload;
    //监听提交
    form.on('submit(btnSubmitBroadcast)', function(data){
        window.resource("/system/broadcast/addBroadcast", data.field, function (data) {}, false,"POST",'broadcastListTable');
        return false;
    });

})