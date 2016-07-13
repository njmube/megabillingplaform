(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('legend', {
            parent: 'entity',
            url: '/legend?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.legend.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/legend/legends.html',
                    controller: 'LegendController',
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
                    $translatePartialLoader.addPart('legend');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('legend-detail', {
            parent: 'entity',
            url: '/legend/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.legend.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/legend/legend-detail.html',
                    controller: 'LegendDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('legend');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Legend', function($stateParams, Legend) {
                    return Legend.get({id : $stateParams.id});
                }]
            }
        })
        .state('legend.new', {
            parent: 'legend',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/legend/legend-dialog.html',
                    controller: 'LegendDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                tax_provision: null,
                                norm: null,
                                text_legend: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('legend', null, { reload: true });
                }, function() {
                    $state.go('legend');
                });
            }]
        })
        .state('legend.edit', {
            parent: 'legend',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/legend/legend-dialog.html',
                    controller: 'LegendDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Legend', function(Legend) {
                            return Legend.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('legend', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('legend.delete', {
            parent: 'legend',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/legend/legend-delete-dialog.html',
                    controller: 'LegendDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Legend', function(Legend) {
                            return Legend.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('legend', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
