(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-storeroom-paybill', {
            parent: 'entity',
            url: '/freecom-storeroom-paybill?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_storeroom_paybill.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-storeroom-paybill/freecom-storeroom-paybills.html',
                    controller: 'Freecom_storeroom_paybillController',
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
                    $translatePartialLoader.addPart('freecom_storeroom_paybill');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-storeroom-paybill-detail', {
            parent: 'entity',
            url: '/freecom-storeroom-paybill/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_storeroom_paybill.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-storeroom-paybill/freecom-storeroom-paybill-detail.html',
                    controller: 'Freecom_storeroom_paybillDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_storeroom_paybill');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_storeroom_paybill', function($stateParams, Freecom_storeroom_paybill) {
                    return Freecom_storeroom_paybill.get({id : $stateParams.id});
                }]
            }
        })
        .state('freecom-storeroom-paybill.new', {
            parent: 'freecom-storeroom-paybill',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-storeroom-paybill/freecom-storeroom-paybill-dialog.html',
                    controller: 'Freecom_storeroom_paybillDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                type_operation: null,
                                employer_registration: null,
                                account_number: null,
                                total: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('freecom-storeroom-paybill', null, { reload: true });
                }, function() {
                    $state.go('freecom-storeroom-paybill');
                });
            }]
        })
        .state('freecom-storeroom-paybill.edit', {
            parent: 'freecom-storeroom-paybill',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-storeroom-paybill/freecom-storeroom-paybill-dialog.html',
                    controller: 'Freecom_storeroom_paybillDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_storeroom_paybill', function(Freecom_storeroom_paybill) {
                            return Freecom_storeroom_paybill.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-storeroom-paybill', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-storeroom-paybill.delete', {
            parent: 'freecom-storeroom-paybill',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-storeroom-paybill/freecom-storeroom-paybill-delete-dialog.html',
                    controller: 'Freecom_storeroom_paybillDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_storeroom_paybill', function(Freecom_storeroom_paybill) {
                            return Freecom_storeroom_paybill.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-storeroom-paybill', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
