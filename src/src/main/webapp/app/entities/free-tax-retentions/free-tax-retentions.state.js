(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('free-tax-retentions', {
            parent: 'entity',
            url: '/free-tax-retentions?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.free_tax_retentions.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-tax-retentions/free-tax-retentions.html',
                    controller: 'Free_tax_retentionsController',
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
                    $translatePartialLoader.addPart('free_tax_retentions');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('free-tax-retentions-detail', {
            parent: 'entity',
            url: '/free-tax-retentions/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.free_tax_retentions.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-tax-retentions/free-tax-retentions-detail.html',
                    controller: 'Free_tax_retentionsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('free_tax_retentions');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Free_tax_retentions', function($stateParams, Free_tax_retentions) {
                    return Free_tax_retentions.get({id : $stateParams.id});
                }]
            }
        })
        .state('free-tax-retentions.new', {
            parent: 'free-tax-retentions',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-tax-retentions/free-tax-retentions-dialog.html',
                    controller: 'Free_tax_retentionsDialogController',
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
                    $state.go('free-tax-retentions', null, { reload: true });
                }, function() {
                    $state.go('free-tax-retentions');
                });
            }]
        })
        .state('free-tax-retentions.edit', {
            parent: 'free-tax-retentions',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-tax-retentions/free-tax-retentions-dialog.html',
                    controller: 'Free_tax_retentionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Free_tax_retentions', function(Free_tax_retentions) {
                            return Free_tax_retentions.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-tax-retentions', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('free-tax-retentions.delete', {
            parent: 'free-tax-retentions',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-tax-retentions/free-tax-retentions-delete-dialog.html',
                    controller: 'Free_tax_retentionsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Free_tax_retentions', function(Free_tax_retentions) {
                            return Free_tax_retentions.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-tax-retentions', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
