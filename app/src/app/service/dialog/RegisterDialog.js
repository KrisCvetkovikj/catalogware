
service.service('RegisterDialog', RegisterDialog);

function RegisterDialog(Modal) {

	var modal = null;

	this.show = function() {
		modal = Modal.create('Register', 'src/views/auth/register.html', 
			'RegisterController', 'registerCtrl');
	}

	this.hide = function() {
		modal && modal.hide();
		modal = null;
	}
}