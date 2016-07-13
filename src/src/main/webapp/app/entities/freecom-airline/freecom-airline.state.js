(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-airline', {
            parent: 'entity',
            url: '/freecom-airline?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_airline.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-airline/freecom-airlines.html',
                    controller: 'Freecom_airlineController',
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
                    $translatePartialLoader.addPart('freecom_airline');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-airline-detail', {
            parent: 'entity',
            url: '/freecom-airline/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_airline.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-airline/freecom-airline-detail.html',
                    controller: 'Freecom_airlineDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_airline');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_airline', function($stateParams, Freecom_airline) {
                    return Freecom_airline.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-airline.new', {
            parent: 'freecom-airline',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-airline/freecom-airline-dialog.html',
                    controller: 'Freecom_airlineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                tua: null,
                                total_charge: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-airline', null, { reload: true });
                }, function() {
                    $state.go('freecom-airline');
                });
            }]
        })
        .state('freecom-airline.edit', {
            parent: 'freecom-airline',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-airline/freecom-airline-dialog.html',
                    controller: 'Freecom_airlineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_airline', function(Freecom_airline) {
                            return Freecom_airline.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-airline', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-airline.delete', {
            parent: 'freecom-airline',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-airline/freecom-airline-delete-dialog.html',
                    controller: 'Freecom_airlineDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_airline', function(Freecom_airline) {
                            return Freecom_airline.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-airline', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
