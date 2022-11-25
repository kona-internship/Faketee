init();

function init(){
    loadDepList()
}

function loadDepList(){
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) +"/dep/list",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            showDepList(data);
        },
        error: function () {
            alert('직무 목록 불러오기 실패!');
        }
    });
}

function showDepList(depList){
    $("#dep-list *").remove();

    for(let dep of depList) {
        $('#dep-list').append(
            '<div>'+ dep.name + '</div>'
        );
    }
}

function registerDep(){
    let jsonData = JSON.stringify({
        name: $('#pos-name').val()
    });
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) +"/dep",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        success: function () {
            goDepListPage();
            alert('등록 성공!');
        },
        error: function () {
            alert('등록 실패!');
        }
    });
}

function goDepListPage(){
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/loc/list";
}

function goDepRegPage(){
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/loc/reg";
}