layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#bankCardList',
        url : '/business/bankCard/getBankCardList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "bankCardListTable",
        cols : [[
            {field: 'userName', title: '用户名称', minWidth:100, align:"center"},
            {field: 'bankName', title: '开户人名称', minWidth:100, align:"center"},
            {field: 'bankCard', title: '银行卡号', minWidth:100, align:"center"},
            {field: 'bankInfo', title: '开户银行名称', minWidth:100, align:"center"},
            {field: 'bankBranch', title: '开户行支行', minWidth:100, align:"center"},
            {field: 'bankProvince', title: '开户行省市', minWidth:100, align:"center"},
            {field: 'telephone', title: '联系电话', minWidth:100, align:"center"},
            {field: 'rejectInfo', title: '驳回原因', minWidth:100, align:"center"},
            {field: 'state', title: '审核状态', minWidth:100, align:"center",templet:function(d){
                    if(d.state == "0"){
                        return "正常";
                    }else if(d.state == "1"){
                        return "申请中";
                    }else if(d.state == "2"){
                        return "驳回";
                    }
                }},
            {field: 'createTime', title: '添加时间', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#bankCardListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(bankCardList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'adopt'){ //审核通过
            window.resources("BUSINESS_BANK_CARD_TO_EXAMINE", function (e) {
                if (e.state == "200") {
                    layer.confirm('确定审核通过吗？',{icon:3, title:'提示信息'},function(index){
                        var dex = top.layer.msg('数据提交中，请稍候',{icon: 16,time:false,shade:0.8});
                        $.ajax({
                            url: "/business/bankCard/editAdopt",
                            data: {
                                id : data.id,
                                state : 0,
                                userId : data.userId
                            },
                            type: "POST",
                            cache:false,
                            success: function (data) {
                                if (data.state == "200"){
                                    setTimeout(function(){
                                        top.layer.msg(data.msg, {icon: 6});
                                        top.layer.close(dex);
                                        layer.close(index);
                                        layui.table.reload("bankCardListTable");
                                    },500);
                                }else if (data.state == "400"){
                                    setTimeout(function(){
                                        top.layer.close(dex);
                                        layer.close(index);
                                        top.layer.msg(data.msg, {icon: 5,time: 1000,shift: 6});
                                    },500);
                                }
                            }
                        });
                    });
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'reject'){	 //审核驳回
            window.resources("BUSINESS_BANK_CARD_TO_EXAMINE", function (e) {
                if (e.state == "200") {
                    openEditAdopt(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'viewHis'){
			window.resources("BUSINESS_BANK_CARD_TO_EXAMINE", function (e) {
                if (e.state == "200") {
                    openViewHis(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
		}
    });

    /**
     * 模糊查询
     */
    $("#btnSearch").on("click",function(){
        table.reload("bankCardListTable",{
            page: {
                curr: 1 //重新从第 1 页开始
            },
            where: {
                telephone: $(".telephoneVal").val(),
                userName: $(".userNameVal").val(),
                state: $(".state").val()
            }
        })
    });

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    })

	/**
		弹出驳回框
	 */
    function openEditAdopt(edit){
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '驳回原因',
                offset: '10%',
                area: ['800px', '280px'],
                content: '/page/business/bankCard/html/bankCardAdopt.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        body.find(".id").val(edit.id);
                        form.render();
                    }
                }
            });
        },500);
    }


	/**
		查看银行卡最新提交的信息
	 */
    function openViewHis(edit){
		var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '银行卡信息',
                offset: '10%',
                area: ['800px', '570px'],
                content: '/page/business/bankCard/html/viewHis.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    $.ajax({
	                    type : 'POST',
	                    url : '/business/bankCard/getBankCardByUserId',
						data:{
							userId : edit.userId
						},
	                    dataType : 'json',
	                    success:function (data) {
	                        if (data.state == "200"){
								top.layer.close(dex);
	                            body.find(".bankName").val(data.data.bankName);
	                            body.find(".bankCard").val(data.data.bankCard);
	                            body.find(".bankInfo").val(data.data.bankInfo);
	                            body.find(".telephone").val(data.data.telephone);
	                            body.find(".bankProvince").val(data.data.bankProvince);
	                            body.find(".bankBranch").val(data.data.bankBranch);
	                            form.render();
	                        }else if(data.state == "400"){
	                            top.layer.close(dex);
	                            layer.msg(data.msg);
	                        }
	                    }
                	})
                }
            });
        },500);
    }

})