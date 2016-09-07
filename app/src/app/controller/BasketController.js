
controller.controller('BasketController', BasketController);

function BasketController($scope, $state, basket, Product, Basket, AddressDialog, toastr, EVENTS) {

	var _this = this;
	this.basket = null;
	var selectedProduct = null;

	basket.$promise.then(function() {
		_this.basket = basket;
	});

	this.isEmpty = function() {
		return this.basket && this.basket.products.length == 0;
	}

	this.total = function() {
		if (this.basket) {
			var sum = 0;
			basket.products.forEach(function(product) {
				sum += product.price;
			});
			return sum;
		}
		return 0;
	}	

	this.removeProduct = function(product) {
		selectedProduct = product;
		Product.removeFromBasket({id: product.id}, {}, 
			removeProductSuccessCallback, removeProductFailedCallback);
	}

	var removeProductSuccessCallback = function() {
		var index = _this.basket.products.indexOf(selectedProduct);
		if (index != -1) {
			_this.basket.products.splice(index, 1);
		}		
		toastr.success("The product has been removed from the basket", "Product removed");
	}

	var removeProductFailedCallback = function() {
		toastr.error("Error occurred while removing the product.", "Error");
	}

	this.checkout = function() {
		AddressDialog.show();		
	}

	var unwatchBasketCheckout = $scope.$on(EVENTS.basketCheckoutSuccess, function(event, data) {
		_this.basket.products = [];
	});

	var unwatchLogout = $scope.$on(EVENTS.logoutSuccess, function(event, data) {
		$state.go('root.home');
	});

	$scope.$on('$destroy', function() {
		unwatchBasketCheckout();
		unwatchLogout();
	});
}