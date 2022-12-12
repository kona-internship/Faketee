/**
 * html시작하자마자
 * 달력 라이브러리 로딩
 *
 */
$(function() {
    let options={
        multidate: true,
        format: 'yyyy-mm-dd',
        todayHighlight: true,
        autoclose: false,
    };
    $('#datepicker').datepicker(options);

    loadSelectTmp();
});

/**
 * 템플릿 불러오기
 */
function loadSelectTmp(){
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR)  + "/template/list",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        success: function (data) {
            showTmpList(data);
        },
        error: function () {
            alert('템플릿 목록 불러오기 실패!');
        }
    });
}

/**
 * 불러온 템플릿 리스트 화면에 나타내주기
 * @param tmpList
 */
function showTmpList(tmpList){
    $('#selectTmp').empty();

    for(let temp of tmpList.entries()){

        let option = '<option value="' +  temp[1].id + '">' + temp[1].name + '</option>'
        $('#selectTmp').append(option);
    }
}

/**
 * 템플릿 선택이 변경될때마다
 * 달라지는 조직들을 가져온다.
 *
 */
let tempId;
function selectTmpChange(){
    let selectTempId = document.getElementById("selectTmp");
    tempId = selectTempId.options[selectTempId.selectedIndex].value;

    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR)  + "/template/departments",
        type: "GET",
        contentType: "application/json",
        dataType: "json",
        data: {
            "tempId": tempId
        },
        success: function (data) {
            showDepList(data, "check");
        },
        error: function () {
            alert('템플릿 목록 불러오기 실패!');
        }
    });
}

/**
 * 조직 select 박스 화면 보였다가
 * 안보이게하는 css
 *
 * @type {boolean}
 */
let expanded = false;

function showCheckboxes() {
    let checkboxes = document.getElementById("dep-list");
    if (!expanded) {
        checkboxes.style.display = "block";
        expanded = true;
    } else {
        checkboxes.style.display = "none";
        expanded = false;
    }
}
/**
 * 직원 select 박스 화면 보였다가
 * 안보이게하는 css
 *
 * @type {boolean}
 */
let expandedEmp = false;

function showEmpCheckboxes() {
    let checkboxes = document.getElementById("emp-list");
    if (!expandedEmp) {
        checkboxes.style.display = "block";
        expandedEmp = true;
    } else {
        checkboxes.style.display = "none";
        expandedEmp = false;
    }
}

/**
 * 조직에 따른 해당되는 직무리스트 가져오기.
 */
function loadEmp(){
    const checkedDep = [];
    $("input:checkbox[name=dep]:checked").each(function () {
        checkedDep.push(
            $(this).attr("value-id")
        );
    })
    let jsonData = JSON.stringify({
        tempId : tempId,
        checkedDep : checkedDep
    });
    $.ajax({
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR)  + PATH_SCH +"/template/emp",
        type: "POST",
        contentType: "application/json",
        data: jsonData,

        success: function (data) {
            $("#emp-list *").remove();
            showEmpList(data);
        },
        error: function () {
            alert('직원 목록 불러오기 실패!');
        }
    });
}

/**
 * 직원 리스트 화면에 나타내주기.
 * @param empList
 */
function showEmpList(empList) {

    for(let emp of empList){
        let msg = '<label><input type="checkbox" id="'+emp.id+'"name="emp">'+emp.name+'</label>' +
            '&nbsp 조직: '+emp.depName+ '&nbsp 직무: '+emp.posName+ '<br>'
        $("#emp-list").append(msg);
    }
}

/**
 * 근무일정 최종 등록
 */
function checkSchRegister(){
    if (confirm("근무 일정을 등록하시겠습니까?")) {

        let str = $('#datepicker').val();
        let strSplit = str.split(',')
        let dates = [];
        for(let i in strSplit){
            dates.push(strSplit[i]);
        }

        const checkedEmp = [];
        $("input:checkbox[name=emp]:checked").each(function () {
            checkedEmp.push(
                $(this).attr("id")
            );
        })
        $.ajax({
            async: true,
            type: "post",
            data: JSON.stringify({
                dates : dates,
                checkedEmp : checkedEmp,
                tempId : tempId
            }),
            url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR)  + PATH_SCH +"/reg",
            contentType: "application/json; charset=UTF-8",
            success:
                function (result) {
                    alert("근무일정 등록 완료");
                    location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_SCH + "/list";

                },
            error:
                function (request, status, error) {
                    alert("근무일정 등록 실패" + "code:" + request.status + "\n" + " message : " + request.responseText + "\n" + "error:" + error);
                }
        });
    }
}