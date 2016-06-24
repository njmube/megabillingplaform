(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('free-part-concept', {
            parent: 'entity',
            url: '/free-part-concept?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.free_part_concept.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-part-concept/free-part-concepts.html',
                    controller: 'Free_part_conceptController',
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
                    $translatePartialLoader.addPart('free_part_concept');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('free-part-concept-detail', {
            parent: 'entity',
            url: '/free-part-concept/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.free_part_concept.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/free-part-concept/free-part-concept-detail.html',
                    controller: 'Free_part_conceptDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('free_part_concept');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Free_part_concept', function($stateParams, Free_part_concept) {
                    return Free_part_concept.get({id : $stateParams.id});
                }]
            }
        })
        .state('free-part-concept.new', {
            parent: 'free-part-concept',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-part-concept/free-part-concept-dialog.html',
                    controller: 'Free_part_conceptDialogController',
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
                                amount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('free-part-concept', null, { reload: true });
                }, function() {
                    $state.go('free-part-concept');
                });
            }]
        })
        .state('free-part-concept.edit', {
            parent: 'free-part-concept',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-part-concept/free-part-concept-dialog.html',
                    controller: 'Free_part_conceptDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Free_part_concept', function(Free_part_concept) {
                            return Free_part_concept.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-part-concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('free-part-concept.delete', {
            parent: 'free-part-concept',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/free-part-concept/free-part-concept-delete-dialog.html',
                    controller: 'Free_part_conceptDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Free_part_concept', function(Free_part_concept) {
                            return Free_part_concept.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('free-part-concept', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
