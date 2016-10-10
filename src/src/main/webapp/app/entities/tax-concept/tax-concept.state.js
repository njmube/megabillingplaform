(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tax-concept', {
            parent: 'entity',
            url: '/tax-concepts/{id}?page&sort&search',
            data: {
                authorities: ['ROLE_AFILITATED'],
                pageTitle: 'megabillingplatformApp.tax_concept.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tax-concept/tax-concepts.html',
                    controller: 'Tax_conceptController',
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
                    $translatePartialLoader.addPart('tax_concept');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tax-concept-detail', {
            parent: 'entity',
            url: '/tax-concept/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.tax_concept.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tax-concept/tax-concept-detail.html',
                    controller: 'Tax_conceptDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tax_concept');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Tax_concept', function($stateParams, Tax_concept) {
                    return Tax_concept.get({id : $stateParams.id});
                }]
            }
        })
        .state('tax-concept.new', {
            parent: 'tax-concept',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tax-concept/tax-concept-dialog.html',
                    controller: 'Tax_conceptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                rate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tax-concept', null, { reload: true });
                }, function() {
                    $state.go('tax-concept');
                });
            }]
        })
        .state('tax-concept.edit', {
            parent: 'tax-concept',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tax-concept/tax-concept-dialog.html',
                    controller: 'Tax_conceptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Tax_concept', function(Tax_concept) {
                            return Tax_concept.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('tax-concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tax-concept.delete', {
            parent: 'tax-concept',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tax-concept/tax-concept-delete-dialog.html',
                    controller: 'Tax_conceptDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Tax_concept', function(Tax_concept) {
                            return Tax_concept.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('tax-concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
