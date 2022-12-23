$(function() {
    let options={
        multidate: true,
        format: 'yyyy-mm-dd',
        todayHighlight: true,
        autoclose: false,
    };
    $('#select-vac-date').datepicker(options);
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
    if($('#select-vac-type').val()!=0){
        $('#select-vac-date').removeAttr("disabled");
        $('#alert').attr("hidden", true);
    }
}

