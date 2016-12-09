(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('public-notaries-federal-entity', {
            parent: 'entity',
            url: '/public-notaries-federal-entity?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.public_notaries_federal_entity.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/public-notaries-federal-entity/public-notaries-federal-entities.html',
                    controller: 'Public_notaries_federal_entityController',
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
                    $translatePartialLoader.addPart('public_notaries_federal_entity');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('public-notaries-federal-entity-detail', {
            parent: 'entity',
            url: '/public-notaries-federal-entity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.public_notaries_federal_entity.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/public-notaries-federal-entity/public-notaries-federal-entity-detail.html',
                    controller: 'Public_notaries_federal_entityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('public_notaries_federal_entity');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Public_notaries_federal_entity', function($stateParams, Public_notaries_federal_entity) {
                    return Public_notaries_federal_entity.get({id : $stateParams.id});
                }]
            }
        })
        .state('public-notaries-federal-entity.new', {
            parent: 'public-notaries-federal-entity',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/public-notaries-federal-entity/public-notaries-federal-entity-dialog.html',
                    controller: 'Public_notaries_federal_entityDialogController',
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
                    $state.go('public-notaries-federal-entity', null, { reload: true });
                }, function() {
                    $state.go('public-notaries-federal-entity');
                });
            }]
        })
        .state('public-notaries-federal-entity.edit', {
            parent: 'public-notaries-federal-entity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/public-notaries-federal-entity/public-notaries-federal-entity-dialog.html',
                    controller: 'Public_notaries_federal_entityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Public_notaries_federal_entity', function(Public_notaries_federal_entity) {
                            return Public_notaries_federal_entity.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('public-notaries-federal-entity', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('public-notaries-federal-entity.delete', {
            parent: 'public-notaries-federal-entity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/public-notaries-federal-entity/public-notaries-federal-entity-delete-dialog.html',
                    controller: 'Public_notaries_federal_entityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Public_notaries_federal_entity', function(Public_notaries_federal_entity) {
                            return Public_notaries_federal_entity.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('public-notaries-federal-entity', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
