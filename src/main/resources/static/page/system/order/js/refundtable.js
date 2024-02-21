var data = {
  // 原因列表
  reasons: [
  ],
};

/**
 * 获取故障选项列表
 */
(async function getBugOptions() {
  const { data: res } = await axios.get(
      "/system/orderExceptionManagement/getOrderExceptionManagementVoList"
  );
  var resData = res.data;

  var obj = {
    options: []
  }

  var causes10 = {
    bugtypename: "", // 故障类型名称
    options: {} // 故障选项列表
  }
  var causes20 = {
    bugtypename: "", // 故障类型名称
    options: {} // 故障选项列表
  }
  var causes30 = {
    bugtypename: "", // 故障类型名称
    options: {} // 故障选项列表
  }
  var causes40 = {
    bugtypename: "", // 故障类型名称
    options: {} // 故障选项列表
  }

  var causeList = []

  resData.forEach((item, index) => {

    if (item.causes.indexOf("10") !== -1) {
      causes10.options[item.orderExceptionManagementId] = item.reason
      causes10.bugtypename = "常报故障"
    }
    if (item.causes.indexOf("20") !== -1) {
      causes20.options[item.orderExceptionManagementId] = item.reason
      causes20.bugtypename = "硬件故障"
    }
    if (item.causes.indexOf("30") !== -1) {
      causes30.options[item.orderExceptionManagementId] = item.reason
      causes30.bugtypename = "软件故障"
    }
    if (item.causes.indexOf("40") !== -1) {
      causes40.options[item.orderExceptionManagementId] = item.reason
      causes40.bugtypename = "非故障"
    }
  });
  console.log(resData)

  console.log(causes10, causes20, causes30, causes40)
  data.reasons.push({
    name: causes10.bugtypename,
    options: causes10.options
  })
  data.reasons.push({
    name: causes20.bugtypename,
    options: causes20.options
  })
  data.reasons.push({
    name: causes30.bugtypename,
    options: causes30.options
  })
  data.reasons.push({
    name: causes40.bugtypename,
    options: causes40.options
  })
  console.log(data.reasons)

  await layui.use(['form','layer','laytpl'],function () {
    var laytpl = layui.laytpl;
    var form = layui.form;

    var getTpl = reportReason.innerHTML,
        viewTemplate = document.getElementById("viewTemplate");
    laytpl(getTpl).render(data, function (html) {
      viewTemplate.innerHTML = html;
    });
  });

  //创建一个script标签
  function loadScriptString(src, type, id, code) {
    var script = document.createElement("script"); //创建一个script标签
    script.type = type;
    // script.src = "/layui/layui.js"; // 因为html里已经引过一次这个layui.js了，引入同一个文件第二次不会重新加载，所以不会生效
    script.src = src
    script.id = id
    try {
      //IE浏览器认为script是特殊元素,不能再访问子节点;报错;
      script.appendChild(document.createTextNode(code));
    } catch (ex) {
      script.text = code;
    }
    document.getElementsByTagName("body")[0].appendChild(script);
  }
  setTimeout(()=>{
    loadScriptString("https://cdn.bootcdn.net/ajax/libs/layui/2.6.8/layui.js", "text/javascript");
  }, 100)
})();
