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

    let level;
    if($('input[name=APVL_LVL]').val() == "1LVL"){
        level = '1'
    }else{
        level = '2'
    }

    let data = {
        name : $("#vacation-group-name").val(),
        approvalLevel : level
    }

    $.ajax({
        type : "POST",
        url : URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_GROUP,
        dataType : "json",
        contentType : "application/json",
        data : JSON.stringify(data),

        success : function (){
            alert('성공');
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

function newVacationType(){

    let data = {
        name : $("#vacation-type-name").val(),
        sub : $("#sub").val(),
        startTime : $("#start-time").val(),
        endTime : $("#end-time").val()
    }

    $.ajax({
        type: "POST",
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_TYPE,
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify(data),

        success : function (typeID){
            alert("성공, typeID:" + typeID);
        },
        error : function (error){
            alert(JSON.stringify(error));
        }
    });
}