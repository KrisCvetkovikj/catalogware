
service.service('ProductDialog', ProductDialog);

function ProductDialog($modal) {

	var modal = null;

	var show = function(product, controller, title, view) {
		return $modal({
			animation: 'am-fade-and-scale',
			title: title,
			contentTemplate: view,
			html: true,
			show: true,
			controller: controller,
			controllerAs: 'productCtrl',
			resolve: {
				product: function() {
					return product;
				}, 
				categories: function(Category) {
					return Category.query();
				}
			}
		});
	}

	this.showCreateDialog = function() {		
		var product = {categories: []};
		modal = show(product, 'CreateProductController',
			'Add new product', 'src/views/products/create.html');
	}

	this.showUpdateDialog = function(product) {
		product.image = true;
		modal = show(product, 'UpdateProductController',
			'Update product', 'src/views/products/edit.html');
	}

	this.hide = function() {
		modal && modal.hide();
		modal = null;
	}
}