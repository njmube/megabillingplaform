(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-concept-fuel', {
            parent: 'entity',
            url: '/freecom-concept-fuel?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_concept_fuel.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-concept-fuel/freecom-concept-fuels.html',
                    controller: 'Freecom_concept_fuelController',
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
                    $translatePartialLoader.addPart('freecom_concept_fuel');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-concept-fuel-detail', {
            parent: 'entity',
            url: '/freecom-concept-fuel/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_concept_fuel.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-concept-fuel/freecom-concept-fuel-detail.html',
                    controller: 'Freecom_concept_fuelDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_concept_fuel');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_concept_fuel', function($stateParams, Freecom_concept_fuel) {
                    return Freecom_concept_fuel.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-concept-fuel.new', {
            parent: 'freecom-concept-fuel',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-concept-fuel/freecom-concept-fuel-dialog.html',
                    controller: 'Freecom_concept_fuelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                identifier: null,
                                date_expedition: null,
                                rfc: null,
                                key_station: null,
                                quantity: null,
                                fuel_name: null,
                                folio_operation: null,
                                unit_value: null,
                                amount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-concept-fuel', null, { reload: true });
                }, function() {
                    $state.go('freecom-concept-fuel');
                });
            }]
        })
        .state('freecom-concept-fuel.edit', {
            parent: 'freecom-concept-fuel',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-concept-fuel/freecom-concept-fuel-dialog.html',
                    controller: 'Freecom_concept_fuelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_concept_fuel', function(Freecom_concept_fuel) {
                            return Freecom_concept_fuel.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-concept-fuel', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-concept-fuel.delete', {
            parent: 'freecom-concept-fuel',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-concept-fuel/freecom-concept-fuel-delete-dialog.html',
                    controller: 'Freecom_concept_fuelDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_concept_fuel', function(Freecom_concept_fuel) {
                            return Freecom_concept_fuel.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-concept-fuel', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
