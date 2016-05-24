(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('category-product', {
            parent: 'entity',
            url: '/category-product?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.category_product.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/category-product/category-products.html',
                    controller: 'Category_productController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('category_product');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('category-product-detail', {
            parent: 'entity',
            url: '/category-product/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.category_product.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/category-product/category-product-detail.html',
                    controller: 'Category_productDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('category_product');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Category_product', function($stateParams, Category_product) {
                    return Category_product.get({id : $stateParams.id});
                }]
            }
        })
        .state('category-product.new', {
            parent: 'category-product',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/category-product/category-product-dialog.html',
                    controller: 'Category_productDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('category-product', null, { reload: true });
                }, function() {
                    $state.go('category-product');
                });
            }]
        })
        .state('category-product.edit', {
            parent: 'category-product',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/category-product/category-product-dialog.html',
                    controller: 'Category_productDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Category_product', function(Category_product) {
                            return Category_product.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('category-product', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('category-product.delete', {
            parent: 'category-product',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/category-product/category-product-delete-dialog.html',
                    controller: 'Category_productDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Category_product', function(Category_product) {
                            return Category_product.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('category-product', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
