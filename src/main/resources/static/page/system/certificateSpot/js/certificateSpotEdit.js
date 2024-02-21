layui.use(['form','layer','jquery'],function(){
    var form = layui.form;
    $ = layui.jquery;
    //监听提交
    form.on('submit(btnSubmitCertificateSpot)', function(data){
        window.resource("/system/certificateSpot/editCertificateSpot", data.field, function (data) {}, false,"POST",'certificateSpotListTable');
        return false;
    });

})