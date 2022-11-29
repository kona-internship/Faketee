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

function loadDepList(type) {
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/dep/list",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            showDepList(data, type);
        },
        error: function () {
            alert('직무 목록 불러오기 실패!');
        }
    });
}

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
    }
}

function showTextDeptList(depList) {

}

function makeDepLevelMap(depList){
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

function showCheckDepList(depList) {
    $("#dep-list *").remove();

    const levelMap = makeDepLevelMap(depList);

    showCheckDepHierarchy(levelMap.root, levelMap.depMap, levelMap.minLevel);
}

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

function showButtonDepList(depList) {
    $("#dep-list *").remove();

    const levelMap = makeDepLevelMap(depList);
    console.log(levelMap);

    showButtonDepHierarchy(levelMap.root, levelMap.depMap, levelMap.minLevel);
}

function showButtonDepHierarchy(superId, depMap, minLevel) {

    if (!depMap.has(superId)) {
        return;
    }
    for (let dep of depMap.get(superId)) {
        let spaces = "&emsp;&emsp;"
        $('#dep-list').append(
            '<div>' +
            '<a href="http://localhost:8080/corporation/1/dep/' + dep.id + '">' + (spaces.repeat(dep.level-minLevel)) + (dep.level-minLevel > 0 ? 'ㄴ' : '') + dep.name + ' </a>' +
            '</div>'
        );
        showButtonDepHierarchy(dep.id, depMap, minLevel);
    }
}

function showRadioDepList(depList) {
    $("#dep-list *").remove();

    const levelMap = makeDepLevelMap(depList);

    showRadioDepHierarchy(levelMap.root, levelMap.depMap, levelMap.minLevel);
}

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
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/dep",
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

function removeDep() {
    const removeDepList = [];
    $("input:checkbox[name=dep]:checked").each(function () {
        console.log($(this).attr("value-id"));
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
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/dep/remove" + getNextPath(window.location.href, PATH_DEP),
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


function modifyDep(){

    let jsonData = JSON.stringify({
        name: $('#dept-name').val(),
        modifyId: $('loc-name').val()
    });
    console.log(jsonData);
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) +"/dep"+ getNextPath(window.location.href, PATH_DEP) + "/mod",
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


function goDepListPage() {
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/dep";
}

function goDepRegPage() {
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/dep/reg";
}


function goDepModPage(){
    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + "/dep"+ getNextPath(window.location.href, PATH_DEP) + "/mod";
}

function loadDetailDepPage(){
    $.ajax({
        async: true,
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) +"/dep/detail"+ getNextPath(window.location.href, PATH_DEP),
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
            showDepList(data.sub, "button");
        },
        error: function () {
            alert('조직 상세 불러오기 실패!');
        }
    });
}