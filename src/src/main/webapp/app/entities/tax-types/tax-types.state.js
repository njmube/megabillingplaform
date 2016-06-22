(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tax-types', {
            parent: 'entity',
            url: '/tax-types?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.tax_types.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tax-types/tax-types.html',
                    controller: 'Tax_typesController',
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
                    $translatePartialLoader.addPart('tax_types');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tax-types-detail', {
            parent: 'entity',
            url: '/tax-types/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.tax_types.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tax-types/tax-types-detail.html',
                    controller: 'Tax_typesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tax_types');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tax_types', function($stateParams, Tax_types) {
                    return Tax_types.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('tax-types.new', {
            parent: 'tax-types',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tax-types/tax-types-dialog.html',
                    controller: 'Tax_typesDialogController',
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
                    $state.go('tax-types', null, { reload: true });
                }, function() {
                    $state.go('tax-types');
                });
            }]
        })
        .state('tax-types.edit', {
            parent: 'tax-types',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tax-types/tax-types-dialog.html',
                    controller: 'Tax_typesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tax_types', function(Tax_types) {
                            return Tax_types.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tax-types', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tax-types.delete', {
            parent: 'tax-types',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tax-types/tax-types-delete-dialog.html',
                    controller: 'Tax_typesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tax_types', function(Tax_types) {
                            return Tax_types.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tax-types', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
