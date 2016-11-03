(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-foreign-tourist-passenger', {
            parent: 'entity',
            url: '/com-foreign-tourist-passenger?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_foreign_tourist_passenger.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-foreign-tourist-passenger/com-foreign-tourist-passengers.html',
                    controller: 'Com_foreign_tourist_passengerController',
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
                    $translatePartialLoader.addPart('com_foreign_tourist_passenger');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-foreign-tourist-passenger-detail', {
            parent: 'entity',
            url: '/com-foreign-tourist-passenger/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_foreign_tourist_passenger.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-foreign-tourist-passenger/com-foreign-tourist-passenger-detail.html',
                    controller: 'Com_foreign_tourist_passengerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_foreign_tourist_passenger');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_foreign_tourist_passenger', function($stateParams, Com_foreign_tourist_passenger) {
                    return Com_foreign_tourist_passenger.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-foreign-tourist-passenger.new', {
            parent: 'com-foreign-tourist-passenger',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-foreign-tourist-passenger/com-foreign-tourist-passenger-dialog.html',
                    controller: 'Com_foreign_tourist_passengerDialogController',
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
                    $state.go('com-foreign-tourist-passenger', null, { reload: true });
                }, function() {
                    $state.go('com-foreign-tourist-passenger');
                });
            }]
        })
        .state('com-foreign-tourist-passenger.edit', {
            parent: 'com-foreign-tourist-passenger',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-foreign-tourist-passenger/com-foreign-tourist-passenger-dialog.html',
                    controller: 'Com_foreign_tourist_passengerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_foreign_tourist_passenger', function(Com_foreign_tourist_passenger) {
                            return Com_foreign_tourist_passenger.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-foreign-tourist-passenger', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-foreign-tourist-passenger.delete', {
            parent: 'com-foreign-tourist-passenger',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-foreign-tourist-passenger/com-foreign-tourist-passenger-delete-dialog.html',
                    controller: 'Com_foreign_tourist_passengerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_foreign_tourist_passenger', function(Com_foreign_tourist_passenger) {
                            return Com_foreign_tourist_passenger.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-foreign-tourist-passenger', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
