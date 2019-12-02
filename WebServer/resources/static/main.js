function updateMatch(matchID, data, silent){
    console.log(matchID+"-E1")
    document.getElementById(matchID+"-E1").innerHTML = data["nomEquipe1"]
    document.getElementById(matchID+"-E2").innerHTML = data["nomEquipe2"]
    document.getElementById(matchID+"-E1b").innerHTML = data["nomEquipe1"]
    document.getElementById(matchID+"-E2b").innerHTML = data["nomEquipe2"]

    document.getElementById(matchID+"-chrono").innerHTML = Math.floor(parseInt(data["chronometreSec"])/60)+":"+parseInt(data["chronometreSec"])%60
    document.getElementById(matchID+"-periode").innerHTML = data["PeriodeEnCours"]

    if(document.getElementById(matchID+"-ScoreP1E1").innerHTML != data["scoreP1"].split(",")[0] ){
        if (window.Notification && Notification.permission !== "denied" && !silent) {
            Notification.requestPermission((status) => {
            // status is "granted", if accepted by user
                var n = new Notification('BUT !!!', {
                    body: data["nomEquipe1"]+'a marqué un but !',
                    
                })
            })
        }
        document.getElementById(matchID+"-ScoreP1E1").innerHTML = data["scoreP1"].split(",")[0]
    }

    if(document.getElementById(matchID+"-ScoreP2E1").innerHTML != data["scoreP2"].split(",")[0]){
        if (window.Notification && Notification.permission !== "denied" && !silent) {
            Notification.requestPermission((status) => {
            // status is "granted", if accepted by user
                var n = new Notification('BUT !!!', {
                    body: data["nomEquipe1"]+'a marqué un but !',
                    
                })
            })
        }
        document.getElementById(matchID+"-ScoreP2E1").innerHTML = data["scoreP2"].split(",")[0]
    }
    if(document.getElementById(matchID+"-ScoreP3E1").innerHTML != data["scoreP3"].split(",")[0] ){
        if (window.Notification && Notification.permission !== "denied" && !silent) {
            Notification.requestPermission((status) => {
            // status is "granted", if accepted by user
                var n = new Notification('BUT !!!', {
                    body: data["nomEquipe1"]+'a marqué un but !',
                    
                })
            })
        }
        document.getElementById(matchID+"-ScoreP3E1").innerHTML = data["scoreP3"].split(",")[0]
    }
    document.getElementById(matchID+"-ScoreTotE1").innerHTML = parseInt(data["scoreP1"].split(",")[0])+parseInt(data["scoreP2"].split(",")[0])+parseInt(data["scoreP3"].split(",")[0])


    if(document.getElementById(matchID+"-ScoreP1E2").innerHTML != data["scoreP1"].split(",")[1].trim()){
        if (window.Notification && Notification.permission !== "denied" && !silent) {
            Notification.requestPermission((status) => {
            // status is "granted", if accepted by user
                var n = new Notification('BUT !!!', {
                    body: data["nomEquipe2"]+'a marqué un but !',
                    
                })
            })
        }
        document.getElementById(matchID+"-ScoreP1E2").innerHTML = data["scoreP1"].split(",")[1].trim()
    }

    if(document.getElementById(matchID+"-ScoreP2E2").innerHTML != data["scoreP2"].split(",")[1].trim() ){
        if (window.Notification && Notification.permission !== "denied" && !silent) {
            Notification.requestPermission((status) => {
            // status is "granted", if accepted by user
                var n = new Notification('BUT !!!', {
                    body: data["nomEquipe2"]+'a marqué un but !',
                    
                })
            })
        }
        document.getElementById(matchID+"-ScoreP2E2").innerHTML = data["scoreP2"].split(",")[1].trim()
    }

    if(document.getElementById(matchID+"-ScoreP3E2").innerHTML != data["scoreP3"].split(",")[1].trim() ){
        if (window.Notification && Notification.permission !== "denied" && !silent) {
            Notification.requestPermission((status) => {
            // status is "granted", if accepted by user
                var n = new Notification('BUT !!!', {
                    body: data["nomEquipe2"]+'a marqué un but !',
                    
                })
            })
        }
        document.getElementById(matchID+"-ScoreP3E2").innerHTML = data["scoreP3"].split(",")[1].trim()
    }

    document.getElementById(matchID+"-ScoreTotE2").innerHTML = parseInt(data["scoreP1"].split(",")[1].trim()) + parseInt(data["scoreP2"].split(",")[1].trim()) + parseInt(data["scoreP3"].split(",")[1].trim())


    if(document.getElementById(matchID+"-pena").innerHTML != data["penalites"] ){
        if (window.Notification && Notification.permission !== "denied" && !silent) {
            Notification.requestPermission((status) => {
            // status is "granted", if accepted by user
                var n = new Notification('PEANLITÉ !!!', {
                    body: "Nouvelle penalité dans le match "+matchID,
                    
                })
            })
        }
        document.getElementById(matchID+"-pena").innerHTML = data["penalites"]
    }
}



function UpdateAll(silent){

    //--------------------------------------Old Version----------------------------------
    /*
    var xmlhttp = new XMLHttpRequest();
    var Url = "/api/match?query={\"objectType\":\"request\",\"objectRequested\":\"ListeDesMatchs\", \"idObjectRequested\":\"\"}";
    xmlhttp.open("GET", Url);
    xmlhttp.send();
    xmlhttp.onload=function(){
        json=JSON.parse(xmlhttp.responseText);
        console.log(JSON.stringify(json));
        json.matchIDs.forEach(mID => queryMatch(mID))

        if(json["matchIDs"].length<5){document.getElementById("match5").hidden = true}
        if(json["matchIDs"].length<4){document.getElementById("match4").hidden = true}
        if(json["matchIDs"].length<3){document.getElementById("match3").hidden = true}
        if(json["matchIDs"].length<2){document.getElementById("match2").hidden = true}
      };*/

    var Url = "/api/match?query={\"objectType\":\"request\",\"objectRequested\":\"ListeDesMatchs\", \"idObjectRequested\":\"\"}";
    fetch(Url).then(function(response) {
        response.text().then(function(text) {
            json=JSON.parse(text);
            if(json != null){
                //document.getElementById("pulse-button").style.box-shadow= "blue";
                document.getElementById("pulse-button").style.backgroundImage = "url('happy.png')";
                console.log(JSON.stringify(json));
                json.matchIDs.forEach(mID => queryMatch(mID,silent))

                if(json["matchIDs"].length<5){document.getElementById("match5").hidden = true}
                if(json["matchIDs"].length<4){document.getElementById("match4").hidden = true}
                if(json["matchIDs"].length<3){document.getElementById("match3").hidden = true}
                if(json["matchIDs"].length<2){document.getElementById("match2").hidden = true}
            }else{
                document.getElementById("pulse-button").style.backgroundImage = "url('sad.png')";
            }
        });
    });



}



function queryMatch(matchID,silent){

    
        //--------------------------------------Old Version----------------------------------
        /*
        var xmlhttp = new XMLHttpRequest();
        var Url = "/api/match?query={\"objectType\":\"request\",\"objectRequested\":\"Match\", \"idObjectRequested\":\""+matchID+"\"}";
        xmlhttp.open("GET", Url);
        xmlhttp.send();
        xmlhttp.onload=function(){
            json=JSON.parse(xmlhttp.responseText);
            console.log(JSON.stringify(json));
            updateMatch(matchID, json["match"])

        };
        }, 1000);*/

        var Url = "/api/match?query={\"objectType\":\"request\",\"objectRequested\":\"Match\", \"idObjectRequested\":\""+matchID+"\"}";
        fetch(Url).then(function(response) {
            response.text().then(function(text) {
                json=JSON.parse(text);
                if(json != null){
                    //document.getElementById("pulse-button").style.box-shadow= "blue";
                    document.getElementById("pulse-button").style.backgroundColor = "#3de868";
                    document.getElementById("pulse-button").style.backgroundImage = "url('./happy.png')";
                    updateMatch(matchID, json["match"],silent);
                }else{

                }

            });

        }).then(function(response){

        }).catch(function(error){
            document.getElementById("pulse-button").style.backgroundImage = "url('./sad.png')";
            //document.getElementById("pulse-button").style.backgroundColor = "red";

        });
    



}


// DOM handling
function remplirTableParis(){
    $("#myTable tbody tr").remove();
    let lineNo = 1;
    presetsArr = JSON.parse(localStorage.getItem('paris')) || [];
    for (const object in presetsArr) {
        markup = "<tr><th scope='row'>"
            + lineNo + "</th><td>"+presetsArr[object].matchID+"</td><td>"+presetsArr[object].Equipe+"</td><td>"+presetsArr[object].montant+"</td></tr>";
        tableBody = $("#myTable tbody");
        tableBody.append(markup);
        lineNo++;
    }

}

remplirTableParis();

// XMLHTTP + status check
function pariCallServer(matchIdPari,equipe,montant){
    var result ;
    var xmlhttp = new XMLHttpRequest();
    var Url = "/api/bet?query={%22objectType%22:%22betUpdate%22,%22Bet%22:{%22matchID%22:%22"+matchIdPari+"%22,%22miseSur%22:"+equipe+",%22sommeMisee%22:"+montant+",}}";
    xmlhttp.open("POST", Url);
    xmlhttp.send();
    xmlhttp.onload=function(){
        json=JSON.parse(xmlhttp.responseText);
        result = json['status'];
        if(result == "0"){
            alert("pari est réussit");
        }else{
            alert("pari est échoué");
        }

        return new Promise(resolve => {
            resolve(json['status']
    )
    })
    };

}
// async await for promesse from pariscall server bets call
async function pariStatus(matchIdPari,equipe,montant){
    var result = await pariCallServer(matchIdPari,equipe,montant);
    console.log(result);
}
// serialisation example
function addPariToLocalStorage(formData){
    presetsArr = JSON.parse(localStorage.getItem('paris')) || [];
    presetsArr.push(formData);
    localStorage.setItem("paris", JSON.stringify(presetsArr));
}

$(function () {
    $('#form1').on('submit',function(event) {

        var formData = {
            'matchID' : 1,
            'Equipe'              : $('#selectop option:selected').val(),
            'montant'             : $('input[name=montant]').val(),
        };
        pariStatus(1,formData.Equipe,formData.montant);
        addPariToLocalStorage(formData);
        remplirTableParis();
        event.preventDefault();

    })
});

$(function () {
    $('#form2').on('submit',function(event) {
        var formData = {
            'matchID' : 2,
            'Equipe'              : $('#selectop2 option:selected').val(),
            'montant'             : $('input[name=montant2]').val(),
        };
        pariStatus(2,formData.Equipe,formData.montant);
        addPariToLocalStorage(formData);
        remplirTableParis();
        event.preventDefault();

    })
});


$(function () {
    $('#form3').on('submit',function(event) {
        var formData = {
            'matchID' : 3,
            'Equipe'              : $('#selectop3 option:selected').val(),
            'montant'             : $('input[name=montant3]').val(),
        };
        pariStatus(3,formData.Equipe,formData.montant);
        addPariToLocalStorage(formData);
        remplirTableParis();
        event.preventDefault();

    })
});
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

checkCookies();
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


UpdateAll(true)

setInterval(function(){UpdateAll(false)},120000)