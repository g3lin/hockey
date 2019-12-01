function updateMatch(matchID, data){
    console.log(matchID+"-E1")
    document.getElementById(matchID+"-E1").innerHTML = data["nomEquipe1"]
    document.getElementById(matchID+"-E2").innerHTML = data["nomEquipe2"]
    document.getElementById(matchID+"-E1b").innerHTML = data["nomEquipe1"]
    document.getElementById(matchID+"-E2b").innerHTML = data["nomEquipe2"]

    document.getElementById(matchID+"-chrono").innerHTML = Math.floor(parseInt(data["chronometreSec"])/60)+":"+parseInt(data["chronometreSec"])%60
    document.getElementById(matchID+"-periode").innerHTML = data["PeriodeEnCours"]

    document.getElementById(matchID+"-ScoreP1E1").innerHTML = data["scoreP1"].split(",")[0]
    document.getElementById(matchID+"-ScoreP2E1").innerHTML = data["scoreP2"].split(",")[0]
    document.getElementById(matchID+"-ScoreP3E1").innerHTML = data["scoreP3"].split(",")[0]
    document.getElementById(matchID+"-ScoreTotE1").innerHTML = parseInt(data["scoreP1"].split(",")[0])+parseInt(data["scoreP2"].split(",")[0])+parseInt(data["scoreP3"].split(",")[0])

    document.getElementById(matchID+"-ScoreP1E2").innerHTML = data["scoreP1"].split(",")[1].trim()
    document.getElementById(matchID+"-ScoreP2E2").innerHTML = data["scoreP2"].split(",")[1].trim()
    document.getElementById(matchID+"-ScoreP3E2").innerHTML = data["scoreP3"].split(",")[1].trim()
    document.getElementById(matchID+"-ScoreTotE2").innerHTML = parseInt(data["scoreP1"].split(",")[1].trim()) + parseInt(data["scoreP2"].split(",")[1].trim()) + parseInt(data["scoreP3"].split(",")[1].trim())

    document.getElementById(matchID+"-pena").innerHTML = data["penalites"]
}



function UpdateAll(){

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
                json.matchIDs.forEach(mID => queryMatch(mID))

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



function queryMatch(matchID){

    setInterval(function(){
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
                    document.getElementById("pulse-button").style.backgroundColor = "green";
                    document.getElementById("pulse-button").style.backgroundImage = "url('./happy.png')";
                    updateMatch(matchID, json["match"]);
                }else{

                }

            });

        }).then(function(response){

        }).catch(function(error){
            document.getElementById("pulse-button").style.backgroundImage = "url('./sad.png')";
            document.getElementById("pulse-button").style.backgroundColor = "red";

        });
    }, 1000);



}





function processBet(matchIdBet){
    alert("methode appelée");
    var formData = {
        'Equipe'              : $('input[name=Equipe]').val(),
        'montant'             : $('input[name=montant]').val(),
    };

    alert(formData.Equipe + " , " + formData.montant);
    /*
    // process the form
    $.ajax({
        type        : 'POST', // define the type of HTTP verb we want to use (POST for our form)
        url         : 'process.php', // the url where we want to POST
        data        : formData, // our data object
        dataType    : 'json', // what type of data do we expect back from the server
                    encode          : true
    })
        // using the done promise callback
        .done(function(data) {

            // log data to the console so we can see
            console.log(data);

            // here we will handle errors and validation messages
        });

    // stop the form from submitting the normal way and refreshing the page*/
    event.preventDefault();

}




UpdateAll()