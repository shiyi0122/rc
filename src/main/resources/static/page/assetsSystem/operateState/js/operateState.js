var myChart;

layui.use(['form', 'layedit', 'laydate', 'table'], function () {
    var form = layui.form
        , layer = layui.layer
        , layedit = layui.layedit
        , laydate = layui.laydate
        , table = layui.table;

    var spotCols = [[
        {field: 'spotName', title: '景区名称', align: 'center'}
        , {
            field: 'yys', title: '有营收/台', totalRow: true, align: 'center', templet: function (d) {
                return "<a onclick='showDetail(" + d.spotId + ",1)'>" + d.yys + "</a>";
            }
        }
        , {
            field: 'yysbl', title: '有营收比例', align: 'center' , sort : true, templet: function (d) {
                return (d.yysbl == null ? 0 : d.yysbl) + "%";
            }
        }
        , {field: 'wys', title: '无营收/台', totalRow: true, align: 'center', templet: function (d) {
                return "<a onclick='showDetail(" + d.spotId + ",2)'>" + d.wys + "</a>";
            }}
        , {
            field: 'wysbl', title: '无营收比例', align: 'center' , sort : true, templet: function (d) {
                return (d.wysbl == null ? 0 : d.wysbl) + "%";
            }
        }
        , {field: 'gz', title: '故障/台', totalRow: true, align: 'center'}
        , {
            field: 'gzbl', title: '故障比例', align: 'center', templet: function (d) {
                return (d.gzbl == null ? 0 : d.gzbl) + "%";
            }
        },
        {
            field: 'jkd', title: '健康度', align: 'center', templet: function (d) {
                return (d.jkd == null ? 0 : d.jkd) + "%";
            }
        }
    ]]
        , robotCols = [[
        {field: 'SCENIC_SPOT_NAME', title: '景区名称', align: 'center'}
        , {field: 'ROBOT_CODE', title: '机器人编码', totalRow: true, align: 'center'}
        , {field: 'ORDER_AMOUNT', title: '交易金额/元', totalRow: true, align: 'center'}
        , {field: 'TOTAL_TIME', title: '运营时长/分钟', totalRow: true, align: 'center'}
    ]];

    var chartDom = document.getElementById('main');
    myChart = echarts.init(chartDom);

    $.ajax({
        url: "/system/subscriptionInformation/allSpot",
        success: function (data) {
            data = data.data;
            $.each(data, function (index, item) {
                $("#spot").append("<option value='" + item.SCENIC_SPOT_ID + "'>" + item.SCENIC_SPOT_NAME + "</option>");
            })
            form.render();
            //日期
            laydate.render({
                elem: '#beginDate',
                value: getRecentDay(-1),
                max: getRecentDay(-1)
            });
            laydate.render({
                elem: '#endDate',
                value: getRecentDay(-1),
                max: getRecentDay(-1)
            });
            getReport();
        }
    })


    $("#reset").on('click' , function () {
        window.location.reload();
    })

    $("#searchEcharts").on('click' , function () {
        $(".layui-table-view").remove();

        $("#main").attr("style" , "display:block");
        getReport();
    })

    $("#exportPng").on('click' , function () {
        var chartUrl = myChart.getDataURL();
        $("#exportPng").attr("href", chartUrl);
    })

    $('#searchTable').on('click', function () {

        $("#main").attr("style" , "display:none");

        if ($("#type").val() == '1') {
            var obj = {field : 'yysbl' , type : 'desc'}
            table.render({
                id: 'searchTable'
                , elem: '#table'
                , url: '/system/operateState/getOperateStateSpotList'
                , title: '用户数据表'
                , totalRow: false
                , height: 'full-125'
                , cols: spotCols
                , where: getFormData(obj)
                , initSort: obj
                , page: true
                , response: {
                    statusCode: 200
                }
                , parseData: function (res) {
                    return {
                        'code': res.state,
                        'msg': res.msg,
                        'count': res.data.total,
                        'data': res.data.list
                    }
                }
            });
        } else {

            table.render({
                id: 'searchTable'
                , elem: '#table'
                , url: '/system/operateState/getOperateStateRobotList'
                , title: '用户数据表'
                , totalRow: false
                , height: 'full-125'
                , cols: robotCols
                , where: getFormData()
                , page: true
                , response: {
                    statusCode: 200
                }
                , parseData: function (res) {
                    return {
                        'code': res.state,
                        'msg': res.msg,
                        'count': res.data.total,
                        'data': res.data.list
                    }
                }
            });
        }
    });

    $("#exportExcel").on('click' , function () {
        window.open('/system/operateState/exportExcel?spotId=' + $("#spot").val() + '&type=' + $("#type").val() + '&beginDate=' + $("#beginDate").val() + '&endDate=' + $("#endDate").val())
    })
    
    table.on('sort(table)' , function (obj) {
        table.reload('searchTable', { //testTable是表格容器id
            initSort: obj //记录初始排序，如果不设的话，将无法标记表头的排序状态。 layui 2.1.1 新增参数
            ,where: getFormData(obj)
        });
    })

});


function getFormData(obj) {
    return {
        beginDate: $("#beginDate").val(),
        endDate: $("#endDate").val(),
        spotId: $("#spot").val(),
        field : obj != undefined ? (obj["field"] + " " + obj["type"]) : null
    };
}

function getRecentDay(day) {
    var today = new Date();
    var targetday_milliseconds = today.getTime() + 1000 * 60 * 60 * 24 * day;
    today.setTime(targetday_milliseconds);
    var tYear = today.getFullYear();
    var tMonth = today.getMonth();
    var tDate = today.getDate();
    tMonth = doHandleMonth(tMonth + 1);
    tDate = doHandleMonth(tDate);
    return tYear + "-" + tMonth + "-" + tDate;
}

function doHandleMonth(month) {
    var m = month;
    if (month.toString().length == 1) {
        m = "0" + month;
    }
    return m;
}

function showDetail(spotId, type) {
    layer.open({
        type: 2,
        title: '机器人明细',
        shadeClose: true,
        shade: 0.3,
        maxmin: true, //开启最大化最小化按钮
        area: ['80%', '80%'],
        content: '/page/assetsSystem/operateState/html/detail.html?spotId=' + spotId + '&type=' + type + '&beginDate=' + $("#beginDate").val() + '&endDate=' + $("#endDate").val()
    });
}

function getReport() {

    $.ajax({
        url: "/system/operateState/getReport",
        data: {
            spotId: $("#spot").val(),
            beginDate : $("#beginDate").val(),
            endDate : $("#endDate").val()
        },
        success : function (data) {
            var x = [] , y1 = [] , y2 = [] , y3 = []
            $.each(data.data , function (index , item) {
                x.push(item.spotName);
                y1.push(item.yys);
                y2.push(item.wys);
                y3.push(item.gz);
            });


            var option = {
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        type: 'shadow' // 'shadow' as default; can also be 'line' or 'shadow'
                    },
                    // formatter: function (params) {
                    //     var varchar = params[0].axisValue + "<br/>"
                    //     $.each(params , function (index , item){
                    //         varchar += item.marker + item.seriesName + '&nbsp;&nbsp;<span style="text-align: right">' + item.data + '台</span><br/>';
                    //     });
                    //     return varchar;
                    // }
                },
                toolbox: {
                    show: false,
                    feature: {
                        restore: {},
                        saveAsImage: {}
                    }
                },
                dataZoom: [
                    {
                        type: 'inside',
                        start: 0,
                        end: 10
                    },
                    {
                        start: 0,
                        end: 10
                    }
                ],
                legend: {},
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                yAxis: {
                    type: 'value'
                },
                xAxis: {
                    type: 'category',
                    data: x
                },
                series: [
                    {
                        name: '有营收/台',
                        type: 'bar',
                        stack: 'total',
                        label: {
                            show: true
                        },
                        emphasis: {
                            focus: 'series'
                        },
                        data: y1
                    },
                    {
                        name: '无营收/台',
                        type: 'bar',
                        stack: 'total',
                        label: {
                            show: true
                        },
                        emphasis: {
                            focus: 'series'
                        },
                        data: y2
                    },
                    {
                        name: '故障/台',
                        type: 'bar',
                        stack: 'total',
                        label: {
                            show: true
                        },
                        emphasis: {
                            focus: 'series'
                        },
                        data: y3
                    }
                ]
            };

            myChart.setOption(option , true);
        }
    })
}
