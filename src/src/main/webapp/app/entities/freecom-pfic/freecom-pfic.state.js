(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-pfic', {
            parent: 'entity',
            url: '/freecom-pfic?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_pfic.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-pfic/freecom-pfics.html',
                    controller: 'Freecom_pficController',
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
                    $translatePartialLoader.addPart('freecom_pfic');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-pfic-detail', {
            parent: 'entity',
            url: '/freecom-pfic/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_pfic.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-pfic/freecom-pfic-detail.html',
                    controller: 'Freecom_pficDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_pfic');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_pfic', function($stateParams, Freecom_pfic) {
                    return Freecom_pfic.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-pfic.new', {
            parent: 'freecom-pfic',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-pfic/freecom-pfic-dialog.html',
                    controller: 'Freecom_pficDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                key_vehicle: null,
                                license_plate: null,
                                rfcpf: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-pfic', null, { reload: true });
                }, function() {
                    $state.go('freecom-pfic');
                });
            }]
        })
        .state('freecom-pfic.edit', {
            parent: 'freecom-pfic',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-pfic/freecom-pfic-dialog.html',
                    controller: 'Freecom_pficDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_pfic', function(Freecom_pfic) {
                            return Freecom_pfic.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-pfic', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-pfic.delete', {
            parent: 'freecom-pfic',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-pfic/freecom-pfic-delete-dialog.html',
                    controller: 'Freecom_pficDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_pfic', function(Freecom_pfic) {
                            return Freecom_pfic.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-pfic', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
