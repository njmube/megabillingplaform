(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-state', {
            parent: 'entity',
            url: '/type-state?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.type_state.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-state/type-states.html',
                    controller: 'Type_stateController',
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
                    $translatePartialLoader.addPart('type_state');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('type-state-detail', {
            parent: 'entity',
            url: '/type-state/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.type_state.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-state/type-state-detail.html',
                    controller: 'Type_stateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('type_state');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Type_state', function($stateParams, Type_state) {
                    return Type_state.get({id : $stateParams.id});
                }]
            }
        })
        .state('type-state.new', {
            parent: 'type-state',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-state/type-state-dialog.html',
                    controller: 'Type_stateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('type-state', null, { reload: true });
                }, function() {
                    $state.go('type-state');
                });
            }]
        })
        .state('type-state.edit', {
            parent: 'type-state',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-state/type-state-dialog.html',
                    controller: 'Type_stateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Type_state', function(Type_state) {
                            return Type_state.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-state', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-state.delete', {
            parent: 'type-state',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-state/type-state-delete-dialog.html',
                    controller: 'Type_stateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Type_state', function(Type_state) {
                            return Type_state.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-state', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
