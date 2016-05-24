(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('billing-account-state', {
            parent: 'entity',
            url: '/billing-account-state?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.billing_account_state.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/billing-account-state/billing-account-states.html',
                    controller: 'Billing_account_stateController',
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
                    $translatePartialLoader.addPart('billing_account_state');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('billing-account-state-detail', {
            parent: 'entity',
            url: '/billing-account-state/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.billing_account_state.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/billing-account-state/billing-account-state-detail.html',
                    controller: 'Billing_account_stateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('billing_account_state');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Billing_account_state', function($stateParams, Billing_account_state) {
                    return Billing_account_state.get({id : $stateParams.id});
                }]
            }
        })
        .state('billing-account-state.new', {
            parent: 'billing-account-state',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/billing-account-state/billing-account-state-dialog.html',
                    controller: 'Billing_account_stateDialogController',
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
                    $state.go('billing-account-state', null, { reload: true });
                }, function() {
                    $state.go('billing-account-state');
                });
            }]
        })
        .state('billing-account-state.edit', {
            parent: 'billing-account-state',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/billing-account-state/billing-account-state-dialog.html',
                    controller: 'Billing_account_stateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Billing_account_state', function(Billing_account_state) {
                            return Billing_account_state.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('billing-account-state', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('billing-account-state.delete', {
            parent: 'billing-account-state',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/billing-account-state/billing-account-state-delete-dialog.html',
                    controller: 'Billing_account_stateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Billing_account_state', function(Billing_account_state) {
                            return Billing_account_state.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('billing-account-state', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
