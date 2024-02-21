layui.use(['form','layer','jquery'],function(){
    var form = layui.form;
    $ = layui.jquery;
    //监听提交
    form.on('submit(btnSubmitActivity)', function(data){
        window.resource("/system/scenicSpotActivity/addScenicSpotActivity", data.field, function (data) {}, false,"POST",'scenicSpotActivityListTable');
        return false;
    });

})