/**
 * 근무일정 유형 목록 페이지 로딩
 */
function loadSchList() {
    $.ajax({
        async: true,
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_SCH + "/type/list",
        type: "post",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            showSchTypeList(data);
        },
        error: function () {
            alert('근무일정 유형 목록 불러오기 실패!');
        }
    });
}
function showSchTypeList(data){

}
function goAddSchTypePage(){

}