
controller.controller('RegisterController', RegisterController);

function RegisterController($rootScope, toastr, ErrorUtils, AuthService, RegisterDialog, EVENTS) {

	var _this = this;
	this.sending = false;
	this.errors = [];

	this.register = function() {
		if (!this.sending) {
			this.sending = true;
			AuthService.register(this.credentials).then(registerSuccessCallback, registerFailedCallback);
		}
	}

	var registerSuccessCallback = function() {
		_this.sending = false;
		RegisterDialog.hide();
		$rootScope.$broadcast(EVENTS.registerSuccess);
	}

	var registerFailedCallback = function(response) {
		_this.sending = false;
		_this.errors = ErrorUtils.getErrorsFromResponse(response.data);
	}

	this.nameError = function() {		
		return this.form && this.form.name && ErrorUtils.fieldError(this.form, this.form.name);
	}

	this.nameOk = function() {
		return this.form && this.form.name && ErrorUtils.fieldOk(this.form, this.form.name);
	}

	this.emailError = function() {		
		return this.form && this.form.email && ErrorUtils.fieldError(this.form, this.form.email);
	}

	this.emailOk = function() {
		return this.form && this.form.email && ErrorUtils.fieldOk(this.form, this.form.email);
	}

	this.passwordError = function() {		
		return this.form && this.form.password && ErrorUtils.fieldError(this.form, this.form.password);
	}

	this.passwordOk = function() {
		return this.form && this.form.password && ErrorUtils.fieldOk(this.form, this.form.password);
	}

	this.confirmPassError = function() {		
		return this.form && this.form.confirmPass && ErrorUtils.fieldError(this.form, this.form.confirmPass);
	}

	this.confirmPassOk = function() {
		return this.form && this.form.confirmPass && ErrorUtils.fieldOk(this.form, this.form.confirmPass);
	}
}