/**
 * Created by Алексей on 12/16/2014.
 */
function cable(){
    var length = document.getElementByName("length").value;
    var type = document.getElementByName("type").value;
    if(!length)
    {
        return;
    }
    if(!type)
    {
        return;
    }
    $.ajax({
        type: "POST",
        url: "/createDC",
        data: { action: 'cable', length: length,type:type },
        cache: false,
        success: function(responce){ $('p[name="result"]').html(responce); }
    });
}

/*function device(){

    var Devicename = document.getElementById("devicename").value;
    /*if(!Devicename)
    {
        return;
    }*/
 /*   $.ajax({
        type: "POST",
        url: "/createDC",
        data: { action: 'device', name: Devicename },
        cache: false,
        success: function(responce){ $('p[name="result"]').html(responce); }
    });
}*/


function device() {
    var devicename=document.getElementById("devicename").value;
    var url = "createDC?action=device&id=" + escape(devicename);
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
}

function callback() {
    if (req.readyState == 4) {
        if (req.status == 200) {
            document.getElementsByName("result").html(req.responseText);
        }
    }
}