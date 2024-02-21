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

    var index =  layedit.build('details'); //建立编辑器
    form.verify({
        content: function(value) {
            return layedit.sync(index);
        }
    });

    //监听提交
    form.on('submit(btnSubmitJournalismArticle)', function(data){
        window.resource("/business/businessConfigs/editConfigs", data.field, function (data) {}, false,"POST",'configsListTable');
        return false;
    });

    upload.render({
        elem: '#file',
        url: 'https://oursjxzy.topsroboteer.ac.cn/common/upload_file',
        accept: 'file',//普通文件
        exts: 'jpg|png',//只允许上传图片
        done: function(res){
            if (res.status == "200") {
                $(".coverId").attr("value",res.data.attachId);
            }else{
                layer.msg('上传失败');
            }
        }
    });

})