
controller.controller('ProductFormController', ProductFormController);

function ProductFormController(ErrorUtils) {

	this.nameError = function() {
		return this.form && this.form.name && ErrorUtils.fieldError(this.form, this.form.name);
	}

	this.nameOk = function() {
		return this.form && this.form.name && ErrorUtils.fieldOk(this.form, this.form.name);
	}

	this.descriptionError = function() {
		return this.form && this.form.description && ErrorUtils.fieldError(this.form, this.form.description);
	}

	this.descriptionOk = function() {
		return this.form && this.form.description && ErrorUtils.fieldOk(this.form, this.form.description);
	}

	this.categoriesError = function() {		
		return this.form && this.form.categories && ErrorUtils.fieldError(this.form, this.form.categories);
	}

	this.categoriesOk = function() {
		return this.form && this.form.categories && ErrorUtils.fieldOk(this.form, this.form.categories);
	}

	this.imageError = function() {		
		return this.form && this.form.image && ErrorUtils.fieldError(this.form, this.form.image);
	}

	this.imageOk = function() {
		return this.form && this.form.image && ErrorUtils.fieldOk(this.form, this.form.image);
	}

	this.priceError = function() {
		return this.form && this.form.price && ErrorUtils.fieldError(this.form, this.form.price);
	}

	this.priceOk = function() {
		return this.form && this.form.price && ErrorUtils.fieldOk(this.form, this.form.price);
	}

	this.deleteImage = function() {
		this.product.image = false;
	}
}