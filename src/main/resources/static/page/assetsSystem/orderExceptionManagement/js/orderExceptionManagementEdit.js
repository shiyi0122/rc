layui.use(['form','layer','jquery'],function(){
    var form = layui.form;
    $ = layui.jquery;
    //监听提交
    form.on('submit(btnSubmitOrderExceptionManagement)', function(data){
        window.resource("/system/orderExceptionManagement/editOrderExceptionManagement", data.field, function (data) {}, false,"POST",'orderExceptionManagementListTable');
        return false;
    });

})