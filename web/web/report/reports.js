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
var loc1;
var loc2;
var loc3;



function doCompletion() {
   date1=document.getElementById("date1");
 date2=document.getElementById("date2");
 option=document.getElementById("option");
    if(document.getElementById("borshaga").checked)
    loc1=document.getElementById("borshaga");
    else loc1='';
    if(document.getElementById("goloseevo").checked)
    loc2=document.getElementById("goloseevo");
    else loc2='';
    if(document.getElementById("troya").checked)
    loc3=document.getElementById("troya");
    else loc3='';
 //document.write(location.pathname-"sirep.jsp")
        var url = "xmlxprt?action=periodic&date1=" + escape(date1.value)+"&date2="+ escape(date2.value)+"&option="+ escape(option.value)+"&loc1="+escape(loc1.value)+"&loc2="+escape(loc2.value)+"&loc3="+escape(loc3.value) ;
        var url2=location.href+""+url;
        var url3=url2.replace("/report","")


    window.location.href = url;
       //window.location.href ="http://localhost:8084/sirep/"+url
// req = initRequest();
       // req.open("GET", url, false);
       // req.onreadystatechange = callback;
       // req.send(null);
}
function doProf(){
 month=document.getElementById("month");
  year=document.getElementById("year");
 /* a=0;
    var month1=parseInt(month,10);
 var   year1=parseInt(year,10);
   if(month>12) {
        a=1;
        document.getElementById("month").value="Wrong data";

    }
    if(month<1) {
        a=1;
        document.getElementById("month").value="Wrong data";

    }

*/

            var url = "xmlxprt?action=prof&month=" + escape(month.value) + "&year=" + escape(year.value);
            var url2 = location.href + "" + url;
            var url3 = url2.replace("/report", "");


    if(year.length!=0)window.location.href = url;


}
function doRI(){


  var url = "xmlxprt?action=ri";
        var url2=location.href+""+url;
    var url3=url2.replace("/report","")
    window.location.href = url;
}
function isrighty(obj)

{

    if (obj.value>2100) obj.value=2100;

    if (obj.value<1) obj.value='';

}
function isrightm(obj)

{

    if (obj.value>12) obj.value=12;

    if (obj.value<1) obj.value='';

}
