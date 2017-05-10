/**
 * Created by 15852 on 2017/4/15.
 */
function Search() {
    var object = $("#focus-object").text();
    var search = $("#search").val();
    if (object == "" || /\s+/g.test(object)) {
        Tip("请选择需要搜索的类", "object-menu");
        return false;
    }
    $.ajax({
        type: 'get',
        url: '/Servlets/SearchServlet',
        data: {"objectName": object, "search": search},
        beforeSend: function () {
            layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
        },
        success: function (data) {
            $("#object-body").html(data);
            InitialContextMenu();
            layer.closeAll("loading");
        },
        error: function (data) {
            $("#object-body").html("");
            layer.closeAll("loading");
        }
    });
}

function CreateObject() {
    var object = $("#focus-object").text();
    if (object == "" || /\s+/g.test(object)) {
        Tip("请选择需要搜索的类", "object-menu");
        return false;
    }
    $(function () {
        $('#createModal').modal({
            keyboard: true
        })
    });
}

function DeleteObject() {
    var object = $("#focus-object").text();
    var id = $("#selected-item").val();

    $.ajax({
        type: 'post',
        url: '/Servlets/DeleteObjectServlet',
        data: {"":object,"":id},
        beforeSend: function () {
            layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
        },
        success: function (data) {
            var list = eval(data);
            for (var i = 0; i < list.length; i++) {
                $("#objects").append("<li><a onclick='InitialObject(this)'>" + list[i] + "</a></li>");
            }
            layer.open({
                title: '提示',
                content: '删除失败',
                icon: 6,
                skin: 'layui-layer-molv',
                closeBtn: 0,
                anim: 1,
            });
            layer.closeAll("loading");
        },
        error: function (data) {
            layer.open({
                title: '提示',
                content: '删除失败',
                icon: 5,
                skin: 'layui-layer-lan',
                closeBtn: 0,
                anim: 2,
            });
            layer.closeAll("loading");
        }
    });
}

function EditObject() {
    $(function () {
        $('#editModal').modal({
            keyboard: true
        })
    });
}

$(document).ready(function () {
    InitialObjectColumns();
});

function InitialObjectColumns() {
    $.ajax({
        type: 'get',
        url: '/Servlets/InitMenuServlet',
        success: function (data) {
            var list = eval(data);
            for (var i = 0; i < list.length; i++) {
                $("#objects").append("<li><a onclick='InitialObject(this)'>" + list[i] + "</a></li>");
            }
        },
        error: function (data) {
            layer.open({
                title: '提示',
                content: '获取对象信息错误',
                icon: 5,
                skin: 'layui-layer-lan',
                closeBtn: 0,
                anim: 2,
            });
        }
    });
}

function InitialObject(event) {
    var object = $(event).text();
    $("#focus-object").text(object);
    InitialTitle();
    Search();
}

function InitialTitle() {
    var object = $("#focus-object").text();
    $.ajax({
        type: 'get',
        url: '/Servlets/InitTitleServlet',
        data: {"objectName": object},
        success: function (data) {
            var list = eval(data);
            $("#object-head").html("");
            for (var i = 0; i < list.length; i++) {
                $("#object-head").append("<th>" + list[i] + "</th>");
            }
        },
        error: function (data) {
            layer.open({
                title: '提示',
                content: '获取对象信息错误',
                icon: 5,
                skin: 'layui-layer-lan',
                closeBtn: 0,
                anim: 2,
            });
        }
    });
}

function InitialContextMenu() {
    $(".object-item").bind("contextmenu", function (ev) {
        var id = this.id;
        var oEvent = ev || event;
        var oDiv = document.getElementById('click-menu');

        $("#selected-item").val(id);

        oDiv.style.display = 'block';

        oDiv.style.left = oEvent.clientX + 'px';
        oDiv.style.top = oEvent.clientY + 'px';

        return false;
    });
}

document.onclick = function () {
    var clickDiv = document.getElementById('click-menu');
    clickDiv.style.display = 'none';
};