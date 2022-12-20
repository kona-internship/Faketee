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
 * 근무일정 유형 목록 페이지 로딩
 */
function loadDraftList() {
    let type = $('#draft-list').attr("value");
    console.log(type);
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_DRAFT + '/' +type,
        type: "get",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            showDraftList(data);
        },
        error: function () {
            alert('요청 목록 불러오기 실패!');
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