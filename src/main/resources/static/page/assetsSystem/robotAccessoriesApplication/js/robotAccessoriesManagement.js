layui.use(['form','layer','table','laytpl','upload'],function(){
    var form = layui.form;
    var layer = layui.layer;
    var laytpl = layui.laytpl;
    var table = layui.table;
    var upload = layui.upload;
    $ = layui.jquery;

    table.render({
        elem: '#accessoriesRecordsList',
        url : '/system/robotAccessoriesApplication/robotAccessoriesIdByApplication',
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
        id : "accessoriesRecordsListTable",
        cols : [[
            {field: 'accessoriesName', title: '配件名称', minWidth:100, align:"center",fixed:"left"},
            {field: 'accessoriesType', title: '配件类型', minWidth:100, align:"center"},
            {field: 'accessoryPrice', title: '配件价格', minWidth:100, align:"center"},
            {field: 'accessoryModel', title: '配件型号', minWidth:100, align:"center"},
            {field: 'accessoryNumber', title: '数量', minWidth:100, align:"center"},
            {field: 'warehouseId', title: '发货库房', minWidth:100, align:"center"},
            {field: 'shippingInstructions', title: '发货说明', minWidth:100, align:"center"},
            {field: 'courierNumber', title: '快递单号', minWidth:100, align:"center"},
            {field: 'expressFee', title: '快递费', minWidth:100, align:"center"},
            {field: 'accessoriesReceivedType', title: '是否签收', minWidth:100, align:"center",templet:function(d){
                    if(d.accessoriesReceivedType == "1"){
                        return "已签收";
                    }else if(d.processingProgress == "2"){
                        return "未签收";
                    }else{
                        return "未签收"
                    }
                }},
            {field: 'createDate', title: '创建时间', minWidth:100, align:"center"},
            {field: 'updateDate', title: '修改时间', minWidth:100, align:"center"},
            {field: 'isSendOutGoods', title: '是否发货', minWidth:100, align:"center",templet:function(d){
                    if(d.accessoriesReceivedType == "1"){
                        return "已发货";
                    }else if(d.processingProgress == "2"){
                        return "未发货";
                    }else{
                        return "未发货"
                    }
                }},

        ]],
        where : {
            accessoriesApplicationId: $(".accessoriesApplicationId").val(),

        }
    });

    // table.reload("accessoriesRecordsListTable",{
    //     page: {
    //         curr: 1 //重新从第 1 页开始
    //     },
    //     where : {
    //         accessoriesName: $(".accessoriesName").val(),
    //     }
    // });



})
