/**
 *회사 등록
 *
 * @returns {boolean}
 */
function checkCorRegister() {
    if (!checkExistData(registerForm.name.value, "이름을")) {

        $("#name").focus();
        return false;
    }
    if (confirm("회사를 등록하시겠습니까?")) {
        $.ajax({
            async: true,
            type: "post",
            data: JSON.stringify({
                name: $("#name").val()
            }),
            url: "/api/corporation",
            contentType: "application/json; charset=UTF-8",
            success:
                function (result) {
                    alert("회사 등록이 완료되었습니다. 감사합니다.");
                    location.href = URL_COR_PREFIX + "/" + result + "/loc";

                },
            error:
                function (request, status, error) {
                    alert("회사 등록 실패" + "code:" + request.status + "\n" + " message : " + request.responseText + "\n" + "error:" + error);
                }
        });
    }
    return true;
}

/**
 * 입력 받은 데이터가 있는지 체크
 * value의 값이 비어있으면 dataName의 값이 비어있다고 alert를 띄운다.
 *
 * @param value
 * @param dataName
 * @returns {boolean} 비어있으면 false, 값이 존재하면 true
 */
function checkExistData(value, dataName) {
    value = value.trim();
    if (value == "") {
        alert(dataName + " 입력해주세요!");
        return false;
    }
    return true;
}