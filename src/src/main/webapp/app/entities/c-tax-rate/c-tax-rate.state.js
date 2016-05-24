(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-tax-rate', {
            parent: 'entity',
            url: '/c-tax-rate?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_tax_rate.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-tax-rate/c-tax-rates.html',
                    controller: 'C_tax_rateController',
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
                    $translatePartialLoader.addPart('c_tax_rate');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-tax-rate-detail', {
            parent: 'entity',
            url: '/c-tax-rate/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_tax_rate.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-tax-rate/c-tax-rate-detail.html',
                    controller: 'C_tax_rateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_tax_rate');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_tax_rate', function($stateParams, C_tax_rate) {
                    return C_tax_rate.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-tax-rate.new', {
            parent: 'c-tax-rate',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-tax-rate/c-tax-rate-dialog.html',
                    controller: 'C_tax_rateDialogController',
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
                    $state.go('c-tax-rate', null, { reload: true });
                }, function() {
                    $state.go('c-tax-rate');
                });
            }]
        })
        .state('c-tax-rate.edit', {
            parent: 'c-tax-rate',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-tax-rate/c-tax-rate-dialog.html',
                    controller: 'C_tax_rateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_tax_rate', function(C_tax_rate) {
                            return C_tax_rate.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-tax-rate', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-tax-rate.delete', {
            parent: 'c-tax-rate',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-tax-rate/c-tax-rate-delete-dialog.html',
                    controller: 'C_tax_rateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_tax_rate', function(C_tax_rate) {
                            return C_tax_rate.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-tax-rate', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
