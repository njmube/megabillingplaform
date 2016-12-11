(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('c-type-estate', {
            parent: 'entity',
            url: '/c-type-estate?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_type_estate.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-type-estate/c-type-estates.html',
                    controller: 'C_type_estateController',
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
                    $translatePartialLoader.addPart('c_type_estate');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('c-type-estate-detail', {
            parent: 'entity',
            url: '/c-type-estate/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.c_type_estate.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/c-type-estate/c-type-estate-detail.html',
                    controller: 'C_type_estateDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('c_type_estate');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'C_type_estate', function($stateParams, C_type_estate) {
                    return C_type_estate.get({id : $stateParams.id});
                }]
            }
        })
        .state('c-type-estate.new', {
            parent: 'c-type-estate',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-type-estate/c-type-estate-dialog.html',
                    controller: 'C_type_estateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('c-type-estate', null, { reload: true });
                }, function() {
                    $state.go('c-type-estate');
                });
            }]
        })
        .state('c-type-estate.edit', {
            parent: 'c-type-estate',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-type-estate/c-type-estate-dialog.html',
                    controller: 'C_type_estateDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['C_type_estate', function(C_type_estate) {
                            return C_type_estate.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-type-estate', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('c-type-estate.delete', {
            parent: 'c-type-estate',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/c-type-estate/c-type-estate-delete-dialog.html',
                    controller: 'C_type_estateDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['C_type_estate', function(C_type_estate) {
                            return C_type_estate.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('c-type-estate', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
