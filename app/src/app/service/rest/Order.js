/**
 * Created by Kristijan Cvetkovikj on 9/7/16.
 */


service.factory('Order', Order);

function Order($resource) {

    return $resource("/api/orders/:id/products", {id: "@id"}, {
        getProducts: {
            method: "GET",            
            isArray: true
        }
    });
}