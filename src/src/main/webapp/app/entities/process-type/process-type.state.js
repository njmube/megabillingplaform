(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('process-type', {
            parent: 'entity',
            url: '/process-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.process_type.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/process-type/process-types.html',
                    controller: 'Process_typeController',
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
                    $translatePartialLoader.addPart('process_type');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('process-type-detail', {
            parent: 'entity',
            url: '/process-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.process_type.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/process-type/process-type-detail.html',
                    controller: 'Process_typeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('process_type');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Process_type', function($stateParams, Process_type) {
                    return Process_type.get({id : $stateParams.id});
                }]
            }
        })
        .state('process-type.new', {
            parent: 'process-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/process-type/process-type-dialog.html',
                    controller: 'Process_typeDialogController',
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
                    $state.go('process-type', null, { reload: true });
                }, function() {
                    $state.go('process-type');
                });
            }]
        })
        .state('process-type.edit', {
            parent: 'process-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/process-type/process-type-dialog.html',
                    controller: 'Process_typeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Process_type', function(Process_type) {
                            return Process_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('process-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('process-type.delete', {
            parent: 'process-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/process-type/process-type-delete-dialog.html',
                    controller: 'Process_typeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Process_type', function(Process_type) {
                            return Process_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('process-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
