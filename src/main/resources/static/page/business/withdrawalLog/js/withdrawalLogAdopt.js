layui.use(['form','layer','jquery'],function(){
    var form = layui.form;
    //监听提交
    form.on('submit(btnSubmitAdopt)', function(data){
        window.resource("/business/withdrawalLog/editAdopt", data.field, function (data) {}, false,"POST",'withdrawalLogListTable');
        return false;
    });

})