(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tax-retentions', {
            parent: 'entity',
            url: '/tax-retentions?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.tax_retentions.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tax-retentions/tax-retentions.html',
                    controller: 'Tax_retentionsController',
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
                    $translatePartialLoader.addPart('tax_retentions');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tax-retentions-detail', {
            parent: 'entity',
            url: '/tax-retentions/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.tax_retentions.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tax-retentions/tax-retentions-detail.html',
                    controller: 'Tax_retentionsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tax_retentions');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tax_retentions', function($stateParams, Tax_retentions) {
                    return Tax_retentions.get({id : $stateParams.id});
                }]
            }
        })
        .state('tax-retentions.new', {
            parent: 'tax-retentions',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tax-retentions/tax-retentions-dialog.html',
                    controller: 'Tax_retentionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                amount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tax-retentions', null, { reload: true });
                }, function() {
                    $state.go('tax-retentions');
                });
            }]
        })
        .state('tax-retentions.edit', {
            parent: 'tax-retentions',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tax-retentions/tax-retentions-dialog.html',
                    controller: 'Tax_retentionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tax_retentions', function(Tax_retentions) {
                            return Tax_retentions.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('tax-retentions', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tax-retentions.delete', {
            parent: 'tax-retentions',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tax-retentions/tax-retentions-delete-dialog.html',
                    controller: 'Tax_retentionsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tax_retentions', function(Tax_retentions) {
                            return Tax_retentions.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('tax-retentions', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
