
controller.controller('ProductDetailsController', ProductDetailsController);

function ProductDetailsController($scope, product, authUserLike, authUserBasket, AuthUser, Product, toastr, EVENTS) {

	var _this = this;
	this.product = product;
	this.authUserLike = authUserLike;
	this.authUserBasket = authUserBasket;
	this.rating = this.authUserLike.rating != null ? this.authUserLike.rating : 0;

	this.canSee = function() {
		return AuthUser.isAuthenticated && !AuthUser.isAdmin();
	}

	this.hasLiked = function() {
		return this.authUserLike.rating != null;
	}

	this.inBasket = function() {
		return this.authUserBasket.inBasket;
	}

	this.addLike = function(rating) {
		this.rating = rating;
		Product.addLike({id: _this.product.id}, {rating: rating}, 
			addLikeSuccessCallback, addLikeFailedCallback);
	}

	var addLikeSuccessCallback = function() {
		toastr.success("The like was added successfully", "Like added");
		_this.authUserLike.rating = _this.rating;
	}

	var addLikeFailedCallback = function() {
		_this.rating = 0;
		toastr.error("A error occurred while trying to add the like. Plase try again later", "Error");
	}

	this.updateLike = function(rating) {
		_this.rating = rating;
		Product.updateLike({id: _this.product.id}, {rating: rating}, 
			updateLikeSuccessCallback, updateLikeFailedCallback);
	}

	var updateLikeSuccessCallback = function() {
		toastr.success("The like was update successfully", "Like updated");
		_this.authUserLike.rating = _this.rating;
	}

	var updateLikeFailedCallback = function() {		
		toastr.error("A error occurred while trying to update the like. Plase try again later", "Error");
	}

	this.removeLike = function() {
		Product.removeLike({id: _this.product.id}, {}, 
			removeLikeSuccessCallback, removeLikeFailedCallback);
	}

	var removeLikeSuccessCallback = function() {
		_this.rating = 0;
		_this.authUserLike.rating = null;
		toastr.success("Like remove successfully", "Like removed");
	}

	var removeLikeFailedCallback = function() {
		toastr.error("Error occurred while trying to remove the like. Plase try again later", "Error");
	}

	this.addToBasket = function() {
		Product.addToBasket({id: _this.product.id}, {}, 
			addToBasketSuccessCallback, addToBasketFailedCallback);
	}

	var addToBasketSuccessCallback = function() {
		_this.authUserBasket.inBasket = true;
		toastr.success("The product was successfully added to the basket", "Product added");
	}

	var addToBasketFailedCallback = function() {		
		toastr.error("Error occurred while trying to add the product in the basket. " + 
			"Plase try again later", "Error");
	}

	this.removeFromBasket = function() {
		Product.removeFromBasket({id: _this.product.id}, {}, 
			removeFromBasketSuccessCallback, removeFromBasketFailedCallback);
	}

	var removeFromBasketSuccessCallback = function() {
		_this.authUserBasket.inBasket = false;
		toastr.success("The product was successfully removed from the basket", "Product removed");		
	}

	var removeFromBasketFailedCallback = function() {
		toastr.error("Error occurred while trying to add the product in the basket. " + 
			"Plase try again later", "Error");
	}

	var unwatch = $scope.$on(EVENTS.loginSuccess, function(event, data) {
		Product.authUserLike({id: _this.product.id}, function(response) {
			_this.authUserLike = response;
			_this.authUserLike.rating != null && (_this.rating = _this.authUserLike.rating);			
		});
		Product.authUserBasket({id: _this.product.id}, function(response) {
			_this.authUserBasket = response;
		});
	});

	$scope.$on('$destroy', function() {
		unwatch();
	});
}