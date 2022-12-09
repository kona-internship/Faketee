$(function() {
    let options={
        multidate: true,
        format: 'yyyy-mm-dd',
        todayHighlight: true,
        autoclose: false,
    };
    $('#datepicker').datepicker(options);

    loadSelectTmp();
});
let dep_detail = [];
function loadSelectTmp(){
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_SCH + "/before/reg",
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            showTmpList(data);
            parseDepList(data.dep);
            dep_detail = data.dep;
        },
        error: function () {
            alert('템플릿 목록 불러오기 실패!');
        }
    });
}
function parseDepList(){

}
function showTmpList(tmpList){
    console.log(tmpList);
    $('#selectTmp').empty();

    for(let temp of tmpList.temp.entries()){
        let option = '<option value="' +  temp[1].id + '">' + temp[1].name + '</option>'
        $('#selectTmp').append(option);
    }
}
function selectTmpChange(){
    let selectTempId = document.getElementById("selectTmp");
    let tempId = selectTempId.options[selectTempId.selectedIndex].value;
    console.log(tempId);
    console.log(dep_detail[tempId]);
    showDepList(dep_detail[tempId], "check");
    console.log("wewew");
}
let expanded = false;

function showCheckboxes() {
    let checkboxes = document.getElementById("dep-list");
    if (!expanded) {
        checkboxes.style.display = "block";
        expanded = true;
    } else {
        checkboxes.style.display = "none";
        expanded = false;
    }
}