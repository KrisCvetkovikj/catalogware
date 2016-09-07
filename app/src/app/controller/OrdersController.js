controller.controller('OrdersController', OrdersController);

function OrdersController(orders, $http, toastr, Order) {

    /*var _this = this;
     _this.orders = orders;
     _this.products = null;*/
    var _this = this;
    _this.orders = null;

    orders.$promise.then(function (data) {
        _this.orders = data;
        _this.orders.forEach(function (order, index) {
            $http.get('/api/orders/' + order.id + '/products').then(function (products) {
                _this.orders[index].products = products.data;
            }, function (err) {
                console.log("GRESKA:", err);
            });
        });
    }, function (error) {
        _this.orders = null;
        console.log(err);
    });

    _this.getProductsForOrder = function (order) {
        return order.products;
    };

    //TODO implement viewing product for order

    /*var getProducts = function() {
     _this.loading = true;
     Order.query({
     id: orders[0]._id
     }, resetSuccessCallback, resetFailedCallback);
     };

     var resetSuccessCallback = function(data) {
     _this.products = data;
     };

     var resetFailedCallback = function() {
     toastr.error("We have some problems with our servers. Please try again later", "Error");
     };*/
}