(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-ecc-11-concept', {
            parent: 'entity',
            url: '/freecom-ecc-11-concept?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_ecc11_concept.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-ecc-11-concept/freecom-ecc-11-concepts.html',
                    controller: 'Freecom_ecc11_conceptController',
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
                    $translatePartialLoader.addPart('freecom_ecc11_concept');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-ecc-11-concept-detail', {
            parent: 'entity',
            url: '/freecom-ecc-11-concept/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_ecc11_concept.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-ecc-11-concept/freecom-ecc-11-concept-detail.html',
                    controller: 'Freecom_ecc11_conceptDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_ecc11_concept');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_ecc11_concept', function($stateParams, Freecom_ecc11_concept) {
                    return Freecom_ecc11_concept.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('freecom-ecc-11-concept.new', {
            parent: 'freecom-ecc-11-concept',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-ecc-11-concept/freecom-ecc-11-concept-dialog.html',
                    controller: 'Freecom_ecc11_conceptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                identifier: null,
                                date: null,
                                rfc: null,
                                key_station: null,
                                quantity: null,
                                unit: null,
                                fuel_name: null,
                                folio_operation: null,
                                unit_value: null,
                                amount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-ecc-11-concept', null, { reload: true });
                }, function() {
                    $state.go('freecom-ecc-11-concept');
                });
            }]
        })
        .state('freecom-ecc-11-concept.edit', {
            parent: 'freecom-ecc-11-concept',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-ecc-11-concept/freecom-ecc-11-concept-dialog.html',
                    controller: 'Freecom_ecc11_conceptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_ecc11_concept', function(Freecom_ecc11_concept) {
                            return Freecom_ecc11_concept.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-ecc-11-concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-ecc-11-concept.delete', {
            parent: 'freecom-ecc-11-concept',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-ecc-11-concept/freecom-ecc-11-concept-delete-dialog.html',
                    controller: 'Freecom_ecc11_conceptDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_ecc11_concept', function(Freecom_ecc11_concept) {
                            return Freecom_ecc11_concept.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-ecc-11-concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
