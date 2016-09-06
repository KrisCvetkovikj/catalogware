
app.constant('ROLES', {
	admin: 'ADMIN',
	customer: 'CUSTOMER'
});

app.constant('EVENTS', {
	notFound: 'event-not-found',
	notAuthenticated: 'event-not-authenticated',
	notAuthorized: 'event-not-authorized',
	internalServerError: 'event-internal-server-error',	
	methodNotAllowed: 'event-method-not=allowed',
	loginSuccess: 'event-login-success',
	authChecked: 'event-auth-checked',
	logoutSuccess: 'event-logout-success',
	registerSuccess: 'event-register-success',
	basketCheckoutSuccess: 'event-basket-chcout-success',
	imageSelected: 'event-image-selected',
	productUpdated: 'event-product-updated'
});

app.constant('PRODUCTS', {
	size: 20,
	latest: true
});