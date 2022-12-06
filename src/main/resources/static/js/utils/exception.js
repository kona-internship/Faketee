function getErrors(jqXHR) {
    console.log(jqXHR);
    let jsonValue = jQuery.parseJSON(jqXHR.responseText);
    return jsonValue.errors;
}

function showError(errorList){
    for(let error of errorList){
        matchId(error.message);
    }
}

function matchId(id){
    if(id=="400_002_001") {
        alert("하위조직이 존재하여 삭제할 수 없습니다.");
    }
}