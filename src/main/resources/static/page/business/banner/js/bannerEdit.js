layui.use(['form','layer','jquery','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var laydate = layui.laydate;
    $ = layui.jquery;
    upload = layui.upload;

    //监听提交
    form.on('submit(btnSubmitBanner)', function(data){
        window.resource("/business/banner/editBanner", data.field, function (data) {}, false,"POST",'bannerListTable');
        return false;
    });

    upload.render({
        elem: '#filePC',
        url: 'https://oursjxzy.topsroboteer.ac.cn/common/upload_file',
        accept: 'file',//普通文件
        exts: 'jpg|png',//只允许上传图片
        done: function(res){
            if (res.status == "200") {
                $(".webImageId").attr("value",res.data.attachId);
            }else{
                layer.msg('上传失败');
            }
        }
    });
    upload.render({
        elem: '#fileAPP',
        url: 'https://oursjxzy.topsroboteer.ac.cn/common/upload_file',
        accept: 'file',//普通文件
        exts: 'jpg|png',//只允许上传图片
        done: function(res){
            if (res.status == "200") {
                $(".appImageId").attr("value",res.data.attachId);
            }else{
                layer.msg('上传失败');
            }
        }
    });

})