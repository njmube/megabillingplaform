(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-local-retentions', {
            parent: 'entity',
            url: '/com-local-retentions?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_local_retentions.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-local-retentions/com-local-retentions.html',
                    controller: 'Com_local_retentionsController',
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
                    $translatePartialLoader.addPart('com_local_retentions');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-local-retentions-detail', {
            parent: 'entity',
            url: '/com-local-retentions/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_local_retentions.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-local-retentions/com-local-retentions-detail.html',
                    controller: 'Com_local_retentionsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_local_retentions');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_local_retentions', function($stateParams, Com_local_retentions) {
                    return Com_local_retentions.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-local-retentions.new', {
            parent: 'com-local-retentions',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-local-retentions/com-local-retentions-dialog.html',
                    controller: 'Com_local_retentionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                implocretentions: null,
                                retentionrate: null,
                                amountretentions: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-local-retentions', null, { reload: true });
                }, function() {
                    $state.go('com-local-retentions');
                });
            }]
        })
        .state('com-local-retentions.edit', {
            parent: 'com-local-retentions',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-local-retentions/com-local-retentions-dialog.html',
                    controller: 'Com_local_retentionsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_local_retentions', function(Com_local_retentions) {
                            return Com_local_retentions.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-local-retentions', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-local-retentions.delete', {
            parent: 'com-local-retentions',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-local-retentions/com-local-retentions-delete-dialog.html',
                    controller: 'Com_local_retentionsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_local_retentions', function(Com_local_retentions) {
                            return Com_local_retentions.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-local-retentions', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
