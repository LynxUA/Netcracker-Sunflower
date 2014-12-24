



var date1;
var date2;
var option;
var month;
var year;
var loc1;
var loc2;
var loc3;
var loc4;
var loc5;
function locations()
{if(document.getElementById("loc1").checked)
    loc1=document.getElementById("loc1");
else loc1='';
    if(document.getElementById("loc2").checked)
        loc2=document.getElementById("loc2");
    else loc2='';
    if(document.getElementById("loc3").checked)
        loc3=document.getElementById("loc3");
    else loc3='';
    if(document.getElementById("loc4").checked)
        loc4=document.getElementById("loc4");
    else loc4='';
    if(document.getElementById("loc5").checked)
        loc5=document.getElementById("loc5");
    else loc5='';

}

function doCompletion() {
   date1=document.getElementById("date1");
 date2=document.getElementById("date2");
 option=document.getElementById("option");
    locations();

        var url = "xmlxprt?action=periodic&date1=" + escape(date1.value)+"&date2="+ escape(date2.value)+"&option="+ escape(option.value)+"&loc1="+escape(loc1.value)+"&loc2="+escape(loc2.value)+"&loc3="+escape(loc3.value)+"&loc4="+escape(loc4.value)+"&loc5="+escape(loc5.value) ;



    window.location.href = url;
   
}
function doProf(){
 month=document.getElementById("month");
  year=document.getElementById("year");
locations();
            var url = "xmlxprt?action=prof&month="+escape(month.value)+"&year="+escape(year.value)+"&loc1="+escape(loc1.value)+"&loc2="+escape(loc2.value)+"&loc3="+escape(loc3.value)+"&loc4="+escape(loc4.value)+"&loc5="+escape(loc5.value) ;

    if((year.length!=0)&&(month.length!=0)) {

        window.location.href = url;
    }
}
function doRI(){

    locations();
  var url = "xmlxprt?action=ri"+"&loc1="+escape(loc1.value)+"&loc2="+escape(loc2.value)+"&loc3="+escape(loc3.value)+"&loc4="+escape(loc4.value)+"&loc5="+escape(loc5.value);

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
