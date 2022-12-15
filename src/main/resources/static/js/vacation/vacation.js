function newVacationGroup(){

    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_GROUP + "/form";
}

function loadVacationGroups(){

    $.ajax({
        async: true,
        type: "GET",
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_GROUP + "/by-cor",
        contentType: "application/json",
        dataType: "json",
        success: function (data){
            drawVgroupList(data);
        },
        error: function (error){
            alert(JSON.stringify(error));
        }
    });
}

function drawVgroupList(groupList){

    $('#vgroup-list *').remove();
    if(groupList.entries().next().value == null){
        $('#vgroup-list').append('<div>' + '등록된 휴가그룹이 없습니다.' + '</div>');
    }
    for(let [index, group] of groupList.entries()){
        let msg = '<div>'
            + '<a href="'+ URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_GROUP + "/details?groupId=" + group.id +'">'
            + (index+1)
            + '. 그룹:'
            + group.name
            + '</a>'
            + '<br>'
            + '<button type="button" onclick=deleteVacationGroup(' + group.id + ')>삭제</button>'
            +'</div>'
        $('#vgroup-list').append(msg);
    }
}

function deleteVacationGroup(groupId){

    $.ajax({
        async: true,
        type: "POST",
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_GROUP + "/delete?id=" + groupId,
        contentType: "application/json",
        success: function (){
            alert("휴가그룹이 삭제되었습니다.");
            loadVacationGroups();
        },
        error: function (error){
            alert(JSON.stringify(error));
            loadVacationGroups();
        }
    });
}

function saveVacationGroup(){

    let input = document.getElementsByName('APVL_LVL');
    let APVL_LVL;

    input.forEach((level) => {
        if(level.checked){
            APVL_LVL = level.value;
        }
    })

    let data = {
        name : $("#vacation-group-name").val(),
        approvalLevel : APVL_LVL
    };

    $.ajax({
        type : "POST",
        url : URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_GROUP,
        dataType : "json",
        contentType : "application/json",
        data : JSON.stringify(data),

        success : function (){
            alert('휴가그룹이 생성되었습니다.');
            window.location.reload();
        },
        error : function (error){
            alert(JSON.stringify(error));
        }
    });
}

function listVacationGroup(){

    $.ajax({
        async: true,
        type: "GET",
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_GROUP + "/by-cor",
        contentType: "application/json",
        dataType: "json",
        success : function (groups){
            for(let[index, group] of groups.entries()){
                let option = document.createElement("option");
                option.value = group.id;
                option.text = group.name;
                $('#vacation-group-list').append(option);
            }
        },
        error : function (error){
            alert(JSON.stringify(error));
        }
    });
}

function loadVacationTypesByGroupId(groupId){

    $('#btn-loadVacTypes').attr("hidden", "hidden");

    $.ajax({
        async: true,
        type: "GET",
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_TYPE + "/by-vacgroup?vacGroupId=" + groupId,
        contentType: "application/json",
        dataType: "json",
        success: function (list){
            drawVacationTypeList(list);
        },
        error: function (error){
            alert(JSON.stringify(error));
        }
    });
}

function drawVacationTypeList(list){

    if(list.entries().next().value == null){
        $('#vacationTypes').append('<div>' + '추가된 휴가 유형이 없습니다.' + '</div>');
    }
    for(let[index, type] of list.entries()){
        let msg = '<div>'
            +'<a href="'+ URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_TYPE + "/details?typeId=" + type.id +'">'
            + (index+1)
            + '. '
            + type.name
            + ' (' + type.vacGroupResponseDto.name +')'
            + '</a>'
            + '<br>'
            + '차감일수: '
            + type.sub
            + '<br>'
            + '시작시간: '
            + type.startTime
            + '<br>'
            + '종료시간: '
            + type.endTime
            + '</div>'
        $('#vacationTypes').append(msg + '<br>');
    }
}

function loadVacationTypesByCorId(){

    $('#vacationTypes *').remove();

    $.ajax({
        async: true,
        type: "GET",
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_TYPE + "/by-cor",
        contentType: "application/json",
        dataType: "json",
        success: function (list){
            drawVacationTypeList(list);
        },
        error: function (error){
            alert(JSON.stringify(error));
        }
    });
}


function newVacationType(){

    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_TYPE + "/form";


}

function saveVacationType(){

    let groupId = $('#vacation-group-list option:selected').val();
    let data = {
        name : $("#vacation-type-name").val(),
        sub : $("#sub").val(),
        startTime : $("#start-time").val(),
        endTime : $("#end-time").val()
    }

    $.ajax({
        type: "POST",
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_TYPE + "?vacGroupId=" + groupId,
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(data),

        success : function (){
            alert("휴가 유형을 추가했습니다.");
        },
        error : function (error){
            alert(JSON.stringify(error));
        }
    });
}

function deleteVacationType(typeId){

    $.ajax({
        async: true,
        type: "POSt",
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_TYPE + "/delete?vacTypeId=" + typeId,
        contentType: "application/json",
        dataType: "json",

        success : function (){
            alert("휴가 유형을 삭제했습니다.");
        },
        error : function (error){
            alert(JSON.stringify(error));
        }
    });
}