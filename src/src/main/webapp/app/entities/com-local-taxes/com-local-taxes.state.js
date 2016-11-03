(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-local-taxes', {
            parent: 'entity',
            url: '/com-local-taxes?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_local_taxes.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-local-taxes/com-local-taxes.html',
                    controller: 'Com_local_taxesController',
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
                    $translatePartialLoader.addPart('com_local_taxes');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-local-taxes-detail', {
            parent: 'entity',
            url: '/com-local-taxes/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_local_taxes.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-local-taxes/com-local-taxes-detail.html',
                    controller: 'Com_local_taxesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_local_taxes');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_local_taxes', function($stateParams, Com_local_taxes) {
                    return Com_local_taxes.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-local-taxes.new', {
            parent: 'com-local-taxes',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-local-taxes/com-local-taxes-dialog.html',
                    controller: 'Com_local_taxesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                total_local_retentions: null,
                                total_local_transfered: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-local-taxes', null, { reload: true });
                }, function() {
                    $state.go('com-local-taxes');
                });
            }]
        })
        .state('com-local-taxes.edit', {
            parent: 'com-local-taxes',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-local-taxes/com-local-taxes-dialog.html',
                    controller: 'Com_local_taxesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_local_taxes', function(Com_local_taxes) {
                            return Com_local_taxes.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-local-taxes', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-local-taxes.delete', {
            parent: 'com-local-taxes',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-local-taxes/com-local-taxes-delete-dialog.html',
                    controller: 'Com_local_taxesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_local_taxes', function(Com_local_taxes) {
                            return Com_local_taxes.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-local-taxes', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
