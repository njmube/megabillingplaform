(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('freecom-custom-unit', {
            parent: 'entity',
            url: '/freecom-custom-unit?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_custom_unit.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-custom-unit/freecom-custom-units.html',
                    controller: 'Freecom_custom_unitController',
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
                    $translatePartialLoader.addPart('freecom_custom_unit');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('freecom-custom-unit-detail', {
            parent: 'entity',
            url: '/freecom-custom-unit/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.freecom_custom_unit.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/freecom-custom-unit/freecom-custom-unit-detail.html',
                    controller: 'Freecom_custom_unitDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('freecom_custom_unit');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Freecom_custom_unit', function($stateParams, Freecom_custom_unit) {
                    return Freecom_custom_unit.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('freecom-custom-unit.new', {
            parent: 'freecom-custom-unit',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-custom-unit/freecom-custom-unit-dialog.html',
                    controller: 'Freecom_custom_unitDialogController',
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
                    $state.go('freecom-custom-unit', null, { reload: true });
                }, function() {
                    $state.go('freecom-custom-unit');
                });
            }]
        })
        .state('freecom-custom-unit.edit', {
            parent: 'freecom-custom-unit',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-custom-unit/freecom-custom-unit-dialog.html',
                    controller: 'Freecom_custom_unitDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Freecom_custom_unit', function(Freecom_custom_unit) {
                            return Freecom_custom_unit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-custom-unit', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('freecom-custom-unit.delete', {
            parent: 'freecom-custom-unit',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/freecom-custom-unit/freecom-custom-unit-delete-dialog.html',
                    controller: 'Freecom_custom_unitDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Freecom_custom_unit', function(Freecom_custom_unit) {
                            return Freecom_custom_unit.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('freecom-custom-unit', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
