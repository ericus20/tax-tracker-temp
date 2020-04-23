$(document).ready(main);


function main() {
    "use strict";

    $("#password").on("keypress keyup keydown", function () {
        let pass = $(this).val();
        let strength = checkPassStrength(pass);
        $("#strength_human").text(strength);

        let $progress = $(".progress");
        $progress.show();

        if (strength === "strong") {
            $("#progress-bar").html("").attr('aria-valuenow', 100).width('100%').removeClass("good weak").addClass("strong");
        } else if (strength === "good") {
            $("#progress-bar").html("").attr('aria-valuenow', 50).width('50%').removeClass("strong weak").addClass("good");
        } else if (strength === "weak") {
            $("#progress-bar").html("").attr('aria-valuenow', 25).width('25%').removeClass("strong good").addClass("weak");
        } else {
            $progress.hide();
        }
    });
}


function scorePassword(pass) {
    let score = 0;
    if (!pass)
        return score;

    // award every unique letter until 5 repetitions
    let letters = {};
    for (let i = 0; i < pass.length; i++) {
        letters[pass[i]] = (letters[pass[i]] || 0) + 1;
        score += 5.0 / letters[pass[i]];
    }

    // bonus points for mixing it up
    let letiations = {
        digits: /\d/.test(pass),
        lower: /[a-z]/.test(pass),
        upper: /[A-Z]/.test(pass),
        nonWords: /\W/.test(pass),
    };

    let letiationCount = 0;
    for (let check in letiations) {
        letiationCount += (letiations[check] === true) ? 1 : 0;
    }
    score += (letiationCount - 1) * 10;

    return parseInt(score);
}

function checkPassStrength(pass) {
    let score = scorePassword(pass);
    if (score > 80)
        return "strong";
    if (score > 60)
        return "good";
    if (score >= 1)
        return "weak";

    return "";
}
