function updateMatch(matchID, data){
    document.getElementById(matchID+"-E1").innerHTML = "Sherbrooke"
    document.getElementById(matchID+"-E2").innerHTML = "Sherbrooke"
    document.getElementById(matchID+"-E1b").innerHTML = "Sherbrooke"
    document.getElementById("1-E2b").innerHTML = "Sherbrooke"

    document.getElementById(matchID+"-chrono").innerHTML = "00:01"

    document.getElementById(matchID+"-ScoreP1E1").innerHTML = "1"
    document.getElementById(matchID+"-ScoreP2E1").innerHTML = "1"
    document.getElementById(matchID+"-ScoreP3E1").innerHTML = "1"
    document.getElementById(matchID+"-ScoreTotE1").innerHTML = "3"

    document.getElementById(matchID+"-ScoreP1E2").innerHTML = "1"
    document.getElementById(matchID+"-ScoreP2E2").innerHTML = "1"
    document.getElementById(matchID+"-ScoreP3E2").innerHTML = "1"
    document.getElementById(matchID+"-ScoreTotE2").innerHTML = "3"

    document.getElementById("1-pena").innerHTML = "Carton jaune"
}

function updateAll(){

    var xmlhttp = new XMLHttpRequest();
    var Url = "/api/ListeDesMatchs";
    xmlhttp.open("GET", Url);
    xmlhttp.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
    // xmlhttp.send(JSON.stringify({"objectType":"request","objectRequested":"ListeDesMatchs", "idObjectRequested":""}));
    xmlhttp.send()


    updateMatch(1)    
}

function processBet(){

}




updateAll()