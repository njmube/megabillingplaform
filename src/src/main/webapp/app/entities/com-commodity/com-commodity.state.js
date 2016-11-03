(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-commodity', {
            parent: 'entity',
            url: '/com-commodity?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_commodity.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-commodity/com-commodities.html',
                    controller: 'Com_commodityController',
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
                    $translatePartialLoader.addPart('com_commodity');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-commodity-detail', {
            parent: 'entity',
            url: '/com-commodity/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_commodity.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-commodity/com-commodity-detail.html',
                    controller: 'Com_commodityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_commodity');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_commodity', function($stateParams, Com_commodity) {
                    return Com_commodity.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-commodity.new', {
            parent: 'com-commodity',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-commodity/com-commodity-dialog.html',
                    controller: 'Com_commodityDialogController',
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
                    $state.go('com-commodity', null, { reload: true });
                }, function() {
                    $state.go('com-commodity');
                });
            }]
        })
        .state('com-commodity.edit', {
            parent: 'com-commodity',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-commodity/com-commodity-dialog.html',
                    controller: 'Com_commodityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_commodity', function(Com_commodity) {
                            return Com_commodity.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-commodity', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-commodity.delete', {
            parent: 'com-commodity',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-commodity/com-commodity-delete-dialog.html',
                    controller: 'Com_commodityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_commodity', function(Com_commodity) {
                            return Com_commodity.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-commodity', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
