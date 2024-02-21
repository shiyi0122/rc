layui.use(['form','layer','table','laytpl'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    $ = layui.jquery;

    var tableIns = table.render({
        elem: '#auctionList',
        url : '/business/auction/getAuctionList',
        cellMinWidth : 100,
        page : true,
        height : "full-125",
        request: {
            pageName: 'pageNum', //页码的参数名称，默认：pageNum
            limitName: 'pageSize' //每页数据量的参数名，默认：pageSize
        },
        where: {
            type : '1'
        },
        response:{
            statusName: 'code', //数据状态的字段名称，默认：code
            statusCode: 200, //成功的状态码，默认：0
            countName: 'totals', //数据总数的字段名称，默认：count
            dataName: 'list' //数据列表的字段名称，默认：data
        },
        id : "auctionListTable",
        cols : [[
            {field: 'scenicSpotName', title: '景区名称', minWidth:100, align:"center"},
            {field: 'robotTables', title: '发布机器人总台数', minWidth:100, align:"center"},
            {field: 'stockNumber', title: '机器人库存量', minWidth:100, align:"center"},
            {field: 'totalPrice', title: '总起拍价', minWidth:100, align:"center"},
            {field: 'bond', title: '保证金', minWidth:100, align:"center"},
            {field: 'effective', title: '是否有效', minWidth:100, align:"center"},
            {title: '操作', minWidth:175, templet:'#auctionListBar',fixed:"right",align:"center"}
        ]]
    });

    //列表操作
    table.on('tool(auctionList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            window.resources("BUSINESS_AUCTION_UPDATE", function (e) {
                if (e.state == "200") {
                    openEditAuction(data);
                } else {
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }else if(layEvent === 'del'){ //删除
            window.resources("BUSINESS_AUCTION_DELETE", function (e) {
                if (e.state == "200"){
                    window.resourcedel("/business/auction/delRushPurchase", {id : data.id}, function (data) {}, false,"GET","auctionListTable");
                }else if (e.state == "400"){
                    layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
                }
            }, false,"GET");
        }
    });

    //点击添加按钮
    $('#btnAddAuction').click(function(){
        window.resources("BUSINESS_AUCTION_INSERT", function (e) {
            if (e.state == "200") {
                openAddAuction();
            } else {
                layer.msg(e.msg, {icon: 5,time: 1000,shift: 6});
            }
        }, false,"GET");
    })

    /**
     * 弹出添加框
     */
    function openAddAuction() {
        layer.open({
            type : 2,
            title: '添加竞拍',
            offset: '10%',
            area: ['800px', '80%'],
            content: '/page/business/auction/html/auctionAdd.html'
        });
    };

    /**
     * 弹出编辑框
     * @param edit
     */
    function openEditAuction(edit){
        var dex = top.layer.msg('数据加载中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.open({
                type : 2,
                title: '修改竞拍',
                offset: '10%',
                area: ['800px', '80%'],
                content: '/page/business/auction/html/auctionEdit.html',
                success : function(layero, index){
                    var body = layui.layer.getChildFrame('body', index);
                    if(edit){
                        top.layer.close(dex);
                        body.find(".id").val(edit.id);
                        body.find(".scenicSpotIds").val(edit.scenicSpotId);
                        body.find(".robotTables").val(edit.robotTables);
                        body.find(".unitPrice").val(edit.unitPrice);
                        body.find(".bond").val(edit.bond);
                        body.find(".startTime").val(edit.startTime);
                        body.find(".endTime").val(edit.endTime);
                        body.find("#demo").html(edit.agreement);
                        form.render();
                    }
                }
            });
        },500);
    }
})