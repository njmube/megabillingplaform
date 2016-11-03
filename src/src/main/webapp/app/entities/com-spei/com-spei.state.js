(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-spei', {
            parent: 'entity',
            url: '/com-spei?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_spei.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-spei/com-speis.html',
                    controller: 'Com_speiController',
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
                    $translatePartialLoader.addPart('com_spei');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-spei-detail', {
            parent: 'entity',
            url: '/com-spei/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_spei.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-spei/com-spei-detail.html',
                    controller: 'Com_speiDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_spei');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_spei', function($stateParams, Com_spei) {
                    return Com_spei.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-spei.new', {
            parent: 'com-spei',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-spei/com-spei-dialog.html',
                    controller: 'Com_speiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-spei', null, { reload: true });
                }, function() {
                    $state.go('com-spei');
                });
            }]
        })
        .state('com-spei.edit', {
            parent: 'com-spei',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-spei/com-spei-dialog.html',
                    controller: 'Com_speiDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_spei', function(Com_spei) {
                            return Com_spei.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-spei', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-spei.delete', {
            parent: 'com-spei',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-spei/com-spei-delete-dialog.html',
                    controller: 'Com_speiDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_spei', function(Com_spei) {
                            return Com_spei.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-spei', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
