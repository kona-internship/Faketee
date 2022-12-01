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

function newTemplate() {
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_TMP + "/new";
}

function loadTemplates(){
    $.ajax({
        async: true,
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_TMP + "/list",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            getTemplateList(data);
        },
        error: function (error) {
            alert('근무일정 템플릿이 없습니다.');
            alert(JSON.stringify(error));
        }
    });
}

function getTemplateList(tempList){
    $('#tmp-list *').remove();
    for (let [index, tmp] of tempList.entries()) {
        let msg = '<div>' + (index + 1) + '. 템플릿: ' + tmp.name +
            ' <button type="button" onclick=deleteTemplate(' + tmp.id + ')>삭제</button></div>';
        $('#tmp-list').append(msg);
    }
}

function saveTemplate(){

    let data = {
        name : $('#tmp-name').val(),
        startTime : $('#tmp-startTime').val(),
        endTime : $('#tmp-endTime').val(),


    }
}

function selectSchType(){
    $.ajax({
        async : true,
        url : URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_SCH + "/type/list",
        type : "POST",
        contentType : "application/json",
        dataType : "json",
        success : function (list){
            for(let [index, type] of list.entries()){
                let option = document.createElement("option");
                option.value = type.name;
                option.text = type.name;
                $('#sch-type-list').appendChild(option);
            }
        },
        error : function (error){
            alert(JSON.stringify(error));
        }
    })
}

function deleteTemplate(templateId){

}