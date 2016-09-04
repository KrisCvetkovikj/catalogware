
service.service('LoginDialog', LoginDialog);

function LoginDialog(Modal) {
	var modal = null;

	this.show = function() {
		modal = Modal.create('Login', 'src/templates/auth/login.html', 'LoginController', 'loginCtrl');
	}

	this.hide = function() {
		modal && modal.hide();
		modal = null;
	}
}