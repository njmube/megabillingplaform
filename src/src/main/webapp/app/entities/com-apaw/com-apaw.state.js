(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-apaw', {
            parent: 'entity',
            url: '/com-apaw?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_apaw.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-apaw/com-apaws.html',
                    controller: 'Com_apawController',
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
                    $translatePartialLoader.addPart('com_apaw');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-apaw-detail', {
            parent: 'entity',
            url: '/com-apaw/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_apaw.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-apaw/com-apaw-detail.html',
                    controller: 'Com_apawDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_apaw');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_apaw', function($stateParams, Com_apaw) {
                    return Com_apaw.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-apaw.new', {
            parent: 'com-apaw',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-apaw/com-apaw-dialog.html',
                    controller: 'Com_apawDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                others_well_type: null,
                                others_acquired_title: null,
                                subtotal: null,
                                iva: null,
                                date_acquisition: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-apaw', null, { reload: true });
                }, function() {
                    $state.go('com-apaw');
                });
            }]
        })
        .state('com-apaw.edit', {
            parent: 'com-apaw',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-apaw/com-apaw-dialog.html',
                    controller: 'Com_apawDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_apaw', function(Com_apaw) {
                            return Com_apaw.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-apaw', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-apaw.delete', {
            parent: 'com-apaw',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-apaw/com-apaw-delete-dialog.html',
                    controller: 'Com_apawDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_apaw', function(Com_apaw) {
                            return Com_apaw.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-apaw', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
