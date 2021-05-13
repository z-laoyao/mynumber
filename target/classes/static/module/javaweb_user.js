let pageNumber = 0;
var path = "/"
let user;
let data;
$(function () {
    search_data();
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

function search_data() {
    layui.use('table', function () {
        let table = layui.table;
        //第一个实例
        table.render({
            elem: '#tableList'
            , url: '/user/list' //数据接口
            , method: "GET"
            , cols: [[
                {field: 'id', width: 100, title: '编号', align: 'center', sort: true, fixed: 'left'}
                , {field: 'name', width: 70, title: '账号', sort: true, align: 'center'}
                , {field: 'username', width: 200, title: '姓名', align: 'center'}
                , {field: 'email', width: 250, title: '邮箱', sort: true, align: 'center'}
                , {field: 'sex', width: 70, title: '性别', align: 'center'},
                {field: 'status', width: 150, title: '审核状态', align: 'center'},
                {align: "center", title: "操作", width: 300, toolbar: "#toolBar"}
            ]]
        });
        table.on('tool(tableList)', function (obj) {
            var data = obj.data//表格id，获取选中行
            switch (obj.event) {
                case 'del':
                    let flag = confirm("确认要删除 id : " + data.id + " 吗?")
                    if (flag) {
                        $.ajax({
                            type: "GET",
                            url: "/user/del/" + data.id,
                            success: function (resp) {
                                search_data();
                            }
                        })
                    }
                    break;
                case 'updateStatus':
                    if (data.status === 1) {
                        alert("审核已通过,无法再次通过")
                        return;
                    }
                    let status = confirm("确认审核通过  id : " + data.id + " 吗?")
                    if (status) {
                        $.ajax({
                            type: "GET",
                            url: "/user/updateStatus/" + data.id,
                            success: function (resp) {
                                search_data();
                            }
                        })
                    }
                    break;
                case 'updatePassword':
                    let password = confirm("确认要初始化   id : " + data.id + " 的密码吗?")
                    if (password) {
                        $.ajax({
                            type: "GET",
                            url: "/user/updatePassword/" + data.id,
                            success: function (resp) {
                                search_data();
                                alert("密码是 : 123")
                            }
                        })
                    }
                    break;
                    break;
            }

        })

    });

}
