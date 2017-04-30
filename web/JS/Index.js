/**
 * Created by 15852 on 2017/4/15.
 */
function Search(){
    var search = $("#search").val();
    alert(search);
}

function CreateObject(){
    $(function () {
        $('#createModal').modal({
            keyboard: true
        })
    });
}

function DeletObject(){
}

function EditObject(){
    $(function () {
        $('#editModal').modal({
            keyboard: true
        })
    });
}

$(document).ready(function () {
    InitialObjectColumns();
    $(".list-group-item").bind("contextmenu", function (ev) {
        var id = this.id;
        var oEvent = ev || event;
        var oDiv = document.getElementById('click-menu');

        $("#modify-role-id").val(id);

        oDiv.style.display = 'block';

        oDiv.style.left = oEvent.clientX + 'px';
        oDiv.style.top = oEvent.clientY + 'px';

        return false;
    });
});

function InitialObjectColumns(){
    $.ajax({
        type:'get',
        url:'InitObjectListServlet',
        dataType:'json',
        success:function(data){
            var list = eval(data);
            for(var i = 0;i < list.length;i++){
                $("#objects").append("<li><a>" + list[i] + "</a></li>");
            }
        },
        error:function(data){
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

document.onclick = function () {
    var clickDiv = document.getElementById('click-menu');
    clickDiv.style.display = 'none';
};