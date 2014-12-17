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
        url: "/assign",
        data: { action: 'cable', length: length,type:type },
        cache: false,
        success: function(responce){ $('p[name="result"]').html(responce); }
    });
}

function unassign(){
    var Devicename = document.getElementByName("Devicename").value;
    if(!Devicename)
    {
        return;
    }
    $.ajax({
        type: "POST",
        url: "/assign",
        data: { action: 'device', name: Devicename },
        cache: false,
        success: function(responce){ $('p[name="result"]').html(responce); }
    });
}