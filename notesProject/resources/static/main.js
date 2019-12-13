function addNotes(){
 var author = document.getElementById("fNoteAuthor").value
  var body = document.getElementById("fNoteBody").value
 var priority = document.getElementById("fNotePriority").value
 var date = document.getElementById("fNoteDate").value
 var uniSpe = document.getElementById("fNoteUniSpe").checked

 var xmlhttp = new XMLHttpRequest();
    var Url = "/api/addNote?author="+author+"&body="+body+"&uniOnly="+uniSpe+"&priority="+priority+"&date="+date;
    xmlhttp.open("POST", Url);
    xmlhttp.send();
    xmlhttp.onload=function(){
        UpdateAll()
    };

    return false

}


function resetHTML(){
    str = `
    <div id="addNoteBox" class="card" style="width: 18rem;">
    <img class="card-img-top" src="images/note.png" alt="Note">
    <div class="card-body">
        <h5 class="card-title"><b>Nouvelle Note</b></h5>
    </div>
    <ul class="list-group list-group-flush">

        <form  id="addNote" >
        <li class="list-group-item"><b>Texte :</b>
            <div class="body" >
                <textarea class="form-control" id="fNoteBody" rows="3" required></textarea>
            </div>
        </li>
        <li class="list-group-item">
            <div class="elem">
                <b>Auteur : </b>
                <input id="fNoteAuthor" type="text" class="form-control"  placeholder="Username"  required>

            </div>
            <div class="elem">
                <b>Priorité : </b>
                <input id="fNotePriority" type="number" name="montant" min="0" max="100" class="form-control" required>

            </div>
            <div class="elem">
                <b>Date : </b>
                <input id="fNoteDate" type="text" class="form-control"  placeholder="2019-12-31" required>

            </div>
            <div class="elem">
                <b>Doit etre fait à l'université : </b><br>
                <input id="fNoteUniSpe" type="checkbox" class="form-check-input" id="exampleCheck1">
                <br class="elem">
                <br class="elem">

            </div>
            <input type="submit" value="Valider la note" class=" elem btn btn-primary">


        </li>


        </form>



    </ul>

</div>
    `

    document.getElementById("notesArea").innerHTML = str
}


function UpdateAll(){

resetHTML()

 var author = document.getElementById("validationCustomUsername").value


 var xmlhttp = new XMLHttpRequest();
    var Url = "/api/getNotes?author="+author;
    xmlhttp.open("GET", Url);
    xmlhttp.send();
    xmlhttp.onload=function(){
        json=JSON.parse(xmlhttp.responseText);
        var i =0
        for (obj in json){
                i++
                var str = `
                <div id="addNoteBox" class="card" style="width: 18rem;">
                        <img class="card-img-top" src="images/note.png" alt="Note">
                        <div class="card-body">
                            <h5 class="card-title"><b>Note n°${i}</b></h5>
                        </div>
                        <ul class="list-group list-group-flush">

                            <form id="addNote">
                            <li class="list-group-item"><b>Texte :</b>
                                <div class="body" >
                                ${json[obj]["body"]} 
                                </div>
                            </li>
                            <li class="list-group-item">
                                <div class="elem">
                                    <b>Auteur : </b>
                                    ${json[obj]["author"]} 
                                </div>
                                <div class="elem">
                                    <b>Priorité : </b>
                                    ${json[obj]["priority"]} 
                                </div>
                                <div class="elem">
                                    <b>Date : </b>
                                    ${json[obj]["date"]} 
                                </div>
                                <div class="elem">
                                    <b>Doit etre fait à l'université : </b><br>
                                    ${json[obj]["uniOnly"]}                                     <br class="elem">
                                    <br class="elem">

                                </div>


                            </li>


                            </form>



                        </ul>

                    </div>
                `


                document.getElementById("notesArea").insertAdjacentHTML("beforeend",str)
        }
    };

}




// cookies reading
function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}
if (window.Notification && Notification.permission !== "granted") {
    Notification.requestPermission()
}
// get navigation geolocation
window.navigator.geolocation.getCurrentPosition(
    (position) => {
    document.cookie="latitude="+position.coords.latitude;
    document.cookie="longitude="+position.coords.longitude;
}, //there is one method to update state object : setState !!
(err) => alert(err.message)

);


function checkCookies(){
    var lat = document.cookie.indexOf('latitude=');
    var lon = document.cookie.indexOf('longitude=');
    if(lat != -1 && lon != -1){
        if (window.Notification && Notification.permission !== "denied") {
            Notification.requestPermission((status) => {
            // status is "granted", if accepted by user
                var n = new Notification('Géo', {
                    body: "Your location is already stocked in website cookies => "+getCookie('latitude') +" , " + getCookie('longitude'),
                    
                })
            })
        }
        
        
    }else{
        if (window.Notification && Notification.permission !== "denied") {
            Notification.requestPermission((status) => {
            // status is "granted", if accepted by user
                var n = new Notification('Géo', {
                    body: "Your location is already stocked in website cookies => "+getCookie('latitude') +" , " + getCookie('longitude'),
                    
                })
            })
        }
    }
}

$(function () {
    $('#addNote').on('submit',function(event) {
				addNotes()
        event.preventDefault();

    })
});


UpdateAll()
