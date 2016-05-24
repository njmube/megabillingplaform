(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('file-state', {
            parent: 'entity',
            url: '/file-state?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.file_state.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/file-state/file-states.html',
                    controller: 'File_stateController',
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
                    $translatePartialLoader.addPart('file_state');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('file-state-detail', {
            parent: 'entity',
            url: '/file-state/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.file_state.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/file-state/file-state-detail.html',
                    controller: 'File_stateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('file_state');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'File_state', function($stateParams, File_state) {
                    return File_state.get({id : $stateParams.id});
                }]
            }
        })
        .state('file-state.new', {
            parent: 'file-state',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-state/file-state-dialog.html',
                    controller: 'File_stateDialogController',
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
                    $state.go('file-state', null, { reload: true });
                }, function() {
                    $state.go('file-state');
                });
            }]
        })
        .state('file-state.edit', {
            parent: 'file-state',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-state/file-state-dialog.html',
                    controller: 'File_stateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['File_state', function(File_state) {
                            return File_state.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('file-state', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('file-state.delete', {
            parent: 'file-state',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-state/file-state-delete-dialog.html',
                    controller: 'File_stateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['File_state', function(File_state) {
                            return File_state.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('file-state', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
