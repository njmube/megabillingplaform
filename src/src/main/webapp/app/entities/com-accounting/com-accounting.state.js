(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-accounting', {
            parent: 'entity',
            url: '/com-accounting?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_accounting.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-accounting/com-accountings.html',
                    controller: 'Com_accountingController',
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
                    $translatePartialLoader.addPart('com_accounting');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-accounting-detail', {
            parent: 'entity',
            url: '/com-accounting/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_accounting.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-accounting/com-accounting-detail.html',
                    controller: 'Com_accountingDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_accounting');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_accounting', function($stateParams, Com_accounting) {
                    return Com_accounting.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-accounting.new', {
            parent: 'com-accounting',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-accounting/com-accounting-dialog.html',
                    controller: 'Com_accountingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                keyaccounting: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-accounting', null, { reload: true });
                }, function() {
                    $state.go('com-accounting');
                });
            }]
        })
        .state('com-accounting.edit', {
            parent: 'com-accounting',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-accounting/com-accounting-dialog.html',
                    controller: 'Com_accountingDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_accounting', function(Com_accounting) {
                            return Com_accounting.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-accounting', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-accounting.delete', {
            parent: 'com-accounting',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-accounting/com-accounting-delete-dialog.html',
                    controller: 'Com_accountingDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_accounting', function(Com_accounting) {
                            return Com_accounting.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-accounting', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
