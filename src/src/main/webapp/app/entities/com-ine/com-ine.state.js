(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-ine', {
            parent: 'entity',
            url: '/com-ine?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_ine.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-ine/com-ines.html',
                    controller: 'Com_ineController',
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
                    $translatePartialLoader.addPart('com_ine');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-ine-detail', {
            parent: 'entity',
            url: '/com-ine/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_ine.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-ine/com-ine-detail.html',
                    controller: 'Com_ineDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_ine');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_ine', function($stateParams, Com_ine) {
                    return Com_ine.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-ine.new', {
            parent: 'com-ine',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-ine/com-ine-dialog.html',
                    controller: 'Com_ineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                ident: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-ine', null, { reload: true });
                }, function() {
                    $state.go('com-ine');
                });
            }]
        })
        .state('com-ine.edit', {
            parent: 'com-ine',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-ine/com-ine-dialog.html',
                    controller: 'Com_ineDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_ine', function(Com_ine) {
                            return Com_ine.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-ine', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-ine.delete', {
            parent: 'com-ine',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-ine/com-ine-delete-dialog.html',
                    controller: 'Com_ineDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_ine', function(Com_ine) {
                            return Com_ine.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-ine', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
