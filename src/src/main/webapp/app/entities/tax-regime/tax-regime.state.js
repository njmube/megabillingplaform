(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tax-regime', {
            parent: 'entity',
            url: '/tax-regime?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.tax_regime.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tax-regime/tax-regimes.html',
                    controller: 'Tax_regimeController',
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
                    $translatePartialLoader.addPart('tax_regime');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tax-regime-detail', {
            parent: 'entity',
            url: '/tax-regime/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.tax_regime.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tax-regime/tax-regime-detail.html',
                    controller: 'Tax_regimeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tax_regime');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tax_regime', function($stateParams, Tax_regime) {
                    return Tax_regime.get({id : $stateParams.id});
                }]
            }
        })
        .state('tax-regime.new', {
            parent: 'tax-regime',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tax-regime/tax-regime-dialog.html',
                    controller: 'Tax_regimeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                code: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tax-regime', null, { reload: true });
                }, function() {
                    $state.go('tax-regime');
                });
            }]
        })
        .state('tax-regime.edit', {
            parent: 'tax-regime',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tax-regime/tax-regime-dialog.html',
                    controller: 'Tax_regimeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tax_regime', function(Tax_regime) {
                            return Tax_regime.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('tax-regime', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tax-regime.delete', {
            parent: 'tax-regime',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tax-regime/tax-regime-delete-dialog.html',
                    controller: 'Tax_regimeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tax_regime', function(Tax_regime) {
                            return Tax_regime.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('tax-regime', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
