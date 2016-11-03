(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-legends', {
            parent: 'entity',
            url: '/com-legends?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_legends.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-legends/com-legends.html',
                    controller: 'Com_legendsController',
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
                    $translatePartialLoader.addPart('com_legends');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-legends-detail', {
            parent: 'entity',
            url: '/com-legends/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_legends.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-legends/com-legends-detail.html',
                    controller: 'Com_legendsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_legends');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_legends', function($stateParams, Com_legends) {
                    return Com_legends.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-legends.new', {
            parent: 'com-legends',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-legends/com-legends-dialog.html',
                    controller: 'Com_legendsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                tax_provision: null,
                                norm: null,
                                text_legend: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-legends', null, { reload: true });
                }, function() {
                    $state.go('com-legends');
                });
            }]
        })
        .state('com-legends.edit', {
            parent: 'com-legends',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-legends/com-legends-dialog.html',
                    controller: 'Com_legendsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_legends', function(Com_legends) {
                            return Com_legends.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-legends', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-legends.delete', {
            parent: 'com-legends',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-legends/com-legends-delete-dialog.html',
                    controller: 'Com_legendsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_legends', function(Com_legends) {
                            return Com_legends.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-legends', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
