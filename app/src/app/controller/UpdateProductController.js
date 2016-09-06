
controller.controller('UpdateProductController', UpdateProductController);

function UpdateProductController($scope, $rootScope, product, categories, Product, ProductDialog, ErrorUtils, toastr, EVENTS) {

	var _this = this;
	this.sending = false;
	this.errors = [];
	this.product = product;
	this.categories = categories;
	this.image = null;

	this.update = function() {
		if (!this.sending) {
			this.sending = true;
			this.errors = [];
			Product.update({id: _this.product.id}, {
				product: _this.product,
				image: _this.image
			}, updateSuccessCallback, updateFailedCallback);
		}
	}

	var updateSuccessCallback = function() {
		_this.sending = false;
		ProductDialog.hide();
		$rootScope.$broadcast(EVENTS.productUpdated);
		toastr.success("Product updated successfully", "Product Updated");
	}

	var updateFailedCallback = function(response) {
		_this.sending = false;
		_this.errors = ErrorUtils.getErrorsFromResponse(response.data);
		if (_this.errors.length == 0) {
			toastr.error("Error occurred while trying to update the product. " + 
			"Please try again later", "Error");
		}
	}

	var unwatch = $scope.$on(EVENTS.imageSelected, function(event, data) {
		_this.image = data.file;
	});

	$scope.$on("$destroy", function(event, data) {
		unwatch();
	});
}