/**이메일 형식 지켰는지 확인
 * 회원가입 email 중복 확인
 **/
let checkEmailFlag = false;

function checkEmail() {
    const email = $('#email').val();

    let emailRegExp = /^[A-Za-z0-9_]+[A-Za-z0-9]*[@]{1}[A-Za-z0-9]+[A-Za-z0-9]*[.]{1}[A-Za-z]{1,3}$/;
    if (!emailRegExp.test(email)) {
        checkEmailFlag = false;
        alert("EMAIL 형식이 올바르지 않습니다!");
        $('#email').val('');
        $("#btn-register").attr("disabled", true);
        return false;
    } else {
        // const header = $("meta[name='_csrf_header']").attr('content');
        // const token = $("meta[name='_csrf']").attr('content');

        $.ajax({
            url: "/api/account/check-email?email=" + email,
            type: "get",
            // data:{"email": email},
            // beforeSend: function(xhr) {
            //     xhr.setRequestHeader(header, token);
            // },
            success : function (emailCheck) {
                /** emailCheck 가 0이라면 -> 사용 가능한 email **/
                if (emailCheck === true) {
                    checkEmailFlag = true;
                    alert("EMAIL 사용 가능합니다.");
                    // $("#btn-register").removeAttr("disabled");
                } else {
                    /** emailCheck 가 1이라면 -> 이미 존재하는 email 이므로 사용 못함 **/
                    checkEmailFlag = false;
                    alert("EMAIL 이미 존재합니다.");
                    $('#email').val('');
                    $("#btn-register").attr("disabled", true);
                }
            },
            error: function (request, status, error) {
                // alert("에러입니다");
                alert("status : " + request.status + ", message : " + request.responseText + ", error : " + error);
            }
        });
    }
}

let sendEmailFlag = false;

function confirmEmail() {
    const email = $('#email').val();

    if(checkEmailFlag == true) {
        $.ajax({
            url: "/api/account/send-email?email=" + email,
            type: "get",
            // data:{"email": email},
            // beforeSend: function(xhr) {
            //     xhr.setRequestHeader(header, token);
            // },
            success: function (emailCheck) {
                /** emailCheck 가 0이라면 -> 사용 가능한 email **/
                if (emailCheck === true) {
                    sendEmailFlag = true;
                    alert("EMAIL 인증 완료되었습니다.");
                    $("#btn-register").removeAttr("disabled");
                } else {
                    /** emailCheck 가 1이라면 -> email 인증 실패함 **/
                    sendEmailFlag = false;
                    alert("EMAIL 인증 실패했습니다.");
                    $("#btn-register").attr("disabled", true);
                }
            },
            error: function (request, status, error) {
                // alert("에러입니다");
                alert("status : " + request.status + ", message : " + request.responseText + ", error : " + error);
            }
        });
    }
}

/**
 * password 제약조건
 * @param password
 * @returns {boolean}
 */
function validatePassword(password) {
    const reg = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,32}$/;
    const hangulCheck = /[ㄱ-ㅎ|ㅏ-ㅣ|가-힣]/;

    if(false === reg.test(password)) {
        alert('PASSWORD 8자 이상 32자 이하 이어야 하며\n숫자/대문자/소문자/특수문자를 모두 포함해야 합니다.');
        $('#password').val('');
        return false;
    }
    else if(hangulCheck.test(password)){
        alert("PASSWORD 한글을 사용 할 수 없습니다.");
        $('#password').val('');
        return false;
    }
    else if(password.search(/\s/) != -1){
        alert("PASSWORD 공백 없이 입력해주세요.");
        $('#password').val('');
        return false;
    }
    return true;
}

/** 회원가입 registerForm 빈 칸 확인
 *  비밀번호 형식 지켰는지 확인
 * 비밀번호, 비밀번호 확인 같은지 확인
 * 이름 형식 지켰는지 확인 **/
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

    validatePassword(registerForm.password.value);

    if (registerForm.password.value != registerForm.passwordCheck.value) {
        alert("PASSWORD, CHECK PASSWORD 일치해야 합니다.");
        return false;
    }

    if(registerForm.name.value.search(/\s/) != -1){
        alert("NAME 공백 없이 입력해주세요.");
        $('#name').val('');
        return false;
    }

    return true;
}

/** 회원가입 **/
function register() {
    if(checkRegisterForm() == true && checkEmailFlag == true && sendEmailFlag == true) {
        // const header = $("meta[name='_csrf_header']").attr('content');
        // const token = $("meta[name='_csrf']").attr('content');

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
            // beforeSend: function (xhr) {
            //     xhr.setRequestHeader(header, token);
            // },
            success: function (data) {
                alert("회원가입에 성공했습니다.\n다시 로그인 해주세요.");
                window.location.replace("/account/login-form");
            },
            error: function (request, status, error) {
                alert("status : " + request.status + ", message : " + request.responseText + ", error : " + error);
                window.location.replace("/");
            }
        });
    } else {
        alert("다시 확인하세요.")
    }
}

/**
 * 로그인 loginForm 빈 칸 확인
 * @returns {boolean}
 */
function checkLoginForm() {
    if (loginForm.email.value=="") {
        alert("EMAIL 입력하세요.");
        return false;
    }
    if (loginForm.password.value=="") {
        alert("PASSWORD 입력하세요.");
        return false;
    }
    return true;
}

/**
 * 회원정보 수정 updateForm 빈 칸 확인
 * 비밀번호, 비밀번호 확인 같은지 확인
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
        // const header = $("meta[name='_csrf_header']").attr('content');
        // const token = $("meta[name='_csrf']").attr('content');

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
            // beforeSend: function (xhr) {
            //     xhr.setRequestHeader(header, token);
            // },
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