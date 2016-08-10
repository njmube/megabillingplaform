(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-ecc-11-transfer', {
            parent: 'entity',
            url: '/freecom-ecc-11-transfer?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_ecc11_transfer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-ecc-11-transfer/freecom-ecc-11-transfers.html',
                    controller: 'Freecom_ecc11_transferController',
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
                    $translatePartialLoader.addPart('freecom_ecc11_transfer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-ecc-11-transfer-detail', {
            parent: 'entity',
            url: '/freecom-ecc-11-transfer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_ecc11_transfer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-ecc-11-transfer/freecom-ecc-11-transfer-detail.html',
                    controller: 'Freecom_ecc11_transferDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_ecc11_transfer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_ecc11_transfer', function($stateParams, Freecom_ecc11_transfer) {
                    return Freecom_ecc11_transfer.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('freecom-ecc-11-transfer.new', {
            parent: 'freecom-ecc-11-transfer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-ecc-11-transfer/freecom-ecc-11-transfer-dialog.html',
                    controller: 'Freecom_ecc11_transferDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                type_tax: null,
                                rate: null,
                                amount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-ecc-11-transfer', null, { reload: true });
                }, function() {
                    $state.go('freecom-ecc-11-transfer');
                });
            }]
        })
        .state('freecom-ecc-11-transfer.edit', {
            parent: 'freecom-ecc-11-transfer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-ecc-11-transfer/freecom-ecc-11-transfer-dialog.html',
                    controller: 'Freecom_ecc11_transferDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_ecc11_transfer', function(Freecom_ecc11_transfer) {
                            return Freecom_ecc11_transfer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-ecc-11-transfer', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-ecc-11-transfer.delete', {
            parent: 'freecom-ecc-11-transfer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-ecc-11-transfer/freecom-ecc-11-transfer-delete-dialog.html',
                    controller: 'Freecom_ecc11_transferDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_ecc11_transfer', function(Freecom_ecc11_transfer) {
                            return Freecom_ecc11_transfer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-ecc-11-transfer', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
