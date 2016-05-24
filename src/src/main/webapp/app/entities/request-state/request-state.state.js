(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('request-state', {
            parent: 'entity',
            url: '/request-state?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.request_state.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/request-state/request-states.html',
                    controller: 'Request_stateController',
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
                    $translatePartialLoader.addPart('request_state');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('request-state-detail', {
            parent: 'entity',
            url: '/request-state/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.request_state.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/request-state/request-state-detail.html',
                    controller: 'Request_stateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('request_state');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Request_state', function($stateParams, Request_state) {
                    return Request_state.get({id : $stateParams.id});
                }]
            }
        })
        .state('request-state.new', {
            parent: 'request-state',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request-state/request-state-dialog.html',
                    controller: 'Request_stateDialogController',
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
                    $state.go('request-state', null, { reload: true });
                }, function() {
                    $state.go('request-state');
                });
            }]
        })
        .state('request-state.edit', {
            parent: 'request-state',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request-state/request-state-dialog.html',
                    controller: 'Request_stateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Request_state', function(Request_state) {
                            return Request_state.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('request-state', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('request-state.delete', {
            parent: 'request-state',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/request-state/request-state-delete-dialog.html',
                    controller: 'Request_stateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Request_state', function(Request_state) {
                            return Request_state.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('request-state', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
