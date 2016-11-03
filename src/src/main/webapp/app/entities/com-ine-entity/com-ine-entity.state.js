(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-ine-entity', {
            parent: 'entity',
            url: '/com-ine-entity?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_ine_entity.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-ine-entity/com-ine-entities.html',
                    controller: 'Com_ine_entityController',
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
                    $translatePartialLoader.addPart('com_ine_entity');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-ine-entity-detail', {
            parent: 'entity',
            url: '/com-ine-entity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_ine_entity.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-ine-entity/com-ine-entity-detail.html',
                    controller: 'Com_ine_entityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_ine_entity');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_ine_entity', function($stateParams, Com_ine_entity) {
                    return Com_ine_entity.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-ine-entity.new', {
            parent: 'com-ine-entity',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-ine-entity/com-ine-entity-dialog.html',
                    controller: 'Com_ine_entityDialogController',
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
                    $state.go('com-ine-entity', null, { reload: true });
                }, function() {
                    $state.go('com-ine-entity');
                });
            }]
        })
        .state('com-ine-entity.edit', {
            parent: 'com-ine-entity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-ine-entity/com-ine-entity-dialog.html',
                    controller: 'Com_ine_entityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_ine_entity', function(Com_ine_entity) {
                            return Com_ine_entity.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-ine-entity', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-ine-entity.delete', {
            parent: 'com-ine-entity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-ine-entity/com-ine-entity-delete-dialog.html',
                    controller: 'Com_ine_entityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_ine_entity', function(Com_ine_entity) {
                            return Com_ine_entity.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-ine-entity', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
