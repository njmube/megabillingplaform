(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('committee', {
            parent: 'entity',
            url: '/committee?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.committee.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/committee/committees.html',
                    controller: 'CommitteeController',
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
                    $translatePartialLoader.addPart('committee');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('committee-detail', {
            parent: 'entity',
            url: '/committee/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.committee.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/committee/committee-detail.html',
                    controller: 'CommitteeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('committee');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Committee', function($stateParams, Committee) {
                    return Committee.get({id : $stateParams.id});
                }]
            }
        })
        .state('committee.new', {
            parent: 'committee',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/committee/committee-dialog.html',
                    controller: 'CommitteeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('committee', null, { reload: true });
                }, function() {
                    $state.go('committee');
                });
            }]
        })
        .state('committee.edit', {
            parent: 'committee',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/committee/committee-dialog.html',
                    controller: 'CommitteeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Committee', function(Committee) {
                            return Committee.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('committee', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('committee.delete', {
            parent: 'committee',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/committee/committee-delete-dialog.html',
                    controller: 'CommitteeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Committee', function(Committee) {
                            return Committee.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('committee', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
