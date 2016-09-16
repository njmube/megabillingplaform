(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('taxpayer-transactions', {
            parent: 'entity',
            url: '/taxpayer-transactions?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.taxpayer_transactions.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/taxpayer-transactions/taxpayer-transactions.html',
                    controller: 'Taxpayer_transactionsController',
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
                    $translatePartialLoader.addPart('taxpayer_transactions');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('taxpayer-transactions-detail', {
            parent: 'entity',
            url: '/taxpayer-transactions/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.taxpayer_transactions.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/taxpayer-transactions/taxpayer-transactions-detail.html',
                    controller: 'Taxpayer_transactionsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('taxpayer_transactions');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Taxpayer_transactions', function($stateParams, Taxpayer_transactions) {
                    return Taxpayer_transactions.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('taxpayer-transactions.new', {
            parent: 'taxpayer-transactions',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-transactions/taxpayer-transactions-dialog.html',
                    controller: 'Taxpayer_transactionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                transactions_available: null,
                                transactions_spent: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('taxpayer-transactions', null, { reload: true });
                }, function() {
                    $state.go('taxpayer-transactions');
                });
            }]
        })
        .state('taxpayer-transactions.edit', {
            parent: 'taxpayer-transactions',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-transactions/taxpayer-transactions-dialog.html',
                    controller: 'Taxpayer_transactionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Taxpayer_transactions', function(Taxpayer_transactions) {
                            return Taxpayer_transactions.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('taxpayer-transactions', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('taxpayer-transactions.delete', {
            parent: 'taxpayer-transactions',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-transactions/taxpayer-transactions-delete-dialog.html',
                    controller: 'Taxpayer_transactionsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Taxpayer_transactions', function(Taxpayer_transactions) {
                            return Taxpayer_transactions.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('taxpayer-transactions', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
