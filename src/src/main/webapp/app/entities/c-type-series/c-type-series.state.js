(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-type-series', {
            parent: 'entity',
            url: '/c-type-series?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_type_series.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-type-series/c-type-series.html',
                    controller: 'C_type_seriesController',
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
                    $translatePartialLoader.addPart('c_type_series');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-type-series-detail', {
            parent: 'entity',
            url: '/c-type-series/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_type_series.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-type-series/c-type-series-detail.html',
                    controller: 'C_type_seriesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_type_series');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_type_series', function($stateParams, C_type_series) {
                    return C_type_series.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('c-type-series.new', {
            parent: 'c-type-series',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-type-series/c-type-series-dialog.html',
                    controller: 'C_type_seriesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-type-series', null, { reload: true });
                }, function() {
                    $state.go('c-type-series');
                });
            }]
        })
        .state('c-type-series.edit', {
            parent: 'c-type-series',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-type-series/c-type-series-dialog.html',
                    controller: 'C_type_seriesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_type_series', function(C_type_series) {
                            return C_type_series.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-type-series', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-type-series.delete', {
            parent: 'c-type-series',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-type-series/c-type-series-delete-dialog.html',
                    controller: 'C_type_seriesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_type_series', function(C_type_series) {
                            return C_type_series.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-type-series', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
