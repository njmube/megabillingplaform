(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-data-operation', {
            parent: 'entity',
            url: '/com-data-operation?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_data_operation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-data-operation/com-data-operations.html',
                    controller: 'Com_data_operationController',
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
                    $translatePartialLoader.addPart('com_data_operation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-data-operation-detail', {
            parent: 'entity',
            url: '/com-data-operation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_data_operation.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-data-operation/com-data-operation-detail.html',
                    controller: 'Com_data_operationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_data_operation');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_data_operation', function($stateParams, Com_data_operation) {
                    return Com_data_operation.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-data-operation.new', {
            parent: 'com-data-operation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-data-operation/com-data-operation-dialog.html',
                    controller: 'Com_data_operationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                notarialinstrument: null,
                                dateinstnotarial: null,
                                amountofoperation: null,
                                subtotal: null,
                                iva: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-data-operation', null, { reload: true });
                }, function() {
                    $state.go('com-data-operation');
                });
            }]
        })
        .state('com-data-operation.edit', {
            parent: 'com-data-operation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-data-operation/com-data-operation-dialog.html',
                    controller: 'Com_data_operationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_data_operation', function(Com_data_operation) {
                            return Com_data_operation.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-data-operation', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-data-operation.delete', {
            parent: 'com-data-operation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-data-operation/com-data-operation-delete-dialog.html',
                    controller: 'Com_data_operationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_data_operation', function(Com_data_operation) {
                            return Com_data_operation.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-data-operation', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
