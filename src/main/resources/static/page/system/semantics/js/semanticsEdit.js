layui.use(['form','layer'],function(){
    var form = layui.form;

    //监听提交
    form.on('submit(btnSubmitSemantics)', function(data){
        window.resource("/system/semantics/editSemantics", data.field, function (data) {}, false,"POST",'semanticsListTable');
        return false;
    });

})