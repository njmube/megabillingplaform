(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-desc-state', {
            parent: 'entity',
            url: '/com-desc-state?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_desc_state.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-desc-state/com-desc-states.html',
                    controller: 'Com_desc_stateController',
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
                    $translatePartialLoader.addPart('com_desc_state');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-desc-state-detail', {
            parent: 'entity',
            url: '/com-desc-state/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_desc_state.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-desc-state/com-desc-state-detail.html',
                    controller: 'Com_desc_stateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_desc_state');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_desc_state', function($stateParams, Com_desc_state) {
                    return Com_desc_state.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-desc-state.new', {
            parent: 'com-desc-state',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-desc-state/com-desc-state-dialog.html',
                    controller: 'Com_desc_stateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                street: null,
                                noext: null,
                                noint: null,
                                locale: null,
                                reference: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('com-desc-state', null, { reload: true });
                }, function() {
                    $state.go('com-desc-state');
                });
            }]
        })
        .state('com-desc-state.edit', {
            parent: 'com-desc-state',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-desc-state/com-desc-state-dialog.html',
                    controller: 'Com_desc_stateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_desc_state', function(Com_desc_state) {
                            return Com_desc_state.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-desc-state', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-desc-state.delete', {
            parent: 'com-desc-state',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-desc-state/com-desc-state-delete-dialog.html',
                    controller: 'Com_desc_stateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_desc_state', function(Com_desc_state) {
                            return Com_desc_state.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-desc-state', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
