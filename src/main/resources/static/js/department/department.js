function listPageInit(){
    loadDepList();
}

function regPageInit(){
    loadLocCheckList();
}

function showDepCheckList(depList){
    $("#dep-list *").remove();

    const depMap = depList.reduce((map, obj) =>{
        let superId = obj.superId;
        if(superId == null){
            superId = 'root';
        }
        map.set(0,0);
        console.log(map.get(superId));
        if(map.has(superId)){
            map.get(superId).push(obj);
        }else{
            let tmpList = [];
            tmpList.push(obj);
            map.set(superId, tmpList);
        }
    }, new Map);

    showDepHierarchy('root', depMap);

}

function showDepHierarchy(superId, depMap){
    for(let dep of depMap.get(superId)){
        $('#dep-list').append(
            '<div>' +
            '<input type="checkbox" name="dep" value='+ dep.id +'>' +
            '<span>'+ dep.name +' </span>' +
            '</div>'
        );
        showDepHierarchy(dep.id);
    }
}

function loadDepList(){
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) +"/dep/list",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            // showDepCheckList(data);
        },
        error: function () {
            alert('직무 목록 불러오기 실패!');
        }
    });
}

function showDepList(depList){
    $("#dep-list *").remove();

    for(let dep of depList) {
        $('#dep-list').append(
            '<div>조직 이름: '+ dep.name +' </div>'
        );
    }
}

function registerDep(){
    const locIdList = [];
    $("input:checkbox[name=loc]:checked").each(function (){
        locIdList.push($(this).val());
    })
    let jsonData = JSON.stringify({
        name: $('#dep-name').val(),
        locationIdList: locIdList
    });
    console.log(jsonData);
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) +"/dep",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        success: function () {
            goDepListPage();
            alert('조직 등록 성공!');
        },
        error: function () {
            alert('조직 등록 실패!');
        }
    });
}

function loadLocCheckList(){
    $.ajax({
        async: true,
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) +"/loc/list",
        type: "post",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            showLocCheckList(data);
        },
        error: function () {
            alert('출퇴근 장소 목록 불러오기 실패!');
        }
    });
}

function showLocCheckList(locList){
    $('#loc-check-list *').remove();

    for(let [index,loc] of locList.entries()) {
        let msg = '<label><input type="checkbox" name="loc" value='+ loc.id +'><div>'+(index+1) +'. 출퇴근 장소명: '+ loc.name +' 주소 : '+ loc.address+' 반경: '+ loc.radius+ ' </div></label>';
        $('#loc-check-list').append(
            '<div>' +
            '<input type="checkbox" name="loc" value='+ loc.id +'>' +
            '<span>'+(index+1) +'. 출퇴근 장소명: '+ loc.name +' 주소 : '+ loc.address+' 반경: '+ loc.radius+ ' </span>' +
            '</div>'
        );
    }
}

function goDepListPage(){
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/dep";
}

function goDepRegPage(){
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/dep/reg";
}