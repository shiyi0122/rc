layui.use(['form','layer'],function(){
    var form = layui.form
    layer = parent.layer === undefined ? layui.layer : top.layer,

    //监听提交
    form.on('submit(btnSubmitRole)', function(data){
        window.resource("/system/role/editRole", data.field, function (data) {}, false,"POST",'roleListTable');
        return false;
    });

})