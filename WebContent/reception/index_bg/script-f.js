
const button = document.querySelector("button");
const word = document.querySelector("h1 span");


button.addEventListener(
"click",
function (e) {
  e.preventDefault;

  word.classList.remove("animating");

  void word.offsetWidth;

  word.classList.add("animating");
},
false);