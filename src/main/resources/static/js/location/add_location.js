let lan = "";
let lng = "";
$(document).ready(function() {
    if (navigator.geolocation) {
        getLocation();
    }
});
function getL(){
    return new Promise(function (resolve) {
        // GeoLocation을 이용해서 접속 위치를 얻어옵니다
        navigator.geolocation.getCurrentPosition(function (position) {
            lan = position.coords.latitude; // 위도
            lng = position.coords.longitude; // 경도
            resolve();
        });
    });
}
async function getLocation(){
    await getL();

    let currentPos = new kakao.maps.LatLng(lan,lng);

    map.panTo(currentPos);

    let marker = new kakao.maps.Marker({
        position: currentPos
    });
    marker.setMap(map);
    transAddress(lan, lng);


    // 지도에 클릭 이벤트를 등록합니다
    // 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
    kakao.maps.event.addListener(map, 'click', function(mouseEvent) {

        // 클릭한 위도, 경도 정보를 가져옵니다
        let latlng = mouseEvent.latLng;

        // 마커 위치를 클릭한 위치로 옮깁니다
        marker.setPosition(latlng);

        transAddress(latlng.getLat(), latlng.getLng());
    });
}
function transAddress(la, ln){
    let geocoder = new kakao.maps.services.Geocoder();

    let coord = new kakao.maps.LatLng(la, ln);
    let callback = function(result, status) {
        if (status === kakao.maps.services.Status.OK) {
            document.getElementById('address').innerHTML = result[0].address.address_name;
        }
    };
    geocoder.coord2Address(coord.getLng(), coord.getLat(), callback);
}