(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-acquired-title', {
            parent: 'entity',
            url: '/c-acquired-title?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_acquired_title.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-acquired-title/c-acquired-titles.html',
                    controller: 'C_acquired_titleController',
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
                    $translatePartialLoader.addPart('c_acquired_title');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-acquired-title-detail', {
            parent: 'entity',
            url: '/c-acquired-title/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_acquired_title.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-acquired-title/c-acquired-title-detail.html',
                    controller: 'C_acquired_titleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_acquired_title');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_acquired_title', function($stateParams, C_acquired_title) {
                    return C_acquired_title.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-acquired-title.new', {
            parent: 'c-acquired-title',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-acquired-title/c-acquired-title-dialog.html',
                    controller: 'C_acquired_titleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-acquired-title', null, { reload: true });
                }, function() {
                    $state.go('c-acquired-title');
                });
            }]
        })
        .state('c-acquired-title.edit', {
            parent: 'c-acquired-title',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-acquired-title/c-acquired-title-dialog.html',
                    controller: 'C_acquired_titleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_acquired_title', function(C_acquired_title) {
                            return C_acquired_title.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-acquired-title', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-acquired-title.delete', {
            parent: 'c-acquired-title',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-acquired-title/c-acquired-title-delete-dialog.html',
                    controller: 'C_acquired_titleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_acquired_title', function(C_acquired_title) {
                            return C_acquired_title.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-acquired-title', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
