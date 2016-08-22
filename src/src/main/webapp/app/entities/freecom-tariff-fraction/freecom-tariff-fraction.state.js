(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-tariff-fraction', {
            parent: 'entity',
            url: '/freecom-tariff-fraction?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_tariff_fraction.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-tariff-fraction/freecom-tariff-fractions.html',
                    controller: 'Freecom_tariff_fractionController',
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
                    $translatePartialLoader.addPart('freecom_tariff_fraction');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-tariff-fraction-detail', {
            parent: 'entity',
            url: '/freecom-tariff-fraction/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_tariff_fraction.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-tariff-fraction/freecom-tariff-fraction-detail.html',
                    controller: 'Freecom_tariff_fractionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_tariff_fraction');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_tariff_fraction', function($stateParams, Freecom_tariff_fraction) {
                    return Freecom_tariff_fraction.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('freecom-tariff-fraction.new', {
            parent: 'freecom-tariff-fraction',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-tariff-fraction/freecom-tariff-fraction-dialog.html',
                    controller: 'Freecom_tariff_fractionDialogController',
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
                    $state.go('freecom-tariff-fraction', null, { reload: true });
                }, function() {
                    $state.go('freecom-tariff-fraction');
                });
            }]
        })
        .state('freecom-tariff-fraction.edit', {
            parent: 'freecom-tariff-fraction',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-tariff-fraction/freecom-tariff-fraction-dialog.html',
                    controller: 'Freecom_tariff_fractionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_tariff_fraction', function(Freecom_tariff_fraction) {
                            return Freecom_tariff_fraction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-tariff-fraction', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-tariff-fraction.delete', {
            parent: 'freecom-tariff-fraction',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-tariff-fraction/freecom-tariff-fraction-delete-dialog.html',
                    controller: 'Freecom_tariff_fractionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_tariff_fraction', function(Freecom_tariff_fraction) {
                            return Freecom_tariff_fraction.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-tariff-fraction', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
