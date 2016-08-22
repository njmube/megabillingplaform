(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-type-operation-ce', {
            parent: 'entity',
            url: '/c-type-operation-ce?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_type_operation_ce.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-type-operation-ce/c-type-operation-ces.html',
                    controller: 'C_type_operation_ceController',
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
                    $translatePartialLoader.addPart('c_type_operation_ce');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-type-operation-ce-detail', {
            parent: 'entity',
            url: '/c-type-operation-ce/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_type_operation_ce.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-type-operation-ce/c-type-operation-ce-detail.html',
                    controller: 'C_type_operation_ceDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_type_operation_ce');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_type_operation_ce', function($stateParams, C_type_operation_ce) {
                    return C_type_operation_ce.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('c-type-operation-ce.new', {
            parent: 'c-type-operation-ce',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-type-operation-ce/c-type-operation-ce-dialog.html',
                    controller: 'C_type_operation_ceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                value: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-type-operation-ce', null, { reload: true });
                }, function() {
                    $state.go('c-type-operation-ce');
                });
            }]
        })
        .state('c-type-operation-ce.edit', {
            parent: 'c-type-operation-ce',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-type-operation-ce/c-type-operation-ce-dialog.html',
                    controller: 'C_type_operation_ceDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_type_operation_ce', function(C_type_operation_ce) {
                            return C_type_operation_ce.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-type-operation-ce', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-type-operation-ce.delete', {
            parent: 'c-type-operation-ce',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-type-operation-ce/c-type-operation-ce-delete-dialog.html',
                    controller: 'C_type_operation_ceDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_type_operation_ce', function(C_type_operation_ce) {
                            return C_type_operation_ce.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-type-operation-ce', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
