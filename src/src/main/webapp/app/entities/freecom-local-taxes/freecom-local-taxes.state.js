(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-local-taxes', {
            parent: 'entity',
            url: '/freecom-local-taxes?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_local_taxes.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-local-taxes/freecom-local-taxes.html',
                    controller: 'Freecom_local_taxesController',
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
                    $translatePartialLoader.addPart('freecom_local_taxes');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-local-taxes-detail', {
            parent: 'entity',
            url: '/freecom-local-taxes/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_local_taxes.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-local-taxes/freecom-local-taxes-detail.html',
                    controller: 'Freecom_local_taxesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_local_taxes');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_local_taxes', function($stateParams, Freecom_local_taxes) {
                    return Freecom_local_taxes.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-local-taxes.new', {
            parent: 'freecom-local-taxes',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-local-taxes/freecom-local-taxes-dialog.html',
                    controller: 'Freecom_local_taxesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                total_retentions: null,
                                total_transfered: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-local-taxes', null, { reload: true });
                }, function() {
                    $state.go('freecom-local-taxes');
                });
            }]
        })
        .state('freecom-local-taxes.edit', {
            parent: 'freecom-local-taxes',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-local-taxes/freecom-local-taxes-dialog.html',
                    controller: 'Freecom_local_taxesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_local_taxes', function(Freecom_local_taxes) {
                            return Freecom_local_taxes.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-local-taxes', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-local-taxes.delete', {
            parent: 'freecom-local-taxes',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-local-taxes/freecom-local-taxes-delete-dialog.html',
                    controller: 'Freecom_local_taxesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_local_taxes', function(Freecom_local_taxes) {
                            return Freecom_local_taxes.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-local-taxes', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
