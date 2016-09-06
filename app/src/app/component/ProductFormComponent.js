
component.component('productForm', {
	templateUrl: 'src/views/products/form.html',
	controller: 'ProductFormController',
	controllerAs: 'productCtrl',
	bindings: {
		product: '<',
		categories: '<',
		errors: '<',
		sending: '<',
		btnText: '@',
		send: '&'
	}
});