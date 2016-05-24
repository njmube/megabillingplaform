(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('voucher-type', {
            parent: 'entity',
            url: '/voucher-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.voucher_type.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/voucher-type/voucher-types.html',
                    controller: 'Voucher_typeController',
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
                    $translatePartialLoader.addPart('voucher_type');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('voucher-type-detail', {
            parent: 'entity',
            url: '/voucher-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.voucher_type.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/voucher-type/voucher-type-detail.html',
                    controller: 'Voucher_typeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('voucher_type');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Voucher_type', function($stateParams, Voucher_type) {
                    return Voucher_type.get({id : $stateParams.id});
                }]
            }
        })
        .state('voucher-type.new', {
            parent: 'voucher-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/voucher-type/voucher-type-dialog.html',
                    controller: 'Voucher_typeDialogController',
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
                    $state.go('voucher-type', null, { reload: true });
                }, function() {
                    $state.go('voucher-type');
                });
            }]
        })
        .state('voucher-type.edit', {
            parent: 'voucher-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/voucher-type/voucher-type-dialog.html',
                    controller: 'Voucher_typeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Voucher_type', function(Voucher_type) {
                            return Voucher_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('voucher-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('voucher-type.delete', {
            parent: 'voucher-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/voucher-type/voucher-type-delete-dialog.html',
                    controller: 'Voucher_typeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Voucher_type', function(Voucher_type) {
                            return Voucher_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('voucher-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
