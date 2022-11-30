function matchId(jqXHR) {
    let jsonValue = jQuery.parseJSON(jqXHR.responseText);
    return jsonValue.message;
}

function showError(id){
    if(id=="400_002_001") {
        alert("하위조직이 존재하여 삭제할 수 없습니다.");
    }
}