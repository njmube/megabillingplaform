(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-state-event', {
            parent: 'entity',
            url: '/c-state-event?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_state_event.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-state-event/c-state-events.html',
                    controller: 'C_state_eventController',
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
                    $translatePartialLoader.addPart('c_state_event');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-state-event-detail', {
            parent: 'entity',
            url: '/c-state-event/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_state_event.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-state-event/c-state-event-detail.html',
                    controller: 'C_state_eventDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_state_event');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_state_event', function($stateParams, C_state_event) {
                    return C_state_event.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-state-event.new', {
            parent: 'c-state-event',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-state-event/c-state-event-dialog.html',
                    controller: 'C_state_eventDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-state-event', null, { reload: true });
                }, function() {
                    $state.go('c-state-event');
                });
            }]
        })
        .state('c-state-event.edit', {
            parent: 'c-state-event',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-state-event/c-state-event-dialog.html',
                    controller: 'C_state_eventDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_state_event', function(C_state_event) {
                            return C_state_event.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-state-event', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-state-event.delete', {
            parent: 'c-state-event',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-state-event/c-state-event-delete-dialog.html',
                    controller: 'C_state_eventDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_state_event', function(C_state_event) {
                            return C_state_event.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-state-event', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
