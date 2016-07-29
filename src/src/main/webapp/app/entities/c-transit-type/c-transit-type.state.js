(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-transit-type', {
            parent: 'entity',
            url: '/c-transit-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_transit_type.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-transit-type/c-transit-types.html',
                    controller: 'C_transit_typeController',
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
                    $translatePartialLoader.addPart('c_transit_type');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-transit-type-detail', {
            parent: 'entity',
            url: '/c-transit-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_transit_type.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-transit-type/c-transit-type-detail.html',
                    controller: 'C_transit_typeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_transit_type');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_transit_type', function($stateParams, C_transit_type) {
                    return C_transit_type.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-transit-type.new', {
            parent: 'c-transit-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-transit-type/c-transit-type-dialog.html',
                    controller: 'C_transit_typeDialogController',
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
                    $state.go('c-transit-type', null, { reload: true });
                }, function() {
                    $state.go('c-transit-type');
                });
            }]
        })
        .state('c-transit-type.edit', {
            parent: 'c-transit-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-transit-type/c-transit-type-dialog.html',
                    controller: 'C_transit_typeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_transit_type', function(C_transit_type) {
                            return C_transit_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-transit-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-transit-type.delete', {
            parent: 'c-transit-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-transit-type/c-transit-type-delete-dialog.html',
                    controller: 'C_transit_typeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_transit_type', function(C_transit_type) {
                            return C_transit_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-transit-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
