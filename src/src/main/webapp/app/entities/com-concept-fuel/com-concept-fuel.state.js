(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-concept-fuel', {
            parent: 'entity',
            url: '/com-concept-fuel?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_concept_fuel.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-concept-fuel/com-concept-fuels.html',
                    controller: 'Com_concept_fuelController',
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
                    $translatePartialLoader.addPart('com_concept_fuel');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-concept-fuel-detail', {
            parent: 'entity',
            url: '/com-concept-fuel/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_concept_fuel.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-concept-fuel/com-concept-fuel-detail.html',
                    controller: 'Com_concept_fuelDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_concept_fuel');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_concept_fuel', function($stateParams, Com_concept_fuel) {
                    return Com_concept_fuel.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-concept-fuel.new', {
            parent: 'com-concept-fuel',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-concept-fuel/com-concept-fuel-dialog.html',
                    controller: 'Com_concept_fuelDialogController',
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
                    $state.go('com-concept-fuel', null, { reload: true });
                }, function() {
                    $state.go('com-concept-fuel');
                });
            }]
        })
        .state('com-concept-fuel.edit', {
            parent: 'com-concept-fuel',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-concept-fuel/com-concept-fuel-dialog.html',
                    controller: 'Com_concept_fuelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_concept_fuel', function(Com_concept_fuel) {
                            return Com_concept_fuel.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-concept-fuel', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-concept-fuel.delete', {
            parent: 'com-concept-fuel',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-concept-fuel/com-concept-fuel-delete-dialog.html',
                    controller: 'Com_concept_fuelDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_concept_fuel', function(Com_concept_fuel) {
                            return Com_concept_fuel.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-concept-fuel', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
