(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-fuel-consumption', {
            parent: 'entity',
            url: '/freecom-fuel-consumption?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_fuel_consumption.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-fuel-consumption/freecom-fuel-consumptions.html',
                    controller: 'Freecom_fuel_consumptionController',
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
                    $translatePartialLoader.addPart('freecom_fuel_consumption');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-fuel-consumption-detail', {
            parent: 'entity',
            url: '/freecom-fuel-consumption/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_fuel_consumption.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-fuel-consumption/freecom-fuel-consumption-detail.html',
                    controller: 'Freecom_fuel_consumptionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_fuel_consumption');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_fuel_consumption', function($stateParams, Freecom_fuel_consumption) {
                    return Freecom_fuel_consumption.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-fuel-consumption.new', {
            parent: 'freecom-fuel-consumption',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-fuel-consumption/freecom-fuel-consumption-dialog.html',
                    controller: 'Freecom_fuel_consumptionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                type_operation: null,
                                account_number: null,
                                subtotal: null,
                                total: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-fuel-consumption', null, { reload: true });
                }, function() {
                    $state.go('freecom-fuel-consumption');
                });
            }]
        })
        .state('freecom-fuel-consumption.edit', {
            parent: 'freecom-fuel-consumption',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-fuel-consumption/freecom-fuel-consumption-dialog.html',
                    controller: 'Freecom_fuel_consumptionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_fuel_consumption', function(Freecom_fuel_consumption) {
                            return Freecom_fuel_consumption.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-fuel-consumption', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-fuel-consumption.delete', {
            parent: 'freecom-fuel-consumption',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-fuel-consumption/freecom-fuel-consumption-delete-dialog.html',
                    controller: 'Freecom_fuel_consumptionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_fuel_consumption', function(Freecom_fuel_consumption) {
                            return Freecom_fuel_consumption.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-fuel-consumption', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
