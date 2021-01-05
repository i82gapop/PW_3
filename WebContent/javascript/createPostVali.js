const title = document.getElementById("_title")
const body = document.getElementById("_body")
const war = document.getElementById("warnings")
const form = document.getElementById("form")

form.addEventListener("submit", e=>{
	let warnings = ""
	let join = false;
	war.innerHTML = ""
	if(title.value.length < 3){
		warnings += `The title is not valid <br>`
		join = true
			
	}
	
	if(body.value.length < 3){
		warnings += `The body is not valid <br>`
		join = true
	}
	
	if(join){
		e.preventDefault();
		war.innerHTML = warnings
	}
	

	
})