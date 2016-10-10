(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('concept', {
            parent: 'entity',
            url: '/concepts/{id}?page&sort&search',
            data: {
                authorities: ['ROLE_AFILITATED'],
                pageTitle: 'megabillingplatformApp.concept.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/concept/concepts.html',
                    controller: 'ConceptController',
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
                taxpayer_account_entity: ['$stateParams', 'Taxpayer_account', function($stateParams, Taxpayer_account) {
                    return Taxpayer_account.get({id : $stateParams.id}).$promise;
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('concept');
                    $translatePartialLoader.addPart('part_concept');
                    $translatePartialLoader.addPart('customs_info');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('concept-detail', {
            parent: 'entity',
            url: '/concept/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.concept.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/concept/concept-detail.html',
                    controller: 'ConceptDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('concept');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Concept', function($stateParams, Concept) {
                    return Concept.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('concept.new', {
            parent: 'concept',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/concept/concept-dialog.html',
                    controller: 'ConceptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                no_identification: null,
                                quantity: null,
                                description: null,
                                unit_value: null,
                                predial_number: null,
                                discount: null,
                                amount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('concept', null, { reload: true });
                }, function() {
                    $state.go('concept');
                });
            }]
        })
        .state('concept.edit', {
            parent: 'concept',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/concept/concept-dialog.html',
                    controller: 'ConceptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Concept', function(Concept) {
                            return Concept.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('concept.delete', {
            parent: 'concept',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/concept/concept-delete-dialog.html',
                    controller: 'ConceptDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Concept', function(Concept) {
                            return Concept.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
