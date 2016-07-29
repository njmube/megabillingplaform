(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-type-road', {
            parent: 'entity',
            url: '/c-type-road?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_type_road.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-type-road/c-type-roads.html',
                    controller: 'C_type_roadController',
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
                    $translatePartialLoader.addPart('c_type_road');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-type-road-detail', {
            parent: 'entity',
            url: '/c-type-road/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_type_road.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-type-road/c-type-road-detail.html',
                    controller: 'C_type_roadDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_type_road');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_type_road', function($stateParams, C_type_road) {
                    return C_type_road.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-type-road.new', {
            parent: 'c-type-road',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-type-road/c-type-road-dialog.html',
                    controller: 'C_type_roadDialogController',
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
                    $state.go('c-type-road', null, { reload: true });
                }, function() {
                    $state.go('c-type-road');
                });
            }]
        })
        .state('c-type-road.edit', {
            parent: 'c-type-road',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-type-road/c-type-road-dialog.html',
                    controller: 'C_type_roadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_type_road', function(C_type_road) {
                            return C_type_road.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-type-road', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-type-road.delete', {
            parent: 'c-type-road',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-type-road/c-type-road-delete-dialog.html',
                    controller: 'C_type_roadDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_type_road', function(C_type_road) {
                            return C_type_road.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-type-road', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
