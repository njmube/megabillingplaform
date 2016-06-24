(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tax-transfered', {
            parent: 'entity',
            url: '/tax-transfered?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.tax_transfered.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tax-transfered/tax-transfereds.html',
                    controller: 'Tax_transferedController',
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
                    $translatePartialLoader.addPart('tax_transfered');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tax-transfered-detail', {
            parent: 'entity',
            url: '/tax-transfered/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.tax_transfered.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tax-transfered/tax-transfered-detail.html',
                    controller: 'Tax_transferedDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tax_transfered');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tax_transfered', function($stateParams, Tax_transfered) {
                    return Tax_transfered.get({id : $stateParams.id});
                }]
            }
        })
        .state('tax-transfered.new', {
            parent: 'tax-transfered',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tax-transfered/tax-transfered-dialog.html',
                    controller: 'Tax_transferedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                amount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tax-transfered', null, { reload: true });
                }, function() {
                    $state.go('tax-transfered');
                });
            }]
        })
        .state('tax-transfered.edit', {
            parent: 'tax-transfered',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tax-transfered/tax-transfered-dialog.html',
                    controller: 'Tax_transferedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tax_transfered', function(Tax_transfered) {
                            return Tax_transfered.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('tax-transfered', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tax-transfered.delete', {
            parent: 'tax-transfered',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tax-transfered/tax-transfered-delete-dialog.html',
                    controller: 'Tax_transferedDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tax_transfered', function(Tax_transfered) {
                            return Tax_transfered.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('tax-transfered', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
