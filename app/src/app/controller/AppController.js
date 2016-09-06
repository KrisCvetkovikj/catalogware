
controller.controller('AppController', AppController);

function AppController($rootScope, $state, LoginDialog, toastr, EVENTS) {

	var notAutenticated = function() {
		toastr.error("You need to be logged in to access this page", "Not Authenticated");
		LoginDialog.show();
	}

	var notAuthorized = function() {		
		toastr.error("You don't have permission to access this page", "Not Authorized");				
	}

	$rootScope.$on(EVENTS.notFound, function(event, data) {
		toastr.error("The requested page was not found", "Not Found");
		$state.go('root.home');
	});

	$rootScope.$on(EVENTS.notAuthenticated, function(event, data) {		
		notAutenticated();
	});

	$rootScope.$on(EVENTS.notAuthorized, function(event, data) {
		notAuthorized();		
		// $state.go('root.home');
	});	

	$rootScope.$on(EVENTS.internalServerError, function(event, data) {		
		$state.go('root.error-500');
	});

	$rootScope.$on(EVENTS.methodNotAllowed, function(event, data) {
		toastr.error("This method on the resource is not allowed", "Method not allowed");
	});	

	$rootScope.$on(EVENTS.loginSuccess, function(event, data) {
		toastr.success("You have successfully logged in", "Logged in");
	});

	$rootScope.$on(EVENTS.logoutSuccess, function(event, data) {
		toastr.success("You have successfully logged out", "Logged out");
		var current = $state.current;	
		if(current.data && current.data.authenticated)
			$state.go('root.home');
	});

	$rootScope.$on(EVENTS.registerSuccess, function(event, data) {
		toastr.success("The registration was successfull", "Registered");
	});
}