(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-ine-entity', {
            parent: 'entity',
            url: '/freecom-ine-entity?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_ine_entity.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-ine-entity/freecom-ine-entities.html',
                    controller: 'Freecom_ine_entityController',
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
                    $translatePartialLoader.addPart('freecom_ine_entity');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-ine-entity-detail', {
            parent: 'entity',
            url: '/freecom-ine-entity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_ine_entity.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-ine-entity/freecom-ine-entity-detail.html',
                    controller: 'Freecom_ine_entityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_ine_entity');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_ine_entity', function($stateParams, Freecom_ine_entity) {
                    return Freecom_ine_entity.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-ine-entity.new', {
            parent: 'freecom-ine-entity',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-ine-entity/freecom-ine-entity-dialog.html',
                    controller: 'Freecom_ine_entityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-ine-entity', null, { reload: true });
                }, function() {
                    $state.go('freecom-ine-entity');
                });
            }]
        })
        .state('freecom-ine-entity.edit', {
            parent: 'freecom-ine-entity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-ine-entity/freecom-ine-entity-dialog.html',
                    controller: 'Freecom_ine_entityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_ine_entity', function(Freecom_ine_entity) {
                            return Freecom_ine_entity.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-ine-entity', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-ine-entity.delete', {
            parent: 'freecom-ine-entity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-ine-entity/freecom-ine-entity-delete-dialog.html',
                    controller: 'Freecom_ine_entityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_ine_entity', function(Freecom_ine_entity) {
                            return Freecom_ine_entity.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-ine-entity', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
