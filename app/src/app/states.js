/**
 * Created by Kristijan Cvetkovikj on 9/5/16.
 */

/**
 * Secured states
 * All inherit from root (which has data : secured)
 */
app.config([
    '$stateProvider',
    '$urlRouterProvider',
    '$authProvider',
    function($stateProvider, $urlRouterProvider, $authProvider) {

        $urlRouterProvider.otherwise('/home');
        $authProvider.loginUrl = '/api/auth/login';
        $authProvider.signupUrl = '/api/auth/register';

        $stateProvider
            .state('root', {
                url: '/',
                views: {
                    nav: {
                        templateUrl: 'src/views/nav/nav.html',
                        controller: 'NavController',
                        controllerAs: 'navCtrl'
                    },
                    main: {
                        templateUrl: 'src/views/main.html'
                    }
                },
                resolve: {
                    auth: function(AuthResolver) {
                        return AuthResolver.resolve();
                    }
                }
            })
            .state('root.home', {
                url: 'home',
                views: {
                    'content@root': {
                        templateUrl: 'src/views/home.html',
                        controller: 'HomeController',
                        controllerAs: 'homeCtrl'
                    }
                },
                resolve: {
                    categories: function(Category) {
                        return Category.query();
                    },
                    products: function(Product, PRODUCTS) {
                        return Product.query({latest: PRODUCTS.latest, size: PRODUCTS.size});
                    }
                }      
            })
            .state('root.product-details', {
                url: 'products/:id',
                views: {
                    'content@root': {
                        templateUrl: 'src/views/products/show.html',
                        controller: 'ProductDetailsController',
                        controllerAs: 'productCtrl'
                    }
                },
                resolve: {
                    product: function(Product, $stateParams) {
                        return Product.get({id: $stateParams.id});
                    },
                    authUserLike: function(Product, $stateParams) {
                        return Product.authUserLike({id: $stateParams.id});
                    },
                    authUserBasket: function(Product, $stateParams) {
                        return Product.authUserBasket({id: $stateParams.id});
                    }
                }
            })            
            .state('root.user-likes', {
                url: 'users/me/likes',
                views: {
                    'content@root': {
                        templateUrl: 'src/views/users/likes.html',
                        controller: function(likes) {
                            this.likes = likes;
                        },
                        controllerAs: 'userCtrl'
                    }
                },
                data: {
                    authenticated: true,
                },
                resolve: {
                    likes: function($rootScope, $q, AuthUser, User, EVENTS) {                        
                        if (AuthUser.isAuthenticated) {                            
                            return User.authUserLikes();
                        } else {                            
                            $rootScope.$broadcast(EVENTS.notAuthorized);                            
                            return $q.reject("not a autheticated");
                        }
                    }
                }
            })
            .state('root.user-orders', {
                url: 'users/me/orders',
                views: {
                    'content@root': {
                        templateUrl: 'src/views/users/orders.html',
                        controller: 'OrdersController',
                        controllerAs: 'userCtrl'
                    }
                },
                data: {
                    authenticated: true,
                },
                resolve: {
                    orders: function($rootScope, $q, AuthUser, User, EVENTS) {
                        if (AuthUser.isAuthenticated) {
                            return User.authUserOrders();
                        } else {                            
                            $rootScope.$broadcast(EVENTS.notAuthorized);                            
                            return $q.reject("not a autheticated");
                        }
                    }
                }
            })
            .state('root.user-basket', {
                url: 'users/me/basket',
                views: {
                    'content@root': {
                        templateUrl: 'src/views/users/basket.html',
                        controller: 'BasketController',
                        controllerAs: 'basketCtrl'
                    }
                },
                data: {
                    authenticated: true,
                },
                resolve: {
                    basket: function($rootScope, $q, AuthUser, Basket, EVENTS) {                        
                        if (AuthUser.isAuthenticated) {                            
                            return Basket.get();
                        } else {                            
                            $rootScope.$broadcast(EVENTS.notAuthorized);                            
                            return $q.reject("not autheticated");
                        } 
                    }
                }
            })            
            .state('root.products', {
                url: 'admin/products',
                data: {
                    authenticated: true,
                },
                views: {
                    'content@root': {
                        templateUrl: 'src/views/admins/products.html',
                        controller: 'AdminController',
                        controllerAs: 'adminCtrl'
                    }
                },
                resolve: {
                    products: function($rootScope, $q, AuthUser, Product, EVENTS) {
                        if (AuthUser.isAdmin()) {
                            return Product.query();
                        } else {
                            $rootScope.$broadcast(EVENTS.notAuthorized);                            
                            return $q.reject("not a admin");
                        }
                    }
                }
            })
            .state('root.error-500', {
                url: 'errors/505',
                views: {
                    "content@root": {
                        templateUrl: 'src/views/errors/500.html'
                    }
                }
            });
}])