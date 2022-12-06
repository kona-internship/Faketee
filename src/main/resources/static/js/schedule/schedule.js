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

function deleteSchType(typeId) {
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
        error: function (jqXHR) {
            let result = getErrors(jqXHR);
            showError(result);
        }
    });
}

function newTemplate() {

    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_TMP + "/new";
}

function loadTemplates() {

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
            alert(JSON.stringify(error));
        }
    });
}

function getTemplateList(tempList) {

    $('#tmp-list *').remove();
    if (tempList.entries().next().value == null) {
        $('#tmp-list').append('<div>' + '근무일정 템플릿이 없습니다' + '</div>');
    }
    for (let [index, tmp] of tempList.entries()) {
        let msg = '<div>' + (index + 1) + '. 템플릿: ' + tmp.name +
            ' <button type="button" onclick=deleteTemplate(' + tmp.id + ')>삭제</button></div>';
        $('#tmp-list').append(msg);
    }
}

function saveTemplate() {

    let depList = new Array();
    let posList = new Array();

    $("input:checkbox[name=dep]:checked").each(function () {
        depList.push($(this).attr("value-id"));
    });
    $("input:checkbox[name=pos]:checked").each(function (){
        posList.push($(this).attr("value"));
    });

    console.log(depList, posList);
    let data = {
        name: $('#tmp-name').val(),
        scheduleId: $('#select-sch-type option:selected').val(),
        startTime: $('#tmp-startTime').val(),
        endTime: $('#tmp-endTime').val(),
        departmentsId: depList,
        positionsId: posList
    };

    $.ajax({
        async : true,
        type : "POST",
        url : URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_TMP + "/new",
        dataType : 'json',
        contentType : "application/json",
        data : JSON.stringify(data),

        beforeSend : function (){
            if($('#tmp-name').value == ""){
                alert("템플릿 명을 입력해주세요");
                window.location.reload();
            }
        },
        success : function (){
            alert('근무일정 템플릿을 추가하였습니다.');

            window.location.reload();
        },
        error : function (error){
            alert(JSON.stringify(error));
        }
    });
}

function listSchType() {

    $.ajax({
        async: true,
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_SCH + "/type/list",
        type: "POST",
        contentType: "application/json",
        dataType: "json",
        success: function (list) {
            for (let [index, type] of list.entries()) {
                let option = document.createElement("option");
                option.value = type.id;
                option.text = type.name;
                $('#select-sch-type').append(option);
            }
        },
        error: function (error) {
            alert(JSON.stringify(error));
        }
    });
}

function listDepartments() {

    $.ajax({
        async: true,
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_DEP + "/list",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (list) {
            for (let [index, department] of list.entries()) {
                let option = document.createElement("option");
                option.text = department.name;
                option.value = department.id;
                // option.id = 'dep' + department.id;
                $('#select-departments').append(option);
            }
        },
        error: function (error) {
            alert(JSON.stringify(error));
        }
    });
}

function listPositions() {

    $.ajax({
        async: true,
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/pos/list",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (list) {
            for(let [index, position] of list.entries()) {
                let input = document.createElement("input");
                input.type = "checkbox";
                input.name = "pos";
                input.value = position.id;
                // input = position.name;
                // option.text = position.name;
                // option.value = position.id;
                // option.id = 'pos' + position.id;
                // $('#select-positions').append(option);
                $('#pos-list').append(input);
                $('#pos-list').append(position.name);
            }
        },
        error : function (error){
            alert(JSON.stringify(error));
        }
    });

}

function deleteTemplate(templateId) {

}