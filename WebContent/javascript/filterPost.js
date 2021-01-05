/**
 * 
 */

function Type(){
    document.getElementById('types').style.display="block";
    document.getElementById('authors').style.display="none";
    document.getElementById('dates').style.display="none";
}

function Author(){
    document.getElementById('types').style.display="none";
    document.getElementById('authors').style.display="block";
    document.getElementById('dates').style.display="none";
}


function Publication(){
    document.getElementById('types').style.display="none";
    document.getElementById('authors').style.display="none";
    document.getElementById('dates').style.display="block";
}

filterSelection("all")
function filterSelection(c) {
  var x, i;
  x = document.getElementsByClassName("postfilter");
  if (c == "all") c = "";
  for (i = 0; i < x.length; i++) {
    Hide(x[i], "show");
    if (x[i].className.indexOf(c) > -1) Show(x[i], "show");
  }
}

function Show(element, name) {
  var i, arr1, arr2;
  arr1 = element.className.split(" ");
  arr2 = name.split(" ");
  for (i = 0; i < arr2.length; i++) {
    if (arr1.indexOf(arr2[i]) == -1) {element.className += " " + arr2[i];}
  }
}

function Hide(element, name) {
  var i, arr1, arr2;
  arr1 = element.className.split(" ");
  arr2 = name.split(" ");
  for (i = 0; i < arr2.length; i++) {
    while (arr1.indexOf(arr2[i]) > -1) {
      arr1.splice(arr1.indexOf(arr2[i]), 1);     
    }
  }
  element.className = arr1.join(" ");
}
					




