
app.config(function($stateProvider, $urlRouterProvider, $authProvider) {

	$authProvider.loginUrl = '/api/auth/login';
	$authProvider.signupUrl = '/api/auth/register';
	$urlRouterProvider.otherwise('/');

	$stateProvider
		.state('root', {
			url: '/',
			templateUrl: 'src/templates/home.html',
			resolve: {
				auth: function(AuthResolver) {
					return AuthResolver.resolve();
				}
			}
		});
});