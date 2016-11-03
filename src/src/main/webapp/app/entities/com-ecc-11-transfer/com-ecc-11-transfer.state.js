(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-ecc-11-transfer', {
            parent: 'entity',
            url: '/com-ecc-11-transfer?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_ecc_11_transfer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-ecc-11-transfer/com-ecc-11-transfers.html',
                    controller: 'Com_ecc_11_transferController',
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
                    $translatePartialLoader.addPart('com_ecc_11_transfer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-ecc-11-transfer-detail', {
            parent: 'entity',
            url: '/com-ecc-11-transfer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_ecc_11_transfer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-ecc-11-transfer/com-ecc-11-transfer-detail.html',
                    controller: 'Com_ecc_11_transferDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_ecc_11_transfer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_ecc_11_transfer', function($stateParams, Com_ecc_11_transfer) {
                    return Com_ecc_11_transfer.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-ecc-11-transfer.new', {
            parent: 'com-ecc-11-transfer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-ecc-11-transfer/com-ecc-11-transfer-dialog.html',
                    controller: 'Com_ecc_11_transferDialogController',
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
                    $state.go('com-ecc-11-transfer', null, { reload: true });
                }, function() {
                    $state.go('com-ecc-11-transfer');
                });
            }]
        })
        .state('com-ecc-11-transfer.edit', {
            parent: 'com-ecc-11-transfer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-ecc-11-transfer/com-ecc-11-transfer-dialog.html',
                    controller: 'Com_ecc_11_transferDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_ecc_11_transfer', function(Com_ecc_11_transfer) {
                            return Com_ecc_11_transfer.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-ecc-11-transfer', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-ecc-11-transfer.delete', {
            parent: 'com-ecc-11-transfer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-ecc-11-transfer/com-ecc-11-transfer-delete-dialog.html',
                    controller: 'Com_ecc_11_transferDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_ecc_11_transfer', function(Com_ecc_11_transfer) {
                            return Com_ecc_11_transfer.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-ecc-11-transfer', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
