(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-used-vehicle', {
            parent: 'entity',
            url: '/freecom-used-vehicle?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_used_vehicle.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-used-vehicle/freecom-used-vehicles.html',
                    controller: 'Freecom_used_vehicleController',
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
                    $translatePartialLoader.addPart('freecom_used_vehicle');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-used-vehicle-detail', {
            parent: 'entity',
            url: '/freecom-used-vehicle/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_used_vehicle.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-used-vehicle/freecom-used-vehicle-detail.html',
                    controller: 'Freecom_used_vehicleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_used_vehicle');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_used_vehicle', function($stateParams, Freecom_used_vehicle) {
                    return Freecom_used_vehicle.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-used-vehicle.new', {
            parent: 'freecom-used-vehicle',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-used-vehicle/freecom-used-vehicle-dialog.html',
                    controller: 'Freecom_used_vehicleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                acquisition_amount: null,
                                monto_enajenacion: null,
                                key_vehicle: null,
                                brand: null,
                                type: null,
                                model: null,
                                number_engine: null,
                                no_serie: null,
                                niv: null,
                                value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-used-vehicle', null, { reload: true });
                }, function() {
                    $state.go('freecom-used-vehicle');
                });
            }]
        })
        .state('freecom-used-vehicle.edit', {
            parent: 'freecom-used-vehicle',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-used-vehicle/freecom-used-vehicle-dialog.html',
                    controller: 'Freecom_used_vehicleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_used_vehicle', function(Freecom_used_vehicle) {
                            return Freecom_used_vehicle.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-used-vehicle', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-used-vehicle.delete', {
            parent: 'freecom-used-vehicle',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-used-vehicle/freecom-used-vehicle-delete-dialog.html',
                    controller: 'Freecom_used_vehicleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_used_vehicle', function(Freecom_used_vehicle) {
                            return Freecom_used_vehicle.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-used-vehicle', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
