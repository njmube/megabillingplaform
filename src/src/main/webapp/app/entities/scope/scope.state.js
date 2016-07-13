(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('scope', {
            parent: 'entity',
            url: '/scope?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.scope.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/scope/scopes.html',
                    controller: 'ScopeController',
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
                    $translatePartialLoader.addPart('scope');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('scope-detail', {
            parent: 'entity',
            url: '/scope/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.scope.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/scope/scope-detail.html',
                    controller: 'ScopeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('scope');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Scope', function($stateParams, Scope) {
                    return Scope.get({id : $stateParams.id});
                }]
            }
        })
        .state('scope.new', {
            parent: 'scope',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scope/scope-dialog.html',
                    controller: 'ScopeDialogController',
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
                    $state.go('scope', null, { reload: true });
                }, function() {
                    $state.go('scope');
                });
            }]
        })
        .state('scope.edit', {
            parent: 'scope',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scope/scope-dialog.html',
                    controller: 'ScopeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Scope', function(Scope) {
                            return Scope.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('scope', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('scope.delete', {
            parent: 'scope',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/scope/scope-delete-dialog.html',
                    controller: 'ScopeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Scope', function(Scope) {
                            return Scope.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('scope', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
