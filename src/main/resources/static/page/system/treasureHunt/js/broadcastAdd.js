layui.use(['form','layer','jquery','upload'],function(){
    var form = layui.form,
        $ = layui.jquery,
        upload = layui.upload;
    //监听提交
    form.on('submit(btnSubmitBroadcast)', function(data){
        // console.log(data.field)
        window.resource("/system/treasureHunt/addBroadcastHunt", data.field, function (data) {}, false,"POST",'broadcastListTable');
        return false;
    });

})