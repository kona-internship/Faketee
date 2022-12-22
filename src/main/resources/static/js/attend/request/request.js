/**
 * 출퇴근기록 생성 요청 시, 날짜 선택
 * 날짜를 선택하지 않고 선택 버튼을 누르면 alert 창을 띄운다
 * 선택한 날짜가 출퇴근기록 생성이 되지 않은 날짜여야 한다
 * 날짜는 한개만 선택 가능하다
 */
function createSelectDate() {
    let selectedDate = document.querySelector('#selectedDate').value;
    if(selectedDate == "") {
        alert("날짜를 선택하세요");
    } else {
        $.ajax({
            async: true,
            url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD_REQ + "/create/check-date",
            type: "get",
            contentType: "application/json",
            data: {
                selectedDate: selectedDate
            },
            success: function (data) {
                /**
                 * 선택된 날짜에 출퇴근기록이 없으면 시간 설정하는 페이지로 이동
                 */
                if (data === true) {
                    location.href = `create/set-time?selectedDate=${selectedDate}`;
                } else {
                    alert("출퇴근기록이 이미 생성된 날짜입니다.");
                }
            },
            error: function (error) {
                alert(JSON.stringify(error));
            }
        });
    }
}

/**
 * 출퇴근기록 생성 요청에서 시간 설정 시, 선택한 날짜와 보여준다
 * 해당 날짜의 그 직원의 근무일정을 보여준다
 * 해당 근무일정의 출근시간과 퇴근시간을 보여준다
 */
function createSetTimeShow() {
    let selectedDate = $('#selectedDate').val()

    let sYear = selectedDate.substring(0,4);
    let sMonth = selectedDate.substring(5,7);
    let sDate = selectedDate.substring(8,10);

    $("input[type=date]").val(sYear + "-" + sMonth + "-" + sDate);

    $.ajax({
        async: true,
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD_REQ + "/create/set-time/sch-info",
        type: "get",
        contentType: "application/json",
        data: {
            date : selectedDate,
            corId : selectedDate,
            empId : selectedDate
        },
        success: function (data) {
            $('input[name=showSch]').attr('value', data.startTime + " ~ " + data.endTime);
            $('input[name=sch-startTime]').attr('value', data.startTime);
            $('input[name=sch-endTime]').attr('value', data.endTime);
            $('input[name=schInfoId]').attr('value', data.id);
        },
        error: function (jqXHR) {
            let result = getErrors(jqXHR);
            showError(result);
            location.replace(URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD_REQ + "/create");
        }
    });
}

/**
 * 출퇴근기록 생성 요청에서 시간 설정 시,
 * 선택한 날짜와 시간을 승인권자 설정 페이지로 이동시킨다
 */
function createSelectTime() {
    let selectedDate = $('#showDate').val()
    let startTime = $('#sch-startTime').val()
    let endTime = $('#sch-endTime').val()
    let schInfoId = $('#schInfoId').val()
    let schInfo = $('#showSch').val()

    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD_REQ
        + `/create/set-apvl?selectedDate=${selectedDate}&startTime=${startTime}&endTime=${endTime}&schInfoId=${schInfoId}&schInfo=${schInfo}`;
}

/**
 * 출퇴근기록 생성 요청에서 승인권자 설정 시, 선택한 날짜와 출퇴근 시간을 보여준다
 * 직원의 승인권자 목록을 보여준다
 */
function createSetApvlShow() {
    let selectedDate = $('#selectedDate').val()
    let startTime = $('#startTime').val()
    let endTime = $('#endTime').val()
    let schInfo = $('#schInfo').val()

    $('input[name=date]').attr('value', selectedDate);
    $('input[name=time]').attr('value', startTime + " ~ " + endTime);
    $('input[name=sch]').attr('value', schInfo);


    $.ajax({
        async: true,
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD_REQ + "/load/apv-emp",
        type: "get",
        contentType: "application/json",
        success: function (data) {
            //여기 작성
        },
        error: function () {
            alert('승인권자 불러오기 실패!');
        }
    });
}

/**
 * 출퇴근기록 수정 요청에서 달 선택 시, 선택한 달에 해당하는 출퇴근 기록들을 가져온다
 */
function updateSelectMonth() {
    let selectedMonth = $('#selectedMonth').val()
    let month = selectedMonth.split("-")[1];
    console.log(month);
    if(selectedMonth == "") {
        alert("달 선택하세요");
    } else {
        $.ajax({
            async: true,
            url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD_REQ + "/update/set-month",
            type: "get",
            contentType: "application/json",
            data: {
                month: Number(month)
            },
            success: function (data) {
                showAtdRecordList(data)
            },
            error: function (jqXHR) {
                let result = getErrors(jqXHR);
                showError(result);
                location.replace(URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD_REQ + "/create");
            }
        });
    }
}

/**
 * 출퇴근 기록들을 보여준다
 * 기록 선택 시, 해당 기록으로 출퇴근기록 수정 - 시간 설정하기 페이지로 이동한다
 * @param data
 */
function showAtdRecordList(data) {
    $('#atd-list *').remove();

    if(data.length === 0) {
        let msg = '<div>출퇴근기록이 없습니다.</div>';
        $('#atd-list').append(msg);
    }
    else {
        for (let [index, atd] of data.entries()) {
            let parameter = [atd.date, atd.startTime, atd.endTime];
            let msg = '<div>' + (index + 1) + '. 날짜 : ' + atd.date + ' 출근시간 : ' + atd.startTime + ' 퇴근시간 : ' + atd.endTime +
                ' <button type="button" onclick=goUpdateAtdReqTimePage(' + JSON.stringify(parameter) + ')>선택</button></div>';
            $('#atd-list').append(msg);
        }
    }
}

/**
 * 출퇴근기록 수정 요청 - 시간 설정하기 페이지로 이동하는 함수
 */
function goUpdateAtdReqTimePage(parameter) {
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD_REQ
        + `/update/set-time?date=${parameter[0]}&startTime=${parameter[1]}&endTime=${parameter[2]}`;
}

/**
 * 출퇴근기록 수정 요청에서 기록 선택 시,
 * 해당 날짜의 그 직원의 출근시간과 퇴근시간을 보여준다
 * 출근시간과 퇴근시간을 설정할 수 있다
 */
function updateSetTimeShow() {
    let date = $('#date').val()
    let startTime = $('#startTime').val()
    let endTime = $('#endTime').val()

    $('input[name=record]').attr('value', "날짜 : " + date + " 출근시간 : " + startTime + " 퇴근시간 : " + endTime);
    $('input[name=update-set-startTime]').attr('value', startTime);
    $('input[name=update-set-endTime]').attr('value', endTime);
}

/**
 * 출퇴근기록 수정 요청에서 시간 설정 시,
 * 선택한 날짜와 시간을 승인권자 설정 페이지로 이동시킨다
 */
function updateSelectTime() {
    let date = $('#date').val()
    let startTime = $('#startTime').val()
    let endTime = $('#endTime').val()
    let updateStartTime = $('#update-set-startTime').val()
    let updateEndTime = $('#update-set-endTime').val()

    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD_REQ
        + `/update/set-apvl?date=${date}&startTime=${startTime}&endTime=${endTime}&updateStartTime=${updateStartTime}&updateEndTime=${updateEndTime}`;
}

/**
 * 출퇴근기록 수정 요청에서 승인권자 설정 시, 수정한 출퇴근 시간을 보여준다
 * 직원의 승인권자 목록을 보여준다
 */
function updateSetApvlShow() {
    let date = $('#date').val()
    let startTime = $('#startTime').val()
    let endTime = $('#endTime').val()
    let updateStartTime = $('#updateStartTime').val()
    let updateEndTime = $('#updateEndTime').val()

    $('input[name=record]').attr('value', "날짜 : " + date + " 출근시간 : " + startTime + " 퇴근시간 : " + endTime);
    $('input[name=before-update]').attr('value', startTime + " ~ " + endTime);
    $('input[name=after-update]').attr('value', updateStartTime + " ~ " + updateEndTime);
}

/**
 * 출퇴근기록 삭제 요청에서 달 선택 시, 선택한 달에 해당하는 출퇴근 기록들을 가져온다
 */
function deleteSelectMonth() {
    let selectedMonth = $('#selectedMonth').val()
    let month = selectedMonth.split("-")[1];
    console.log(month);
    if(selectedMonth == "") {
        alert("달 선택하세요");
    } else {
        $.ajax({
            async: true,
            url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD_REQ + "/update/set-month",
            type: "get",
            contentType: "application/json",
            data: {
                month: Number(month)
            },
            success: function (data) {
                showUpdateAtdRecordList(data)
            },
            error: function (jqXHR) {
                let result = getErrors(jqXHR);
                showError(result);
                location.replace(URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD_REQ + "/create");
            }
        });
    }
}

/**
 * 출퇴근 기록들을 보여준다
 * 기록 선택 시, 해당 기록으로 출퇴근기록 삭제 - 승인권자 설정하기 페이지로 이동한다
 * @param data
 */
function showUpdateAtdRecordList(data) {
    $('#atd-list *').remove();

    if(data.length === 0) {
        let msg = '<div>출퇴근기록이 없습니다.</div>';
        $('#atd-list').append(msg);
    } else {
        for (let [index, atd] of data.entries()) {
            let parameter = [atd.date, atd.startTime, atd.endTime];
            let msg = '<div>' + (index + 1) + '. 날짜 : ' + atd.date + ' 출근시간 : ' + atd.startTime + ' 퇴근시간 : ' + atd.endTime +
                ' <button type="button" onclick=goDeleteAtdReqApvlPage(' + JSON.stringify(parameter) + ')>선택</button></div>';
            $('#atd-list').append(msg);
        }
    }
}

/**
 * 출퇴근기록 삭제 요청 - 승인권자 설정하기 페이지로 이동하는 함수
 */
function goDeleteAtdReqApvlPage(parameter) {
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD_REQ
        + `/delete/set-apvl?date=${parameter[0]}&startTime=${parameter[1]}&endTime=${parameter[2]}`;
}

/**
 * 출퇴근기록 삭제 요청에서 승인권자 설정 시, 삭제할 출퇴근기록을 보여준다
 * 직원의 승인권자 목록을 보여준다
 */
function deleteSetApvlShow() {
    let date = $('#date').val()
    let startTime = $('#startTime').val()
    let endTime = $('#endTime').val()

    $('input[name=record]').attr('value', "날짜 : " + date + " 출근시간 : " + startTime + " 퇴근시간 : " + endTime);
}

/**
 * 출퇴근기록 생성 요청 보내기
 */
function createAtdReq() {
    $.ajax({
        async: true,
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD_REQ + "/create",
        type: "post",
        data: JSON.stringify({
            requestMessage: $('#msg').val(),
            // apvEmpFinId: $('#apvEmp').val(),
            apvEmpFinId: 1,
            atdReqDate: $('#selectedDate').val(),
            startTime: $('#startTime').val(),
            endTime: $('#endTime').val(),
        }),
        contentType: "application/json; charset=UTF-8",
        success: function () {
            alert("출퇴근기록 생성 요청이 완료되었습니다.");
        },
        error: function (request, status, error) {
            alert("출퇴근기록 생성 요청 실패" + "code:" + request.status + "\n" + " message : " + request.responseText + "\n" + "error:" + error);
        }
    });
}

/**
 * 출퇴근기록 수정 요청 보내기
 */
function updateAtdReq() {
    $.ajax({
        async: true,
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD_REQ + "/update",
        type: "post",
        data: JSON.stringify({
            requestMessage: $('#msg').val(),
            // apvEmpFinId: $('#apvEmp').val(),
            apvEmpFinId: 1,
            atdReqDate: $('#date').val(),
            startTime: $('#updateStartTime').val(),
            endTime: $('#updateEndTime').val(),
        }),
        contentType: "application/json; charset=UTF-8",
        success: function () {
            alert("출퇴근기록 수정 요청이 완료되었습니다.");
        },
        error: function (request, status, error) {
            alert("출퇴근기록 수정 요청 실패" + "code:" + request.status + "\n" + " message : " + request.responseText + "\n" + "error:" + error);
        }
    });
}

/**
 * 출퇴근기록 삭제 요청 보내기
 */
function deleteAtdReq() {
    $.ajax({
        async: true,
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD_REQ + "/delete",
        type: "post",
        data: JSON.stringify({
            requestMessage: $('#msg').val(),
            // apvEmpFinId: $('#apvEmp').val(),
            apvEmpFinId: 1,
            atdReqDate: $('#date').val(),
        }),
        contentType: "application/json; charset=UTF-8",
        success: function () {
            alert("출퇴근기록 삭제 요청이 완료되었습니다.");
        },
        error: function (request, status, error) {
            alert("출퇴근기록 삭제 요청 실패" + "code:" + request.status + "\n" + " message : " + request.responseText + "\n" + "error:" + error);
        }
    });
}