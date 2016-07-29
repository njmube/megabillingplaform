(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-foreign-exchange', {
            parent: 'entity',
            url: '/freecom-foreign-exchange?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_foreign_exchange.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-foreign-exchange/freecom-foreign-exchanges.html',
                    controller: 'Freecom_foreign_exchangeController',
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
                    $translatePartialLoader.addPart('freecom_foreign_exchange');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-foreign-exchange-detail', {
            parent: 'entity',
            url: '/freecom-foreign-exchange/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_foreign_exchange.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-foreign-exchange/freecom-foreign-exchange-detail.html',
                    controller: 'Freecom_foreign_exchangeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_foreign_exchange');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_foreign_exchange', function($stateParams, Freecom_foreign_exchange) {
                    return Freecom_foreign_exchange.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-foreign-exchange.new', {
            parent: 'freecom-foreign-exchange',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-foreign-exchange/freecom-foreign-exchange-dialog.html',
                    controller: 'Freecom_foreign_exchangeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-foreign-exchange', null, { reload: true });
                }, function() {
                    $state.go('freecom-foreign-exchange');
                });
            }]
        })
        .state('freecom-foreign-exchange.edit', {
            parent: 'freecom-foreign-exchange',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-foreign-exchange/freecom-foreign-exchange-dialog.html',
                    controller: 'Freecom_foreign_exchangeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_foreign_exchange', function(Freecom_foreign_exchange) {
                            return Freecom_foreign_exchange.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-foreign-exchange', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-foreign-exchange.delete', {
            parent: 'freecom-foreign-exchange',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-foreign-exchange/freecom-foreign-exchange-delete-dialog.html',
                    controller: 'Freecom_foreign_exchangeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_foreign_exchange', function(Freecom_foreign_exchange) {
                            return Freecom_foreign_exchange.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-foreign-exchange', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
