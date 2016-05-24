(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-state', {
            parent: 'entity',
            url: '/c-state?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_state.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-state/c-states.html',
                    controller: 'C_stateController',
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
                    $translatePartialLoader.addPart('c_state');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-state-detail', {
            parent: 'entity',
            url: '/c-state/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_state.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-state/c-state-detail.html',
                    controller: 'C_stateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_state');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_state', function($stateParams, C_state) {
                    return C_state.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-state.new', {
            parent: 'c-state',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-state/c-state-dialog.html',
                    controller: 'C_stateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                abrev: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-state', null, { reload: true });
                }, function() {
                    $state.go('c-state');
                });
            }]
        })
        .state('c-state.edit', {
            parent: 'c-state',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-state/c-state-dialog.html',
                    controller: 'C_stateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_state', function(C_state) {
                            return C_state.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-state', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-state.delete', {
            parent: 'c-state',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-state/c-state-delete-dialog.html',
                    controller: 'C_stateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_state', function(C_state) {
                            return C_state.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-state', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
