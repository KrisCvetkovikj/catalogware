
controller.controller('CreateProductController', CreateProductController);

function CreateProductController($scope, product, categories, Product, ErrorUtils, ProductDialog, EVENTS, $http) {

	var _this = this;
	this.sending = false;
	this.product = product;
	this.categories = categories;
	this.errors = [];
	this.image = null;

	this.store = function() {		
		_this.product = {
			name: "Test",
			description: "Anuglar Test",
			price: 124,
			categories: [{id: 1, name: "Category 1"}, {id: 2, name: "Category 2"}]
		};
		// if (!this.sending) {
		// 	this.sending = true;
		// 	this.errors = [];
		// 	// Product.save(data, storeSuccessCallback, storeFailedCallback);
			$http({
			    method: 'POST',
			    url: 'api/products',
			    headers: {'Content-Type': undefined },
			    transformRequest: function (data) {
			        var formData = new FormData();
			        formData.append('product', new Blob([angular.toJson(data.product)], {
			            type: "application/json"
			        }));
			        formData.append("image", data.image);
			        return formData;
			    },
			    data: { 
			    	product: _this.product, 
			    	file: _this.image
			    }
			});
		// }
	}

	var storeSuccessCallback = function() {
		_this.sending = false;
		ProductDialog.hide();
		toastr.success("Product saved successfully", "Product Saved");
	}

	var storeFailedCallback = function(response) {
		_this.sending - false;
		_this.errors = ErrorUtils.getErrorsFromResponse(response.data);
	}

	var unwatch = $scope.$on(EVENTS.imageSelected, function(event, data) {		
		_this.image = data.file;
	});

	$scope.$on("$destroy", function(event, data) {
		unwatch();
	});
}