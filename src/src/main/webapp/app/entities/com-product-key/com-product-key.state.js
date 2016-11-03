(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-product-key', {
            parent: 'entity',
            url: '/com-product-key?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_product_key.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-product-key/com-product-keys.html',
                    controller: 'Com_product_keyController',
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
                    $translatePartialLoader.addPart('com_product_key');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-product-key-detail', {
            parent: 'entity',
            url: '/com-product-key/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_product_key.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-product-key/com-product-key-detail.html',
                    controller: 'Com_product_keyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_product_key');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_product_key', function($stateParams, Com_product_key) {
                    return Com_product_key.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-product-key.new', {
            parent: 'com-product-key',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-product-key/com-product-key-dialog.html',
                    controller: 'Com_product_keyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-product-key', null, { reload: true });
                }, function() {
                    $state.go('com-product-key');
                });
            }]
        })
        .state('com-product-key.edit', {
            parent: 'com-product-key',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-product-key/com-product-key-dialog.html',
                    controller: 'Com_product_keyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_product_key', function(Com_product_key) {
                            return Com_product_key.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-product-key', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-product-key.delete', {
            parent: 'com-product-key',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-product-key/com-product-key-delete-dialog.html',
                    controller: 'Com_product_keyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_product_key', function(Com_product_key) {
                            return Com_product_key.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-product-key', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
