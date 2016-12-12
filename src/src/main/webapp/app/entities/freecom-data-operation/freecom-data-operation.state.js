(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-data-operation', {
            parent: 'entity',
            url: '/freecom-data-operation?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_data_operation.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-data-operation/freecom-data-operations.html',
                    controller: 'Freecom_data_operationController',
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
                    $translatePartialLoader.addPart('freecom_data_operation');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-data-operation-detail', {
            parent: 'entity',
            url: '/freecom-data-operation/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_data_operation.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-data-operation/freecom-data-operation-detail.html',
                    controller: 'Freecom_data_operationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_data_operation');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_data_operation', function($stateParams, Freecom_data_operation) {
                    return Freecom_data_operation.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-data-operation.new', {
            parent: 'freecom-data-operation',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-data-operation/freecom-data-operation-dialog.html',
                    controller: 'Freecom_data_operationDialogController',
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
                    $state.go('freecom-data-operation', null, { reload: true });
                }, function() {
                    $state.go('freecom-data-operation');
                });
            }]
        })
        .state('freecom-data-operation.edit', {
            parent: 'freecom-data-operation',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-data-operation/freecom-data-operation-dialog.html',
                    controller: 'Freecom_data_operationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_data_operation', function(Freecom_data_operation) {
                            return Freecom_data_operation.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-data-operation', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-data-operation.delete', {
            parent: 'freecom-data-operation',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-data-operation/freecom-data-operation-delete-dialog.html',
                    controller: 'Freecom_data_operationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_data_operation', function(Freecom_data_operation) {
                            return Freecom_data_operation.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-data-operation', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
