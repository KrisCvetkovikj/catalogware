controller.controller('BasketController', BasketController);

function BasketController($scope, $state, basket, Product, Basket, AddressDialog, toastr, EVENTS) {

    var _this = this;

    basket.$promise.then(function (data) {
        _this.basket = data;
    }, function(error) {
        _this.basket = null;
        console.log(err);
    });

    var selectedProduct = null;

    this.isEmpty = function () {
        if (_this.basket && _this.basket.products && _this.basket.products.length == 0) {
            return true;
        }
        return false;
    };

    this.total = function () {
        var sum = 0;

        if (!this.isEmpty()) {
            console.log("_BASKET:", _this.basket);
            console.log("BASKET:", this.basket);
            console.log("basket:", basket);
            _this.basket.products.forEach(function (product) {
                sum += product.price;
            });
        }

        return sum;
    };

    this.removeProduct = function (product) {
        selectedProduct = product;
        Product.removeFromBasket({id: product.id}, {},
            removeProductSuccessCallback, removeProductFailedCallback);
    }

    var removeProductSuccessCallback = function () {
        var index = _this.basket.products.indexOf(selectedProduct);
        if (index != -1) {
            _this.basket.products.splice(index, 1);
        }
        toastr.success("The product has been removed from the basket", "Product removed");
    }

    var removeProductFailedCallback = function () {
        toastr.error("Error occurred while removing the product.", "Error");
    }

    this.checkout = function () {
        AddressDialog.show();
    }

    var unwatchBasketCheckout = $scope.$on(EVENTS.basketCheckoutSuccess, function (event, data) {
        _this.basket.products = [];
    });

    var unwatchLogout = $scope.$on(EVENTS.logoutSuccess, function (event, data) {
        $state.go('root.home');
    });

    $scope.$on('$destroy', function () {
        unwatchBasketCheckout();
        unwatchLogout();
    });
}