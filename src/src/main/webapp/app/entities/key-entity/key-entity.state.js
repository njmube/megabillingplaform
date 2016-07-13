(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('key-entity', {
            parent: 'entity',
            url: '/key-entity?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.key_entity.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/key-entity/key-entities.html',
                    controller: 'Key_entityController',
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
                    $translatePartialLoader.addPart('key_entity');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('key-entity-detail', {
            parent: 'entity',
            url: '/key-entity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.key_entity.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/key-entity/key-entity-detail.html',
                    controller: 'Key_entityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('key_entity');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Key_entity', function($stateParams, Key_entity) {
                    return Key_entity.get({id : $stateParams.id});
                }]
            }
        })
        .state('key-entity.new', {
            parent: 'key-entity',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/key-entity/key-entity-dialog.html',
                    controller: 'Key_entityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                key: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('key-entity', null, { reload: true });
                }, function() {
                    $state.go('key-entity');
                });
            }]
        })
        .state('key-entity.edit', {
            parent: 'key-entity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/key-entity/key-entity-dialog.html',
                    controller: 'Key_entityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Key_entity', function(Key_entity) {
                            return Key_entity.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('key-entity', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('key-entity.delete', {
            parent: 'key-entity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/key-entity/key-entity-delete-dialog.html',
                    controller: 'Key_entityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Key_entity', function(Key_entity) {
                            return Key_entity.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('key-entity', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
