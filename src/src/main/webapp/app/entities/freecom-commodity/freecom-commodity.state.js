(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-commodity', {
            parent: 'entity',
            url: '/freecom-commodity?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_commodity.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-commodity/freecom-commodities.html',
                    controller: 'Freecom_commodityController',
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
                    $translatePartialLoader.addPart('freecom_commodity');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-commodity-detail', {
            parent: 'entity',
            url: '/freecom-commodity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_commodity.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-commodity/freecom-commodity-detail.html',
                    controller: 'Freecom_commodityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_commodity');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_commodity', function($stateParams, Freecom_commodity) {
                    return Freecom_commodity.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-commodity.new', {
            parent: 'freecom-commodity',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-commodity/freecom-commodity-dialog.html',
                    controller: 'Freecom_commodityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                noidentification: null,
                                customs_quantity: null,
                                customs_unit_value: null,
                                dollar_value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-commodity', null, { reload: true });
                }, function() {
                    $state.go('freecom-commodity');
                });
            }]
        })
        .state('freecom-commodity.edit', {
            parent: 'freecom-commodity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-commodity/freecom-commodity-dialog.html',
                    controller: 'Freecom_commodityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_commodity', function(Freecom_commodity) {
                            return Freecom_commodity.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-commodity', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-commodity.delete', {
            parent: 'freecom-commodity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-commodity/freecom-commodity-delete-dialog.html',
                    controller: 'Freecom_commodityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_commodity', function(Freecom_commodity) {
                            return Freecom_commodity.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-commodity', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
