let todayDate;
function toSchType(){
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_SCH + "/type";
}
function toTemp(){
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/temp";
}
function toSchReg(){
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_SCH + "/reg";
}
function toEmployee(){
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_EMP;
}
function toDepartment(){
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_DEP;
}
function toPosition(){
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_POS;
}

function toLocation(){
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_LOC + "/list";
}

function toRequest(){
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_DRAFT + "/req";
}

function toSchedule() {
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_SCH + "/list";
}

function toAttendance(){

}

function toCorSelect(){
    location.href = "/";
}

function toMyPage(){
    location.href = "/account/mypage";
}

function toVacation(){
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_INFO;
}

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
const ATTEND = {
    ABSENCE : "결근",
    OFF_WORK : "퇴근완료",
};
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

    if(data.state === ATTEND.ABSENCE || data.state === ATTEND.OFF_WORK){
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

        month = month >= 10 ? month : '0' + month;
        date = date >= 10 ? date : '0' + date;

        let when = ['일', '월', '화', '수', '목', '금', '토'];

        todayDate = year + "-" + month + "-" + date;
        return month + "/" + date + "(" + when[day] + ") <br>";
}

function sendRequest(value){

    switch (value){
        case "요청" :
            return URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR);
        case "출퇴근기록 생성" :
            return URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD_REQ + "/create";
        case "출퇴근기록 수정" :
            return URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD_REQ + "/update";
        case "출퇴근기록 삭제" :
            return URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD_REQ + "/delete";
        case "휴가 생성" :
            return URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_REQ + "/new";
    }
}