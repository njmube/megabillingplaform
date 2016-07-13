(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('well-type', {
            parent: 'entity',
            url: '/well-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.well_type.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/well-type/well-types.html',
                    controller: 'Well_typeController',
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
                    $translatePartialLoader.addPart('well_type');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('well-type-detail', {
            parent: 'entity',
            url: '/well-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.well_type.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/well-type/well-type-detail.html',
                    controller: 'Well_typeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('well_type');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Well_type', function($stateParams, Well_type) {
                    return Well_type.get({id : $stateParams.id});
                }]
            }
        })
        .state('well-type.new', {
            parent: 'well-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/well-type/well-type-dialog.html',
                    controller: 'Well_typeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('well-type', null, { reload: true });
                }, function() {
                    $state.go('well-type');
                });
            }]
        })
        .state('well-type.edit', {
            parent: 'well-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/well-type/well-type-dialog.html',
                    controller: 'Well_typeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Well_type', function(Well_type) {
                            return Well_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('well-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('well-type.delete', {
            parent: 'well-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/well-type/well-type-delete-dialog.html',
                    controller: 'Well_typeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Well_type', function(Well_type) {
                            return Well_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('well-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
