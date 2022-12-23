
let dateSelectedInString;
let dateSelectedInArray = [];
$(function() {

    let options={
        multidate: true,
        format: 'yyyy-mm-dd',
        todayHighlight: false,
        autoclose: false,
    };
    $('#select-vac-date').datepicker(options);

    $('#select-vac-date').on("change", function (){
        let selected = $(this).val();
        loadRemaining(selected);
        dateSelectedInString = selected;
    });
});

function loadVacTypeSelectBox(){

    $.ajax({
        async: true,
        type: "GET",
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_TYPE + "/by-cor",
        contentType: "application/json",
        dataType: "json",

        success : function (data){
            drawVacTypeSelectBox(data);
        },
        error : function (error){
            alert(JSON.stringify(error));
        }
    });
}

function drawVacTypeSelectBox(data){

    for(let [index, type] of data.entries()){
        let option = document.createElement("option");
        option.value = type.id;
        option.text = type.name + '(' + type.vacGroupResponseDto.name + ')';
        $("#select-vac-type").append(option);
    }
}

function checkType(){

    $('#remaining *').remove();
    if($('#select-vac-type').val()==0)
        return;
    $('#select-vac-date').removeAttr("disabled");
    $('#alert').attr("hidden", true);
}


function loadRemaining(str){

    $('#remaining *').remove();
    let strSplit = str.split(',');
    let dates = [];
    for(let i in strSplit)
        dates.push(strSplit[i]);
    dateSelectedInArray = dates;
    let vacTypeId = $('#select-vac-type option:selected').val();
    $.ajax({
        async: true,
        type: "GET",
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_REQ + "/remaining?type=" + vacTypeId,
        contentType: "application/json",
        dataType: "json",

        success : function (data){
            drawRemaining(data, dates.length);
        },
        error : function (error){
            alert(JSON.stringify(error));
        }
    });
}

function drawRemaining(response, dates){

    $('#remaining *').remove();

    let calRemain = response.remain - (dates * response.sub);

    let msg = '<i>'
    + (response.vacGroup)
    + '의 잔여일수는&nbsp;'
    + (response.remain)
    + '일&nbsp;-> &nbsp;'
    + calRemain
    + '일 입니다.'
    + '</i>'
    $('#remaining').append(msg);
}

function setInfo(){

    window.location = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_REQ + "/form?type=" + $('#select-vac-type option:selected').val() + "&dates=" + dateSelectedInString;
}

function loadApprovalLine(vacType){

    alert(vacType.name);
}
