let lan = "";
let lng = "";
$.ajax({
    async: true,
    url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD + "/load/atd/loc",
    type: "get",
    contentType: "application/json",
    success: function (data) {
        getLocation(data);
    },
    error: function () {
        alert('장소 불러오기 실패!');
    }
});

/**
 * 위도 경도 가져오기
 * @returns {Promise<unknown>}
 */
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
 * 동기로 위도 경도 가져온 다음
 * 카카오 map으로 현재위치 지도를 그려준다.
 * 조직에 해당하는 위치의 반경도 나타내준다.
 *
 * @param data
 * @returns {Promise<void>}
 */
async function getLocation(data) {
    await getL();

    let currentPos = new kakao.maps.LatLng(lan, lng);

    map.panTo(currentPos);
    // 마커 이미지의 이미지 주소입니다
    let imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
    // 마커 이미지의 이미지 크기 입니다
    let imageSize = new kakao.maps.Size(24, 35);

    // 마커 이미지를 생성합니다
    let markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize);

    let marker = new kakao.maps.Marker({
        map: map,
        position: currentPos,
        image: markerImage // 마커 이미지
    });
    let message = '<div style="padding:5px;">현 위치!</div>'; // 인포윈도우에 표시될 내용입니다

    let iwContent = message, // 인포윈도우에 표시할 내용
        iwRemoveable = false;

    // 인포윈도우를 생성합니다
    let infowindow = new kakao.maps.InfoWindow({
        content: iwContent,
        removable: iwRemoveable
    });

    // 인포윈도우를 마커위에 표시합니다
    infowindow.open(map, marker);

    // 지도 중심좌표를 접속위치로 변경합니다
    map.setCenter(currentPos);

    for (let i = 0; i < data.length; i++) {
        let locMarker = new kakao.maps.Marker({
            map: map,
            position: new kakao.maps.LatLng(data[i].lat, data[i].lng),
            title: data[i].name
        })
        let circle = new kakao.maps.Circle({
            center: new kakao.maps.LatLng(data[i].lat, data[i].lng),  // 원의 중심좌표 입니다
            radius: data[i].radius, // 미터 단위의 원의 반지름입니다
            strokeWeight: 2, // 선의 두께입니다
            strokeColor: '#524e4e', // 선의 색깔입니다
            strokeOpacity: 0.5, // 선의 불투명도 입니다 1에서 0 사이의 값이며 0에 가까울수록 투명합니다
            strokeStyle: 'solid', // 선의 스타일 입니다
            fillColor: '#837b7b', // 채우기 색깔입니다
            fillOpacity: 0.4  // 채우기 불투명도 입니다
        });
        circle.setMap(map);
    }

    drawLocList(data);
}

/**
 * 출퇴근 장소의 리스트를 화면에 출력한다.
 * @param data
 */
function drawLocList(data) {
    for (let [index, loc] of data.entries()) {
        let distance = getDistance(loc.lat, loc.lng);
        let msg = "<input type='radio' name='atd_loc' id='"
            + loc.id + "'> <label for='" + loc.id + "'>"
            + loc.name + "</label>" + "[<span id='d" + loc.id + "'>" + distance + "</span> m]" +
            "<input type='hidden' id='h" + loc.id + "'value='" + loc.radius + "'> <br>"
        $('#loc-list').append(msg);
    }
    $("#" + data[0].id).attr("checked", true);
}

/**
 * 현재위치와 클릭한 출퇴근 장소와의 거리 반환한다.
 * @param loc_lat
 * @param loc_lng
 * @returns {number}
 */
function getDistance(loc_lat, loc_lng) {
    let clickLine = new kakao.maps.Polyline({
        path: [
            new kakao.maps.LatLng(lan, lng),
            new kakao.maps.LatLng(loc_lat, loc_lng)
        ]
    });
    let distance = clickLine.getLength();
    //소수점 버림, 정수 반환
    return Math.floor(distance);
}

/**
 * 출퇴근 장소와 현재위치가 반경보다 작으면
 * 해당 시간을 저장하고 그렇지 않으면 alert를 내보낸다.
 */
function saveAtdInfo() {
    //선택한 장소와 현재 위치와의 반경 차이가 있으면 오류 없으면 등록하기이ㅣ
    let state = $("input:hidden[name='atd-state']").val();
    if (confirm(state + "하시겠습니까?")) {
        let locId = $("input[name='atd_loc']:checked").attr('id');
        let radius = Number($("#h"+locId).val());
        let distance = Number($("#d"+locId).text());
        if (radius >= distance) {
            // 가능한 위치일 경우 ajax 보내기
            let data;
            if(state == "출근"){
                data = "on"
            }else {
                //퇴근
                data = "off"
            }
            $.ajax({
                async: true,
                type: "get",
                url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_ATD + "/reg?onoff=" + data,
                contentType: "application/json; charset=UTF-8",
                success:
                    function () {
                        location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR);

                    },
                error:
                    function (request, status, error) {
                        alert(state+" 실패" + "code:" + request.status + "\n" + " message : " + request.responseText + "\n" + "error:" + error);
                    }
            });
        } else {
            alert(state+" 가능한 위치가 아닙니다")
        }
    }
}