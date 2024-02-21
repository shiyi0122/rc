layui.use([ 'table'], function () {
    var table = layui.table;

    $.ajax({
        url: '/system/operateState/getDetailBySpot',
        type : "GET",
        data : {
            type : getParam("type"),
            spotId: getParam("spotId"),
            beginDate: getParam("beginDate"),
            endDate:getParam("endDate")
        },
        success : function (data){
            table.render({
                data:data.data,
                elem: '#table',
                limit: data.data.length,
                height: 'full-20',
                cols: [[
                    {field: 'zizeng', title: '序号', align: 'center',templet:'#zizeng'}
                    , {field: 'SCENIC_SPOT_NAME', title: '景区名称', align: 'center'}
                    , {field: 'ROBOT_CODE', title: '机器人编码', totalRow: true, align: 'center'}
                    , {field: 'ORDER_AMOUNT', title: '交易金额', totalRow: true, align: 'center'}
                    , {field: 'TOTAL_TIME', title: '运营时长', totalRow: true, align: 'center'}
                ]]
            })
        }
    })

});


function getParam(name) {
    var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = window.location.search.substr(1).match(reg);  //匹配目标参数
    if (r!=null) return unescape(r[2]); return null; //返回参数值
}