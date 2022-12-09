let posCheckIdList=[];

function addPosCheckId(id){
    posCheckIdList.push(id);
}

function getPosCheckIdList(){
    return posCheckIdList;
}

function clearPosCheckIdList(){
    posCheckIdList=[];
}

init();

function init() {
    loadPosList();
}

function loadPosList(type) {
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/pos/list",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            showPosList(data, type);
        },
        error: function (jqXHR) {
            alert('직무 목록 불러오기 실패!');
            let result = getErrors(jqXHR);
            showError(result);
        }
    });
}

function showPosList(posList, type) {
    $("#pos-list *").remove();

    switch (type) {
        case "radio":
            showRadioPosList(posList);
            break;
        case "text":
        default:
            showTextPosList(posList);
            break;
    }
}

function showTextPosList(posList){
    for (let [index, pos] of posList.entries()) {
        let msg = '<div>' + (index + 1) + '. 직무명: ' + pos.name
            + ' <button type="button" onclick=goUpdatePos(' + pos.id + ',"' + pos.name + '")>수정</button>'
            + ' <button type="button" onclick=deletePos(' + pos.id + ')>삭제</button>' + '</div>'
        $('#pos-list').append(msg);
    }
}

function showRadioPosList(posList){
    for (let pos of posList) {
        let check;
        if(posCheckIdList.length != 0) {
            check = posCheckIdList.includes(pos.id);
        }
        let msg;
        msg = '<input type="radio" name="pos" value=' + pos.id + ((check)? ' checked' : '') + '>' + pos.name + '<br>';
        $('#pos-list').append(msg);
    }
}

function goUpdatePos(posId, posName) {
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/pos/" + posId + "/" + posName;
}

function deletePos(posId) {
    $.ajax({
        async: true,
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/pos/delete/" + posId,
        type: "post",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            alert("직무 삭제 성공");
            showPosList(data);

        },
        error: function (jqXHR) {
            alert('직무 목록 불러오기 실패!');
            let result = getErrors(jqXHR);
            showError(result);
        }
    });
}

function registerPos() {
    let jsonData = JSON.stringify({
        name: $('#pos-name').val()
    });
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/pos",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        success: function () {
            loadPosList();
            alert('등록 성공!');
        },
        error: function (jqXHR) {
            alert('등록 실패!');
            let result = getErrors(jqXHR);
            showError(result);
        }
    });
}

function goListPosRegister() {
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/pos";
}

function updatePos(posId) {
    if (!checkExistData($("#up-pos-name").val(), "직무명을")) {

        $("#up-pos-name").focus();
        return false;
    }
    let jsonData = JSON.stringify({
        name: $('#up-pos-name').val()
    });
    if (confirm("직무명를 수정하시겠습니까?")) {
        $.ajax({
            async: true,
            type: "post",
            data: jsonData,
            contentType: "application/json",
            url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/pos/update/" + posId,
            success: function () {
                alert('수정 성공!');
                goListPosRegister();
            },
            error: function (jqXHR) {
                alert('수정 실패!');
                let result = getErrors(jqXHR);
                showError(result);
            }
        });
    }
    return true;

}

function checkExistData(value, dataName) {
    value = value.trim();
    if (value == "") {
        alert(dataName + " 입력해주세요!");
        return false;
    }
    return true;
}