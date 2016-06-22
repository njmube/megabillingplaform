(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('cfdi-types', {
            parent: 'entity',
            url: '/cfdi-types?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.cfdi_types.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cfdi-types/cfdi-types.html',
                    controller: 'Cfdi_typesController',
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
                    $translatePartialLoader.addPart('cfdi_types');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('cfdi-types-detail', {
            parent: 'entity',
            url: '/cfdi-types/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.cfdi_types.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/cfdi-types/cfdi-types-detail.html',
                    controller: 'Cfdi_typesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('cfdi_types');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Cfdi_types', function($stateParams, Cfdi_types) {
                    return Cfdi_types.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('cfdi-types.new', {
            parent: 'cfdi-types',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfdi-types/cfdi-types-dialog.html',
                    controller: 'Cfdi_typesDialogController',
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
                    $state.go('cfdi-types', null, { reload: true });
                }, function() {
                    $state.go('cfdi-types');
                });
            }]
        })
        .state('cfdi-types.edit', {
            parent: 'cfdi-types',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfdi-types/cfdi-types-dialog.html',
                    controller: 'Cfdi_typesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Cfdi_types', function(Cfdi_types) {
                            return Cfdi_types.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cfdi-types', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('cfdi-types.delete', {
            parent: 'cfdi-types',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/cfdi-types/cfdi-types-delete-dialog.html',
                    controller: 'Cfdi_typesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Cfdi_types', function(Cfdi_types) {
                            return Cfdi_types.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('cfdi-types', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
