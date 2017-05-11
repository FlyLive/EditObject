/**
 * Created by 15852 on 2017/4/15.
 */

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
            OpenTip('获取对象信息错误', 1);
        }
    });
}

function InitialObject(event) {
    var object = $(event).text();
    $("#focus-object").text(object);
    InitialTitle();
    Search(undefined);
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
            OpenTip('获取对象信息错误', 1);
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
    $("#selected-item").val("");
    var clickDiv = document.getElementById('click-menu');
    clickDiv.style.display = 'none';
};

function CreateObject() {
    var object = $("#focus-object").text();
    if (object == "" || /\s+/g.test(object)) {
        Tip("请选择需要搜索的类", "object-menu");
        return false;
    }
    $.ajax({
        type: 'get',
        url: '/Servlets/CreateObjectServlet',
        data: {"objectName": object},
        success: function (data) {
            $(".modal-content").html(data);
            $(function () {
                $('#objectModal').modal({
                    keyboard: true
                })
            });
        },
        error: function (data) {
            OpenTip("未知错误发生了", 2);
        }
    });
}

function SubmitCreate() {
    var inputs = new Array();
    var options = new Array();
    var object = $("#focus-object").text();

    $(".modal-body .input-item").each(function(){
        options.push(this.id);
        inputs.push($(this).val());
    });

    $.ajax({
       type:'post',
        url:'/Servlets/CreateObjectServlet',
        data:{"objectName":object,"inputs":inputs,"options":options},
        beforeSend:function(){
            layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
        },
        success:function(data){
            layer.closeAll("loading");
            if(data == "true"){
                $('#objectModal').modal('hide');
                Search();
                OpenTipSuccess("添加成功!",2);
                return true;
            }
            OpenTip("格式错误,请重试!",3);
        },
        error:function(data){
            layer.closeAll("loading");
            OpenTip("未知错误发生了!",3);
        }
    });
}

function DeleteObject() {
    var object = $("#focus-object").text();
    var objectId = $("#selected-item").val();

    $.ajax({
        type: 'post',
        url: '/Servlets/DeleteObjectServlet',
        data: {"objectName": object, "id": objectId},
        beforeSend: function () {
            layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
        },
        success: function (data) {
            if (data == "true") {
                $("#object-body .object-item").remove("#" + objectId);
                OpenTipSuccess("删除成功", 2);
            } else {
                OpenTip("删除失败", 2);
            }
            layer.closeAll("loading");
        },
        error: function (data) {
            OpenTip("不可预知错误发生了", 1);
            layer.closeAll("loading");
        }
    });
}

function EditObject() {
    var object = $("#focus-object").text();
    var objectId = $("#selected-item").val();
    if (object == "" || /\s+/g.test(object)) {
        Tip("请选择需要搜索的类", "object-menu");
        return false;
    }
    $.ajax({
        type: 'get',
        url: '/Servlets/EditObjectServlet',
        data: {"objectName": object,"objectId":objectId},
        success: function (data) {
            $(".modal-content").html(data);
            $(function () {
                $('#objectModal').modal({
                    keyboard: true
                })
            });
        },
        error: function (data) {
            OpenTip("未知错误发生了", 2);
        }
    });
}

function SubmitEdit() {
    var inputs = new Array();
    var options = new Array();
    var object = $("#focus-object").text();

    $(".modal-body .input-item").each(function(){
        options.push(this.id);
        inputs.push($(this).val());
    });

    $.ajax({
        type:'post',
        url:'/Servlets/EditObjectServlet',
        data:{"objectName":object,"inputs":inputs,"options":options},
        beforeSend:function(){
            layer.load(1, {
                shade: [0.1, '#fff'] //0.1透明度的白色背景
            });
        },
        success:function(data){
            layer.closeAll("loading");
            if(data == "true"){
                $('#objectModal').modal('hide');
                Search();
                OpenTipSuccess("修改成功!",2);
                return true;
            }
            OpenTip("格式错误,请重试!",3);
        },
        error:function(data){
            layer.closeAll("loading");
            OpenTip("未知错误发生了!",3);
        }
    });
}

function Search(search) {
    var object = $("#focus-object").text();
    var search = search || $("#search").val();
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
