(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-well-type', {
            parent: 'entity',
            url: '/c-well-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_well_type.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-well-type/c-well-types.html',
                    controller: 'C_well_typeController',
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
                    $translatePartialLoader.addPart('c_well_type');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-well-type-detail', {
            parent: 'entity',
            url: '/c-well-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_well_type.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-well-type/c-well-type-detail.html',
                    controller: 'C_well_typeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_well_type');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_well_type', function($stateParams, C_well_type) {
                    return C_well_type.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-well-type.new', {
            parent: 'c-well-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-well-type/c-well-type-dialog.html',
                    controller: 'C_well_typeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-well-type', null, { reload: true });
                }, function() {
                    $state.go('c-well-type');
                });
            }]
        })
        .state('c-well-type.edit', {
            parent: 'c-well-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-well-type/c-well-type-dialog.html',
                    controller: 'C_well_typeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_well_type', function(C_well_type) {
                            return C_well_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-well-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-well-type.delete', {
            parent: 'c-well-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-well-type/c-well-type-delete-dialog.html',
                    controller: 'C_well_typeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_well_type', function(C_well_type) {
                            return C_well_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-well-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
