(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-foreign-tourist-passenger', {
            parent: 'entity',
            url: '/freecom-foreign-tourist-passenger?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_foreign_tourist_passenger.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-foreign-tourist-passenger/freecom-foreign-tourist-passengers.html',
                    controller: 'Freecom_foreign_tourist_passengerController',
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
                    $translatePartialLoader.addPart('freecom_foreign_tourist_passenger');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-foreign-tourist-passenger-detail', {
            parent: 'entity',
            url: '/freecom-foreign-tourist-passenger/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_foreign_tourist_passenger.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-foreign-tourist-passenger/freecom-foreign-tourist-passenger-detail.html',
                    controller: 'Freecom_foreign_tourist_passengerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_foreign_tourist_passenger');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_foreign_tourist_passenger', function($stateParams, Freecom_foreign_tourist_passenger) {
                    return Freecom_foreign_tourist_passenger.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-foreign-tourist-passenger.new', {
            parent: 'freecom-foreign-tourist-passenger',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-foreign-tourist-passenger/freecom-foreign-tourist-passenger-dialog.html',
                    controller: 'Freecom_foreign_tourist_passengerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                date_traffic: null,
                                typeid: null,
                                numerid: null,
                                nationality: null,
                                transportcompany: null,
                                idtransport: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-foreign-tourist-passenger', null, { reload: true });
                }, function() {
                    $state.go('freecom-foreign-tourist-passenger');
                });
            }]
        })
        .state('freecom-foreign-tourist-passenger.edit', {
            parent: 'freecom-foreign-tourist-passenger',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-foreign-tourist-passenger/freecom-foreign-tourist-passenger-dialog.html',
                    controller: 'Freecom_foreign_tourist_passengerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_foreign_tourist_passenger', function(Freecom_foreign_tourist_passenger) {
                            return Freecom_foreign_tourist_passenger.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-foreign-tourist-passenger', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-foreign-tourist-passenger.delete', {
            parent: 'freecom-foreign-tourist-passenger',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-foreign-tourist-passenger/freecom-foreign-tourist-passenger-delete-dialog.html',
                    controller: 'Freecom_foreign_tourist_passengerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_foreign_tourist_passenger', function(Freecom_foreign_tourist_passenger) {
                            return Freecom_foreign_tourist_passenger.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-foreign-tourist-passenger', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
