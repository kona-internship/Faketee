function newVacationGroup(){

    let level
    if($('input[name=APVL_LVL]').val().eq("1LVL")){
        level = 'F'
    }else{
        level = 'T'
    }

    alert($('input[name=APVL_LVL]').val().eq("1LVL"))
    alert(level)

    let data = {
        name : $("#vacation-group-name").val(),
        approvalLevel : level.val()
    }

    $.ajax({
        type : "POST",
        url : URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC + "/group",
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
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC + "/groups",
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
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC + "/type",
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