function loadCorporations(){

    $.ajax({
        async: true,
        type: "GET",
        url: "/api/index/cor-list",
        contentType: "application/json",

        success : function (list){
            drawCorporationList(list);
        },
        error : function (error){
            alert(JSON.stringify(error));
        }
    });
}

function drawCorporationList(list){

    if(list.entries().next().value == null){
        $('#cor-list').append('<div>' + 'WRONG ATTEMPT' + '</div>');
    }
    for(let [index, cor] of list.entries()){
        let msg = '<div>'
            + '<a href="' + "/corporation/" + cor.id + '">'
            + (index+1)
            + '. '
            + cor.name
            + '</a>'
            + '<br>'
            + '</div>'
        $('#cor-list').append(msg + '<br>');
    }
}