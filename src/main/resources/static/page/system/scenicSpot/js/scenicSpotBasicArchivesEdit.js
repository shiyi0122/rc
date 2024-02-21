layui.use(['form','layer','jquery','upload','layedit'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var layedit = layui.layedit;
    $ = layui.jquery;
    upload = layui.upload;

    //监听提交
    form.on('submit(btnSubmitScenicSpotBasicArchives)', function(data){
        window.resource("/system/scenicSpot/editScenicSpotNew", data.field, function (data) {}, false,"POST",'scenicSpotBasicArchivesListTable');
        return false;
    });
    var index =  layedit.build('scenicSpotIntroduce'); //建立编辑器
    form.verify({
        content: function(value) {
            console.log("value"+value);
            // console.log(index);
            return layedit.sync(index);
        }
    });

    upload.render({
        elem: '#file',
        url: 'https://oursjxzy.topsroboteer.ac.cn/common/upload_file',
        accept: 'file',//普通文件
        exts: 'jpg|png',//只允许上传图片
        done: function(res){
            if (res.status == "200") {
                console.log(res.data.attachId)
                $(".imageId").attr("value",res.data.attachId);
            }else{
                console.log(res.data.attachId)
                layer.msg('上传失败');
            }
        }
    });



    /*城市菜单*/
    form.on('select(province)',function(data){
        $('#city').empty();//选择省的时候清空城市地区的值
        $('#city').html('<option value="">市</option>');
        $('#county').empty();
        $('#county').html('<option value="">区/县</option>');
        if(data.value){
            $.ajax({
                type:"POST",
                url:"/business/scenicSpotExpand/getProvince",
                data:{"pid":data.value},
                dataType:"json",
                success:function(data){
                    var item = data.data;
                    var htt = '<option value="">选择市</option>';
                    $.each(item, function (k, v) {
                        htt += '<option value="'+ item[k].id +'">'+ item[k].name +'</option>'
                    });
                    $("#city").html(htt);
                    form.render('select');
                }
            });
        }
    })


    /*区县菜单*/
    form.on('select(city)',function(data){
        $('#county').empty();
        $('#county').html('<option value="">区/县</option>');
        if(data.value){
            $.ajax({
                type:"POST",
                url:"/business/scenicSpotExpand/getProvince",
                data:{"pid":data.value},
                dataType:"json",
                success:function(data){
                    var item = data.data;
                    var htt = '<option value="">选择区/县</option>';
                    $.each(item, function (k, v) {
                        htt += '<option value="'+ item[k].id +'">'+ item[k].name +'</option>'
                    });
                    $("#county").html(htt);
                    form.render('select');
                }
            });
        }
    })

})