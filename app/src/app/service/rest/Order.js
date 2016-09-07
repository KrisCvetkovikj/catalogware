/**
 * Created by Kristijan Cvetkovikj on 9/7/16.
 */


service.factory('Order', Order);

function Order($resource) {

    return $resource("/api/orders/:id/:path", {id: "@id"}, {
        getProducts: {
            method: "GET",
            params: {
                path: "products"
            },
            isArray: true
        }
    });
}