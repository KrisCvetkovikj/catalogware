
controller.controller('OrdersController', OrdersController);

function OrdersController(orders, Order, $modal) {	

	var _this = this;
    _this.orders = null;

    orders.$promise.then(function (data) {
        _this.orders = data;
        _this.orders.forEach(function (order, index) {
            Order.query({id: order.id}, {}, function(response) {            	
                _this.orders[index].products = response;
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
            controllerAs: "productCtrl",
            resolve: {
                products: function(){
                    return order.products;
                }
            }
        });
    };
}