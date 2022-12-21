/**
 * 조직 목록 화면(list.html)으로 이동하는 함수
 */
function goApvlWaitList() {
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_DRAFT + "/wait";
}

/**
 * 조직 등록 화면(registration.html)으로 이동하는 함수
 */
function goReqList() {
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_DRAFT + "/req";
}

/**
 * 조직 삭제 화면(remove.html)으로 이동하는 함수
 */
function goReqDoneList() {
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_DRAFT + "/done";
}

function goDetailPage(draftId){
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_DRAFT + "/" + draftId;
}

/**
 * 기안 목록 페이지 로딩
 *
 * draft-list를 id로 가지고 있는 태그의 value 값을 type으로 하여 어떤 목록을 가져올지 구분한다.
 */
function loadDraftList() {
    let type = $('#draft-list').attr("value");
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_DRAFT + '/' +type,
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            showDraftList(data);
        },
        error: function (jqXHR) {
            alert('목록 불러오기 실패!');
            let result = getErrors(jqXHR);
            showError(result);
        }
    });
}

function showDraftList(draftList) {
    $('#draft-list *').remove();

    $('#draft-list').append(
        '<ul>'
    );
    for (let draft of draftList) {
        $('#draft-list').append(
            '<li>'+
                '<button onclick="goDetailPage('+ draft.id+')"'+
                    '<div>요청날짜: '+draft.requestDate+'</div>'+
                    '<div>'+ draft.requestType + ' '+ draft.crudType + ' - ' + draft.requestEmployee + '</div>'+
                    '<div>'
        );
        for(let draftDate of draft.dateList){
            $('#draft-list').append(
                    draftDate.date + ' '
            );
        }
        $('#draft-list').append(
                    '/' + draft.reqList.vacType +
                    '</div>' +
                    '<div>'+draft.stateCode+'</div>'+
                '</button>'+
            '</li>'
        );
    }
    $('#draft-list').append(
        '</ul>'
    );
}

function apvlDraft() {
    let jsonData = JSON.stringify({
        draftId: getNextPath(window.location.href, PATH_DRAFT_APVL),
        apvlMsg: $('#draft-detail-apvl-msg')
    });
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_DRAFT + "apvl",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        success: function () {
            goApvlWaitList();
        },
        error: function (jqXHR) {
            alert('승인 실패!');
            let result = getErrors(jqXHR);
            showError(result);
        }
    });
}

function rejectDraft() {
    let jsonData = JSON.stringify({
        draftId: getNextPath(window.location.href, PATH_DRAFT_APVL),
        apvlMsg: $('#draft-detail-apvl-msg')
    });
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_DRAFT + "reject",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        success: function () {
            goApvlWaitList();
        },
        error: function (jqXHR) {
            alert('거절 실패!');
            let result = getErrors(jqXHR);
            showError(result);
        }
    });
}

function cancelDraft() {
    let state = $('#draft-detail-state').attr("text");
    if(!(state == "대기중" || state == "1차승인")){
        alert("승인 대기 중인 상태에서만 회수할 수 있습니다");
        return;
    }
    let jsonData = JSON.stringify({
        draftId: getNextPath(window.location.href, PATH_DRAFT_REQ)
    });
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_DRAFT + "reject",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        success: function () {
            goApvlWaitList();
        },
        error: function (jqXHR) {
            alert('거절 실패!');
            let result = getErrors(jqXHR);
            showError(result);
        }
    });
}