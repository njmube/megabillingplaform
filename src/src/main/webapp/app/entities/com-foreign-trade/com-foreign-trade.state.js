(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-foreign-trade', {
            parent: 'entity',
            url: '/com-foreign-trade?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_foreign_trade.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-foreign-trade/com-foreign-trades.html',
                    controller: 'Com_foreign_tradeController',
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
                    $translatePartialLoader.addPart('com_foreign_trade');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-foreign-trade-detail', {
            parent: 'entity',
            url: '/com-foreign-trade/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_foreign_trade.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-foreign-trade/com-foreign-trade-detail.html',
                    controller: 'Com_foreign_tradeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_foreign_trade');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_foreign_trade', function($stateParams, Com_foreign_trade) {
                    return Com_foreign_trade.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-foreign-trade.new', {
            parent: 'com-foreign-trade',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-foreign-trade/com-foreign-trade-dialog.html',
                    controller: 'Com_foreign_tradeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                emitter_curp: null,
                                receiver_curp: null,
                                receiver_numregidtrib: null,
                                origin_certificate: null,
                                number_origin_certificate: null,
                                number_reliable_exporter: null,
                                subdivision: null,
                                observations: null,
                                typechangeusd: null,
                                totalusd: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-foreign-trade', null, { reload: true });
                }, function() {
                    $state.go('com-foreign-trade');
                });
            }]
        })
        .state('com-foreign-trade.edit', {
            parent: 'com-foreign-trade',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-foreign-trade/com-foreign-trade-dialog.html',
                    controller: 'Com_foreign_tradeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_foreign_trade', function(Com_foreign_trade) {
                            return Com_foreign_trade.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-foreign-trade', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-foreign-trade.delete', {
            parent: 'com-foreign-trade',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-foreign-trade/com-foreign-trade-delete-dialog.html',
                    controller: 'Com_foreign_tradeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_foreign_trade', function(Com_foreign_trade) {
                            return Com_foreign_trade.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-foreign-trade', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
