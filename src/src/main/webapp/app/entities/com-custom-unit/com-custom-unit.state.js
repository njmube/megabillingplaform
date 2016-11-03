(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('com-custom-unit', {
            parent: 'entity',
            url: '/com-custom-unit?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_custom_unit.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-custom-unit/com-custom-units.html',
                    controller: 'Com_custom_unitController',
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
                    $translatePartialLoader.addPart('com_custom_unit');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('com-custom-unit-detail', {
            parent: 'entity',
            url: '/com-custom-unit/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.com_custom_unit.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/com-custom-unit/com-custom-unit-detail.html',
                    controller: 'Com_custom_unitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('com_custom_unit');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Com_custom_unit', function($stateParams, Com_custom_unit) {
                    return Com_custom_unit.get({id : $stateParams.id});
                }]
            }
        })
        .state('com-custom-unit.new', {
            parent: 'com-custom-unit',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-custom-unit/com-custom-unit-dialog.html',
                    controller: 'Com_custom_unitDialogController',
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
                    $state.go('com-custom-unit', null, { reload: true });
                }, function() {
                    $state.go('com-custom-unit');
                });
            }]
        })
        .state('com-custom-unit.edit', {
            parent: 'com-custom-unit',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-custom-unit/com-custom-unit-dialog.html',
                    controller: 'Com_custom_unitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Com_custom_unit', function(Com_custom_unit) {
                            return Com_custom_unit.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-custom-unit', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('com-custom-unit.delete', {
            parent: 'com-custom-unit',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/com-custom-unit/com-custom-unit-delete-dialog.html',
                    controller: 'Com_custom_unitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Com_custom_unit', function(Com_custom_unit) {
                            return Com_custom_unit.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('com-custom-unit', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
