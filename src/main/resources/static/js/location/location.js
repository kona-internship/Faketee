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
                    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/loc/list";

                },
            error:
                function (request, status, error) {
                    alert("장소 등록 실패" + "code:" + request.status + "\n" + " message : " + request.responseText + "\n" + "error:" + error);
                }
        });
    }
    return true;
}

function checkExistData(value, dataName) {
    value = value.trim();
    if (value == "") {
        alert(dataName + " 입력해주세요!");
        return false;
    }
    return true;
}
function goListLocRegister(){
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/loc/list";
}
function goAddLocPage() {
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/loc";
}
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
            alert('출퇴근 장소 목록 불러오기 실패!');
        }
    });
}
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
function showLocList(locList){
    $('#loc-list *').remove();

    for(let [index,loc] of locList.entries()) {
        let msg = '<div>'+(index+1) +'. 출퇴근 장소명: '+ loc.name +' 주소 : '+ loc.address+' 반경: '+ loc.radius+
            ' <button type="button" onclick=deleteLoc('+loc.id+')>삭제</button></div>';
        $('#loc-list').append(msg);
    }
}
