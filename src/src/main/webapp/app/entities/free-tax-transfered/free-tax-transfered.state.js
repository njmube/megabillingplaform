(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('free-tax-transfered', {
            parent: 'entity',
            url: '/free-tax-transfered?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.free_tax_transfered.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-tax-transfered/free-tax-transfereds.html',
                    controller: 'Free_tax_transferedController',
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
                    $translatePartialLoader.addPart('free_tax_transfered');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('free-tax-transfered-detail', {
            parent: 'entity',
            url: '/free-tax-transfered/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.free_tax_transfered.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-tax-transfered/free-tax-transfered-detail.html',
                    controller: 'Free_tax_transferedDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('free_tax_transfered');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Free_tax_transfered', function($stateParams, Free_tax_transfered) {
                    return Free_tax_transfered.get({id : $stateParams.id});
                }]
            }
        })
        .state('free-tax-transfered.new', {
            parent: 'free-tax-transfered',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-tax-transfered/free-tax-transfered-dialog.html',
                    controller: 'Free_tax_transferedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                rate: null,
                                amount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('free-tax-transfered', null, { reload: true });
                }, function() {
                    $state.go('free-tax-transfered');
                });
            }]
        })
        .state('free-tax-transfered.edit', {
            parent: 'free-tax-transfered',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-tax-transfered/free-tax-transfered-dialog.html',
                    controller: 'Free_tax_transferedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Free_tax_transfered', function(Free_tax_transfered) {
                            return Free_tax_transfered.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-tax-transfered', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('free-tax-transfered.delete', {
            parent: 'free-tax-transfered',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-tax-transfered/free-tax-transfered-delete-dialog.html',
                    controller: 'Free_tax_transferedDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Free_tax_transfered', function(Free_tax_transfered) {
                            return Free_tax_transfered.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-tax-transfered', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
