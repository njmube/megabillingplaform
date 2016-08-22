(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-foreign-trade', {
            parent: 'entity',
            url: '/freecom-foreign-trade?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_foreign_trade.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-foreign-trade/freecom-foreign-trades.html',
                    controller: 'Freecom_foreign_tradeController',
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
                    $translatePartialLoader.addPart('freecom_foreign_trade');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-foreign-trade-detail', {
            parent: 'entity',
            url: '/freecom-foreign-trade/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_foreign_trade.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-foreign-trade/freecom-foreign-trade-detail.html',
                    controller: 'Freecom_foreign_tradeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_foreign_trade');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_foreign_trade', function($stateParams, Freecom_foreign_trade) {
                    return Freecom_foreign_trade.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('freecom-foreign-trade.new', {
            parent: 'freecom-foreign-trade',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-foreign-trade/freecom-foreign-trade-dialog.html',
                    controller: 'Freecom_foreign_tradeDialogController',
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
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-foreign-trade', null, { reload: true });
                }, function() {
                    $state.go('freecom-foreign-trade');
                });
            }]
        })
        .state('freecom-foreign-trade.edit', {
            parent: 'freecom-foreign-trade',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-foreign-trade/freecom-foreign-trade-dialog.html',
                    controller: 'Freecom_foreign_tradeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_foreign_trade', function(Freecom_foreign_trade) {
                            return Freecom_foreign_trade.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-foreign-trade', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-foreign-trade.delete', {
            parent: 'freecom-foreign-trade',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-foreign-trade/freecom-foreign-trade-delete-dialog.html',
                    controller: 'Freecom_foreign_tradeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_foreign_trade', function(Freecom_foreign_trade) {
                            return Freecom_foreign_trade.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-foreign-trade', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
