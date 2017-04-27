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
    $(".wrapper").bind("contextmenu", function (ev) {
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



document.onclick = function () {
    var clickDiv = document.getElementById('click-menu');
    clickDiv.style.display = 'none';
};