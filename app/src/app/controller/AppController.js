
controller.controller('AppController', AppController);

function AppController($rootScope, EVENTS, toastr) {

	$rootScope.$on(EVENTS.loginSuccess, function() {
		toastr.success("You have successfully logged in.", "Login successful");
	});

	$rootScope.$on(EVENTS.registerSuccess, function() {
		toastr.success("The registartion is successful.", "Register successful");
	});

	$rootScope.$on(EVENTS.logoutSuccess, function() {
		toastr.success("You have successfully logged out", "Logout successful");
	});
}