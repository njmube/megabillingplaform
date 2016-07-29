(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-type-operation', {
            parent: 'entity',
            url: '/c-type-operation?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_type_operation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-type-operation/c-type-operations.html',
                    controller: 'C_type_operationController',
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
                    $translatePartialLoader.addPart('c_type_operation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-type-operation-detail', {
            parent: 'entity',
            url: '/c-type-operation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_type_operation.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-type-operation/c-type-operation-detail.html',
                    controller: 'C_type_operationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_type_operation');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_type_operation', function($stateParams, C_type_operation) {
                    return C_type_operation.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-type-operation.new', {
            parent: 'c-type-operation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-type-operation/c-type-operation-dialog.html',
                    controller: 'C_type_operationDialogController',
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
                    $state.go('c-type-operation', null, { reload: true });
                }, function() {
                    $state.go('c-type-operation');
                });
            }]
        })
        .state('c-type-operation.edit', {
            parent: 'c-type-operation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-type-operation/c-type-operation-dialog.html',
                    controller: 'C_type_operationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_type_operation', function(C_type_operation) {
                            return C_type_operation.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-type-operation', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-type-operation.delete', {
            parent: 'c-type-operation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-type-operation/c-type-operation-delete-dialog.html',
                    controller: 'C_type_operationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_type_operation', function(C_type_operation) {
                            return C_type_operation.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-type-operation', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
