(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-apaw', {
            parent: 'entity',
            url: '/freecom-apaw?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_apaw.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-apaw/freecom-apaws.html',
                    controller: 'Freecom_apawController',
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
                    $translatePartialLoader.addPart('freecom_apaw');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-apaw-detail', {
            parent: 'entity',
            url: '/freecom-apaw/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_apaw.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-apaw/freecom-apaw-detail.html',
                    controller: 'Freecom_apawDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_apaw');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_apaw', function($stateParams, Freecom_apaw) {
                    return Freecom_apaw.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-apaw.new', {
            parent: 'freecom-apaw',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-apaw/freecom-apaw-dialog.html',
                    controller: 'Freecom_apawDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                others_well_type: null,
                                others_acquired_title: null,
                                subtotal: null,
                                iva: null,
                                date_acquisition: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-apaw', null, { reload: true });
                }, function() {
                    $state.go('freecom-apaw');
                });
            }]
        })
        .state('freecom-apaw.edit', {
            parent: 'freecom-apaw',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-apaw/freecom-apaw-dialog.html',
                    controller: 'Freecom_apawDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_apaw', function(Freecom_apaw) {
                            return Freecom_apaw.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-apaw', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-apaw.delete', {
            parent: 'freecom-apaw',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-apaw/freecom-apaw-delete-dialog.html',
                    controller: 'Freecom_apawDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_apaw', function(Freecom_apaw) {
                            return Freecom_apaw.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-apaw', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
