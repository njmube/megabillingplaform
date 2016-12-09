(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-acquiring-data', {
            parent: 'entity',
            url: '/com-acquiring-data?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_acquiring_data.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-acquiring-data/com-acquiring-data.html',
                    controller: 'Com_acquiring_dataController',
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
                    $translatePartialLoader.addPart('com_acquiring_data');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-acquiring-data-detail', {
            parent: 'entity',
            url: '/com-acquiring-data/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_acquiring_data.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-acquiring-data/com-acquiring-data-detail.html',
                    controller: 'Com_acquiring_dataDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_acquiring_data');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_acquiring_data', function($stateParams, Com_acquiring_data) {
                    return Com_acquiring_data.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-acquiring-data.new', {
            parent: 'com-acquiring-data',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-acquiring-data/com-acquiring-data-dialog.html',
                    controller: 'Com_acquiring_dataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                coprosocconyugaie: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-acquiring-data', null, { reload: true });
                }, function() {
                    $state.go('com-acquiring-data');
                });
            }]
        })
        .state('com-acquiring-data.edit', {
            parent: 'com-acquiring-data',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-acquiring-data/com-acquiring-data-dialog.html',
                    controller: 'Com_acquiring_dataDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_acquiring_data', function(Com_acquiring_data) {
                            return Com_acquiring_data.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-acquiring-data', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-acquiring-data.delete', {
            parent: 'com-acquiring-data',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-acquiring-data/com-acquiring-data-delete-dialog.html',
                    controller: 'Com_acquiring_dataDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_acquiring_data', function(Com_acquiring_data) {
                            return Com_acquiring_data.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-acquiring-data', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
