(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-donees', {
            parent: 'entity',
            url: '/freecom-donees?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_donees.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-donees/freecom-donees.html',
                    controller: 'Freecom_doneesController',
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
                    $translatePartialLoader.addPart('freecom_donees');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-donees-detail', {
            parent: 'entity',
            url: '/freecom-donees/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_donees.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-donees/freecom-donees-detail.html',
                    controller: 'Freecom_doneesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_donees');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_donees', function($stateParams, Freecom_donees) {
                    return Freecom_donees.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-donees.new', {
            parent: 'freecom-donees',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-donees/freecom-donees-dialog.html',
                    controller: 'Freecom_doneesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                no_authorization: null,
                                date_authorization: null,
                                legend: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-donees', null, { reload: true });
                }, function() {
                    $state.go('freecom-donees');
                });
            }]
        })
        .state('freecom-donees.edit', {
            parent: 'freecom-donees',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-donees/freecom-donees-dialog.html',
                    controller: 'Freecom_doneesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_donees', function(Freecom_donees) {
                            return Freecom_donees.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-donees', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-donees.delete', {
            parent: 'freecom-donees',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-donees/freecom-donees-delete-dialog.html',
                    controller: 'Freecom_doneesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_donees', function(Freecom_donees) {
                            return Freecom_donees.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-donees', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
