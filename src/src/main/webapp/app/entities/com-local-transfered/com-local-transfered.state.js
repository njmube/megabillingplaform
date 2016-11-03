(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-local-transfered', {
            parent: 'entity',
            url: '/com-local-transfered?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_local_transfered.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-local-transfered/com-local-transfereds.html',
                    controller: 'Com_local_transferedController',
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
                    $translatePartialLoader.addPart('com_local_transfered');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-local-transfered-detail', {
            parent: 'entity',
            url: '/com-local-transfered/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_local_transfered.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-local-transfered/com-local-transfered-detail.html',
                    controller: 'Com_local_transferedDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_local_transfered');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_local_transfered', function($stateParams, Com_local_transfered) {
                    return Com_local_transfered.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-local-transfered.new', {
            parent: 'com-local-transfered',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-local-transfered/com-local-transfered-dialog.html',
                    controller: 'Com_local_transferedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                imploctransfered: null,
                                transferedrate: null,
                                amounttransfered: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-local-transfered', null, { reload: true });
                }, function() {
                    $state.go('com-local-transfered');
                });
            }]
        })
        .state('com-local-transfered.edit', {
            parent: 'com-local-transfered',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-local-transfered/com-local-transfered-dialog.html',
                    controller: 'Com_local_transferedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_local_transfered', function(Com_local_transfered) {
                            return Com_local_transfered.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-local-transfered', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-local-transfered.delete', {
            parent: 'com-local-transfered',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-local-transfered/com-local-transfered-delete-dialog.html',
                    controller: 'Com_local_transferedDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_local_transfered', function(Com_local_transfered) {
                            return Com_local_transfered.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-local-transfered', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
