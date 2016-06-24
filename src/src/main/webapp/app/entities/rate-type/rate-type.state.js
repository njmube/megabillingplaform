(function() {
    'use strict';

    angular
        .module('megabillingplatformApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('rate-type', {
            parent: 'entity',
            url: '/rate-type?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.rate_type.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rate-type/rate-types.html',
                    controller: 'Rate_typeController',
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
                    $translatePartialLoader.addPart('rate_type');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('rate-type-detail', {
            parent: 'entity',
            url: '/rate-type/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'megabillingplatformApp.rate_type.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rate-type/rate-type-detail.html',
                    controller: 'Rate_typeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('rate_type');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Rate_type', function($stateParams, Rate_type) {
                    return Rate_type.get({id : $stateParams.id});
                }]
            }
        })
        .state('rate-type.new', {
            parent: 'rate-type',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rate-type/rate-type-dialog.html',
                    controller: 'Rate_typeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                value: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('rate-type', null, { reload: true });
                }, function() {
                    $state.go('rate-type');
                });
            }]
        })
        .state('rate-type.edit', {
            parent: 'rate-type',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rate-type/rate-type-dialog.html',
                    controller: 'Rate_typeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Rate_type', function(Rate_type) {
                            return Rate_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('rate-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rate-type.delete', {
            parent: 'rate-type',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rate-type/rate-type-delete-dialog.html',
                    controller: 'Rate_typeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Rate_type', function(Rate_type) {
                            return Rate_type.get({id : $stateParams.id});
                        }]
                    }
                }).result.then(function() {
                    $state.go('rate-type', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
