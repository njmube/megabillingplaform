(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-donees', {
            parent: 'entity',
            url: '/com-donees?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_donees.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-donees/com-donees.html',
                    controller: 'Com_doneesController',
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
                    $translatePartialLoader.addPart('com_donees');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-donees-detail', {
            parent: 'entity',
            url: '/com-donees/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_donees.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-donees/com-donees-detail.html',
                    controller: 'Com_doneesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_donees');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_donees', function($stateParams, Com_donees) {
                    return Com_donees.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-donees.new', {
            parent: 'com-donees',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-donees/com-donees-dialog.html',
                    controller: 'Com_doneesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                version: null,
                                no_authorization: null,
                                date_authorization: null,
                                legend: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-donees', null, { reload: true });
                }, function() {
                    $state.go('com-donees');
                });
            }]
        })
        .state('com-donees.edit', {
            parent: 'com-donees',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-donees/com-donees-dialog.html',
                    controller: 'Com_doneesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_donees', function(Com_donees) {
                            return Com_donees.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-donees', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-donees.delete', {
            parent: 'com-donees',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-donees/com-donees-delete-dialog.html',
                    controller: 'Com_doneesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_donees', function(Com_donees) {
                            return Com_donees.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-donees', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
