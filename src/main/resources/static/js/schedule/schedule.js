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

function showSchTypeList(scheduleTypeList) {
    $('#sch-type-list *').remove();

    for (let [index, type] of scheduleTypeList.entries()) {
        let msg = '<div>' + (index + 1) + '. 근무일정 유형: ' + type.name +
            ' <button type="button" onclick=deleteSchType(' + type.id + ')>삭제</button></div>';
        $('#sch-type-list').append(msg);
    }
}

function goAddSchTypePage() {
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_SCH;
}

function goTypeList() {
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_SCH + "/type";
}

function checkSchTypeRegister() {
    if (!checkExistData($("#name").val(), "유형명을")) {

        $("#name").focus();
        return false;
    }
}

function checkExistData(value, dataName) {
    value = value.trim();
    if (value == "") {
        alert(dataName + " 입력해주세요!");
        return false;
    }
    $.ajax({
        async: true,
        type: "post",
        data: JSON.stringify({
            name: $("#name").val()
        }),
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_SCH + "/type",
        contentType: "application/json; charset=UTF-8",
        success:
            function () {
                alert("유형 등록 성공");
                goTypeList();

            },
        error:
            function (request, status, error) {
                alert("유형 등록 실패" + "code:" + request.status + "\n" + " message : " + request.responseText + "\n" + "error:" + error);
            }
    });
    return true;
}
function deleteSchType(typeId){
    $.ajax({
        async: true,
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_SCH + "/type/delete/" + typeId,
        type: "post",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            alert("유형 삭제 성공");
            showSchTypeList(data);

        },
        error: function () {
            alert('직무 목록 불러오기 실패!');
        }
    });
}
function newTemplate() {

}