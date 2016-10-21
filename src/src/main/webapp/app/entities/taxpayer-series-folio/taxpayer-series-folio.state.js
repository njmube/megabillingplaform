(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('taxpayer-series-folio', {
            parent: 'entity',
            url: '/taxpayer-series-folio?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.taxpayer_series_folio.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/taxpayer-series-folio/taxpayer-series-folios.html',
                    controller: 'Taxpayer_series_folioController',
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
                    $translatePartialLoader.addPart('taxpayer_series_folio');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('taxpayer-series-folio-detail', {
            parent: 'entity',
            url: '/taxpayer-series-folio/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.taxpayer_series_folio.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/taxpayer-series-folio/taxpayer-series-folio-detail.html',
                    controller: 'Taxpayer_series_folioDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('taxpayer_series_folio');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Taxpayer_series_folio', function($stateParams, Taxpayer_series_folio) {
                    return Taxpayer_series_folio.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('taxpayer-series-folio.new', {
            parent: 'taxpayer-series-folio',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-series-folio/taxpayer-series-folio-dialog.html',
                    controller: 'Taxpayer_series_folioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                serie: null,
                                folio_start: null,
                                folio_end: null,
                                folio_current: null,
                                date_creation: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('taxpayer-series-folio', null, { reload: true });
                }, function() {
                    $state.go('taxpayer-series-folio');
                });
            }]
        })
        .state('taxpayer-series-folio.edit', {
            parent: 'taxpayer-series-folio',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-series-folio/taxpayer-series-folio-dialog.html',
                    controller: 'Taxpayer_series_folioDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Taxpayer_series_folio', function(Taxpayer_series_folio) {
                            return Taxpayer_series_folio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('taxpayer-series-folio', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('taxpayer-series-folio.delete', {
            parent: 'taxpayer-series-folio',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-series-folio/taxpayer-series-folio-delete-dialog.html',
                    controller: 'Taxpayer_series_folioDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Taxpayer_series_folio', function(Taxpayer_series_folio) {
                            return Taxpayer_series_folio.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('taxpayer-series-folio', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
