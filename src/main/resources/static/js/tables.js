function show(nr) {
    document.getElementById("table1").style.display="none";
    document.getElementById("table2").style.display="none";
    document.getElementById("table3").style.display="none";
    document.getElementById("table"+nr).style.display="block";
}

function myFunction () {
    $(this).toggleClass('buttonClassB');
}

$(document).ready(function(){
    $('#btn-all, #btn-saving, #btn-spending').click(function(){
        $('#btn-all, #btn-saving, #btn-spending').removeClass('active');
        $(this).addClass('active');
    });
});

// Get the container element
var btnContainer = document.getElementById("myDIV");

// Get all buttons with class="btn" inside the container
var btns = btnContainer.getElementsByClassName("btn");

// Loop through the buttons and add the active class to the current/clicked button
for (var i = 0; i < btns.length; i++) {
    btns[i].addEventListener("click", function() {
        var current = document.getElementsByClassName("w3-button w3-black");

        // If there's no active class
        if (current.length > 0) {
            current[0].className = current[0].className.replace("w3-button w3-black", "w3-button w3-white");
        }

        // Add the active class to the current/clicked button
        this.className += "w3-button w3-black";
    });
}

$(document).ready(function() {
    $('#Rank').bind('change', function() {
        var elements = $('div.container').children().hide(); // hide all the elements
        var value = $(this).val();

        if (value.length) { // if somethings' selected
            elements.filter('.' + value).show(); // show the ones we want
        }
    }).trigger('change');
});

var elem = document.getElementById("security_question_1");
elem.onchange = function(){
    var hiddenDiv = document.getElementById("showMe");
    hiddenDiv.style.display = (this.value == "") ? "none":"block";
};

$("#beerStyle").change ( function () {
    var targID  = $(this).val ();
    $("div.style-sub-1").hide ();
    $('#' + targID).show ();
} )

function checkInp()
{
    var x=document.forms["myForm"]["value"].value;
    var y=document.forms["myForm"]["period"].value;
    if (isNaN(x) || isNaN(y))
    {
        alert("Must input numbers");
        return false;
    }
}

function submitPoll(){
    document.getElementById("votebutton").disabled = true;
    setTimeout(function() {
        document.getElementById("votebutton").disabled = false;
    }, 5000);

}

document.getElementById("votebutton").addEventListener("click", submitPoll);


setTimeout(function(){
    var element = document.getElementsByName("submit")[0] ;
    element.disabled = false;
}, 10000);
