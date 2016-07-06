(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('type-taxpayer', {
            parent: 'entity',
            url: '/type-taxpayer?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.type_taxpayer.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-taxpayer/type-taxpayers.html',
                    controller: 'Type_taxpayerController',
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
                    $translatePartialLoader.addPart('type_taxpayer');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('type-taxpayer-detail', {
            parent: 'entity',
            url: '/type-taxpayer/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.type_taxpayer.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/type-taxpayer/type-taxpayer-detail.html',
                    controller: 'Type_taxpayerDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('type_taxpayer');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Type_taxpayer', function($stateParams, Type_taxpayer) {
                    return Type_taxpayer.get({id : $stateParams.id});
                }]
            }
        })
        .state('type-taxpayer.new', {
            parent: 'type-taxpayer',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-taxpayer/type-taxpayer-dialog.html',
                    controller: 'Type_taxpayerDialogController',
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
                    $state.go('type-taxpayer', null, { reload: true });
                }, function() {
                    $state.go('type-taxpayer');
                });
            }]
        })
        .state('type-taxpayer.edit', {
            parent: 'type-taxpayer',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-taxpayer/type-taxpayer-dialog.html',
                    controller: 'Type_taxpayerDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Type_taxpayer', function(Type_taxpayer) {
                            return Type_taxpayer.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-taxpayer', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('type-taxpayer.delete', {
            parent: 'type-taxpayer',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/type-taxpayer/type-taxpayer-delete-dialog.html',
                    controller: 'Type_taxpayerDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Type_taxpayer', function(Type_taxpayer) {
                            return Type_taxpayer.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('type-taxpayer', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
