
controller.controller('CreateOrderController', CreateOrderController);

function CreateOrderController($rootScope, Basket, AddressDialog, ErrorUtils, toastr, EVENTS) {

	var _this = this;
	this.sending = false;
	this.title = ":Title";

	this.create = function() {		
		if (!this.sending) {
			this.sending = true;
			Basket.checkout({}, {shippingAddress: _this.address}, 
				createSuccessCallback, createFailedCallback);
		}		
	}

	var createSuccessCallback = function() {
		_this.sending = false;
		toastr.success("The order is created successfully.", "Order created");
		$rootScope.$broadcast(EVENTS.basketCheckoutSuccess);		
		AddressDialog.hide();
	}

	var createFailedCallback = function() {
		_this.sending = false;
		toastr.error("Error occurred while creating the order. Please try again", "Error");
	}

	this.addressOk = function() {		
		return this.form && this.form.address && ErrorUtils.fieldOk(this.form, this.form.address);
	}

	this.addressError = function() {				
		return this.form && this.form.address && ErrorUtils.fieldError(this.form, this.form.address);
	}
}