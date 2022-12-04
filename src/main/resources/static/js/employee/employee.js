/**
 * 직원 등록 시, 직무와 조직 선택
 * 직무는 새로 함수를 작성해 주었지만 부서는 기존의 position.js 를 끌어와서 사용한다
 */
init();

function init() {
    loadPosList()
    loadDepList("radio")
}

function loadPosList() {
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/pos/list",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            showPosList(data);
        },
        error: function () {
            alert('직무 목록 불러오기 실패!');
        }
    });
}

function showPosList(posList) {
    $("#pos-list *").remove();

    for (let [index, pos] of posList.entries()) {
        let msg = '<input type=\'radio\' name=\'pos\' value=' + pos.id + '>' + pos.name + "<br>"
        $('#pos-list').append(msg);
    }
}

/**
 * 직원 등록
 */
function registerEmp() {
    if(checkRegisterForm() == true) {
        alert("name : " +  $('#name').val() +
            "\nrole : " + $('input[name=role]:checked').val() +
            "\npositionId : " + $('input[name=pos]:checked').val() +
            "\ndepartmentId : " + $('input[name=dep]:checked').val() +
            "\njoinDate : " + $('#joinDate').val() +
            "\nfreeDate : " + $('#freeDate').val() +
            "\nempNo : " + $('#empNo').val() +
            "\nmajor : " + $('#major').val() +
            "\ncert : " + $('#cert').val() +
            "\ninfo : " + $('#info').val() +
            "\nemail : " + $('#email').val());

        $.ajax({
            async: "true",
            type: "POST",
            url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_EMP + "/register",
            // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            contentType: "application/json; charset=UTF-8",
            dataType: 'JSON',
            // dataType: "text",
            data: JSON.stringify({
                name: $('#name').val(),
                role: $('input[name=role]:checked').val(),
                positionId: $('input[name=pos]:checked').val(),
                departmentId: $('input[name=dep]:checked').val(),
                joinDate: $('#joinDate').val(),
                freeDate: $('#freeDate').val(),
                empNo: $('#empNo').val(),
                major: $('#major').val(),
                cert: $('#cert').val(),
                info: $('#info').val(),
                email: $('#email').val(),
            }),
            success: function () {
                alert("직원등록에 성공했습니다.");
                window.location.replace("/");
            },
            error: function (request, status, error) {
                alert("status : " + request.status + ", message : " + request.responseText + ", error : " + error);
                window.location.replace("/");
            }
        });
    } else {
        alert("다시 확인하세요");
    }

}
/**
 * RegisterForm 빈 칸 확인
 * 이메일 형식 확인
 */
function checkRegisterForm() {
    if(checkExistData(registerForm.name.value, "이름") == false) {
        return false;
    }
    if(checkExistData(registerForm.role.value, "권한") == false) {
        return false;
    }
    if(checkExistData(registerForm.pos.value, "직무") == false) {
        return false;
    }
    if(checkExistData(registerForm.dep.value, "조직") == false) {
        return false;
    }
    if(registerForm.joinDate.value == null) {
        alert("입사일 입력해주세요!");
        return false;
    }
    if(checkExistData(registerForm.empNo.value, "사원번호") == false) {
        return false;
    }
    if(checkExistData(registerForm.email.value, "이메일") == false) {
        return false;
    }

    const email = $('#email').val();
    let emailRegExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;
    if (!emailRegExp.test(email)) {
        alert("EMAIL 형식이 올바르지 않습니다!");
        $('#email').val('');
        return false;
    }

    return true;
}


/**
 * 직원 수정
 */
function update() {

}

/**
 * 직원 비활성화
 */
function free() {

}

/**
 * 직원 합류 초대 재전송
 */
function reSend() {

}

/**
 * 빈 칸 확인
 * @param value
 * @param dataName
 * @returns {boolean}
 */
function checkExistData(value, dataName) {
    value = value.trim();
    if (value == "") {
        alert(dataName + " 입력해주세요!");
        return false;
    }
    return true;
}

