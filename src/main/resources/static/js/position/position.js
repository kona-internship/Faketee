init();

function init(){
    loadPosList()
}

function loadPosList(){
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) +"/pos/list",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            showPosList(data);
        },
        error: function () {
            alert('직무 목록 불러오기 실패!');
        }
    });
}

function showPosList(posList){
    $("#pos-list *").remove();

    for(let pos of posList) {
        $('#pos-list').append(
            '<div>'+ pos.name + '</div>'
        );
    }
}

function registerPos(){
    let jsonData = JSON.stringify({
        name: $('#pos-name').val()
    });
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) +"/pos",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        success: function () {
            loadPosList();
            alert('등록 성공!');
        },
        error: function () {
            alert('등록 실패!');
        }
    });
}