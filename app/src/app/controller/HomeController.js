
controller.controller('HomeController', HomeController);

function HomeController(categories, products, Product, toastr, PRODUCTS) {

	var _this = this;	
	this.loading = false;
	this.done = false;
	this.categories = categories;
	this.products = products;
	var page = 1;
	var selectedCategories = [];

	this.loadMore = function() {		
		if (!this.loading && !this.done) {
			this.loading = true;
			Product.query({
				latest: PRODUCTS.latest,
				size: PRODUCTS.size,
				page: page,
				categories: selectedCategories
			}, loadMoreSuccessCallback, loadMoreFailedCallback);
		}
	}

	var loadMoreSuccessCallback = function(data) {		
		_this.loading = false;
		page++;
		_this.products = _this.products.concat(data);
		if(data.length < PRODUCTS.size) {
			_this.done = true;
			if(selectedCategories.length > 0)
				toastr.info('There are no more products of this categories in our store');
			else
				toastr.info('There are no more products in our store');
		}			
	}

	var loadMoreFailedCallback = function(response) {		
		_this.loading = false;
		toastr.error("We have some problems with our servers. Please try again later", "Error");
	}

	var createCategoriesString = function() {
		var result = "";
		for(var i = 0; i < selectedCategories.length; i++) {
			if(i == 0)
				result += selectedCategories[i];
			else
				result += "," + selectedCategories[i];
		}

		return result.length > 0 ? result : null;
	}

	this.filter = function(category) {		
		if (!this.loading) {
			if(category.selected) {
				var index = selectedCategories.indexOf(category.name);
				index > -1 && (selectedCategories.splice(index, 1));				
				category.selected = false;				
			} else {
				selectedCategories.push(category.name);				
				category.selected = true;				
			}
			reset();
		}
	}

	var reset = function() {
		_this.done = false;
		if(!_this.loading) {
			_this.loading = true;
			Product.query({
				latest: PRODUCTS.latest,
				size: PRODUCTS.size,
				page: 0,
				categories: createCategoriesString()
			}, resetSuccessCallback, resetFailedCallback);
		}
	}

	var resetSuccessCallback = function(data) {		
		_this.loading = false;
		page = 1;
		_this.products = data;
	}

	var resetFailedCallback = function() {
		_this.loading = false;
		toastr.error("We have some problems with our servers. Please try again later", "Error");
	}	
}