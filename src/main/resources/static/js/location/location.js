let lan = "";
let lng = "";

function getL() {
    return new Promise(function (resolve) {
        // GeoLocation을 이용해서 접속 위치를 얻어옵니다
        navigator.geolocation.getCurrentPosition(function (position) {
            lan = position.coords.latitude; // 위도
            lng = position.coords.longitude; // 경도
            resolve();
        });
    });
}

/**
 * 출퇴근 장소 등록 페이지
 * 나의 현재 위치를 가져온다.
 * getL()가 끝날 때까지 기다렸다가 순차적으로 진행한다.
 *
 * @returns {Promise<void>}
 */
async function getLocation() {
    await getL();

    let currentPos = new kakao.maps.LatLng(lan, lng);

    map.panTo(currentPos);

    let marker = new kakao.maps.Marker({
        position: currentPos
    });
    marker.setMap(map);
    transAddress(lan, lng);


    // 지도에 클릭 이벤트를 등록합니다
    // 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
    kakao.maps.event.addListener(map, 'click', function (mouseEvent) {

        // 클릭한 위도, 경도 정보를 가져옵니다
        let latlng = mouseEvent.latLng;

        // 마커 위치를 클릭한 위치로 옮깁니다
        marker.setPosition(latlng);

        transAddress(latlng.getLat(), latlng.getLng());
    });
}

/**
 * 파라미터로 받아온 위도,경도를 가지고 도로명 주소로 변환한다.
 * 변환 후 html에 출력을 바꿔준다.
 *
 * @param la
 * @param ln
 */
function transAddress(la, ln) {
    let geocoder = new kakao.maps.services.Geocoder();

    let coord = new kakao.maps.LatLng(la, ln);
    let callback = function (result, status) {
        if (status === kakao.maps.services.Status.OK) {
            document.getElementById('address').innerHTML = result[0].address.address_name;
        }
    };
    geocoder.coord2Address(coord.getLng(), coord.getLat(), callback);
}

/**
 * 데이터 존재 여부를 모두 체크한 후
 * 회사를 등록한다.
 *
 * @returns {boolean}
 */

function checkLocRegister() {
    if (!checkExistData($("#name").val(), "장소명을")) {

        $("#name").focus();
        return false;
    }
    if (!checkExistData($("#radius").val(), "반경 범위를")) {

        $("#radius").focus();
        return false;
    }
    if (isNaN($("#radius").val())) {
        alert("숫자를 입력해주세요");
        $("#radius").focus();
        return false;
    }
    if (confirm("회사를 등록하시겠습니까?")) {

        $.ajax({
            async: true,
            type: "post",
            data: JSON.stringify({
                name: $("#name").val(),
                address: document.getElementById('address').innerText,
                radius: $("#radius").val(),
                lat: lan,
                lng: lng
            }),
            url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/loc",
            contentType: "application/json; charset=UTF-8",
            success:
                function () {
                    alert("장소 등록 성공");
                    goLocList();

                },
            error:
                function (request, status, error) {
                    alert("장소 등록 실패" + "code:" + request.status + "\n" + " message : " + request.responseText + "\n" + "error:" + error);
                }
        });
    }
    return true;
}
/**
 * 입력 받은 데이터가 있는지 체크
 * value의 값이 비어있으면 dataName의 값이 비어있다고 alert를 띄운다.
 *
 * @param value
 * @param dataName
 * @returns {boolean} 비어있으면 false, 값이 존재하면 true
 */

function checkExistData(value, dataName) {
    value = value.trim();
    if (value == "") {
        alert(dataName + " 입력해주세요!");
        return false;
    }
    return true;
}

/**
 * 출퇴근 장소 목록 리스트 페이지로 이동
 */
function goLocList(){
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/loc/list";
}

/**
 * 출퇴근 장소를 등록하는 페이지로 이동
 */
function goAddLocPage() {
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/loc";
}

/**
 * 출퇴근 장소를 삭제한다.
 *
 * @param locId 삭제를 진행할 출퇴근 장소의 id
 *
 */
function deleteLoc(locId){
    $.ajax({
        async: true,
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) +"/loc/delete/"+locId,
        type: "post",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            alert("장소 삭제 성공");
            showLocList(data);

        },
        error: function () {
            alert('출퇴근 장소 삭제 실패!');
        }
    });
}

/**
 * 출퇴근 장소 목록페이지 로딩
 *
 */
function loadLocList(){
    $.ajax({
        async: true,
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) +"/loc/list",
        type: "post",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            showLocList(data);
        },
        error: function () {
            alert('출퇴근 장소 목록 불러오기 실패!');
        }
    });
}

/**
 * 출퇴근 장소리스트를 화면에 출력해준다.
 * 
 * @param locList 화면에 나올 출퇴근 장소 데이터의 리스트
 */
function showLocList(locList){
    $('#loc-list *').remove();

    for(let [index,loc] of locList.entries()) {
        let msg = '<div>'+(index+1) +'. 출퇴근 장소명: '+ loc.name +' 주소 : '+ loc.address+' 반경: '+ loc.radius+
            ' <button type="button" onclick=deleteLoc('+loc.id+')>삭제</button></div>';
        $('#loc-list').append(msg);
    }
}
