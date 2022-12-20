function getErrors(jqXHR) {
    console.log(jqXHR);
    if(jqXHR.responseText != null) {
        let jsonValue = jQuery.parseJSON(jqXHR.responseText);
        if(jsonValue.errors != null) {
            return jsonValue.errors;
        }
    }
    return null;
}

function showError(errorList){
    if(errorList != null) {
        for (let error of errorList) {
            matchId(error.message);
        }
    }else{
        matchId(errorList);
    }
}

function matchId(id){
    if(id=="400_002_001") {
        alert("하위조직이 존재하여 삭제할 수 없습니다.");
    }
    else if(id=="400_003_001"){
        alert("본인이 속해있는 회사에서의 요청이 아닙니다. 정해진 경로를 통해 다시 한번 접속해주세요.");
    }
    else if(id=="400_003_002"){
        alert("해당 요청에 대한 권한이 없습니다. 조직의 관리자에게 문의바랍니다.");
    }
    else if(id=="400_003_003"){
        alert("본인이 관리하지 않는 직원에 대한 요청이 있습니다. 요청을 다시 한번 확인해주세요.");
    }
    else if(id=="400_004_001"){
        alert("합류코드가 전송된 이메일과 같은 시프티 계정으로 합류해주세요.")
    }
    else if(id=="400_004_002"){
        alert("회사에 중복된 상태의 유저가 있습니다. 관리자에게 문의해주세요.")
    }
    else{
        alert("알 수 없는 오류입니다");
    }
}