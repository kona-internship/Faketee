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
            url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD_REQ + "/check-date",
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
                    window.location.replace("/");
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