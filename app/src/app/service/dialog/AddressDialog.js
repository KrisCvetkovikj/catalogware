
service.service('AddressDialog', AddressDialog);

function AddressDialog(Modal) {

	var modal = null;

	this.show = function() {
		modal = Modal.create("Basket Checkout", "src/views/orders/create.html", 
			"CreateOrderController", "orderCtrl");
	}

	this.hide = function() {
		modal && modal.hide();
		modal = null;
	}
}