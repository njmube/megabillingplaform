(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('package-state', {
            parent: 'entity',
            url: '/package-state?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.package_state.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/package-state/package-states.html',
                    controller: 'Package_stateController',
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
                    $translatePartialLoader.addPart('package_state');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('package-state-detail', {
            parent: 'entity',
            url: '/package-state/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.package_state.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/package-state/package-state-detail.html',
                    controller: 'Package_stateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('package_state');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Package_state', function($stateParams, Package_state) {
                    return Package_state.get({id : $stateParams.id});
                }]
            }
        })
        .state('package-state.new', {
            parent: 'package-state',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/package-state/package-state-dialog.html',
                    controller: 'Package_stateDialogController',
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
                    $state.go('package-state', null, { reload: true });
                }, function() {
                    $state.go('package-state');
                });
            }]
        })
        .state('package-state.edit', {
            parent: 'package-state',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/package-state/package-state-dialog.html',
                    controller: 'Package_stateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Package_state', function(Package_state) {
                            return Package_state.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('package-state', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('package-state.delete', {
            parent: 'package-state',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/package-state/package-state-delete-dialog.html',
                    controller: 'Package_stateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Package_state', function(Package_state) {
                            return Package_state.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('package-state', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
