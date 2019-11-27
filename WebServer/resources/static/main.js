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
      };
}


function queryMatch(matchID){
    var xmlhttp = new XMLHttpRequest();
    var Url = "/api/match?query={\"objectType\":\"request\",\"objectRequested\":\"Match\", \"idObjectRequested\":\""+matchID+"\"}";
    xmlhttp.open("GET", Url);
    xmlhttp.send();
    xmlhttp.onload=function(){
        json=JSON.parse(xmlhttp.responseText);
        console.log(JSON.stringify(json));
        updateMatch(matchID, json["match"])
        
      };

}





function processBet(){

}




UpdateAll()