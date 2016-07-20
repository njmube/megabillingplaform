(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('audit-event-type', {
            parent: 'entity',
            url: '/audit-event-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.audit_event_type.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/audit-event-type/audit-event-types.html',
                    controller: 'Audit_event_typeController',
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
                    $translatePartialLoader.addPart('audit_event_type');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('audit-event-type-detail', {
            parent: 'entity',
            url: '/audit-event-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.audit_event_type.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/audit-event-type/audit-event-type-detail.html',
                    controller: 'Audit_event_typeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('audit_event_type');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Audit_event_type', function($stateParams, Audit_event_type) {
                    return Audit_event_type.get({id : $stateParams.id});
                }]
            }
        })
        .state('audit-event-type.new', {
            parent: 'audit-event-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/audit-event-type/audit-event-type-dialog.html',
                    controller: 'Audit_event_typeDialogController',
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
                    $state.go('audit-event-type', null, { reload: true });
                }, function() {
                    $state.go('audit-event-type');
                });
            }]
        })
        .state('audit-event-type.edit', {
            parent: 'audit-event-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/audit-event-type/audit-event-type-dialog.html',
                    controller: 'Audit_event_typeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Audit_event_type', function(Audit_event_type) {
                            return Audit_event_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('audit-event-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('audit-event-type.delete', {
            parent: 'audit-event-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/audit-event-type/audit-event-type-delete-dialog.html',
                    controller: 'Audit_event_typeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Audit_event_type', function(Audit_event_type) {
                            return Audit_event_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('audit-event-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
