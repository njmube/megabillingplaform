(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-incoterm', {
            parent: 'entity',
            url: '/com-incoterm?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_incoterm.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-incoterm/com-incoterms.html',
                    controller: 'Com_incotermController',
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
                    $translatePartialLoader.addPart('com_incoterm');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-incoterm-detail', {
            parent: 'entity',
            url: '/com-incoterm/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_incoterm.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-incoterm/com-incoterm-detail.html',
                    controller: 'Com_incotermDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_incoterm');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_incoterm', function($stateParams, Com_incoterm) {
                    return Com_incoterm.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-incoterm.new', {
            parent: 'com-incoterm',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-incoterm/com-incoterm-dialog.html',
                    controller: 'Com_incotermDialogController',
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
                    $state.go('com-incoterm', null, { reload: true });
                }, function() {
                    $state.go('com-incoterm');
                });
            }]
        })
        .state('com-incoterm.edit', {
            parent: 'com-incoterm',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-incoterm/com-incoterm-dialog.html',
                    controller: 'Com_incotermDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_incoterm', function(Com_incoterm) {
                            return Com_incoterm.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-incoterm', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-incoterm.delete', {
            parent: 'com-incoterm',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-incoterm/com-incoterm-delete-dialog.html',
                    controller: 'Com_incotermDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_incoterm', function(Com_incoterm) {
                            return Com_incoterm.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-incoterm', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
