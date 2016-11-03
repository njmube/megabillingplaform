(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-foreign-exchange', {
            parent: 'entity',
            url: '/com-foreign-exchange?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_foreign_exchange.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-foreign-exchange/com-foreign-exchanges.html',
                    controller: 'Com_foreign_exchangeController',
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
                    $translatePartialLoader.addPart('com_foreign_exchange');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-foreign-exchange-detail', {
            parent: 'entity',
            url: '/com-foreign-exchange/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_foreign_exchange.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-foreign-exchange/com-foreign-exchange-detail.html',
                    controller: 'Com_foreign_exchangeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_foreign_exchange');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_foreign_exchange', function($stateParams, Com_foreign_exchange) {
                    return Com_foreign_exchange.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-foreign-exchange.new', {
            parent: 'com-foreign-exchange',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-foreign-exchange/com-foreign-exchange-dialog.html',
                    controller: 'Com_foreign_exchangeDialogController',
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
                    $state.go('com-foreign-exchange', null, { reload: true });
                }, function() {
                    $state.go('com-foreign-exchange');
                });
            }]
        })
        .state('com-foreign-exchange.edit', {
            parent: 'com-foreign-exchange',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-foreign-exchange/com-foreign-exchange-dialog.html',
                    controller: 'Com_foreign_exchangeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_foreign_exchange', function(Com_foreign_exchange) {
                            return Com_foreign_exchange.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-foreign-exchange', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-foreign-exchange.delete', {
            parent: 'com-foreign-exchange',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-foreign-exchange/com-foreign-exchange-delete-dialog.html',
                    controller: 'Com_foreign_exchangeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_foreign_exchange', function(Com_foreign_exchange) {
                            return Com_foreign_exchange.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-foreign-exchange', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
