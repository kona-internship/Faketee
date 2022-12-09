/**
 * 직원 등록 시, 직무와 조직 선택
 */
init();

function init() {
    // 현재 직무 선택 표시
    // position.js
    clearPosCheckIdList();
    addPosCheckId(parseInt($('#posValue').val()));
    loadPosList("radio");

    // 현재 조직 선택 표시
    // department.js
    clearDepCheckIdList();
    addDepCheckId(parseInt($('#depValue').val()));
    loadDepList("radio");

    //현재 권한 선택 표시
    let roleValue = $('#roleValue').val()
    $("input:radio[name='role']:radio[value='" + roleValue + "']").attr('checked', true);
}

/**
 * 직원 등록
 */
function registerEmp() {
    if(checkRegisterForm() == true) {
        alert("직원등록에 성공했습니다.");

        window.location.replace("/");

        $.ajax({
            async: "true",
            type: "POST",
            url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_EMP + "/register",
            // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            contentType: "application/json; charset=UTF-8",
            // dataType: 'JSON',
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
function updateEmp(empId) {
    if(checkUpdateForm() == true) {
        $.ajax({
            async: "true",
            type: "POST",
            url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_EMP + "/update/" + empId,
            // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            contentType: "application/json; charset=UTF-8",
            // dataType: 'JSON',
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
            }),
            success: function () {
                alert("직원수정에 성공했습니다.");
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
 * updateForm 빈 칸 확인
 */
function checkUpdateForm() {
    if(checkExistData(updateForm.name.value, "이름") == false) {
        return false;
    }
    if(checkExistData(updateForm.role.value, "권한") == false) {
        return false;
    }
    if(checkExistData(updateForm.pos.value, "직무") == false) {
        return false;
    }
    if(checkExistData(updateForm.dep.value, "조직") == false) {
        return false;
    }
    if(updateForm.joinDate.value == null) {
        alert("입사일 입력해주세요!");
        return false;
    }
    if(checkExistData(updateForm.empNo.value, "사원번호") == false) {
        return false;
    }
    return true;
}

/**
 * 직원 비활성화
 */
function freeEmp(empId) {
    const result = confirm("직원을 비활성화 하시겠습니까?");

    if(result == true) {
        $.ajax({
            url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_EMP + "/deactivate/" + empId,
            type: "get",
            success : function () {
                alert("비활성화 했습니다.");
                window.location.replace("/");
            },
            error: function (request, status, error) {
                // alert("에러입니다");
                alert("status : " + request.status + ", message : " + request.responseText + ", error : " + error);
            }
        });
    }
}

/**
 * 직원 합류 초대 재전송
 */
function reSend(empId) {
    const result = confirm("합류 초대를 재전송 하시겠습니까?");

    if(result == true && checkResendForm() == true) {
        alert("재전송에 성공했습니다.");

        $.ajax({
            async: "true",
            type: "POST",
            url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_EMP + "/reSend/" + empId,
            // contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            contentType: "application/json; charset=UTF-8",
            // dataType: 'JSON',
            // dataType: "text",
            data: JSON.stringify({
                email: $('#email').val()
            }),
            success: function () {
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
 * ResendForm 빈 칸 확인
 * 이메일 형식 확인
 * @returns {boolean}
 */
function checkResendForm() {
    if(checkExistData(reSendForm.email.value, "이메일") == false) {
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

function goRegisterEmp() {
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_EMP + "/register";
}

/**
 * 직원 수정하는 page로 이동
 */
function goUpdateEmp(empId) {
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_EMP + "/update/" + empId;
}

/**
 * 직원 목록 불러오기
 */
function loadEmpList() {
    $.ajax({
        async: true,
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_EMP + "/list",
        type: "get",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            showEmpList(data);
        },
        error: function () {
            alert('직원 목록 불러오기 실패!');
        }
    });
}

/**
 * 직원 목록을 화면에 보여준다
 * @param locList 화면에 나올 출퇴근 장소 데이터의 리스트
 */
function showEmpList(empList){
    $('#emp-list *').remove();

    for(let [index, emp] of empList.entries()) {
        let msg = '<div>'+ (index + 1) +'. 사원번호 : '+ emp.empNo +' 권한 : '+ emp.role + ' 직원명 : '+ emp.name +
            ' <button type="button" onclick=goUpdateEmp('+emp.id+')>수정</button></div>';
        $('#emp-list').append(msg);
    }
}

/**
 * 직원 합류하기
 */
function joinEmployee(){

    let data = {
        joinCode : $('#joincode').val()
    };


    $.ajax({
        async : true,
        type : "POST",
        url : "/api/join-corporation",
        contentType : "application/json",
        data : JSON.stringify(data),
        success : function (param){
            alert("회사에 합류하셨습니다.");
            let URL = "/corporation/" + param;
            window.location.replace(URL);
        },
        error : function(error){
            alert(JSON.stringify(error));
        }
        }
    );
}