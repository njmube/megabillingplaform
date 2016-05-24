(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('file-type', {
            parent: 'entity',
            url: '/file-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.file_type.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/file-type/file-types.html',
                    controller: 'File_typeController',
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
                    $translatePartialLoader.addPart('file_type');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('file-type-detail', {
            parent: 'entity',
            url: '/file-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.file_type.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/file-type/file-type-detail.html',
                    controller: 'File_typeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('file_type');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'File_type', function($stateParams, File_type) {
                    return File_type.get({id : $stateParams.id});
                }]
            }
        })
        .state('file-type.new', {
            parent: 'file-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-type/file-type-dialog.html',
                    controller: 'File_typeDialogController',
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
                    $state.go('file-type', null, { reload: true });
                }, function() {
                    $state.go('file-type');
                });
            }]
        })
        .state('file-type.edit', {
            parent: 'file-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-type/file-type-dialog.html',
                    controller: 'File_typeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['File_type', function(File_type) {
                            return File_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('file-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('file-type.delete', {
            parent: 'file-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/file-type/file-type-delete-dialog.html',
                    controller: 'File_typeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['File_type', function(File_type) {
                            return File_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('file-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
