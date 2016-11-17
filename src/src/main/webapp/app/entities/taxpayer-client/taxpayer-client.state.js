(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('taxpayer-client', {
            parent: 'entity',
            url: '/taxpayer-client/{id}?page&sort&search',
            data: {
                authorities: ['ROLE_AFILITATED'],
                pageTitle: 'megabillingplatformApp.taxpayer_client.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/taxpayer-client/taxpayer-clients.html',
                    controller: 'Taxpayer_clientController',
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
                taxpayer_account_entity: ['$stateParams', 'Taxpayer_account', function($stateParams, Taxpayer_account) {
                    return Taxpayer_account.get({id : $stateParams.id}).$promise;
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('taxpayer_client');
                    $translatePartialLoader.addPart('taxpayer_account');
                    $translatePartialLoader.addPart('client_address');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('taxpayer-client-detail', {
            parent: 'entity',
            url: '/taxpayer-client-detail/{id}',
            data: {
                authorities: ['ROLE_AFILITATED'],
                pageTitle: 'megabillingplatformApp.taxpayer_client.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/taxpayer-client/taxpayer-client-detail.html',
                    controller: 'Taxpayer_clientDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('taxpayer_client');
                    $translatePartialLoader.addPart('client_address');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Taxpayer_client', function($stateParams, Taxpayer_client) {
                    return Taxpayer_client.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('taxpayer-client.new', {
            parent: 'taxpayer-client',
            url: '/new/{id}',
            data: {
                authorities: ['ROLE_AFILITATED']
            },
            onEnter: ['$stateParams', 'Taxpayer_account', '$state', '$uibModal', function($stateParams, Taxpayer_account, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-client/taxpayer-client-dialog.html',
                    controller: 'Taxpayer_clientDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: '',
                    resolve: {
                        entity: function () {
                            return {
                                rfc: null,
                                bussinesname: null,
                                email: null,
                                id: null
                            };
                        },
                        taxpayer_account_entity: function($stateParams, Taxpayer_account) {
                            return Taxpayer_account.get({id : $stateParams.id}).$promise;
                        }
                    }
                }).result.then(function() {
                    $state.go('taxpayer-client', null, { reload: true });
                }, function() {
                    $state.go('taxpayer-client');
                });
            }]
        })
        .state('taxpayer-client.edit', {
            parent: 'taxpayer-client',
            url: '/edit/{id}',
            data: {
                authorities: ['ROLE_AFILITATED']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-client/taxpayer-client-dialog.html',
                    controller: 'Taxpayer_clientDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: '',
                    resolve: {
                        entity: ['Taxpayer_client', function(Taxpayer_client) {
                            return Taxpayer_client.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('taxpayer-client', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('taxpayer-client.delete', {
            parent: 'taxpayer-client',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_AFILITATED']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-client/taxpayer-client-delete-dialog.html',
                    controller: 'Taxpayer_clientDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Taxpayer_client', function(Taxpayer_client) {
                            return Taxpayer_client.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('taxpayer-client', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
