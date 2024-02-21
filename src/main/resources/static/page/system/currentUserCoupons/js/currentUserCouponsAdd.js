layui.use(['form','layer','jquery','upload'],function(){
    var form = layui.form,
        $ = layui.jquery,
        upload = layui.upload;
    //监听提交
    form.on('submit(btnSubmitUserCoupons)', function(data){
        window.resource("/system/currentUserCoupons/addCurrentUserCoupons", data.field, function (data) {}, false,"POST",'currentUserCouponsListTable');
        return false;
    });

})