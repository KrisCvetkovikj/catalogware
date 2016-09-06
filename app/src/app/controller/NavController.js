
controller.controller('NavController', NavController);

function NavController($rootScope, LoginDialog, RegisterDialog, ProductDialog, AuthUser, AuthService, EVENTS) {

	this.user = AuthUser;

	this.isAdmin = function() {
		return this.user.isAdmin();
	}

	this.isAuthenticated = function() {
		return this.user.isAuthenticated;
	}

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

	this.showCreateProductDialog = function() {		
		ProductDialog.showCreateDialog();
	}
}