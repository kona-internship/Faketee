let dateSelectedInString;
let dateSelectedInArray = [];
let approvalChecked = 0;
let approvalLevel = 0;

function checkBoxOnclickHandler(){
    approvalChecked = $('input:checkbox[name=approvals]:checked').length;
    if(approvalChecked>approvalLevel || approvalChecked===0 || approvalChecked<approvalLevel){
        $('#select-approval').replaceWith(
            '<label id="select-approval" style="color: crimson">'
            + approvalChecked
            + ' / '
            + approvalLevel
            +'</label>'
        );
    }else  {
        $('#select-approval').replaceWith(
            '<label id="select-approval">'
            + approvalChecked
            + ' / '
            + approvalLevel
            +'</label>'
        );
    }

    if(approvalChecked !== 0)
        $('#send-form').removeAttr("disabled");
    if(approvalChecked === 0 || approvalChecked > approvalLevel)
        $('#send-form').attr("disabled", "disabled");
}

function parseStrDateToArray(str){

    let strSplit = str.split(',');
    let dates = [];
    for(let i in strSplit)
        dates.push(strSplit[i]);
    dateSelectedInArray = dates;
}

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
    parseStrDateToArray(str);
    let vacTypeId = $('#select-vac-type option:selected').val();

    // TODO: ???????????? ????????? ?????? ???????????? ?????????????????? ?????? ??????????????????, ????????? ???????????? ??????????????? call ????????? ??????????????? ???????????? - ???????????? ??????????????? ?????? ????????? ???.
    $.ajax({
        async: true,
        type: "GET",
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_REQ + "/remaining?type=" + vacTypeId,
        contentType: "application/json",
        dataType: "json",

        success : function (data){
            drawRemaining(data, dateSelectedInArray.length);
            $('#to-form').removeAttr("disabled")
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
    + '??? ???????????????&nbsp;'
    + (response.remain)
    + '???&nbsp;-> &nbsp;'
    + calRemain
    + '??? ?????????.'
    + '</i>'
    $('#remaining').append(msg);
}

function setInfo(){

    window.location = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_REQ + "/form?type=" + $('#select-vac-type option:selected').val() + "&dates=" + dateSelectedInString;
}

function loadApprovalLine(vacType){

    $.ajax({
        async: true,
        type: "GET",
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_REQ + "/load/approvals",
        contentType: "application/json",

        success : function (list){
            approvalLevel = vacType.vacGroupResponseDto.approvalLevel;
            drawApprovalLine(list);
        },
        error : function (error){
            alert(JSON.stringify(error));
        }
    });
}

function drawApprovalLine(list){

    $('#approval-list *').remove();

    $('#approval-list').append('<label id="select-approval" style="color: crimson">'
        + approvalChecked
        + ' / '
        + approvalLevel
        +'</label>'
        +  '<br>');

    $('#approval-list').append('<br>'
        + '<div style="color: gray">'
        + '???????????????'
        + '</div>');

    for(let[index, emp] of list.entries()){
        let msg =
            '<label>'
            + '<input type="checkbox" onclick="checkBoxOnclickHandler()" name="approvals" value=' + emp.id + '>'
            + emp.name
            + '</label>'
            + '<br>'
        $('#approval-list').append(msg);
    }

    $('#approval-list').append('<br>' + '<div style="color: gray">' + '???????????????' + '</div>');

    $.ajax({
        async: true,
        type: "GET",
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_REQ + "/load/approvals/admin",
        contentType: "application/json",

        success : function (admin){
            $('#approval-list').append('<label>'
                +'<input type="checkbox" onclick="checkBoxOnclickHandler()" name="approvals" value=' + admin.id + '>'
                + admin.name
                +'</label>'
                +'<br><br>'
            );
        },
        error : function (error){
            alert('?????????????????? ??????????????? ??????????????????');
            alert(JSON.stringify(error));
        }
    });
}

function loadRequestInfo(vacType, dates){

    parseStrDateToArray(dates);

    $('#summary').append(
        '<div style="color: gray">'
        + '????????????'
        + '</div>'
        + '<div>'
        + vacType.name
        + '&nbsp;'
        + vacType.startTime
        + ' ~ '
        + vacType.endTime
        + '</div>'
        + '<br>'
    )

    $('#summary').append(
        '<div style = "color: gray">'
        + '????????????'
        + '</div>'
    );

    for(let i=0; i<dateSelectedInArray.length; i++){
        $('#summary').append(
            '<div>'
            + dateSelectedInArray[i]
            + '</div>'
        );
    }

    $.ajax({
        async: true,
        type: "GET",
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_REQ + "/remaining?type=" + vacType.id,
        contentType: "application/json",
        dataType: "json",

        success : function (data){
            remains = data.remain - (dateSelectedInArray.length * data.sub);
            $('#summary').append(
                '<br>'
                + '<i style="color: crimson">'
                + (data.vacGroup)
                + '??? ???????????????&nbsp;'
                + (data.remain)
                + '???&nbsp;-> &nbsp;'
                + remains
                + '??? ?????????.'
                + '</i>'
            )
        },
        error : function (error){
            alert(JSON.stringify(error));
        }
    });
}

function sendForm(vacTypeId){

    let approvalSelected = [];
    $("input:checkbox[name=approvals]:checked").each(function (){
        approvalSelected.push(
            $(this).attr("value")
        );
    });

    let data = {
        vacTypeId : vacTypeId,
        approvals : approvalSelected,
        dates : dateSelectedInArray,
        requestMessage : $('#message').val()
    }

    $.ajax({
        async: true,
        type: "POST",
        url: URL_API_COR_PREFIX + getNextPath(window.location.href, PATH_COR) + PATH_VAC_REQ + "/request",
        data: JSON.stringify(data),
        contentType: "application/json; charset=UTF-8",

        success : function (){
            alert("????????? ???????????????.")
            window.location.href = URL_COR_PREFIX + getNextPath(window.location.href, PATH_COR);
        },
        error : function (error){
            alert(JSON.stringify(error));
        }
    });
}
