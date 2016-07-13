(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-tar', {
            parent: 'entity',
            url: '/c-tar?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_tar.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-tar/c-tars.html',
                    controller: 'C_tarController',
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
                    $translatePartialLoader.addPart('c_tar');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-tar-detail', {
            parent: 'entity',
            url: '/c-tar/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_tar.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-tar/c-tar-detail.html',
                    controller: 'C_tarDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_tar');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_tar', function($stateParams, C_tar) {
                    return C_tar.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-tar.new', {
            parent: 'c-tar',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-tar/c-tar-dialog.html',
                    controller: 'C_tarDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-tar', null, { reload: true });
                }, function() {
                    $state.go('c-tar');
                });
            }]
        })
        .state('c-tar.edit', {
            parent: 'c-tar',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-tar/c-tar-dialog.html',
                    controller: 'C_tarDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_tar', function(C_tar) {
                            return C_tar.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-tar', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-tar.delete', {
            parent: 'c-tar',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-tar/c-tar-delete-dialog.html',
                    controller: 'C_tarDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_tar', function(C_tar) {
                            return C_tar.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-tar', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
