(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-payer', {
            parent: 'entity',
            url: '/com-payer?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_payer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-payer/com-payers.html',
                    controller: 'Com_payerController',
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
                    $translatePartialLoader.addPart('com_payer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-payer-detail', {
            parent: 'entity',
            url: '/com-payer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_payer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-payer/com-payer-detail.html',
                    controller: 'Com_payerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_payer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_payer', function($stateParams, Com_payer) {
                    return Com_payer.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-payer.new', {
            parent: 'com-payer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-payer/com-payer-dialog.html',
                    controller: 'Com_payerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                emitter_bank: null,
                                name: null,
                                type_account: null,
                                account: null,
                                rfc: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-payer', null, { reload: true });
                }, function() {
                    $state.go('com-payer');
                });
            }]
        })
        .state('com-payer.edit', {
            parent: 'com-payer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-payer/com-payer-dialog.html',
                    controller: 'Com_payerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_payer', function(Com_payer) {
                            return Com_payer.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-payer', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-payer.delete', {
            parent: 'com-payer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-payer/com-payer-delete-dialog.html',
                    controller: 'Com_payerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_payer', function(Com_payer) {
                            return Com_payer.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-payer', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
