
controller.controller('LoginController', LoginController);

function LoginController($rootScope, toastr, ErrorUtils, AuthService, LoginDialog, EVENTS) {

	var _this = this;
	this.sending = false;

	this.login = function() {
		if (!this.sending) {
			this.sending = true;			
			AuthService.login(this.credentials).then(loginSuccessCallback, loginFailedCallback);
		}
	}

	var loginSuccessCallback = function(response) {		
		_this.sending = false;
		LoginDialog.hide();
		$rootScope.$broadcast(EVENTS.loginSuccess);
	}

	var loginFailedCallback = function(response) {		
		_this.sending = false;		
		_this.errors = ErrorUtils.getErrorsFromResponse(response.data);				
	}

	this.emailOk = function() {		
		return this.form && this.form.email && ErrorUtils.fieldOk(this.form, this.form.email);
	}

	this.emailError = function() {				
		return this.form && this.form.email && ErrorUtils.fieldError(this.form, this.form.email);
	}

	this.passwordOk = function() {		
		return this.form && this.form.password && ErrorUtils.fieldOk(this.form, this.form.password);
	}

	this.passwordError = function() {
		return this.form && this.form.password && ErrorUtils.fieldError(this.form, this.form.password);
	}
}