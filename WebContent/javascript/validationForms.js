const form = document.getElementById('form');
const inputs = document.querySelectorAll('#form input');

const expressions = {
	firstname: /^[a-zA-ZÀ-ÿ\_\-\s]{1,20}$/, // letters, numbers and - _
	surname: /^[a-zA-ZÀ-ÿ\s]{1,40}$/, // 
	password: /^.{4,12}$/, // 4 a 12 digitos.
	email: /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/,
	birthday: /^\w/, 
	interests: 	/^[a-zA-Z\,\s]{1,50}$/

}

const fields = {
	firstname: false,
	surname: false,
	password: false,
	email: false,
	birthday: false,
	interests: false
}

const validateform = (e) => {
	switch (e.target.name) {
		case "firstname":
			validateField(expressions.firstname, e.target, 'firstname');
		break;
		case "surname":
			validateField(expressions.surname, e.target, 'surname');
		break;
		case "password":
			validateField(expressions.password, e.target, 'password');
			validatePassword2();
		break;
		case "password2":
			validatePassword2();
		break;
		case "email":
			validateField(expressions.email, e.target, 'email');
		break;
		case "birthday":
			validateField(expressions.birthday, e.target, 'birthday');
		break;
		case "interests":
			validateField(expressions.interests, e.target, 'interests');
		break;
	}
}

const validateField = (expression, input, field) => {
	if(expression.test(input.value)){
		document.getElementById(`group__${field}`).classList.remove('form__group-incorrect');
		document.getElementById(`group__${field}`).classList.add('form__group-correct');
		document.querySelector(`#group__${field} i`).classList.add('fa-check-circle');
		document.querySelector(`#group__${field} i`).classList.remove('fa-times-circle');
		document.querySelector(`#group__${field} .form__input-error`).classList.remove('form__input-error-active');
		fields[field] = true;
	} else {
		document.getElementById(`group__${field}`).classList.add('form__group-incorrect');
		document.getElementById(`group__${field}`).classList.remove('form__group-correct');
		document.querySelector(`#group__${field} i`).classList.add('fa-times-circle');
		document.querySelector(`#group__${field} i`).classList.remove('fa-check-circle');
		document.querySelector(`#group__${field} .form__input-error`).classList.add('form__input-error-active');
		fields[field] = false;
	}
}

const validatePassword2 = () => {
	const inputPassword1 = document.getElementById('password');
	const inputPassword2 = document.getElementById('password2');

	if(inputPassword1.value !== inputPassword2.value){
		document.getElementById(`group__password2`).classList.add('form__group-incorrect');
		document.getElementById(`group__password2`).classList.remove('form__group-correct');
		document.querySelector(`#group__password2 i`).classList.add('fa-times-circle');
		document.querySelector(`#group__password2 i`).classList.remove('fa-check-circle');
		document.querySelector(`#group__password2 .form__input-error`).classList.add('form__input-error-active');
		fields['password'] = false;
	} else {
		document.getElementById(`group__password2`).classList.remove('form__group-incorrect');
		document.getElementById(`group__password2`).classList.add('form__group-correct');
		document.querySelector(`#group__password2 i`).classList.remove('fa-times-circle');
		document.querySelector(`#group__password2 i`).classList.add('fa-check-circle');
		document.querySelector(`#group__password2 .form__input-error`).classList.remove('form__input-error-active');
		fields['password'] = true;
	}
}

inputs.forEach((input) => {
	input.addEventListener('keyup', validateform);
	input.addEventListener('blur', validateform);
});

form.addEventListener('submit', (e)=> {
	

	


	const terms = document.getElementById('terms');
	if(fields.firstname && fields.surname && fields.password && fields.email && fields.birthday && fields.interests && terms.checked ){


	} else {
		e.preventDefault();
		document.getElementById('form__message').classList.add('form__message-active');
	}
});