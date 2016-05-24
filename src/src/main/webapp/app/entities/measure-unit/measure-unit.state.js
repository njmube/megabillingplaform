(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('measure-unit', {
            parent: 'entity',
            url: '/measure-unit?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.measure_unit.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/measure-unit/measure-units.html',
                    controller: 'Measure_unitController',
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
                    $translatePartialLoader.addPart('measure_unit');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('measure-unit-detail', {
            parent: 'entity',
            url: '/measure-unit/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.measure_unit.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/measure-unit/measure-unit-detail.html',
                    controller: 'Measure_unitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('measure_unit');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Measure_unit', function($stateParams, Measure_unit) {
                    return Measure_unit.get({id : $stateParams.id});
                }]
            }
        })
        .state('measure-unit.new', {
            parent: 'measure-unit',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/measure-unit/measure-unit-dialog.html',
                    controller: 'Measure_unitDialogController',
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
                    $state.go('measure-unit', null, { reload: true });
                }, function() {
                    $state.go('measure-unit');
                });
            }]
        })
        .state('measure-unit.edit', {
            parent: 'measure-unit',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/measure-unit/measure-unit-dialog.html',
                    controller: 'Measure_unitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Measure_unit', function(Measure_unit) {
                            return Measure_unit.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('measure-unit', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('measure-unit.delete', {
            parent: 'measure-unit',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/measure-unit/measure-unit-delete-dialog.html',
                    controller: 'Measure_unitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Measure_unit', function(Measure_unit) {
                            return Measure_unit.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('measure-unit', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
