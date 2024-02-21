layui.use(['form','layer','jquery','upload','layedit'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;


    //监听提交
    form.on('submit(btnSubmitAllocateRole)', function(data){
        window.resource("/business/businessUsers/editBusinessUsersFilling", data.field, function (data) {}, false,"POST",'businessUsersListTable');
        return false;
    });


})