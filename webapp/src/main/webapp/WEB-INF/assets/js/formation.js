var positions = [   "gk",
                    "lb", "lcb", "cb", "rcb", "rb",
                    "lm", "lcm", "cdm", "cam", "rcm", "rm",
                    "lw", "lf", "st", "rf", "rw"];
var subPos = ["sub1", "sub2", "sub3", "sub4", "sub5", "sub6", "sub7"];
var players = [];
var substitutes = [];

jQuery(document).ready(function(){
    load();
});

function load() {
    var i;
    getInitialValues();
    /*
    jQuery("#cap option").each(function()
    {
        jQuery(this).toggleOption(false);
    });
    jQuery("#fk option").each(function()
    {
        jQuery(this).toggleOption(false);
    });
    jQuery("#pen option").each(function()
    {
        jQuery(this).toggleOption(false);
    });
    */
    for(i=0; i<positions.length; i++){
        var player = players[i];
        jQuery('#sub1').find("option[value='"+player.toString()+"']").toggleOption(false);
        jQuery('#sub2').find("option[value='"+player.toString()+"']").toggleOption(false);
        jQuery('#sub3').find("option[value='"+player.toString()+"']").toggleOption(false);
        jQuery('#sub4').find("option[value='"+player.toString()+"']").toggleOption(false);
        jQuery('#sub5').find("option[value='"+player.toString()+"']").toggleOption(false);
        jQuery('#sub6').find("option[value='"+player.toString()+"']").toggleOption(false);
        jQuery('#sub7').find("option[value='"+player.toString()+"']").toggleOption(false);
        /*
        jQuery('#cap').find("option[value='"+player.toString()+"']").toggleOption(true);
        jQuery('#fk').find("option[value='"+player.toString()+"']").toggleOption(true);
        jQuery('#pen').find("option[value='"+player.toString()+"']").toggleOption(true);
        */
    }

    document.getElementById("pressure").value = document.getElementById("pressure").options[0].value;
    document.getElementById("attitude").value = document.getElementById("attitude").options[0].value;
    document.getElementById("formation").value = document.getElementById("formation").options[0].value;
    document.getElementById("cap").value = document.getElementById("cap").options[0].value;
    document.getElementById("fk").value = document.getElementById("fk").options[0].value;
    document.getElementById("pen").value = document.getElementById("pen").options[0].value;

    showCurrentFormation(document.getElementById("formation").value);
    var error = document.getElementById("error");
    error.style.display = "none";


    if(window.location.href.includes("error=true")){
        error.style.display = "";
    } else if (window.location.href.includes("error=false")){
        var popup = jQuery('#staticModal');
        popup.modal('show');
    }
}

function getInitialValues() {
    var i;
    for(i=0; i<positions.length; i++) {
        players[i] = document.getElementById(positions[i]).value;
    }
    for(i=0; i<subPos.length; i++) {
        substitutes[i] = document.getElementById(subPos[i]).value;
    }
}

function showCurrentFormation(formation){

    if(formation.toString() === "343"){
        f343();
    }
    else if(formation.toString() === "352"){
        f352();
    }
    else if(formation.toString() === "433"){
        f433();
    }
    else if(formation.toString() === "442") {
        f442();
    }
    else if(formation.toString() === "451"){
        f451();
    }
    else if(formation.toString() === "532"){
        f532();
    }
    else if(formation.toString() === "523"){
        f523();
    }
    else if(formation.toString() === "541"){
        f541();
    }
}

function f343(){
    document.getElementById("cb").style.display = "";
    document.getElementById("lm").style.display = "";
    document.getElementById("rm").style.display = "";
    document.getElementById("lw").style.display = "";
    document.getElementById("st").style.display = "";
    document.getElementById("rw").style.display = "";

    document.getElementById("cam").style.display = "none";
    document.getElementById("cam").value = 0;
    document.getElementById("lb").style.display = "none";
    document.getElementById("lb").value = 0;
    document.getElementById("rb").style.display = "none";
    document.getElementById("rb").value = 0;
    document.getElementById("lf").style.display = "none";
    document.getElementById("lf").value = 0;
    document.getElementById("cdm").style.display = "none";
    document.getElementById("cdm").value = 0;
    document.getElementById("rf").style.display = "none";
    document.getElementById("rf").value = 0;
}

function f352(){
    document.getElementById("cb").style.display = "";
    document.getElementById("lm").style.display = "";
    document.getElementById("rm").style.display = "";
    document.getElementById("cam").style.display = "";
    document.getElementById("lf").style.display = "";
    document.getElementById("rf").style.display = "";

    document.getElementById("cdm").style.display = "none";
    document.getElementById("cdm").value = 0;
    document.getElementById("lb").style.display = "none";
    document.getElementById("lb").value = 0;
    document.getElementById("rb").style.display = "none";
    document.getElementById("rb").value = 0;
    document.getElementById("lw").style.display = "none";
    document.getElementById("lw").value = 0;
    document.getElementById("st").style.display = "none";
    document.getElementById("st").value = 0;
    document.getElementById("rw").style.display = "none";
    document.getElementById("rw").value = 0;
}

function f433() {
    document.getElementById("lb").style.display = "";
    document.getElementById("rb").style.display = "";
    document.getElementById("cdm").style.display = "";
    document.getElementById("lw").style.display = "";
    document.getElementById("st").style.display = "";
    document.getElementById("rw").style.display = "";

    document.getElementById("cam").style.display = "none";
    document.getElementById("cam").value = 0;
    document.getElementById("lm").style.display = "none";
    document.getElementById("lm").value = 0;
    document.getElementById("rm").style.display = "none";
    document.getElementById("rm").value = 0;
    document.getElementById("lf").style.display = "none";
    document.getElementById("lf").value = 0;
    document.getElementById("cb").style.display = "none";
    document.getElementById("cb").value = 0;
    document.getElementById("rf").style.display = "none";
    document.getElementById("rf").value = 0;
}

function f442() {
    document.getElementById("lb").style.display = "";
    document.getElementById("rb").style.display = "";
    document.getElementById("lm").style.display = "";
    document.getElementById("rm").style.display = "";
    document.getElementById("lf").style.display = "";
    document.getElementById("rf").style.display = "";

    document.getElementById("cb").style.display = "none";
    document.getElementById("cb").value = 0;
    document.getElementById("cdm").style.display = "none";
    document.getElementById("cdm").value = 0;
    document.getElementById("cam").style.display = "none";
    document.getElementById("cam").value = 0;
    document.getElementById("lw").style.display = "none";
    document.getElementById("lw").value = 0;
    document.getElementById("st").style.display = "none";
    document.getElementById("st").value = 0;
    document.getElementById("rw").style.display = "none";
    document.getElementById("rw").value = 0;
}

function f451() {
    document.getElementById("lb").style.display = "";
    document.getElementById("rb").style.display = "";
    document.getElementById("lm").style.display = "";
    document.getElementById("rm").style.display = "";
    document.getElementById("cam").style.display = "";
    document.getElementById("st").style.display = "";

    document.getElementById("cb").style.display = "none";
    document.getElementById("cb").value = 0;
    document.getElementById("cdm").style.display = "none";
    document.getElementById("cdm").value = 0;
    document.getElementById("lw").style.display = "none";
    document.getElementById("lw").value = 0;
    document.getElementById("lf").style.display = "none";
    document.getElementById("lf").value = 0;
    document.getElementById("rw").style.display = "none";
    document.getElementById("rw").value = 0;
    document.getElementById("rf").style.display = "none";
    document.getElementById("rf").value = 0;
}

function f532() {
    document.getElementById("lb").style.display = "";
    document.getElementById("rb").style.display = "";
    document.getElementById("cb").style.display = "";
    document.getElementById("cam").style.display = "";
    document.getElementById("lf").style.display = "";
    document.getElementById("rf").style.display = "";

    document.getElementById("cdm").style.display = "none";
    document.getElementById("cdm").value = 0;
    document.getElementById("lm").style.display = "none";
    document.getElementById("lm").value = 0;
    document.getElementById("rm").style.display = "none";
    document.getElementById("rm").value = 0;
    document.getElementById("lw").style.display = "none";
    document.getElementById("lw").value = 0;
    document.getElementById("st").style.display = "none";
    document.getElementById("st").value = 0;
    document.getElementById("rw").style.display = "none";
    document.getElementById("rw").value = 0;
}

function f523() {
    document.getElementById("lb").style.display = "";
    document.getElementById("rb").style.display = "";
    document.getElementById("cb").style.display = "";
    document.getElementById("lw").style.display = "";
    document.getElementById("st").style.display = "";
    document.getElementById("rw").style.display = "";

    document.getElementById("cam").style.display = "none";
    document.getElementById("cam").value = 0;
    document.getElementById("lm").style.display = "none";
    document.getElementById("lm").value = 0;
    document.getElementById("rm").style.display = "none";
    document.getElementById("rm").value = 0;
    document.getElementById("lf").style.display = "none";
    document.getElementById("lf").value = 0;
    document.getElementById("cdm").style.display = "none";
    document.getElementById("cdm").value = 0;
    document.getElementById("rf").style.display = "none";
    document.getElementById("rf").value = 0;
}

function f541() {
    document.getElementById("lb").style.display = "";
    document.getElementById("rb").style.display = "";
    document.getElementById("cb").style.display = "";
    document.getElementById("lm").style.display = "";
    document.getElementById("rm").style.display = "";
    document.getElementById("st").style.display = "";

    document.getElementById("cam").style.display = "none";
    document.getElementById("cam").value = 0;
    document.getElementById("lw").style.display = "none";
    document.getElementById("lw").value = 0;
    document.getElementById("rw").style.display = "none";
    document.getElementById("rw").value = 0;
    document.getElementById("lf").style.display = "none";
    document.getElementById("lf").value = 0;
    document.getElementById("cdm").style.display = "none";
    document.getElementById("cdm").value = 0;
    document.getElementById("rf").style.display = "none";
    document.getElementById("rf").value = 0;
}

function update(position, player) {
    var playerIndex = positions.indexOf(position);
    var i;
    if(playerIndex !== -1) {
        var oldPlayer = players[playerIndex];
        for (i = 0; i < players.length; i++) {
            if (i !== playerIndex && players[i] === player) {//Cambio titular por titular
                document.getElementById(positions[i]).value = players[playerIndex];
                players[i] = players[playerIndex];
                players[playerIndex] = player;
                break;
            }
        }
        if (i >= players.length) {//Cambio titular por suplente
            changeSubs(player, oldPlayer);
            for (i = 0; i < substitutes.length; i++) {
                if (substitutes[i] === player) {
                    document.getElementById(subPos[i]).value = players[playerIndex];
                    if (players[playerIndex] === document.getElementById("cap").value) {
                        document.getElementById("cap").value = player;
                    }
                    if (players[playerIndex] === document.getElementById("fk").value) {
                        document.getElementById("fk").value = player;
                    }
                    if (players[playerIndex] === document.getElementById("pen").value) {
                        document.getElementById("pen").value = player;
                    }
                    substitutes[i] = players[playerIndex];
                    players[playerIndex] = player;
                    break;
                }
            }
            if (i >= substitutes.length) {//Cambio titular por reserva
                if (players[playerIndex] === document.getElementById("cap").value) {
                    document.getElementById("cap").value = player;
                }
                if (players[playerIndex] === document.getElementById("fk").value) {
                    document.getElementById("fk").value = player;
                }
                if (players[playerIndex] === document.getElementById("pen").value) {
                    document.getElementById("pen").value = player;
                }
                players[playerIndex] = player;
            }
            //changeRoles(player, oldPlayer);
        }
    }
    else {//Cambio de suplente con suplente
        playerIndex = subPos.indexOf(position);
            for (i = 0; i < substitutes.length; i++) {
                if (substitutes[i] === player) {
                    document.getElementById(subPos[i]).value = substitutes[playerIndex];
                    substitutes[i] = substitutes[playerIndex];
                    substitutes[playerIndex] = player;
                    break;
                }
            }
            if (i >= substitutes.length) {//Cambio suplente por reserva
                substitutes[playerIndex] = player;
            }

    }
}

function changeRoles(player, oldPlayer) {
    jQuery('#cap').find("option[value='"+oldPlayer.toString()+"']").toggleOption(false);
    jQuery('#cap').find("option[value='"+player.toString()+"']").toggleOption(true);
    jQuery('#fk').find("option[value='"+oldPlayer.toString()+"']").toggleOption(false);
    jQuery('#fk').find("option[value='"+player.toString()+"']").toggleOption(true);
    jQuery('#pen').find("option[value='"+oldPlayer.toString()+"']").toggleOption(false);
    jQuery('#pen').find("option[value='"+player.toString()+"']").toggleOption(true);
}

function changeSubs(player, oldPlayer){
    jQuery('#sub1').find("option[value='"+oldPlayer.toString()+"']").toggleOption(true);
    jQuery('#sub1').find("option[value='"+player.toString()+"']").toggleOption(false);
    jQuery('#sub2').find("option[value='"+oldPlayer.toString()+"']").toggleOption(true);
    jQuery('#sub2').find("option[value='"+player.toString()+"']").toggleOption(false);
    jQuery('#sub3').find("option[value='"+oldPlayer.toString()+"']").toggleOption(true);
    jQuery('#sub3').find("option[value='"+player.toString()+"']").toggleOption(false);
    jQuery('#sub4').find("option[value='"+oldPlayer.toString()+"']").toggleOption(true);
    jQuery('#sub4').find("option[value='"+player.toString()+"']").toggleOption(false);
    jQuery('#sub5').find("option[value='"+oldPlayer.toString()+"']").toggleOption(true);
    jQuery('#sub5').find("option[value='"+player.toString()+"']").toggleOption(false);
    jQuery('#sub6').find("option[value='"+oldPlayer.toString()+"']").toggleOption(true);
    jQuery('#sub6').find("option[value='"+player.toString()+"']").toggleOption(false);
    jQuery('#sub7').find("option[value='"+oldPlayer.toString()+"']").toggleOption(true);
    jQuery('#sub7').find("option[value='"+player.toString()+"']").toggleOption(false);
}

jQuery.fn.toggleOption = function( show ) {
    jQuery( this ).toggle( show );
    if( show ) {
        if( jQuery( this ).parent( 'span.toggleOption' ).length )
            jQuery( this ).unwrap( );
    } else {
        if( jQuery( this ).parent( 'span.toggleOption' ).length === 0 )
            jQuery( this ).wrap( '<span class="toggleOption" style="display: none;" />' );
    }
};

function changeFormation(formation) {
    showCurrentFormation(formation);
    getInitialValues();
    var captain = document.getElementById("cap");
    var freekickTaker = document.getElementById("fk");
    var penaltyTaker = document.getElementById("pen");
    var capFound, fkFound, penFound, i;

    for(i=0; i<players.length; i++){
        var p = players[i];
        if(p === captain.value)
            capFound = true;
        if(p === freekickTaker.value)
            fkFound = true;
        if(p === penaltyTaker.value)
            fkFound = true;
    }
    if(!capFound)
        captain.value = 0;
    if(!fkFound)
        freekickTaker.value = 0;
    if(!penFound)
        penaltyTaker.value = 0;
}