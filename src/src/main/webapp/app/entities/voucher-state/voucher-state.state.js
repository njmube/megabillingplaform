(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('voucher-state', {
            parent: 'entity',
            url: '/voucher-state?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.voucher_state.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/voucher-state/voucher-states.html',
                    controller: 'Voucher_stateController',
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
                    $translatePartialLoader.addPart('voucher_state');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('voucher-state-detail', {
            parent: 'entity',
            url: '/voucher-state/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.voucher_state.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/voucher-state/voucher-state-detail.html',
                    controller: 'Voucher_stateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('voucher_state');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Voucher_state', function($stateParams, Voucher_state) {
                    return Voucher_state.get({id : $stateParams.id});
                }]
            }
        })
        .state('voucher-state.new', {
            parent: 'voucher-state',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/voucher-state/voucher-state-dialog.html',
                    controller: 'Voucher_stateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('voucher-state', null, { reload: true });
                }, function() {
                    $state.go('voucher-state');
                });
            }]
        })
        .state('voucher-state.edit', {
            parent: 'voucher-state',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/voucher-state/voucher-state-dialog.html',
                    controller: 'Voucher_stateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Voucher_state', function(Voucher_state) {
                            return Voucher_state.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('voucher-state', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('voucher-state.delete', {
            parent: 'voucher-state',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/voucher-state/voucher-state-delete-dialog.html',
                    controller: 'Voucher_stateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Voucher_state', function(Voucher_state) {
                            return Voucher_state.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('voucher-state', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
