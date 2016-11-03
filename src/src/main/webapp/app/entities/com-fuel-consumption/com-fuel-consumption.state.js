(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-fuel-consumption', {
            parent: 'entity',
            url: '/com-fuel-consumption?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_fuel_consumption.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-fuel-consumption/com-fuel-consumptions.html',
                    controller: 'Com_fuel_consumptionController',
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
                    $translatePartialLoader.addPart('com_fuel_consumption');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-fuel-consumption-detail', {
            parent: 'entity',
            url: '/com-fuel-consumption/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_fuel_consumption.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-fuel-consumption/com-fuel-consumption-detail.html',
                    controller: 'Com_fuel_consumptionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_fuel_consumption');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_fuel_consumption', function($stateParams, Com_fuel_consumption) {
                    return Com_fuel_consumption.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-fuel-consumption.new', {
            parent: 'com-fuel-consumption',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-fuel-consumption/com-fuel-consumption-dialog.html',
                    controller: 'Com_fuel_consumptionDialogController',
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
                    $state.go('com-fuel-consumption', null, { reload: true });
                }, function() {
                    $state.go('com-fuel-consumption');
                });
            }]
        })
        .state('com-fuel-consumption.edit', {
            parent: 'com-fuel-consumption',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-fuel-consumption/com-fuel-consumption-dialog.html',
                    controller: 'Com_fuel_consumptionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_fuel_consumption', function(Com_fuel_consumption) {
                            return Com_fuel_consumption.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-fuel-consumption', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-fuel-consumption.delete', {
            parent: 'com-fuel-consumption',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-fuel-consumption/com-fuel-consumption-delete-dialog.html',
                    controller: 'Com_fuel_consumptionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_fuel_consumption', function(Com_fuel_consumption) {
                            return Com_fuel_consumption.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-fuel-consumption', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
