(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-acquiring-data', {
            parent: 'entity',
            url: '/freecom-acquiring-data?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_acquiring_data.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-acquiring-data/freecom-acquiring-data.html',
                    controller: 'Freecom_acquiring_dataController',
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
                    $translatePartialLoader.addPart('freecom_acquiring_data');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-acquiring-data-detail', {
            parent: 'entity',
            url: '/freecom-acquiring-data/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_acquiring_data.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-acquiring-data/freecom-acquiring-data-detail.html',
                    controller: 'Freecom_acquiring_dataDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_acquiring_data');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_acquiring_data', function($stateParams, Freecom_acquiring_data) {
                    return Freecom_acquiring_data.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-acquiring-data.new', {
            parent: 'freecom-acquiring-data',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-acquiring-data/freecom-acquiring-data-dialog.html',
                    controller: 'Freecom_acquiring_dataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                coprosocconyugaie: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-acquiring-data', null, { reload: true });
                }, function() {
                    $state.go('freecom-acquiring-data');
                });
            }]
        })
        .state('freecom-acquiring-data.edit', {
            parent: 'freecom-acquiring-data',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-acquiring-data/freecom-acquiring-data-dialog.html',
                    controller: 'Freecom_acquiring_dataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_acquiring_data', function(Freecom_acquiring_data) {
                            return Freecom_acquiring_data.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-acquiring-data', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-acquiring-data.delete', {
            parent: 'freecom-acquiring-data',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-acquiring-data/freecom-acquiring-data-delete-dialog.html',
                    controller: 'Freecom_acquiring_dataDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_acquiring_data', function(Freecom_acquiring_data) {
                            return Freecom_acquiring_data.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-acquiring-data', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
