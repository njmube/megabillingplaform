(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-vehicle-customs-information', {
            parent: 'entity',
            url: '/freecom-vehicle-customs-information?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_vehicle_customs_information.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-vehicle-customs-information/freecom-vehicle-customs-informations.html',
                    controller: 'Freecom_vehicle_customs_informationController',
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
                    $translatePartialLoader.addPart('freecom_vehicle_customs_information');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-vehicle-customs-information-detail', {
            parent: 'entity',
            url: '/freecom-vehicle-customs-information/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_vehicle_customs_information.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-vehicle-customs-information/freecom-vehicle-customs-information-detail.html',
                    controller: 'Freecom_vehicle_customs_informationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_vehicle_customs_information');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_vehicle_customs_information', function($stateParams, Freecom_vehicle_customs_information) {
                    return Freecom_vehicle_customs_information.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-vehicle-customs-information.new', {
            parent: 'freecom-vehicle-customs-information',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-vehicle-customs-information/freecom-vehicle-customs-information-dialog.html',
                    controller: 'Freecom_vehicle_customs_informationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                number: null,
                                date_expedition: null,
                                customs: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-vehicle-customs-information', null, { reload: true });
                }, function() {
                    $state.go('freecom-vehicle-customs-information');
                });
            }]
        })
        .state('freecom-vehicle-customs-information.edit', {
            parent: 'freecom-vehicle-customs-information',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-vehicle-customs-information/freecom-vehicle-customs-information-dialog.html',
                    controller: 'Freecom_vehicle_customs_informationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_vehicle_customs_information', function(Freecom_vehicle_customs_information) {
                            return Freecom_vehicle_customs_information.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-vehicle-customs-information', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-vehicle-customs-information.delete', {
            parent: 'freecom-vehicle-customs-information',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-vehicle-customs-information/freecom-vehicle-customs-information-delete-dialog.html',
                    controller: 'Freecom_vehicle_customs_informationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_vehicle_customs_information', function(Freecom_vehicle_customs_information) {
                            return Freecom_vehicle_customs_information.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-vehicle-customs-information', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
