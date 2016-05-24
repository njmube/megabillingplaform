(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('package-transactions', {
            parent: 'entity',
            url: '/package-transactions?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.package_transactions.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/package-transactions/package-transactions.html',
                    controller: 'Package_transactionsController',
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
                    $translatePartialLoader.addPart('package_transactions');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('package-transactions-detail', {
            parent: 'entity',
            url: '/package-transactions/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.package_transactions.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/package-transactions/package-transactions-detail.html',
                    controller: 'Package_transactionsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('package_transactions');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Package_transactions', function($stateParams, Package_transactions) {
                    return Package_transactions.get({id : $stateParams.id});
                }]
            }
        })
        .state('package-transactions.new', {
            parent: 'package-transactions',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/package-transactions/package-transactions-dialog.html',
                    controller: 'Package_transactionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                price: null,
                                expiry_time: null,
                                unit_time: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('package-transactions', null, { reload: true });
                }, function() {
                    $state.go('package-transactions');
                });
            }]
        })
        .state('package-transactions.edit', {
            parent: 'package-transactions',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/package-transactions/package-transactions-dialog.html',
                    controller: 'Package_transactionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Package_transactions', function(Package_transactions) {
                            return Package_transactions.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('package-transactions', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('package-transactions.delete', {
            parent: 'package-transactions',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/package-transactions/package-transactions-delete-dialog.html',
                    controller: 'Package_transactionsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Package_transactions', function(Package_transactions) {
                            return Package_transactions.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('package-transactions', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
