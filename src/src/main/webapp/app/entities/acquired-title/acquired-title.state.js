(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('acquired-title', {
            parent: 'entity',
            url: '/acquired-title?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.acquired_title.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/acquired-title/acquired-titles.html',
                    controller: 'Acquired_titleController',
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
                    $translatePartialLoader.addPart('acquired_title');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('acquired-title-detail', {
            parent: 'entity',
            url: '/acquired-title/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.acquired_title.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/acquired-title/acquired-title-detail.html',
                    controller: 'Acquired_titleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('acquired_title');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Acquired_title', function($stateParams, Acquired_title) {
                    return Acquired_title.get({id : $stateParams.id});
                }]
            }
        })
        .state('acquired-title.new', {
            parent: 'acquired-title',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/acquired-title/acquired-title-dialog.html',
                    controller: 'Acquired_titleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('acquired-title', null, { reload: true });
                }, function() {
                    $state.go('acquired-title');
                });
            }]
        })
        .state('acquired-title.edit', {
            parent: 'acquired-title',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/acquired-title/acquired-title-dialog.html',
                    controller: 'Acquired_titleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Acquired_title', function(Acquired_title) {
                            return Acquired_title.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('acquired-title', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('acquired-title.delete', {
            parent: 'acquired-title',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/acquired-title/acquired-title-delete-dialog.html',
                    controller: 'Acquired_titleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Acquired_title', function(Acquired_title) {
                            return Acquired_title.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('acquired-title', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
