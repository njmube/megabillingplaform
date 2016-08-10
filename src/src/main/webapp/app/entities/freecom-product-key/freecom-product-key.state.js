(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-product-key', {
            parent: 'entity',
            url: '/freecom-product-key?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_product_key.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-product-key/freecom-product-keys.html',
                    controller: 'Freecom_product_keyController',
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
                    $translatePartialLoader.addPart('freecom_product_key');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-product-key-detail', {
            parent: 'entity',
            url: '/freecom-product-key/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_product_key.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-product-key/freecom-product-key-detail.html',
                    controller: 'Freecom_product_keyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_product_key');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_product_key', function($stateParams, Freecom_product_key) {
                    return Freecom_product_key.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('freecom-product-key.new', {
            parent: 'freecom-product-key',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-product-key/freecom-product-key-dialog.html',
                    controller: 'Freecom_product_keyDialogController',
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
                    $state.go('freecom-product-key', null, { reload: true });
                }, function() {
                    $state.go('freecom-product-key');
                });
            }]
        })
        .state('freecom-product-key.edit', {
            parent: 'freecom-product-key',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-product-key/freecom-product-key-dialog.html',
                    controller: 'Freecom_product_keyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_product_key', function(Freecom_product_key) {
                            return Freecom_product_key.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-product-key', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-product-key.delete', {
            parent: 'freecom-product-key',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-product-key/freecom-product-key-delete-dialog.html',
                    controller: 'Freecom_product_keyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_product_key', function(Freecom_product_key) {
                            return Freecom_product_key.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-product-key', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
