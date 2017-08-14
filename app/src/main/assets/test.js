/**
 * Created by asus on 2017-8-14.
 */
var android;

(function () {
    if (!window.android) {
        console.log("native interface is not inject!");
        return;
    }
    android = window.android;


})();

function getFund() {
    if (!android) {
        return "nothing";
    }
    var fund = android.getFund();

    return fund.toString() + " " + fund.get_id() + " " + fund.getFundName() + " " + fund.getIconUrl();
}

function showText(text) {
    console.log(text)
    window.document.getElementById('output').innerHTML = text;
    return window.document.getElementById('output').innerHTML;
}