(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-payer', {
            parent: 'entity',
            url: '/freecom-payer?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_payer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-payer/freecom-payers.html',
                    controller: 'Freecom_payerController',
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
                    $translatePartialLoader.addPart('freecom_payer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-payer-detail', {
            parent: 'entity',
            url: '/freecom-payer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_payer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-payer/freecom-payer-detail.html',
                    controller: 'Freecom_payerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_payer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_payer', function($stateParams, Freecom_payer) {
                    return Freecom_payer.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('freecom-payer.new', {
            parent: 'freecom-payer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-payer/freecom-payer-dialog.html',
                    controller: 'Freecom_payerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                emitter_bank: null,
                                name: null,
                                type_account: null,
                                account: null,
                                rfc: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-payer', null, { reload: true });
                }, function() {
                    $state.go('freecom-payer');
                });
            }]
        })
        .state('freecom-payer.edit', {
            parent: 'freecom-payer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-payer/freecom-payer-dialog.html',
                    controller: 'Freecom_payerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_payer', function(Freecom_payer) {
                            return Freecom_payer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-payer', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-payer.delete', {
            parent: 'freecom-payer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-payer/freecom-payer-delete-dialog.html',
                    controller: 'Freecom_payerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_payer', function(Freecom_payer) {
                            return Freecom_payer.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-payer', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
