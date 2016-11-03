(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-charge', {
            parent: 'entity',
            url: '/com-charge?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_charge.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-charge/com-charges.html',
                    controller: 'Com_chargeController',
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
                    $translatePartialLoader.addPart('com_charge');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-charge-detail', {
            parent: 'entity',
            url: '/com-charge/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_charge.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-charge/com-charge-detail.html',
                    controller: 'Com_chargeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_charge');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_charge', function($stateParams, Com_charge) {
                    return Com_charge.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-charge.new', {
            parent: 'com-charge',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-charge/com-charge-dialog.html',
                    controller: 'Com_chargeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                codecharge: null,
                                amount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-charge', null, { reload: true });
                }, function() {
                    $state.go('com-charge');
                });
            }]
        })
        .state('com-charge.edit', {
            parent: 'com-charge',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-charge/com-charge-dialog.html',
                    controller: 'Com_chargeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_charge', function(Com_charge) {
                            return Com_charge.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-charge', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-charge.delete', {
            parent: 'com-charge',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-charge/com-charge-delete-dialog.html',
                    controller: 'Com_chargeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_charge', function(Com_charge) {
                            return Com_charge.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-charge', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
