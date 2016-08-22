(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-legend', {
            parent: 'entity',
            url: '/freecom-legend?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_legend.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-legend/freecom-legends.html',
                    controller: 'Freecom_legendController',
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
                    $translatePartialLoader.addPart('freecom_legend');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-legend-detail', {
            parent: 'entity',
            url: '/freecom-legend/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_legend.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-legend/freecom-legend-detail.html',
                    controller: 'Freecom_legendDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_legend');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_legend', function($stateParams, Freecom_legend) {
                    return Freecom_legend.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('freecom-legend.new', {
            parent: 'freecom-legend',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-legend/freecom-legend-dialog.html',
                    controller: 'Freecom_legendDialogController',
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
                    $state.go('freecom-legend', null, { reload: true });
                }, function() {
                    $state.go('freecom-legend');
                });
            }]
        })
        .state('freecom-legend.edit', {
            parent: 'freecom-legend',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-legend/freecom-legend-dialog.html',
                    controller: 'Freecom_legendDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_legend', function(Freecom_legend) {
                            return Freecom_legend.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-legend', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-legend.delete', {
            parent: 'freecom-legend',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-legend/freecom-legend-delete-dialog.html',
                    controller: 'Freecom_legendDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_legend', function(Freecom_legend) {
                            return Freecom_legend.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-legend', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
