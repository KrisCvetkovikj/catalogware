
app.config(function(cfpLoadingBarProvider, toastrConfig, $httpProvider) {
	cfpLoadingBarProvider.includeSpinner = false;

	// $httpProvider.interceptors.push('HttpInterceptor');

	angular.extend(toastrConfig, {
		closeButton: true,
		tapToDismiss: false	    
	});
});