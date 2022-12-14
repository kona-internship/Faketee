let todayDate;

function loadTodaySchedule() {
    // $('#today-schedule').remove();
    $('#today-schedule').append(getToday());

    $.ajax({
        async: true,
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD + "/load",
        type: "get",
        contentType: "application/json",
        data: {
            date: todayDate
        },
        success: function (data) {
            showTodaySchedule(data);
        },
        error: function () {
            alert('근무일정 유형 목록 불러오기 실패!');
        }
    });
}

function showTodaySchedule(data) {
    if (data === "") {
        $('#today-schedule').append("일정 없음");
    } else {
        console.log(data);
        let msg = data.startTime + "-" + data.endTime;
        if (data.depName !== "") {
            msg += " | " + data.depName;
        }
        $('#today-schedule').append(msg);
    }
}

function getToday() {
    let today = new Date();
    let date = today.getDate();
    let month = today.getMonth() + 1;
    let year = today.getFullYear();
    let day = today.getDay();

    let when = ['일', '월', '화', '수', '목', '금', '토'];

    todayDate = year + "-" + month + "-" + date;
    return month + "/" + date + "(" + when[day] + ") <br>";
}