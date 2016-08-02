(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-federal-entity', {
            parent: 'entity',
            url: '/c-federal-entity?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_federal_entity.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-federal-entity/c-federal-entities.html',
                    controller: 'C_federal_entityController',
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
                    $translatePartialLoader.addPart('c_federal_entity');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-federal-entity-detail', {
            parent: 'entity',
            url: '/c-federal-entity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_federal_entity.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-federal-entity/c-federal-entity-detail.html',
                    controller: 'C_federal_entityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_federal_entity');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_federal_entity', function($stateParams, C_federal_entity) {
                    return C_federal_entity.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-federal-entity.new', {
            parent: 'c-federal-entity',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-federal-entity/c-federal-entity-dialog.html',
                    controller: 'C_federal_entityDialogController',
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
                    $state.go('c-federal-entity', null, { reload: true });
                }, function() {
                    $state.go('c-federal-entity');
                });
            }]
        })
        .state('c-federal-entity.edit', {
            parent: 'c-federal-entity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-federal-entity/c-federal-entity-dialog.html',
                    controller: 'C_federal_entityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_federal_entity', function(C_federal_entity) {
                            return C_federal_entity.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-federal-entity', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-federal-entity.delete', {
            parent: 'c-federal-entity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-federal-entity/c-federal-entity-delete-dialog.html',
                    controller: 'C_federal_entityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_federal_entity', function(C_federal_entity) {
                            return C_federal_entity.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-federal-entity', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
