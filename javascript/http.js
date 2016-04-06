/*
	version: 1.0.0
	author: Alexander Elias
	description: ajax form submit
*/

/*
	EXAMPLE FORM

	<form
		action="/submit"
		method="get | post | put | delete"
		enctype=" text/plain | multipart/form-data | application/x-www-form-urlencoded"
	>
		<input type="text" name="data" placeholder="Data Input">
		<input type="submit" value="Submit"/>
	</form>

	<div class="response"></div>
*/


(function () {
	'use strict';

	overriedFormSubmit();

	function overriedFormSubmit () {
		var length = document.forms.length;
		var form = null;
		var i = 0;

		for (i; i < length; i++) {
			document.forms[i].onsubmit = function (event) {
				event.preventDefault();
				dynamicResponse(this, this.action, this.method, this.enctype);
				return false;
			}
		}
	}

	function dynamicResponse(form, action, method, enctype) {
		var responseBox = document.querySelector('.response');
		var xhr = new XMLHttpRequest();

		method = method.toUpperCase();

		form.method = method;
		form.enctype = (enctype) ? enctype : 'text/plain';

		var queryData = (method === 'GET') ? getFormData(form.elements) : '';
		var payloadData = (method === 'POST') ? new FormData(form) : null;

		if (method === 'GET') action = action + '?' + queryData;

		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4 && xhr.status == 200) {
				responseBox.innerText = xhr.responseText;
			}
		};

		xhr.open(method, action, true);
		xhr.send(payloadData);
	}

	function getFormData (inputs) {
		var length = inputs.length;
		var data = '';
		var i = 0;

		for (i; i < length; i++) {
			if (inputs[i].type !== 'button' && inputs[i].type !== 'submit') {
				if (i !== 0) data = data + '&';
				data = data + '' + inputs[i].name + '=' + inputs[i].value;
			}
		}

		return data;
	}
})();
