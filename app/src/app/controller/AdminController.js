
controller.controller('AdminController', AdminController);

function AdminController($scope, products, ProductDialog, Product, toastr, EVENTS) {	
	var _this = this;
	this.products = products;
	var selectedProduct = null;

	this.showUpdateProductDialog = function(product) {
		ProductDialog.showUpdateDialog(product);
	}

	this.removeProduct = function(product) {
		selectedProduct = product;
		Product.delete({id: product.id}, 
			removeProductSuccessCallback, removeProductFailedCallback);
	}

	var removeProductSuccessCallback = function() {
		var index = _this.products.indexOf(selectedProduct);
		if (index != -1) {
			_this.products.splice(index, 1);
		}
		toastr.success("Product is deleted successfully", "Product Deleted");
		selectedProduct = null;
	}

	var removeProductFailedCallback = function() {
		toastr.error("Error occurred while deleting the product. " + 
			"Plase try again later.", "Product Deleted");
	}

	var unwatch = $scope.$on(EVENTS.productUpdated, function(event, data) {
		Product.query({}, function(response) {			
			_this.products = response;
		});
		selectedProduct = null;
	});

	$scope.$on("$destroy", function(event, data) {
		unwatch();
	});
}