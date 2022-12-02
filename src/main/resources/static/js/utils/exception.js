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
    if(id=="400_003_001"){
        alert("해당 장소를 가진 조직이 존재합니다.");
    }
    if(id=="400_004_001"){
        alert("해당 유형을 참조하고 있는 템플릿이 존재합니다.");
    }
}