layui.use(["form", "layer", "table", "laytpl", "upload", "flow"], function () {
    var flow = layui.flow;
    var layer = layui.layer;
    var table = layui.table;
    var $ = layui.jquery;

    var tableData = [];

    //   var load = top.layer.msg("数据加载中，请稍候", {
    //     icon: 16,
    //     time: false,
    //     shade: 0.8,
    //   });

    function renderTable(tableData) {
        table.render({
            elem: "#scenicSpotRankingList",
            // url: "/system/operationBigData/getScenicSpotRankingList",
            data: tableData,
            cellMinWidth: 100,
            page: false,
            limit: 99999999, // 不限制条数
            // height: 400,
            initSort: { field: "orderAmount", type: "desc" },
            autoSort: false, //禁用前端自动排序
            request: {
                pageName: "pageNum", //页码的参数名称，默认：pageNum
                limitName: "pageSize", //每页数据量的参数名，默认：pageSize
            },
            response: {
                statusName: "code", //数据状态的字段名称，默认：code
                statusCode: 200, //成功的状态码，默认：0
                countName: "totals", //数据总数的字段名称，默认：count
                dataName: "list", //数据列表的字段名称，默认：data
            },
            where: {
                id: $(".id").val(),
            },
            id: "scenicSpotRankingListTable",
            cols: [
                [
                    {
                        field: "scenicSpotName",
                        title: "景区名称",
                        minWidth: 100,
                        align: "center",
                    },
                    {
                        field: "operationTime",
                        title: "运营时长/分钟",
                        minWidth: 100,
                        align: "center",
                        sort: true,
                    },
                    {
                        field: "orderNumber",
                        title: "订单数量",
                        minWidth: 100,
                        align: "center",
                        sort: true,
                    },
                    {
                        field: "orderAmount",
                        title: "订单金额/元",
                        minWidth: 100,
                        align: "center",
                        sort: true,
                    },
                    {
                        field: "completionRatio",
                        title: "目标完成比例",
                        minWidth: 100,
                        align: "center",
                        sort: true,
                    },
                    {
                        field: "robotReceivingOrder",
                        title: "单台机器人接单量",
                        minWidth: 100,
                        align: "center",
                        sort: true,
                    },
                    {
                        field: "robotOutputValue",
                        title: "单台机器人产值/元",
                        minWidth: 100,
                        align: "center",
                        sort: true,
                    },
                    {
                        field: "robotOperationTime",
                        title: "单台机器人运营时长/分钟",
                        minWidth: 100,
                        align: "center",
                        sort: true,
                    },
                    {
                        field: "operatorsReceivingOrder",
                        title: "单运营人员接单量",
                        minWidth: 100,
                        align: "center",
                        sort: true,
                    },
                    {
                        field: "robotUtilization",
                        title: "机器人利用率/%",
                        minWidth: 100,
                        align: "center",
                        sort: true,
                    },
                    {
                        field: "unitPricePerCustomer",
                        title: "客单价/元",
                        minWidth: 100,
                        align: "center",
                        sort: true,
                    },
                    {
                        field: "failureRate",
                        title: "订单故障率",
                        minWidth: 100,
                        align: "center",
                        sort: true,
                    },
                    {
                        field: "robotLaunchQuantity",
                        title: "机器人投放数量",
                        minWidth: 100,
                        align: "center",
                        sort: true,
                    },
                ],
            ],
        });
    }
    // renderTable(tableData);

    /**
     * 重置
     */
    $("#reset").click(function () {
        location.reload();
    });

    //点击导出EXCEL表
    $("#downloadExcel").click(function () {
        window.resources(
            "SYSTEM_USER_DELETE",
            function (e) {
                if (e.state == "200") {
                    downloadExcel();
                } else {
                    layer.msg(e.msg, { icon: 5, time: 1000, shift: 6 });
                }
            },
            false,
            "GET"
        );
    });

    /**
     * 导出EXCEL表
     */
    function downloadExcel() {
        var scenicSpotId = $(".scenicSpotId").val();
        var companyId = $(".companyId").val();
        var dataType = $(".dataType").val();
        var startTime = $(".startTime").val();
        var endTime = $(".endTime").val();
        var id = $(".id").val();
        window.location.href =
            "/system/operationBigData/uploadScenicSpotRankingExcel?scenicSpotId=" +
            scenicSpotId +
            "&companyId=" +
            companyId +
            "&dataType=" +
            dataType +
            "&startTime=" +
            startTime +
            "&endTime=" +
            endTime +
            "&id=" +
            id;
    }

    /**
     * 获取表格数据
     */
    var dom = null;
    var pageSize = 10; // 定义每页条数
    var total = 1; // 总页数
    async function getTableData(page) {
        var loading = layer.load(0, {
            shade: false, // 是否有遮罩
            time: 10 * 1000, // 最大等待时间
        });
        const { data: res } = await axios.get(
            "/system/operationBigData/getScenicSpotRankingList",
            {
                params: {
                    pageNum: page, // 当前页数
                    pageSize: pageSize, // 每页的条数
                    id:  $(".id").val(),

                    scenicSpotId: $(".scenicSpotId").val(), //搜索的关键字
                    companyId: $(".companyId").val(), //搜索的关键字
                    dataType: $(".dataType").val(), //搜索的关键字
                    startTime: $(".startTime").val(), //搜索的关键字
                    endTime: $(".endTime").val(), //搜索的关键字
                },
            }
        );
        total = Math.ceil(res.totals / pageSize); // 总条数/每页条数 = 总页数
        tableData.push(...res.list);
        layer.close(loading);
        // 重载表格
        renderTable(tableData);
        // table.reload({
        //   elem: "#scenicSpotRankingList",
        // });

        // 数据加载完毕之后，拿到这个需要滚动的dom元素，在后面的滚动事件中进行操作
        dom = document.querySelector(
            ".layui-table-box .layui-table-body > table > tbody"
        );
    }
    setTimeout(() => {
        getTableData(1); // 初始加载数据(第一次传递的页数应该为1)
    }, 500)

    var isReachBottom = false;
    var reachBottomDistance = 100;
    var bottomOutNum = 1; // 触底次数（作为页数）
    var isBottomOut = false; // 是否触底

    // 监听鼠标滚动事件
    window.addEventListener("mousewheel", function () {
        if (dom) {
            dom.addEventListener("scroll", function (e) {
                let scrollTop = dom.scrollTop;
                let scrollHeight = dom.scrollHeight;

                let offsetHeight = Math.ceil(dom.getBoundingClientRect().height);
                let currentHeight = scrollTop + offsetHeight + reachBottomDistance;

                // console.log(currentHeight, scrollHeight);
                if (currentHeight < scrollHeight && isReachBottom) {
                    isReachBottom = false;
                }
                if (isReachBottom) {
                    return;
                }
                if (isBottomOut) {
                    return;
                }
                // 如果currentHeight >= scrollHeight 并且当前页数小于总页数
                if (currentHeight >= scrollHeight && bottomOutNum < total) {
                    isReachBottom = true;
                    console.log("触底");
                    bottomOutNum++;
                    getTableData(bottomOutNum);
                } else if (currentHeight >= scrollHeight && bottomOutNum == total) {
                    isBottomOut = true; // 已经触底
                    layer.msg("已经到达最底部了");
                    return;
                }
            });
        }
    });

    /**
     * 根据用户名称模糊查询
     */
    $("#btnSearch").on("click", async function () {
        var loading = layer.load(0, {
            shade: false, // 是否有遮罩
            time: 10 * 1000, // 最大等待时间
        });
        const { data: res } = await axios.get(
            "/system/operationBigData/getScenicSpotRankingList",
            {
                params: {
                    pageNum: 1, // 当前页数
                    pageSize: pageSize, // 每页的条数
                    id:  $(".id").val(),

                    scenicSpotId: $(".scenicSpotId").val(), //搜索的关键字
                    companyId: $(".companyId").val(), //搜索的关键字
                    dataType: $(".dataType").val(), //搜索的关键字
                    startTime: $(".startTime").val(), //搜索的关键字
                    endTime: $(".endTime").val(), //搜索的关键字
                },
            }
        );
        tableData = res.list
        layer.close(loading);
        // 重载表格
        renderTable(tableData);
        // table.reload("scenicSpotRasnkingListTable", {
        //     data: tableData
        // });
        // table.reload("scenicSpotRankingListTable", {
        //     page: {
        //         curr: 1, //重新从第 1 页开始
        //     },
        //     where: {
        //         scenicSpotId: $(".scenicSpotId").val(), //搜索的关键字
        //         companyId: $(".companyId").val(), //搜索的关键字
        //         dataType: $(".dataType").val(), //搜索的关键字
        //         startTime: $(".startTime").val(), //搜索的关键字
        //         endTime: $(".endTime").val(), //搜索的关键字
        //     },
        // });
    });

    /**
     * 排序操作
     */
    //注：sort 是工具条事件名，operate_table 是 table 原始容器的属性 lay-filter="对应的值"
    table.on("sort(scenicSpotRankingList)", async function (obj) {
        console.log(obj)
        var loading = layer.load(0, {
            shade: false, // 是否有遮罩
            time: 10 * 1000, // 最大等待时间
        });
        const { data: res } = await axios.get(
            "/system/operationBigData/getScenicSpotRankingList",
            {
                params: {
                    pageNum: 1, // 当前页数
                    pageSize: pageSize, // 每页的条数
                    id:  $(".id").val(),

                    field: obj.field,
                    type: obj.type, //排序方式
                },
            }
        );
        tableData = res.list
        layer.close(loading);
        // 重载表格
        // renderTable(tableData);
        table.reload("scenicSpotRankingListTable", {
            initSort: obj,
            data: tableData
        });
    });
});
