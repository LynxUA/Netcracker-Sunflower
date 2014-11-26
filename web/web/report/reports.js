/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



var date1;
var date2;
var option;
var month;
var year;


function doCompletion() {
   date1=document.getElementById("date1");
 date2=document.getElementById("date2");
 option=document.getElementById("option");
 //document.write(location.pathname-"sirep.jsp")
        var url = "xmlxprt?action=periodic&date1=" + escape(date1.value)+"&date2="+ escape(date2.value)+"&option="+ escape(option.value) ;
        var url2=location.href+""+url;
        var url3=url2.replace("/report","")

    window.location.href = url3;
       //window.location.href ="http://localhost:8084/sirep/"+url
// req = initRequest();
       // req.open("GET", url, false);
       // req.onreadystatechange = callback;
       // req.send(null);
}
function doProf(){
 month=document.getElementById("month");
  year=document.getElementById("year");
  var a=1;
  month=month.parse
  var url = "xmlxprt?action=prof&month=" + escape(month.value)+"&year="+ escape(year.value);
        var url2=location.href+""+url;
    var url3=url2.replace("/report","")
    window.location.href = url3;
}
function doRI(){


  var url = "xmlxprt?action=ri";
        var url2=location.href+""+url;
    var url3=url2.replace("/report","")
    window.location.href = url3;
}

