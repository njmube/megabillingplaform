(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('taxpayer-concept', {
            parent: 'entity',
            url: '/taxpayer-concepts/{id}?page&sort&search',
            data: {
                authorities: ['ROLE_AFILITATED'],
                pageTitle: 'megabillingplatformApp.taxpayer_concept.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/taxpayer-concept/taxpayer-concepts.html',
                    controller: 'Taxpayer_conceptController',
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
                    $translatePartialLoader.addPart('taxpayer_concept');
                    $translatePartialLoader.addPart('measure_unit_concept');
                    $translatePartialLoader.addPart('price_concept');
                    $translatePartialLoader.addPart('discount_concept');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('taxpayer-concept-detail', {
            parent: 'entity',
            url: '/taxpayer-concept/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.taxpayer_concept.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/taxpayer-concept/taxpayer-concept-detail.html',
                    controller: 'Taxpayer_conceptDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('taxpayer_concept');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Taxpayer_concept', function($stateParams, Taxpayer_concept) {
                    return Taxpayer_concept.get({id : $stateParams.id});
                }]
            }
        })
        .state('taxpayer-concept.new', {
            parent: 'taxpayer-concept',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-concept/taxpayer-concept-dialog.html',
                    controller: 'Taxpayer_conceptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                no_identification: null,
                                description: null,
                                predial_number: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('taxpayer-concept', null, { reload: true });
                }, function() {
                    $state.go('taxpayer-concept');
                });
            }]
        })
        .state('taxpayer-concept.edit', {
            parent: 'taxpayer-concept',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-concept/taxpayer-concept-dialog.html',
                    controller: 'Taxpayer_conceptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Taxpayer_concept', function(Taxpayer_concept) {
                            return Taxpayer_concept.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('taxpayer-concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('taxpayer-concept.delete', {
            parent: 'taxpayer-concept',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/taxpayer-concept/taxpayer-concept-delete-dialog.html',
                    controller: 'Taxpayer_conceptDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Taxpayer_concept', function(Taxpayer_concept) {
                            return Taxpayer_concept.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('taxpayer-concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
