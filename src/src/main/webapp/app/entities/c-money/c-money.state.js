(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-money', {
            parent: 'entity',
            url: '/c-money?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_money.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-money/c-monies.html',
                    controller: 'C_moneyController',
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
                    $translatePartialLoader.addPart('c_money');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-money-detail', {
            parent: 'entity',
            url: '/c-money/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_money.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-money/c-money-detail.html',
                    controller: 'C_moneyDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_money');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_money', function($stateParams, C_money) {
                    return C_money.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-money.new', {
            parent: 'c-money',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-money/c-money-dialog.html',
                    controller: 'C_moneyDialogController',
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
                    $state.go('c-money', null, { reload: true });
                }, function() {
                    $state.go('c-money');
                });
            }]
        })
        .state('c-money.edit', {
            parent: 'c-money',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-money/c-money-dialog.html',
                    controller: 'C_moneyDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_money', function(C_money) {
                            return C_money.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-money', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-money.delete', {
            parent: 'c-money',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-money/c-money-delete-dialog.html',
                    controller: 'C_moneyDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_money', function(C_money) {
                            return C_money.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-money', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
