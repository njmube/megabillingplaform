(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-process-type', {
            parent: 'entity',
            url: '/c-process-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_process_type.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-process-type/c-process-types.html',
                    controller: 'C_process_typeController',
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
                    $translatePartialLoader.addPart('c_process_type');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-process-type-detail', {
            parent: 'entity',
            url: '/c-process-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_process_type.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-process-type/c-process-type-detail.html',
                    controller: 'C_process_typeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_process_type');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_process_type', function($stateParams, C_process_type) {
                    return C_process_type.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-process-type.new', {
            parent: 'c-process-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-process-type/c-process-type-dialog.html',
                    controller: 'C_process_typeDialogController',
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
                    $state.go('c-process-type', null, { reload: true });
                }, function() {
                    $state.go('c-process-type');
                });
            }]
        })
        .state('c-process-type.edit', {
            parent: 'c-process-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-process-type/c-process-type-dialog.html',
                    controller: 'C_process_typeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_process_type', function(C_process_type) {
                            return C_process_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-process-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-process-type.delete', {
            parent: 'c-process-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-process-type/c-process-type-delete-dialog.html',
                    controller: 'C_process_typeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_process_type', function(C_process_type) {
                            return C_process_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-process-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
