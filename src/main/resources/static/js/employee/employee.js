/**
 * 직원 등록 시, 직무와 조직 선택
 * 직무는 새로 함수를 작성해 주었지만 부서는 기존의 position.js 를 끌어와서 사용한다
 */
init();

function loadList(){
    return new Promise(function (resolve){
        // loadPosList();

        $.ajax({
            url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/pos/list",
            type: "GET",
            contentType: "application/json",
            dataType: "json",
            async : false,
            success: function (posList) {
                // showPosList(data);

                $("#pos-list *").remove();

                for (let [index, pos] of posList.entries()) {
                    let msg = '<input type=\'radio\' name=\'pos\' value=' + pos.id + '>' + pos.name + "<br>"
                    $('#pos-list').append(msg);
                }
                alert("직무 출력 완료");
            },
            error: function () {
                alert('직무 목록 불러오기 실패!');
            }
        });
        resolve();
    });

}

function loadCheck() {
    // return new Promise(function (resolve){
        if($('#posValue').val() != null) {
            radioChecked();
            resolve();
        }
    // });
}

async function init() {
    await loadList();
    loadCheck();

    loadDepList("radio");
    alert("직무 조직 출력 완료");
}

function loadPosList() {
    // $.ajax({
    //     url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/pos/list",
    //     type: "GET",
    //     contentType: "application/json",
    //     dataType: "json",
    //     success: function (posList) {
    //         // showPosList(data);
    //
    //         $("#pos-list *").remove();
    //
    //         for (let [index, pos] of posList.entries()) {
    //             let msg = '<input type=\'radio\' name=\'pos\' value=' + pos.id + '>' + pos.name + "<br>"
    //             $('#pos-list').append(msg);
    //         }
    //         alert("직무 출력 완료");
    //     },
    //     error: function () {
    //         alert('직무 목록 불러오기 실패!');
    //     }
    // });
}

/**
 * 직원 수정 시
 * 권한, 직무, 조직 radio 버튼에 checked 속성 추가
 */
function radioChecked() {
    let roleValue = $('#roleValue').val()
    let posValue = $('#posValue').val()
    let depValue = $('#depValue').val()

    alert(roleValue + " : " + posValue + " : " + depValue);

    // $('input[name="role"]').val([roleValue]);
    // $('input[name="pos"]').val([posValue]);
    // $('input[name="dep"]').val([depValue]);

    // $('input:radio[name="pos"]').filter('[value="1"]').attr('checked', true);

    $("input:radio[name ='role']:input[value = 'GENERAL_MANAGER']").prop("checked", true);
    $("input:radio[name ='pos']:input[value = '1']").prop("checked", true);
    $("input:radio[name ='dep']:input[value = depValue]").prop("checked", true);
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
function updateEmp(empId) {
    if(checkUpdateForm() == true) {
        alert("name : " +  $('#name').val() +
            "\nrole : " + $('input[name=role]:checked').val() +
            "\npositionId : " + $('input[name=pos]:checked').val() +
            "\ndepartmentId : " + $('input[name=dep]:checked').val() +
            "\njoinDate : " + $('#joinDate').val() +
            "\nfreeDate : " + $('#freeDate').val() +
            "\nempNo : " + $('#empNo').val() +
            "\nmajor : " + $('#major').val() +
            "\ncert : " + $('#cert').val() +
            "\ninfo : " + $('#info').val());

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
                alert("재전송에 성공했습니다.");
                // window.location.replace("/");
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

