function newVacationGroup(){

    let data = {
        name : $("#vacation-group-name").val()
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