var lowCost, mediumCost, highCost;
var lowValue, mediumValue, highValue;
var lowInput, mediumInput, highInput;
var totalContainer;
jQuery(document).ready(function(){
    lowCost = document.getElementById("lowCost").innerHTML;
    lowValue = document.getElementById("lowInput").value;

    mediumCost = document.getElementById("mediumCost").innerHTML;
    mediumValue = document.getElementById("mediumInput").value;

    highCost = document.getElementById("highCost").innerHTML;
    highValue = document.getElementById("highInput").value;

    lowInput = document.getElementById("lowInput");
    mediumInput = document.getElementById("mediumInput");
    highInput = document.getElementById("highInput");

    lowInput.addEventListener("input", updateInput);
    mediumInput.addEventListener("input", updateInput);
    highInput.addEventListener("input", updateInput);


    totalContainer = document.getElementById("total");

    jQuery("#sForm").submit(function(e){
        var url = "/upgradeStadium";
        jQuery.ajax({
            type: "POST",
            url: url,
            data: jQuery("#sForm").serialize(),
            dataType: "json",
            success: function(result){
                var popup = jQuery('#staticModal');
                var button = jQuery('#returnButton');
                var content = jQuery('#modalContent');
                if(!result){
                    content.html(jQuery('#failMessage').html());
                    button.html(jQuery('#retry').html());
                    button.click(function () {
                        popup.modal('show');
                    });
                } else {
                    content.html(jQuery('#successMessage').html());
                    button.html(jQuery('#confirm').html());
                    button.click(function () {
                        window.location.href = "../finance";
                    });
                }
                popup.modal('show');
            }
        });
        e.preventDefault();
    });
});

function updateInput(ev){
    var input = ev.srcElement;
    var prevValue, cost, container;
    if(input.id === "lowInput"){
        prevValue = lowValue;
        cost = lowCost;
        input = document.getElementById("lowInput");
        //container = document.getElementById("lowTemp");
    } else if( input.id === "mediumInput") {
        prevValue = mediumValue;
        cost = mediumCost;
        input = document.getElementById("mediumInput");
        //container = document.getElementById("mediumTemp");
    } else if( input.id === "highInput"){
        prevValue = highValue;
        cost = highCost;
        input = document.getElementById("highInput");
        //container = document.getElementById("highTemp");
    }
    var deltaAmount = parseInt(input.value) - prevValue;
    //container.innerHTML = cost*deltaAmount + " $";
    updateTotal();
}

function updateTotal() {
    var lowAmount = (parseInt(lowInput.value) - lowValue)*lowCost;
    var mediumAmount = (parseInt(mediumInput.value) - mediumValue)*mediumCost;
    var highAmount = (parseInt(highInput.value) - highValue)*highCost;
    var totalNum = lowAmount + mediumAmount + highAmount;
    totalContainer.innerHTML = '$ ' + totalNum.toString();
}