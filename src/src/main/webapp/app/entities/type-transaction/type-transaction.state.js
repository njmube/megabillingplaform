(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-transaction', {
            parent: 'entity',
            url: '/type-transaction?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.type_transaction.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-transaction/type-transactions.html',
                    controller: 'Type_transactionController',
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
                    $translatePartialLoader.addPart('type_transaction');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('type-transaction-detail', {
            parent: 'entity',
            url: '/type-transaction/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.type_transaction.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-transaction/type-transaction-detail.html',
                    controller: 'Type_transactionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('type_transaction');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Type_transaction', function($stateParams, Type_transaction) {
                    return Type_transaction.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('type-transaction.new', {
            parent: 'type-transaction',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-transaction/type-transaction-dialog.html',
                    controller: 'Type_transactionDialogController',
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
                    $state.go('type-transaction', null, { reload: true });
                }, function() {
                    $state.go('type-transaction');
                });
            }]
        })
        .state('type-transaction.edit', {
            parent: 'type-transaction',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-transaction/type-transaction-dialog.html',
                    controller: 'Type_transactionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Type_transaction', function(Type_transaction) {
                            return Type_transaction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-transaction', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-transaction.delete', {
            parent: 'type-transaction',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-transaction/type-transaction-delete-dialog.html',
                    controller: 'Type_transactionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Type_transaction', function(Type_transaction) {
                            return Type_transaction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-transaction', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
