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

    var index =  layedit.build('demo'); //建立编辑器
    form.verify({
        content: function(value) {
            return layedit.sync(index);
        }
    });

    //监听提交
    form.on('submit(btnSubmitRushPurchase)', function(data){
        window.resource("/business/auction/editRushPurchase", data.field, function (data) {}, false,"POST",'rushPurchaseListTable');
        return false;
    });

})