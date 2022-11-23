/** 회원가입 email 중복 확인
중복이 되었을 때 화면 처리 과정 추가 필요함 **/
let emailFlag = false;
function checkEmail() {
    const email = $('#email').val();

    const header = $("meta[name='_csrf_header']").attr('content');
    const token = $("meta[name='_csrf']").attr('content');

    $.ajax({
        url: "/api/account/check-email",
        type: "post",
        data:{"email": email},
        beforeSend: function(xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function(emailCheck) {
            /** emailCheck 가 0이라면 -> 사용 가능한 email **/
            if(emailCheck === true) {
                alert("EMAIL 사용 가능합니다.");
                emailFlag = true;
            } else {
                /** emailCheck 가 1이라면 -> 이미 존재하는 email 이므로 사용 못함 **/
                alert("EMAIL 이미 존재합니다.");
                $('#email').val('');
                emailFlag = false;
                // $("#btn-register").attr("disabled", true);
            }
        },
        error: function(request, status, error) {
            // alert("에러입니다");
            alert("status : " + request.status + ", message : " + request.responseText + ", error : " + error);
        }
    });
}

/** 회원가입 registerForm 빈 칸 확인 + 비밀번호, 비밀번호 확인 같은지 확인 **/
function checkRegisterForm() {
    if (registerForm.email.value === "") {
        alert("EMAIL 입력하세요.");
        return false;
    }
    if (registerForm.password.value === "") {
        alert("PASSWORD 입력하세요.");
        return false;
    }
    if (registerForm.passwordCheck.value === "") {
        alert("CHECK PASSWORD 입력하세요.");
        return false;
    }
    if (registerForm.name.value === "") {
        alert("NAME 입력하세요.");
        return false;
    }
    if (registerForm.password.value != registerForm.passwordCheck.value) {
        alert("PASSWORD, CHECK PASSWORD 일치해야 합니다.");
        return false;
    }
    return true;
}

/** 회원가입 **/
function register() {
    if(emailFlag == true && checkRegisterForm() == true) {
        const header = $("meta[name='_csrf_header']").attr('content');
        const token = $("meta[name='_csrf']").attr('content');

        $.ajax({
            async: "true",
            type: "POST",
            url: '/api/account/register',
            // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            contentType: "application/json; charset=UTF-8",
            dataType: 'JSON',
            // dataType: "text",
            data: JSON.stringify({
                email: $('input[name="email"]').val(),
                password: $('input[name="password"]').val(),
                name: $('input[name="name"]').val(),
            }),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                alert("회원가입에 성공했습니다.\n다시 로그인 해주세요.");
                window.location.replace("/");
            },
            error: function (request, status, error) {
                alert("status : " + request.status + ", message : " + request.responseText + ", error : " + error);
                window.location.replace("/");
            }
        });
    }
}

/**
 * 회원정보수정 updateForm 빈 칸 확인 + 비밀번호, 비밀번호 확인 같은지 확인
 */
function checkUpdateForm() {
    if (updateForm.password.value === "") {
        alert("현재 PASSWORD 입력하세요.");
        return false;
    }
    if (registerForm.newPassword.value === "") {
        alert("변경 PASSWORD 입력하세요.");
        return false;
    }
    if (registerForm.newPasswordCheck.value === "") {
        alert("변경 PASSWORD 확인 입력하세요.");
        return false;
    }
    if (registerForm.newPassword.value != registerForm.newPasswordCheck.value) {
        alert("변경 PASSWORD, 변경 PASSWORD 확인 일치해야 합니다.");
        return false;
    }
    return true;
}

/**
 * 회원 정보 수정
 */
function update() {
    if(checkUpdateForm() == true) {
        // ajax 통신
        const header = $("meta[name='_csrf_header']").attr('content');
        const token = $("meta[name='_csrf']").attr('content');

        $.ajax({
            async: "true",
            type: "POST",
            url: "/api/account/update-password",
            // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            contentType: "application/json; charset=UTF-8",
            dataType: 'JSON',
            // dataType: "text",
            data: JSON.stringify({
                oldPassword: $('input[name="password"]').val(),
                newPassword: $('input[name="newPassword"]').val(),
            }),
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (data) {
                alert("PASSWORD를 수정했습니다.");
                window.location.replace("/account/mypage");
            },
            error: function (request, status, error) {
                alert("status : " + request.status + ", message : " + request.responseText + ", error : " + error);
                window.location.replace("/");
            }
        });
    }
}