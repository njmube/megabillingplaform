(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-scope-type', {
            parent: 'entity',
            url: '/c-scope-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_scope_type.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-scope-type/c-scope-types.html',
                    controller: 'C_scope_typeController',
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
                    $translatePartialLoader.addPart('c_scope_type');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-scope-type-detail', {
            parent: 'entity',
            url: '/c-scope-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_scope_type.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-scope-type/c-scope-type-detail.html',
                    controller: 'C_scope_typeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_scope_type');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_scope_type', function($stateParams, C_scope_type) {
                    return C_scope_type.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-scope-type.new', {
            parent: 'c-scope-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-scope-type/c-scope-type-dialog.html',
                    controller: 'C_scope_typeDialogController',
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
                    $state.go('c-scope-type', null, { reload: true });
                }, function() {
                    $state.go('c-scope-type');
                });
            }]
        })
        .state('c-scope-type.edit', {
            parent: 'c-scope-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-scope-type/c-scope-type-dialog.html',
                    controller: 'C_scope_typeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_scope_type', function(C_scope_type) {
                            return C_scope_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-scope-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-scope-type.delete', {
            parent: 'c-scope-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-scope-type/c-scope-type-delete-dialog.html',
                    controller: 'C_scope_typeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_scope_type', function(C_scope_type) {
                            return C_scope_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-scope-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
