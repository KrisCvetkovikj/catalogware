
controller.controller('NavController', NavController);

function NavController($rootScope, LoginDialog, RegisterDialog, AuthUser, AuthService, EVENTS) {

	this.user = AuthUser;

	this.showLoginDialog = function() {
		LoginDialog.show();
	}

	this.showRegisterDialog = function() {
		RegisterDialog.show();
	}

	this.logout = function() {
		AuthService.logout().then(function() {
			$rootScope.$broadcast(EVENTS.logoutSuccess);
		});
	}
}