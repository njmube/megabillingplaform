(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-pn-federal-entity', {
            parent: 'entity',
            url: '/c-pn-federal-entity?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_pn_federal_entity.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-pn-federal-entity/c-pn-federal-entities.html',
                    controller: 'C_pn_federal_entityController',
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
                    $translatePartialLoader.addPart('c_pn_federal_entity');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-pn-federal-entity-detail', {
            parent: 'entity',
            url: '/c-pn-federal-entity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_pn_federal_entity.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-pn-federal-entity/c-pn-federal-entity-detail.html',
                    controller: 'C_pn_federal_entityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_pn_federal_entity');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_pn_federal_entity', function($stateParams, C_pn_federal_entity) {
                    return C_pn_federal_entity.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-pn-federal-entity.new', {
            parent: 'c-pn-federal-entity',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-pn-federal-entity/c-pn-federal-entity-dialog.html',
                    controller: 'C_pn_federal_entityDialogController',
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
                    $state.go('c-pn-federal-entity', null, { reload: true });
                }, function() {
                    $state.go('c-pn-federal-entity');
                });
            }]
        })
        .state('c-pn-federal-entity.edit', {
            parent: 'c-pn-federal-entity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-pn-federal-entity/c-pn-federal-entity-dialog.html',
                    controller: 'C_pn_federal_entityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_pn_federal_entity', function(C_pn_federal_entity) {
                            return C_pn_federal_entity.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-pn-federal-entity', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-pn-federal-entity.delete', {
            parent: 'c-pn-federal-entity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-pn-federal-entity/c-pn-federal-entity-delete-dialog.html',
                    controller: 'C_pn_federal_entityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_pn_federal_entity', function(C_pn_federal_entity) {
                            return C_pn_federal_entity.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-pn-federal-entity', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
