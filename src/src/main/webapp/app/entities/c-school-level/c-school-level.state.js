(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-school-level', {
            parent: 'entity',
            url: '/c-school-level?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_school_level.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-school-level/c-school-levels.html',
                    controller: 'C_school_levelController',
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
                    $translatePartialLoader.addPart('c_school_level');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-school-level-detail', {
            parent: 'entity',
            url: '/c-school-level/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_school_level.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-school-level/c-school-level-detail.html',
                    controller: 'C_school_levelDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_school_level');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_school_level', function($stateParams, C_school_level) {
                    return C_school_level.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-school-level.new', {
            parent: 'c-school-level',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-school-level/c-school-level-dialog.html',
                    controller: 'C_school_levelDialogController',
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
                    $state.go('c-school-level', null, { reload: true });
                }, function() {
                    $state.go('c-school-level');
                });
            }]
        })
        .state('c-school-level.edit', {
            parent: 'c-school-level',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-school-level/c-school-level-dialog.html',
                    controller: 'C_school_levelDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_school_level', function(C_school_level) {
                            return C_school_level.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-school-level', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-school-level.delete', {
            parent: 'c-school-level',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-school-level/c-school-level-delete-dialog.html',
                    controller: 'C_school_levelDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_school_level', function(C_school_level) {
                            return C_school_level.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-school-level', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
