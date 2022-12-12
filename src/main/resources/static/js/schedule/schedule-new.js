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
function showTmpList(tmpList){
    $('#selectTmp').empty();

    for(let temp of tmpList.entries()){

        let option = '<option value="' +  temp[1].id + '">' + temp[1].name + '</option>'
        $('#selectTmp').append(option);
    }
}
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
function showEmpList(empList) {

    for(let emp of empList){
        let msg = '<label><input type="checkbox" id="'+emp.id+'"name="emp">'+emp.name+'</label>' +
            '&nbsp 조직: '+emp.depName+ '&nbsp 직무: '+emp.posName+ '<br>'
        $("#emp-list").append(msg);
    }
}
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