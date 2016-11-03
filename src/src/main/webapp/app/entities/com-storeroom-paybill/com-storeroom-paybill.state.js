(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-storeroom-paybill', {
            parent: 'entity',
            url: '/com-storeroom-paybill?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_storeroom_paybill.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-storeroom-paybill/com-storeroom-paybills.html',
                    controller: 'Com_storeroom_paybillController',
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
                    $translatePartialLoader.addPart('com_storeroom_paybill');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-storeroom-paybill-detail', {
            parent: 'entity',
            url: '/com-storeroom-paybill/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_storeroom_paybill.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-storeroom-paybill/com-storeroom-paybill-detail.html',
                    controller: 'Com_storeroom_paybillDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_storeroom_paybill');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_storeroom_paybill', function($stateParams, Com_storeroom_paybill) {
                    return Com_storeroom_paybill.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-storeroom-paybill.new', {
            parent: 'com-storeroom-paybill',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-storeroom-paybill/com-storeroom-paybill-dialog.html',
                    controller: 'Com_storeroom_paybillDialogController',
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
                    $state.go('com-storeroom-paybill', null, { reload: true });
                }, function() {
                    $state.go('com-storeroom-paybill');
                });
            }]
        })
        .state('com-storeroom-paybill.edit', {
            parent: 'com-storeroom-paybill',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-storeroom-paybill/com-storeroom-paybill-dialog.html',
                    controller: 'Com_storeroom_paybillDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_storeroom_paybill', function(Com_storeroom_paybill) {
                            return Com_storeroom_paybill.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-storeroom-paybill', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-storeroom-paybill.delete', {
            parent: 'com-storeroom-paybill',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-storeroom-paybill/com-storeroom-paybill-delete-dialog.html',
                    controller: 'Com_storeroom_paybillDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_storeroom_paybill', function(Com_storeroom_paybill) {
                            return Com_storeroom_paybill.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-storeroom-paybill', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
