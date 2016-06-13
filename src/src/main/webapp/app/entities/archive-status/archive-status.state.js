(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('archive-status', {
            parent: 'entity',
            url: '/archive-status?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.archive_status.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/archive-status/archive-statuses.html',
                    controller: 'Archive_statusController',
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
                    $translatePartialLoader.addPart('archive_status');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('archive-status-detail', {
            parent: 'entity',
            url: '/archive-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.archive_status.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/archive-status/archive-status-detail.html',
                    controller: 'Archive_statusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('archive_status');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Archive_status', function($stateParams, Archive_status) {
                    return Archive_status.get({id : $stateParams.id});
                }]
            }
        })
        .state('archive-status.new', {
            parent: 'archive-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/archive-status/archive-status-dialog.html',
                    controller: 'Archive_statusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                date: null,
                                date1: null,
                                first_surname: null,
                                second_surname: null,
                                date_born: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('archive-status', null, { reload: true });
                }, function() {
                    $state.go('archive-status');
                });
            }]
        })
        .state('archive-status.edit', {
            parent: 'archive-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/archive-status/archive-status-dialog.html',
                    controller: 'Archive_statusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Archive_status', function(Archive_status) {
                            return Archive_status.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('archive-status', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('archive-status.delete', {
            parent: 'archive-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/archive-status/archive-status-delete-dialog.html',
                    controller: 'Archive_statusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Archive_status', function(Archive_status) {
                            return Archive_status.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('archive-status', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
