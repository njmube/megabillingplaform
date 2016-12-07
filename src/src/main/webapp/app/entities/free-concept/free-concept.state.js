(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('free-concept', {
            parent: 'entity',
            url: '/free-concept?page&sort&search',
            data: {
                authorities: [],
                pageTitle: 'megabillingplatformApp.free_concept.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-concept/free-concepts.html',
                    controller: 'Free_conceptController',
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
                    $translatePartialLoader.addPart('free_concept');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('free-concept-detail', {
            parent: 'entity',
            url: '/free-concept/{id}',
            data: {
                authorities: [],
                pageTitle: 'megabillingplatformApp.free_concept.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-concept/free-concept-detail.html',
                    controller: 'Free_conceptDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('free_concept');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Free_concept', function($stateParams, Free_concept) {
                    return Free_concept.get({id : $stateParams.id});
                }]
            }
        })
        .state('free-concept.new', {
            parent: 'free-concept',
            url: '/new',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-concept/free-concept-dialog.html',
                    controller: 'Free_conceptDialogController',
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
                                amount: null,
                                discount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('free-concept', null, { reload: true });
                }, function() {
                    $state.go('free-concept');
                });
            }]
        })
        .state('free-concept.edit', {
            parent: 'free-concept',
            url: '/{id}/edit',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-concept/free-concept-dialog.html',
                    controller: 'Free_conceptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Free_concept', function(Free_concept) {
                            return Free_concept.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('free-concept.delete', {
            parent: 'free-concept',
            url: '/{id}/delete',
            data: {
                authorities: []
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-concept/free-concept-delete-dialog.html',
                    controller: 'Free_conceptDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Free_concept', function(Free_concept) {
                            return Free_concept.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
