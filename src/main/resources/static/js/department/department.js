/**
 * html page 별 init 함수들
 */
function listPageInit() {
    loadDepList("button");
}

function removePageInit() {
    loadDepList("check");
}

function regPageInit() {
    loadLocCheckList();
    loadDepList("radio");
}

function detailPageInit(){
    loadDetailDepPage();
}

function modPageInit(){
    loadLocCheckList();
    loadDepList("text");
}

/**
 * 조직 목록을 불러와주는 함수
 * ajax 통신이 정상적으로 처리 될 시 showDepList 함수를 호출한다.
 *
 * @param type 조직 목록을 불러올 형태.
 *             type 종류에는 "check", "button", "radio", "text" 가 있다.
 *             check: 체크박스 형태의 목록 출력
 *             button: 상세페이지로 갈 수 있는 url을 가지고 있는 <a> 태그 형태의 목록 출력
 *             radio: 리디오버튼 형태의 목록 출력
 *             text: <span> 태그 형태의 목록 출력
 */
function loadDepList(type) {
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_DEP + "/list",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            showDepList(data, type);
        },
        error: function () {
            alert('조직 목록 불러오기 실패!');
        }
    });
}

/**
 * 조직 목록을 불러올 때 타입에 맞는 함수를 호출해준다.
 *
 * @param depList 정렬이 되어있지 않은 조직 목록
 * @param type 조직 목록을 불러올 형태
 */
function showDepList(depList, type) {
    switch (type) {
        case "check":
            showCheckDepList(depList);
            break;
        case "button":
            showButtonDepList(depList);
            break;
        case "radio":
            showRadioDepList(depList);
            break;
        case "text":
            showTextDeptList(depList);
            break;
    }
}

/**
 * 정렬이 안된 조직 List를 받아 상위 조직의 ID를 Key, 조직의 객체 List를 Value로 Map을 만들어,
 * Map과 그 정보를 담은 객체를 리턴한다.
 *
 * @param depList 정렬이 되어있지 않은 조직 목록
 * @returns {{minLevel: number, root, depMap: *}} minLevel: 가장 상위 조직이 갖는 level.
 *                                                root: 목록의 가장 상위 조직들의 상위 조직(root).
 *                                                depMap: 상위조직을 key로 만든 조직 맵. (id, list) 형태.
 */
function makeDepIdMap(depList){
    let minLevel = Number.POSITIVE_INFINITY;
    let root;

    const depMap = depList.reduce((map, obj) => {
        let superId = obj.superId;
        if (superId == null) {
            superId = 'root';
        }
        if(obj.level < minLevel){
            minLevel = obj.level
            root = superId
        }
        if (map.has(superId)) {
            map.get(superId).push(obj);
        } else {
            let tmpList = [];
            tmpList.push(obj);
            map.set(superId, tmpList);
        }
        return map;
    }, new Map);

    return {
        root: root,
        depMap: depMap,
        minLevel: minLevel
    }
}

/**
 * 텍스트 형식(<span>)의 조직 목록을 호출한다.
 * 정렬이 되지 않은 조직 목록을 받아
 * makeDepIdMap 함수를 호출하여 ID를 key 값으로 하는 조직 맵을 만들고,
 * showTextDepHierarchy 함수를 호출하여 계층적인 형태의 조직 목록을 호출한다.
 *
 * @param depList 정렬이 되어있지 않은 조직 목록
 */
function showTextDeptList(depList) {
    $("#dep-list *").remove();

    const levelMap = makeDepIdMap(depList);

    showTextDepHierarchy(levelMap.root, levelMap.depMap, levelMap.minLevel);
}

/**
 * 자기 자신을 재귀적으로 호출하여 텍스트 형태의 계층적인 조직 목록을 출력한다.
 *
 * @param superId 상위 조직 ID
 * @param depMap 상위조직을 key로 만든 조직 맵. (id, list) 형태
 * @param minLevel 가장 상위 조직이 갖는 level
 */
function showTextDepHierarchy(superId, depMap, minLevel){
    if (!depMap.has(superId)) {
        return;
    }
    for (let dep of depMap.get(superId)) {
        let spaces = "&emsp;&emsp;"
        $('#dep-list').append(
            '<div>' +
            '<span name="dep" value-id=' + dep.id + ' value-level=' + dep.level + '>' + (spaces.repeat(dep.level - minLevel)) + (dep.level - minLevel > 0 ? 'ㄴ' : '') + dep.name + ' </span>' +
            '</div>'
        );
        showTextDepHierarchy(dep.id, depMap, minLevel);
    }
}

/**
 * 체크박스 형식의 조직 목록을 호출한다.
 * 정렬이 되지 않은 조직 목록을 받아
 * makeDepIdMap 함수를 호출하여 id를 key 값으로 하는 조직 맵을 만들고,
 * showTextDepHierarchy 함수를 호출하여 계층적인 형태의 조직 목록을 호출한다.
 *
 * @param depList 정렬이 되어있지 않은 조직 목록
 */
function showCheckDepList(depList) {
    $("#dep-list *").remove();

    const levelMap = makeDepIdMap(depList);

    showCheckDepHierarchy(levelMap.root, levelMap.depMap, levelMap.minLevel);
}

/**
 * 자기 자신을 재귀적으로 호출하여 체크박스 형태의 계층적인 조직 목록을 출력한다.
 *
 * @param superId 상위 조직 ID
 * @param depMap 상위조직을 key로 만든 조직 맵. (id, list) 형태
 * @param minLevel 가장 상위 조직이 갖는 level
 */
function showCheckDepHierarchy(superId, depMap, minLevel) {

    if (!depMap.has(superId)) {
        return;
    }
    for (let dep of depMap.get(superId)) {
        let spaces = "&emsp;&emsp;"
        $('#dep-list').append(
            '<div>' +
            '<input type="checkbox" name="dep" value-id=' + dep.id + ' value-level=' + dep.level + '>' +
            '<span>' + (spaces.repeat(dep.level-minLevel)) + (dep.level-minLevel > 0 ? 'ㄴ' : '') + dep.name + ' </span>' +
            '</div>'
        );
        showCheckDepHierarchy(dep.id, depMap, minLevel);
    }
}

/**
 * 버튼 형식(<a>)의 조직 목록을 호출한다.
 * 정렬이 되지 않은 조직 목록을 받아
 * makeDepIdMap 함수를 호출하여 id를 key 값으로 하는 조직 맵을 만들고,
 * showTextDepHierarchy 함수를 호출하여 계층적인 형태의 조직 목록을 호출한다.
 *
 * @param depList 정렬이 되어있지 않은 조직 목록
 */
function showButtonDepList(depList) {
    $("#dep-list *").remove();

    const levelMap = makeDepIdMap(depList);
    console.log(levelMap);

    showButtonDepHierarchy(levelMap.root, levelMap.depMap, levelMap.minLevel);
}

/**
 * 자기 자신을 재귀적으로 호출하여 버튼 형태의 계층적인 조직 목록을 출력한다.
 *
 * @param superId 상위 조직 ID
 * @param depMap 상위조직을 key로 만든 조직 맵. (id, list) 형태
 * @param minLevel 가장 상위 조직이 갖는 level
 */
function showButtonDepHierarchy(superId, depMap, minLevel) {

    if (!depMap.has(superId)) {
        return;
    }
    for (let dep of depMap.get(superId)) {
        let spaces = "&emsp;&emsp;"
        $('#dep-list').append(
            '<div>' +
            '<a href="http://localhost:8080/corporation'+ getNextPath(window.location.href, PATH_COR) +'/dep/' + dep.id + '">' + (spaces.repeat(dep.level-minLevel)) + (dep.level-minLevel > 0 ? 'ㄴ' : '') + dep.name + ' </a>' +
            '</div>'
        );
        showButtonDepHierarchy(dep.id, depMap, minLevel);
    }
}

/**
 * 라디오박스 형식의 조직 목록을 호출한다.
 * 정렬이 되지 않은 조직 목록을 받아
 * makeDepIdMap 함수를 호출하여 id를 key 값으로 하는 조직 맵을 만들고,
 * showTextDepHierarchy 함수를 호출하여 계층적인 형태의 조직 목록을 호출한다.
 *
 * @param depList 정렬이 되어있지 않은 조직 목록
 */
function showRadioDepList(depList) {
    $("#dep-list *").remove();

    const levelMap = makeDepIdMap(depList);

    showRadioDepHierarchy(levelMap.root, levelMap.depMap, levelMap.minLevel);
}

/**
 * 자기 자신을 재귀적으로 호출하여 라디오 버튼 형태의 계층적인 조직 목록을 출력한다.
 *
 * @param superId 상위 조직 ID
 * @param depMap 상위조직을 key로 만든 조직 맵. (id, list) 형태
 * @param minLevel 가장 상위 조직이 갖는 level
 */
function showRadioDepHierarchy(superId, depMap, minLevel) {

    if (!depMap.has(superId)) {
        return;
    }
    for (let dep of depMap.get(superId)) {
        let spaces = "&emsp;&emsp;"
        $('#dep-list').append(
            '<div>' +
            '<input type="radio" name="dep" value=' + dep.id + '>' +
            '<span>' + (spaces.repeat(dep.level-minLevel)) + (dep.level-minLevel > 0 ? 'ㄴ' : '') + dep.name + ' </span>' +
            '</div>'
        );
        showRadioDepHierarchy(dep.id, depMap, minLevel);
    }
}

/**
 * 출퇴근 장소 목록을 가져온 후 출력을 위해 showLocCheckList 함수 호출.
 */
function loadLocCheckList() {
    $.ajax({
        async: true,
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/loc/list",
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

/**
 * 출퇴근 장소 List를 받아 HTML에 출력하여준다.
 * @param locList 출퇴근 장소 목록
 */
function showLocCheckList(locList) {
    $('#loc-check-list *').remove();

    for (let [index, loc] of locList.entries()) {
        let msg = '<label><input type="checkbox" name="loc" value=' + loc.id + '><div>' + (index + 1) + '. 출퇴근 장소명: ' + loc.name + ' 주소 : ' + loc.address + ' 반경: ' + loc.radius + ' </div></label>';
        $('#loc-check-list').append(
            '<div>' +
            '<input type="checkbox" name="loc" value=' + loc.id + '>' +
            '<span>' + (index + 1) + '. 출퇴근 장소명: ' + loc.name + ' 주소 : ' + loc.address + ' 반경: ' + loc.radius + ' </span>' +
            '</div>'
        );
    }
}

/**
 * 조직을 등록하는 함수.
 * Ajax 통신을 통해 조직이름(name), 상위조직의 ID(superId), 출퇴근장소 목록(locationIdList)를 json으로 보내준다.
 * 정상적으로 조직을 등록하였을 경우 alert와 함께 조직 목록 화면으로 이동한다.
 */
function registerDep() {
    const locIdList = [];
    $("input:checkbox[name=loc]:checked").each(function () {
        locIdList.push($(this).val());
    })
    let jsonData = JSON.stringify({
        name: $('#dep-name').val(),
        superId: $("input:radio[name=dep]:checked").val(),
        locationIdList: locIdList
    });

    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_DEP,
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        success: function () {
            alert('조직 등록 성공!');
            goDepListPage();
        },
        error: function () {
            alert('조직 등록 실패!');
        }
    });
}

/**
 * 조직을 삭제하는 함수.
 * Ajax 통신을 통해 조직이름(name), 지우고 싶은 조직 목록(removeDepList)를 json으로 보내준다.
 * 정상적으로 조직을 삭제하였을 경우 alert와 함께 조직 목록 화면으로 이동한다.
 */
function removeDep() {
    const removeDepList = [];
    $("input:checkbox[name=dep]:checked").each(function () {
        removeDepList.push({
            [$(this).attr("value-id")]: $(this).attr("value-level")
        });
    })
    let jsonData = JSON.stringify({
        name: $('#dep-name').val(),
        removeDepList: removeDepList
    });
    console.log(jsonData);
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_DEP +"/remove" + getNextPath(window.location.href, PATH_DEP),
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        success: function () {
            alert('조직 삭제 성공!');
            goDepListPage();
        },
        error: function (jqXHR) {
            let result = getErrors(jqXHR);
            showError(result);
        }
    });
}

/**
 * 조직을 수정하는 함수.
 * Ajax 통신을 통해 조직이름(name), 지우고 싶은 조직 목록(removeDepList)를 json으로 보내준다.
 * 정상적으로 조직을 삭제하였을 경우 alert와 함께 조직 목록 화면으로 이동한다.
 */
function modifyDep(){
    const locIdList = [];
    $("input:checkbox[name=loc]:checked").each(function () {
        locIdList.push($(this).val());
    })
    let jsonData = JSON.stringify({
        name: $('#dept-name').val(),
        lowDepartmentIdList: locIdList,
        locationIdList: locIdList,
        isModifyLow: $("#isModifyLow").is(':checked')
    });
    console.log(jsonData);
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) +PATH_DEP+ getNextPath(window.location.href, PATH_DEP) + "/mod",
        type: "POST",
        data: jsonData,
        contentType: "application/json",
        success: function () {
            goDepListPage();
            alert('조직 수정 성공!');
        },
        error: function () {
            alert('조직 수정 실패!');
        }
    });
}

/**
 * 조직 목록 화면(list.html)으로 이동하는 함수
 */
function goDepListPage() {
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_DEP;
}

/**
 * 조직 등록 화면(registration.html)으로 이동하는 함수
 */
function goDepRegPage() {
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_DEP +"/reg";
}

/**
 * 조직 삭제 화면(remove.html)으로 이동하는 함수
 */
function goDepRemovePage() {
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_DEP +"/remove";
}

/**
 * 조직 수정 화면(modification.html)으로 이동하는 함수
 */
function goDepModPage(){
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_DEP+ getNextPath(window.location.href, PATH_DEP) + "/mod";
}

/**
 * 조직 상세 화면(detail.html)에서 하위 조직들의 목록을 불러와 목록을 출력해주는 showDepList 함수를 호출한다.
 */
function loadDetailDepPage(){
    $.ajax({
        async: true,
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) +PATH_DEP +"/detail"+ getNextPath(window.location.href, PATH_DEP),
        type: "post",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            console.log(data);
            $('#dept-name').attr("value", data.dep.name);
            $('#up-dept-name').attr("value", data.dep.superName);
            let locs = data.loc;
            let locText = $('#loc-name');
            for (let [index, lo] of locs.entries()) {
                locText.val(locText.val() + lo.name + ", ");
            }
//하위조직
            showDepList(data.sub, "text");
        },
        error: function () {
            alert('조직 상세 불러오기 실패!');
        }
    });
}