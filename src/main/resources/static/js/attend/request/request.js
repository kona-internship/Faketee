/**
 * 출퇴근기록 생성 요청 시, 날짜 선택
 * 날짜를 선택하지 않고 선택 버튼을 누르면 alert 창을 띄운다
 * 선택한 날짜가 출퇴근기록 생성이 되지 않은 날짜여야 한다
 * 날짜는 한개만 선택 가능하다
 */
function createSelectDate() {
    let selectedDate = document.querySelector('#selectedDate').value;
    alert("날짜 : " + selectedDate);
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
        error: function (error) {
            alert(JSON.stringify(error));
        }
    });
}

/**
 * 출퇴근기록 생성 요청 시, 선택한 날짜와 시간을 다음 페이지로 이동시킨다
 */
function createSelectTime() {
    let selectedDate = $('#showDate').val()
    let startTime = $('#startTime').val()
    let endTime = $('#endTime').val()
    let schInfoId = $('#schInfoId').val()
    let schInfo = $('#showSch').val()

    location.href = `create/set-time?selectedDate=${selectedDate}&startTime=${startTime}&endTime=${endTime}&schInfoId=${schInfoId}&schInfo=${schInfo}`;
}

/**
 * 출퇴근 생성 요청에서 승인권자 설정 시, 선택한 날짜와 출퇴근 시간을 보여준다
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
}

/**
 * 사유와 승인권자를 입력했는지 확인한다
 */
function checkCreateForm() {

}