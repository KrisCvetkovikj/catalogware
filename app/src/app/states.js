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
                resolve: {
                    likes: function(User) {
                        return User.authUserLikes();
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
                resolve: {
                    orders: function(User) {
                        return User.authUserOrders();
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
                resolve: {
                    basket: function(Basket) {
                        return Basket.get();
                    }
                }
            })            



        $stateProvider.state('root.about', {
            url: 'about',
            views: {
                'main@': {
                    templateUrl: "src/views/about.html"
                }
            }
        });

        $stateProvider.state('root.book-likes', {
            url: 'book-likes',
            views: {
                'main@': {
                    templateUrl: "src/views/book-likes.html"
                }
            }
        });

        $stateProvider.state('root.book-orders', {
            url: 'book-orders',
            views: {
                'main@': {
                    templateUrl: "src/views/book-orders.html"
                }
            }
        });


        $stateProvider.state('root.book-price', {
            url: 'book-price',
            views: {
                'main@': {
                    templateUrl: "src/views/book-price.html"
                }
            }
        });

        $stateProvider.state('root.books', {
            url: 'books',
            views: {
                'main@': {
                    templateUrl: "src/views/books.html"
                }
            }
        });

        $stateProvider.state('root.create', {
            url: 'create',
            views: {
                'main@': {
                    templateUrl: "src/views/create.html"
                }
            }
        });

        $stateProvider.state('root.dashboard', {
            url: 'dashboard',
            views: {
                'main@': {
                    templateUrl: "src/views/dashboard.html"
                }
            }
        });

        $stateProvider.state('root.form', {
            url: 'form',
            views: {
                'main@': {
                    templateUrl: "src/views/form.html"
                }
            }
        });        

        $stateProvider.state('root.like-stars', {
            url: 'like-stars',
            views: {
                'main@': {
                    templateUrl: "src/views/like-stars.html"
                }
            }
        });

        $stateProvider.state('root.index', {
            url: 'index',
            views: {
                'main@': {
                    templateUrl: "src/views/index.html"
                }
            }
        });

        $stateProvider.state('root.likes', {
            url: 'likes',
            views: {
                'main@': {
                    templateUrl: "src/views/likes.html"
                }
            }
        });

        $stateProvider.state('root.list', {
            url: 'list',
            views: {
                'main@': {
                    templateUrl: "src/views/list.html"
                }
            }
        });

        $stateProvider.state('root.login', {
            url: 'login',
            views: {
                'main@': {
                    templateUrl: "src/views/login.html"
                }
            }
        });

        $stateProvider.state('root.orders', {
            url: 'orders',
            views: {
                'main@': {
                    templateUrl: "src/views/orders.html"
                }
            }
        });

        $stateProvider.state('root.register', {
            url: 'register',
            views: {
                'main@': {
                    templateUrl: "src/views/register.html"
                }
            }
        });

        $stateProvider.state('root.show', {
            url: 'show',
            views: {
                'main@': {
                    templateUrl: "src/views/show.html"
                }
            }
        });

        $stateProvider.state('root.top', {
            url: 'top',
            views: {
                'main@': {
                    templateUrl: "src/views/top.html"
                }
            }
        });

        $stateProvider.state('root.update', {
            url: 'update',
            views: {
                'main@': {
                    templateUrl: "src/views/update.html"
                }
            }
        });

        $stateProvider.state('root.users', {
            url: 'users',
            views: {
                'main@': {
                    templateUrl: "src/views/users.html"
                }
            }
        });
    }])
    .run([
        '$rootScope',
        function($rootScope) {

            $rootScope.menuItems = [{
                state: 'root',
                icon: 'fa-home',
                name: 'wp.home'

            }, {
                state: 'root.students',
                icon: 'fa-users',
                name: 'wp.students'
            }, {
                state: 'root.courses',
                icon: 'fa-users',
                name: 'wp.courses'
            }];

        }]);