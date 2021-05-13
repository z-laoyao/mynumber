var path = "/"
let user;
let data;
let startTime, endTime;
let update = false;
$(function () {
    $.ajax({
        url: "/dic/list",
        type: "POST",
        dataType: "json",
        success: function (resp) {
            data = resp;
        }
    })

    $.ajax({
        url: "/login",
        type: "PUT",
        dataType: "json",
        success: function (responseEntity) {
            if (responseEntity.code == 200) {
                user = responseEntity.data;//给uid赋值
                alarm()
            }
        }
    })

});

function alarm() {
    if (update) {
        return;
    }
    if (data == undefined) {
        return
    }
    let alarm_var = data.alarm
    let maxArr = new Array();
    let isalarm = false;
    for (d of data.data) {
        maxArr.push(d.maxHigh);

    }
    if (maxArr[maxArr.length - 1] >= alarm_var) {
        isalarm = true;
    }

    if (isalarm) {
        layer.confirm("数据超过报警数值 : " + alarm_var, {icon: 2});
    }
}

function down_excl() {
    window.location.href = "/downExcl.xls?startTime=" + startTime + "&endTime=" + endTime;
}

function search_my() {
    startTime = document.getElementsByName("startTime")[0].value;
    endTime = document.getElementsByName("endTime")[0].value;
    search_data()
    return false; //阻止提交
}

function search_data() {
    layui.use('table', function () {
        let table = layui.table;
        //第一个实例
        table.render({
            elem: '#tableList'
            , url: '/dic/search' //数据接口
            , method: "GET"
            , where: {
                'startTime': startTime,
                "endTime": endTime
            }
            , cols: [[
                {field: 'id', width: 100, title: 'ID', align: 'center', sort: true, fixed: 'left'}

                , {field: 'high_1', width: 100, title: 'high_1', sort: true, align: 'center'}
                , {field: 'high_2', width: 100, title: 'high_2', sort: true, align: 'center'}
                , {field: 'high_3', width: 100, title: 'high_3', sort: true, align: 'center'}
                , {field: 'high_4', width: 100, title: 'high_4', sort: true, align: 'center'}
                , {field: 'maxHigh', width: 100, title: 'maxHigh', sort: true, align: 'center'}
                , {field: 'time', width: 300, title: 'time', sort: true, align: 'center'}
            ]]
        });

    });
}

function update_alarm() {
    let alarm = prompt("请输入新的警报值: ");
    if (alarm != null) {
        window.location.href = "/updateAlarm?alarm=" + alarm;
    }

}

setInterval(() => {
    update = true;
    init()
}, 5000)

init();

function init() {
    layui.use('table', function () {
        let table = layui.table;
        //第一个实例
        table.render({
            elem: '#tableList'
            , url: '/dic/list' //数据接口
            , method: "POST"
            , cols: [[
                {field: 'id', width: 100, title: 'ID', align: 'center', sort: true, fixed: 'left'}
                , {field: 'high_1', width: 100, title: 'high_1', sort: true, align: 'center'}
                , {field: 'high_2', width: 100, title: 'high_2', sort: true, align: 'center'}
                , {field: 'high_3', width: 100, title: 'high_3', sort: true, align: 'center'}
                , {field: 'high_4', width: 100, title: 'high_4', sort: true, align: 'center'}
                , {field: 'maxHigh', width: 100, title: 'maxHigh', sort: true, align: 'center'}
                , {field: 'time', width: 300, title: 'time', sort: true, align: 'center'}
            ]],
            parseData: function (resp) {
                data = resp;
                $("#alarm").text("警报值 : " + data.alarm)
                alarm();
                return resp;
            }
        });

    });
}


