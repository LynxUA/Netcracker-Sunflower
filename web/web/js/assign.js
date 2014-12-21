/**
 * Created by Алексей on 12/13/2014.
 */


    function isrightm(obj)

    {

    if (obj.value>100000) obj.value=100000;

    if (obj.value<1) obj.value=1;

}
function getports() {
    var router = document.getElementById('router');

    $$a({

            type:'get',//тип запроса: get,post либо head

    url:'assign',//url адрес файла обработчика

    data:{action: 'getports', id_router: router.value},//параметры запроса

    response:'text',//тип возвращаемого ответа text либо xml

    success:function (data) {//возвращаемый результат от сервера

        //$$('Id_Port',$$('Id_Port').innerHTML+data);
        $('select[name = "Id_Port"]').html(data);
    }

});

}

/*function getports() {
    var router = document.getElementById('router');
    var url = "assign?action=getports&id_router=" + escape(router.value);
    req = initRequest();
    req.open("GET", url, true);
    req.onreadystatechange = callback;
    req.send(null);
}*/

