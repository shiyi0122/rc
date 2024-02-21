layui.use(['form','layer','jquery','upload'],function(){
    var form = layui.form,
        $ = layui.jquery,
        upload = layui.upload;
    //监听提交
    form.on('submit(btnSubmitOfflineTradingOrder)', function(data){
        window.resource("/business/order/editOrder", data.field, function (data) {}, false,"POST",'offlineTradingOrderListTable');
        return false;
    });

})