(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('accounting', {
            parent: 'entity',
            url: '/accounting?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.accounting.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/accounting/accountings.html',
                    controller: 'AccountingController',
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
                    $translatePartialLoader.addPart('accounting');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('accounting-detail', {
            parent: 'entity',
            url: '/accounting/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.accounting.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/accounting/accounting-detail.html',
                    controller: 'AccountingDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('accounting');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Accounting', function($stateParams, Accounting) {
                    return Accounting.get({id : $stateParams.id});
                }]
            }
        })
        .state('accounting.new', {
            parent: 'accounting',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/accounting/accounting-dialog.html',
                    controller: 'AccountingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                keyaccounting: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('accounting', null, { reload: true });
                }, function() {
                    $state.go('accounting');
                });
            }]
        })
        .state('accounting.edit', {
            parent: 'accounting',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/accounting/accounting-dialog.html',
                    controller: 'AccountingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Accounting', function(Accounting) {
                            return Accounting.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('accounting', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('accounting.delete', {
            parent: 'accounting',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/accounting/accounting-delete-dialog.html',
                    controller: 'AccountingDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Accounting', function(Accounting) {
                            return Accounting.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('accounting', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
