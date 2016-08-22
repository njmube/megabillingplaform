(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-key-pediment', {
            parent: 'entity',
            url: '/c-key-pediment?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_key_pediment.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-key-pediment/c-key-pediments.html',
                    controller: 'C_key_pedimentController',
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
                    $translatePartialLoader.addPart('c_key_pediment');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-key-pediment-detail', {
            parent: 'entity',
            url: '/c-key-pediment/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_key_pediment.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-key-pediment/c-key-pediment-detail.html',
                    controller: 'C_key_pedimentDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_key_pediment');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_key_pediment', function($stateParams, C_key_pediment) {
                    return C_key_pediment.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('c-key-pediment.new', {
            parent: 'c-key-pediment',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-key-pediment/c-key-pediment-dialog.html',
                    controller: 'C_key_pedimentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                value: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-key-pediment', null, { reload: true });
                }, function() {
                    $state.go('c-key-pediment');
                });
            }]
        })
        .state('c-key-pediment.edit', {
            parent: 'c-key-pediment',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-key-pediment/c-key-pediment-dialog.html',
                    controller: 'C_key_pedimentDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_key_pediment', function(C_key_pediment) {
                            return C_key_pediment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-key-pediment', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-key-pediment.delete', {
            parent: 'c-key-pediment',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-key-pediment/c-key-pediment-delete-dialog.html',
                    controller: 'C_key_pedimentDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_key_pediment', function(C_key_pediment) {
                            return C_key_pediment.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-key-pediment', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
