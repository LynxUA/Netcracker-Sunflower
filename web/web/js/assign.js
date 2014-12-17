/**
 * Created by Алексей on 12/13/2014.
 */
var req;
var isIE;
var option;

/*function init() {
    completeField = document.getElementById("complete-field");
}

function selectdevice() {
     option=document.getElementById("option")
    document.get
    var url = "assign?action=getports&id=" + option;
    req = initRequest();
    req.open("GET", url, true);
    req.onreadystatechange = callback;
    req.send(null);
}

function initRequest() {
    if (window.XMLHttpRequest) {
        if (navigator.userAgent.indexOf('MSIE') != -1) {
            isIE = true;
        }
        return new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        isIE = true;
        return new ActiveXObject("Microsoft.XMLHTTP");
    }
    function callback() {
        if (req.readyState == 4) {
            if (req.status == 200) {
                parseMessages(req.responseXML);
            }
        }
    }
}*/

function selectRouter(){
    var router = $('select[name="router"]').val();


        $.ajax({
            type: "POST",
            url: "/assign",
            data: { action: 'getports', id_router: router },
            cache: false,
            success: function(responce){ $('select[name="ports"]').html(responce); }
        });

};
function assign(){

    var port = document.getElementByName("ports")
    var portname=port.val
    if(!portname)
    {
        return;
    }
    $.ajax({
        type: "POST",
        url: "/assign",
        data: { action: 'assign', parameters: port },
        cache: false,
        success: function(responce){ $('p[name="result"]').html(responce); }
    });
}

function unassign(){
    var port = document.getElementByName("unports").value;
    if(!port)
    {
        return;
    }
    $.ajax({
        type: "POST",
        url: "/assign",
        data: { action: 'unassign', parameters: port },
        cache: false,
        success: function(responce){ $('p[name="result"]').html(responce); }
    });
}

function createCir(){

    $.ajax({
        type: "POST",
        url: "/assign",
        data: { action: 'crateCir' },
        cache: false,
        success: function(responce){ $('p[name="result"]').html(responce); }
    });
}

function isrightm(obj)

{

    if (obj.value>100000) obj.value=100000;

    if (obj.value<1) obj.value=1;

}

function remove(){

    $.ajax({
        type: "POST",
        url: "/assign",
        data: { action: 'remove'},
        cache: false,
        success: function(responce){ $('p[name="result"]').html(responce); }
    });
}