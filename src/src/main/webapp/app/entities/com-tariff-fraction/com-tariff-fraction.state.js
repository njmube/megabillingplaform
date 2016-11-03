(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-tariff-fraction', {
            parent: 'entity',
            url: '/com-tariff-fraction?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_tariff_fraction.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-tariff-fraction/com-tariff-fractions.html',
                    controller: 'Com_tariff_fractionController',
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
                    $translatePartialLoader.addPart('com_tariff_fraction');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-tariff-fraction-detail', {
            parent: 'entity',
            url: '/com-tariff-fraction/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_tariff_fraction.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-tariff-fraction/com-tariff-fraction-detail.html',
                    controller: 'Com_tariff_fractionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_tariff_fraction');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_tariff_fraction', function($stateParams, Com_tariff_fraction) {
                    return Com_tariff_fraction.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-tariff-fraction.new', {
            parent: 'com-tariff-fraction',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-tariff-fraction/com-tariff-fraction-dialog.html',
                    controller: 'Com_tariff_fractionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-tariff-fraction', null, { reload: true });
                }, function() {
                    $state.go('com-tariff-fraction');
                });
            }]
        })
        .state('com-tariff-fraction.edit', {
            parent: 'com-tariff-fraction',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-tariff-fraction/com-tariff-fraction-dialog.html',
                    controller: 'Com_tariff_fractionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_tariff_fraction', function(Com_tariff_fraction) {
                            return Com_tariff_fraction.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-tariff-fraction', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-tariff-fraction.delete', {
            parent: 'com-tariff-fraction',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-tariff-fraction/com-tariff-fraction-delete-dialog.html',
                    controller: 'Com_tariff_fractionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_tariff_fraction', function(Com_tariff_fraction) {
                            return Com_tariff_fraction.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-tariff-fraction', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
