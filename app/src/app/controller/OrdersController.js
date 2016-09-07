controller.controller('OrdersController', OrdersController);

function OrdersController(orders, $http, toastr, Order, $modal) {

    /*var _this = this;
     _this.orders = orders;
     _this.products = null;*/
    var _this = this;
    _this.orders = null;

    orders.$promise.then(function (data) {
        _this.orders = data;
        _this.orders.forEach(function (order, index) {
            Order.getProducts({id: order.id}, {}, function(response) {
                _this.orders[index].products = response.data;
            }, function(response) {
                console.log("GRESKA:", response);
            });
        });
    }, function (error) {
        _this.orders = null;
        console.log(err);
    });

    _this.getProductsForOrder = function (order) {
        $modal({
            animation: 'am-fade-and-scale',
            title: "Products",
            contentTemplate: "src/views/orders/products.html",
            html: true,
            show: true,
            controller: function(products) {
                this.products = products;                
            },
            controllerAs: "orderCtrl",
            resolve: {
                products: function(){
                    return order.products;
                }
            }
        });
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