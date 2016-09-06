
controller.controller('CreateProductController', CreateProductController);

function CreateProductController($scope, product, categories, Product, ErrorUtils, ProductDialog, EVENTS, $http) {

	var _this = this;
	this.sending = false;
	this.product = product;
	this.categories = categories;
	this.errors = [];
	this.image = null;

	this.store = function() {			
		if (!this.sending) {
			this.sending = true;
			this.errors = [];
			Product.save({product: _this.product, image: _this.image}, 
				storeSuccessCallback, storeFailedCallback);
		}
	}

	var storeSuccessCallback = function() {
		_this.sending = false;
		ProductDialog.hide();
		toastr.success("Product saved successfully", "Product Saved");
	}

	var storeFailedCallback = function(response) {		
		_this.sending = false;		
		_this.errors = ErrorUtils.getErrorsFromResponse(response.data);
		if (_this.errors.length == 0) {
			toastr.success("Error occurred while trying to update the product. " + 
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