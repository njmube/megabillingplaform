(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-determined', {
            parent: 'entity',
            url: '/com-determined?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_determined.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-determined/com-determineds.html',
                    controller: 'Com_determinedController',
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
                    $translatePartialLoader.addPart('com_determined');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-determined-detail', {
            parent: 'entity',
            url: '/com-determined/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_determined.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-determined/com-determined-detail.html',
                    controller: 'Com_determinedDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_determined');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_determined', function($stateParams, Com_determined) {
                    return Com_determined.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-determined.new', {
            parent: 'com-determined',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-determined/com-determined-dialog.html',
                    controller: 'Com_determinedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                rate: null,
                                amount: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-determined', null, { reload: true });
                }, function() {
                    $state.go('com-determined');
                });
            }]
        })
        .state('com-determined.edit', {
            parent: 'com-determined',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-determined/com-determined-dialog.html',
                    controller: 'Com_determinedDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_determined', function(Com_determined) {
                            return Com_determined.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-determined', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-determined.delete', {
            parent: 'com-determined',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-determined/com-determined-delete-dialog.html',
                    controller: 'Com_determinedDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_determined', function(Com_determined) {
                            return Com_determined.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-determined', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
