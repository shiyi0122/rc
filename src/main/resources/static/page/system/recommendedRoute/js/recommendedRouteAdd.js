layui.use(['form','layer','jquery','upload'],function(){
    var form = layui.form,
        $ = layui.jquery,
        upload = layui.upload;
    //监听提交
    form.on('submit(btnSubmitRecommendedRoute)', function(data){
        window.resource("/system/recommendedRoute/addRecommendedRoute", data.field, function (data) {}, false,"POST",'recommendedRouteListTable');
        return false;
    });

})