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
function loadSelectTmp(){
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_SCH + "/template/list",
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            showTmpList(data);
        },
        error: function () {
            alert('템플릿 목록 불러오기 실패!');
        }
    });
}
function showTmpList(tmpList){
    $('#selectTmp').empty();

    for(let temp of tmpList.entries()){
        let option = '<option value="' +  temp.id + '">' + temp.name + '</option>'
        $('#selectTmp').append(option);
    }
}