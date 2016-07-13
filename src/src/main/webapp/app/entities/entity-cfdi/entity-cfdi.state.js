(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('entity-cfdi', {
            parent: 'entity',
            url: '/entity-cfdi?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.entity_cfdi.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entity-cfdi/entity-cfdis.html',
                    controller: 'Entity_cfdiController',
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
                    $translatePartialLoader.addPart('entity_cfdi');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('entity-cfdi-detail', {
            parent: 'entity',
            url: '/entity-cfdi/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.entity_cfdi.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/entity-cfdi/entity-cfdi-detail.html',
                    controller: 'Entity_cfdiDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('entity_cfdi');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Entity_cfdi', function($stateParams, Entity_cfdi) {
                    return Entity_cfdi.get({id : $stateParams.id});
                }]
            }
        })
        .state('entity-cfdi.new', {
            parent: 'entity-cfdi',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entity-cfdi/entity-cfdi-dialog.html',
                    controller: 'Entity_cfdiDialogController',
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
                    $state.go('entity-cfdi', null, { reload: true });
                }, function() {
                    $state.go('entity-cfdi');
                });
            }]
        })
        .state('entity-cfdi.edit', {
            parent: 'entity-cfdi',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entity-cfdi/entity-cfdi-dialog.html',
                    controller: 'Entity_cfdiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Entity_cfdi', function(Entity_cfdi) {
                            return Entity_cfdi.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('entity-cfdi', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('entity-cfdi.delete', {
            parent: 'entity-cfdi',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/entity-cfdi/entity-cfdi-delete-dialog.html',
                    controller: 'Entity_cfdiDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Entity_cfdi', function(Entity_cfdi) {
                            return Entity_cfdi.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('entity-cfdi', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
