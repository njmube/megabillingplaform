(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-colony', {
            parent: 'entity',
            url: '/c-colony?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_colony.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-colony/c-colonies.html',
                    controller: 'C_colonyController',
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
                    $translatePartialLoader.addPart('c_colony');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-colony-detail', {
            parent: 'entity',
            url: '/c-colony/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_colony.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-colony/c-colony-detail.html',
                    controller: 'C_colonyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_colony');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_colony', function($stateParams, C_colony) {
                    return C_colony.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-colony.new', {
            parent: 'c-colony',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-colony/c-colony-dialog.html',
                    controller: 'C_colonyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-colony', null, { reload: true });
                }, function() {
                    $state.go('c-colony');
                });
            }]
        })
        .state('c-colony.edit', {
            parent: 'c-colony',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-colony/c-colony-dialog.html',
                    controller: 'C_colonyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_colony', function(C_colony) {
                            return C_colony.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-colony', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-colony.delete', {
            parent: 'c-colony',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-colony/c-colony-delete-dialog.html',
                    controller: 'C_colonyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_colony', function(C_colony) {
                            return C_colony.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-colony', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
