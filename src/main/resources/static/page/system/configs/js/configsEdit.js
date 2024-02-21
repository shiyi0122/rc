layui.use(['form','layer','jquery','upload','layedit'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var laydate = layui.laydate;
    var layedit = layui.layedit;
    $ = layui.jquery;
    upload = layui.upload;

    layedit.set({
        uploadImage: {
            url: '/business/article/uploadApiFile' //接口url
            ,type: 'POST' //默认post
        }
    });

    //监听提交
    form.on('submit(btnSubmitCnfigs)', function(data){
        window.resource("/system/configs/editConfigs", data.field, function (data) {}, false,"POST",'configsListTable');
        return false;
    });

    var index =  layedit.build('configsValues'); //建立编辑器
    form.verify({
        content: function(value) {
            return layedit.sync(index);
        }
    });

});