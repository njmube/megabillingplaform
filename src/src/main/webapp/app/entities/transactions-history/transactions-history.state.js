(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('transactions-history', {
            parent: 'entity',
            url: '/transactions-history?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.transactions_history.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/transactions-history/transactions-histories.html',
                    controller: 'Transactions_historyController',
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
                    $translatePartialLoader.addPart('transactions_history');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('transactions-history-detail', {
            parent: 'entity',
            url: '/transactions-history/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.transactions_history.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/transactions-history/transactions-history-detail.html',
                    controller: 'Transactions_historyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('transactions_history');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Transactions_history', function($stateParams, Transactions_history) {
                    return Transactions_history.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('transactions-history.new', {
            parent: 'transactions-history',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transactions-history/transactions-history-dialog.html',
                    controller: 'Transactions_historyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                date_transaction: null,
                                quantity: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('transactions-history', null, { reload: true });
                }, function() {
                    $state.go('transactions-history');
                });
            }]
        })
        .state('transactions-history.edit', {
            parent: 'transactions-history',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transactions-history/transactions-history-dialog.html',
                    controller: 'Transactions_historyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Transactions_history', function(Transactions_history) {
                            return Transactions_history.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('transactions-history', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('transactions-history.delete', {
            parent: 'transactions-history',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/transactions-history/transactions-history-delete-dialog.html',
                    controller: 'Transactions_historyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Transactions_history', function(Transactions_history) {
                            return Transactions_history.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('transactions-history', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
