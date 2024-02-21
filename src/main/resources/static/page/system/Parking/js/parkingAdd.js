layui.use(['form','layer','jquery','upload'],function(){
    var form = layui.form,
        $ = layui.jquery,
        upload = layui.upload;
    //监听提交
    form.on('submit(btnSubmitParking)', function(data){
        window.resource("/system/parking/addParking", data.field, function (data) {}, false,"POST",'parkingListTable');
        return false;
    });

})