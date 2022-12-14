/**
 * 날짜를 선택하지 않고 선택 버튼을 누르면 alert 창을 띄운다
 * 선택한 날짜가 출퇴근기록 생성이 되지 않은 날짜여야 한다
 * 날짜는 한개만 선택 가능하다
 */
function selectDate() {
    let selectedDate = document.querySelector('#selectedDate').value;

    if(selectedDate == null) {
        alert("날짜를 선택하세요");
    } else {
        $.ajax({
            async: true,
            url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_SCH + "/list",
            type: "get",
            contentType: "application/json",
            data: {
                selectedDate: selectedDate
            },
            success: function (data) {
                drawScheduleList(data);
            },
            error: function (error) {
                alert(JSON.stringify(error));
            }
        });
    }
}