let todayDate;

/**
 * 근무일정 가져오는 ajax
 */
function loadTodaySchedule() {
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
            alert('근무일정 불러오기 실패!');
        }
    });
}

/**
 * 근무일정, 상태, 근태를 화면에 나타내기
 * @param data
 */
function showTodaySchedule(data) {
    let msg = '';
    if (data === "") {
        msg += "일정 없음 <p id='sch-nothing'>무일정</p><br>" +
            "<button type='button' disabled>근무 일정 없음</button>";

    } else {
        console.log(data);
        if (data.atdStartTime === 'null') {
            msg += "<p id='atd-before'>" + data.state + "</p><br>";
            msg += showSchDetail(data);
            msg += "<button type='button' id='on' onclick='goClickAtd(\"on\")'> 출근하기 </button>";
        } else if(data.atdEndTime === 'null'){
            msg += "<p id='atd-after'>" + data.state + "</p><br>";
            msg += showSchDetail(data);
            msg += "<button type='button' id='off' onclick='goClickAtd(\"off\")'> 퇴근하기 </button>";
            msg += "<hr> 출근 : " + data.atdStartTime + "<br>";
        } else {
            msg += "<p id='atd-before'>" + data.state + "</p><br>";
            msg += showSchDetail(data);
            msg += "<button type='button' id='on' onclick='goClickAtd(\"on\")'> 출근하기 </button>";
            msg += "<hr> 출근 : " + data.atdStartTime + "<br>";
            msg += " 퇴근 : " + data.atdEndTime + "<br>";
        }

    }
    $('#today-schedule').append(msg);

    if(data.state === '결근' || data.state === '퇴근완료'){
        $('#on').attr('disabled', true);
    }
}

/**
 * 근태버튼 클릭하면 페이지 이동
 * @param flag
 */
function goClickAtd(flag) {
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD + "/reg?flag="+flag;
}

/**
 * 근무일정 시간 반환
 * @param data
 * @returns {string}
 */
function showSchDetail(data) {
    let msg = data.schStartTime + "-" + data.schEndTime;
    if (data.depName !== "") {
        msg += " | " + data.depName;
    }
    return msg + '<br>';
}

/**
 * 요일과 날짜 반환
 * @returns {string}
 */
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