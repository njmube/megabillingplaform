(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-local-transfered', {
            parent: 'entity',
            url: '/freecom-local-transfered?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_local_transfered.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-local-transfered/freecom-local-transfereds.html',
                    controller: 'Freecom_local_transferedController',
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
                    $translatePartialLoader.addPart('freecom_local_transfered');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-local-transfered-detail', {
            parent: 'entity',
            url: '/freecom-local-transfered/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_local_transfered.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-local-transfered/freecom-local-transfered-detail.html',
                    controller: 'Freecom_local_transferedDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_local_transfered');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_local_transfered', function($stateParams, Freecom_local_transfered) {
                    return Freecom_local_transfered.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('freecom-local-transfered.new', {
            parent: 'freecom-local-transfered',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-local-transfered/freecom-local-transfered-dialog.html',
                    controller: 'Freecom_local_transferedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                imploctransfered: null,
                                transferedrate: null,
                                amounttransfered: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-local-transfered', null, { reload: true });
                }, function() {
                    $state.go('freecom-local-transfered');
                });
            }]
        })
        .state('freecom-local-transfered.edit', {
            parent: 'freecom-local-transfered',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-local-transfered/freecom-local-transfered-dialog.html',
                    controller: 'Freecom_local_transferedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_local_transfered', function(Freecom_local_transfered) {
                            return Freecom_local_transfered.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-local-transfered', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-local-transfered.delete', {
            parent: 'freecom-local-transfered',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-local-transfered/freecom-local-transfered-delete-dialog.html',
                    controller: 'Freecom_local_transferedDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_local_transfered', function(Freecom_local_transfered) {
                            return Freecom_local_transfered.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-local-transfered', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
