(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-airline', {
            parent: 'entity',
            url: '/com-airline?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_airline.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-airline/com-airlines.html',
                    controller: 'Com_airlineController',
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
                    $translatePartialLoader.addPart('com_airline');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-airline-detail', {
            parent: 'entity',
            url: '/com-airline/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_airline.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-airline/com-airline-detail.html',
                    controller: 'Com_airlineDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_airline');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_airline', function($stateParams, Com_airline) {
                    return Com_airline.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-airline.new', {
            parent: 'com-airline',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-airline/com-airline-dialog.html',
                    controller: 'Com_airlineDialogController',
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
                    $state.go('com-airline', null, { reload: true });
                }, function() {
                    $state.go('com-airline');
                });
            }]
        })
        .state('com-airline.edit', {
            parent: 'com-airline',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-airline/com-airline-dialog.html',
                    controller: 'Com_airlineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_airline', function(Com_airline) {
                            return Com_airline.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-airline', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-airline.delete', {
            parent: 'com-airline',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-airline/com-airline-delete-dialog.html',
                    controller: 'Com_airlineDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_airline', function(Com_airline) {
                            return Com_airline.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-airline', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
