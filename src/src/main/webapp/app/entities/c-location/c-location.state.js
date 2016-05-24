(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-location', {
            parent: 'entity',
            url: '/c-location?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_location.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-location/c-locations.html',
                    controller: 'C_locationController',
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
                    $translatePartialLoader.addPart('c_location');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-location-detail', {
            parent: 'entity',
            url: '/c-location/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_location.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-location/c-location-detail.html',
                    controller: 'C_locationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_location');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_location', function($stateParams, C_location) {
                    return C_location.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-location.new', {
            parent: 'c-location',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-location/c-location-dialog.html',
                    controller: 'C_locationDialogController',
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
                    $state.go('c-location', null, { reload: true });
                }, function() {
                    $state.go('c-location');
                });
            }]
        })
        .state('c-location.edit', {
            parent: 'c-location',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-location/c-location-dialog.html',
                    controller: 'C_locationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_location', function(C_location) {
                            return C_location.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-location', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-location.delete', {
            parent: 'c-location',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-location/c-location-delete-dialog.html',
                    controller: 'C_locationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_location', function(C_location) {
                            return C_location.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-location', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
