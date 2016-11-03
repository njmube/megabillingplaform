(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-vehicle-customs-information', {
            parent: 'entity',
            url: '/com-vehicle-customs-information?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_vehicle_customs_information.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-vehicle-customs-information/com-vehicle-customs-informations.html',
                    controller: 'Com_vehicle_customs_informationController',
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
                    $translatePartialLoader.addPart('com_vehicle_customs_information');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-vehicle-customs-information-detail', {
            parent: 'entity',
            url: '/com-vehicle-customs-information/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_vehicle_customs_information.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-vehicle-customs-information/com-vehicle-customs-information-detail.html',
                    controller: 'Com_vehicle_customs_informationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_vehicle_customs_information');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_vehicle_customs_information', function($stateParams, Com_vehicle_customs_information) {
                    return Com_vehicle_customs_information.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-vehicle-customs-information.new', {
            parent: 'com-vehicle-customs-information',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-vehicle-customs-information/com-vehicle-customs-information-dialog.html',
                    controller: 'Com_vehicle_customs_informationDialogController',
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
                    $state.go('com-vehicle-customs-information', null, { reload: true });
                }, function() {
                    $state.go('com-vehicle-customs-information');
                });
            }]
        })
        .state('com-vehicle-customs-information.edit', {
            parent: 'com-vehicle-customs-information',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-vehicle-customs-information/com-vehicle-customs-information-dialog.html',
                    controller: 'Com_vehicle_customs_informationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_vehicle_customs_information', function(Com_vehicle_customs_information) {
                            return Com_vehicle_customs_information.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-vehicle-customs-information', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-vehicle-customs-information.delete', {
            parent: 'com-vehicle-customs-information',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-vehicle-customs-information/com-vehicle-customs-information-delete-dialog.html',
                    controller: 'Com_vehicle_customs_informationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_vehicle_customs_information', function(Com_vehicle_customs_information) {
                            return Com_vehicle_customs_information.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-vehicle-customs-information', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
