(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-used-vehicle', {
            parent: 'entity',
            url: '/com-used-vehicle?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_used_vehicle.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-used-vehicle/com-used-vehicles.html',
                    controller: 'Com_used_vehicleController',
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
                    $translatePartialLoader.addPart('com_used_vehicle');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-used-vehicle-detail', {
            parent: 'entity',
            url: '/com-used-vehicle/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_used_vehicle.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-used-vehicle/com-used-vehicle-detail.html',
                    controller: 'Com_used_vehicleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_used_vehicle');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_used_vehicle', function($stateParams, Com_used_vehicle) {
                    return Com_used_vehicle.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-used-vehicle.new', {
            parent: 'com-used-vehicle',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-used-vehicle/com-used-vehicle-dialog.html',
                    controller: 'Com_used_vehicleDialogController',
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
                    $state.go('com-used-vehicle', null, { reload: true });
                }, function() {
                    $state.go('com-used-vehicle');
                });
            }]
        })
        .state('com-used-vehicle.edit', {
            parent: 'com-used-vehicle',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-used-vehicle/com-used-vehicle-dialog.html',
                    controller: 'Com_used_vehicleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_used_vehicle', function(Com_used_vehicle) {
                            return Com_used_vehicle.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-used-vehicle', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-used-vehicle.delete', {
            parent: 'com-used-vehicle',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-used-vehicle/com-used-vehicle-delete-dialog.html',
                    controller: 'Com_used_vehicleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_used_vehicle', function(Com_used_vehicle) {
                            return Com_used_vehicle.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-used-vehicle', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
